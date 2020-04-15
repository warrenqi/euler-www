package euler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rocksdb.BloomFilter;
import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.CompressionType;
import org.rocksdb.DBOptions;
import org.rocksdb.Filter;
import org.rocksdb.Options;
import org.rocksdb.RateLimiter;
import org.rocksdb.ReadOptions;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.rocksdb.Statistics;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;

import euler.dbs.RocksCols;
import euler.dbs.RocksDBUtils;

public class TestRocksDB
{
    static String db_path;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        RocksDB.loadLibrary();

        File tempDirectory = FileUtils.getTempDirectory();
        try
        {
            FileStore fileStore = Files.getFileStore(tempDirectory.toPath());
            long freespace = fileStore.getUsableSpace();
            System.out.println("freespace on /tmp = " + freespace / FileUtils.ONE_GB);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        db_path = FilenameUtils.concat(tempDirectory.toPath().toString(), "euler_rocsdb_" + System.currentTimeMillis());
        System.out.println("db_path = " + db_path);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        try
        {
            FileUtils.deleteDirectory(new File(db_path));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testColumnFamily()
    {
        String dummy_cf = new String(RocksCols.DEFAULT.cName);
        final List<ColumnFamilyDescriptor> columnFamilyDescriptors = RocksDBUtils.knownColumnDescriptors();
        try (final Options options = new Options();
                final Filter bloomFilter = new BloomFilter(10);
                final ReadOptions readOptions = new ReadOptions().setFillCache(false);
                final Statistics stats = new Statistics();
                final RateLimiter rateLimiter = new RateLimiter(10000000, 10000, 10))
        {
            try
            {
                RocksDBUtils.setDBOptions(options, stats, rateLimiter);
            } catch (final IllegalArgumentException e)
            {
                assert (false);
            }
            // critical for column family
            options.setCreateMissingColumnFamilies(true);
            List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();

            try (final RocksDB db = RocksDB.open(new DBOptions(options), db_path, columnFamilyDescriptors,
                                                 columnFamilyHandles))
            {
                assertEquals(RocksCols.values().length, columnFamilyHandles.size());
                assertEquals(dummy_cf, new String(columnFamilyHandles.get(RocksCols.DEFAULT.ordinal()).getName()));

                // put and get from non-default column family
                db.put(columnFamilyHandles.get(RocksCols.DEFAULT.ordinal()), new WriteOptions(), "key".getBytes(),
                       "value".getBytes());

                // atomic write
                try (final WriteBatch wb = new WriteBatch())
                {
                    wb.put(columnFamilyHandles.get(0), "key2".getBytes(), "value2".getBytes());
                    wb.put(columnFamilyHandles.get(0), "key3".getBytes(), "value3".getBytes());
                    wb.delete(columnFamilyHandles.get(0), "key3".getBytes());
                    db.write(new WriteOptions(), wb);
                }
                assertEquals("value2", new String(db.get(columnFamilyHandles.get(0), "key2".getBytes())));
            } catch (RocksDBException e)
            {
                e.printStackTrace();
                assert (false);
            } finally
            {
                for (final ColumnFamilyHandle columnFamilyHandle : columnFamilyHandles)
                {
                    columnFamilyHandle.close();
                }
            }
        }
        System.out.println("passed column family test");
    }

    @Test
    public void testReadWrite()
    {
        try (final Options options = new Options();
                final Filter bloomFilter = new BloomFilter(10);
                final ReadOptions readOptions = new ReadOptions().setFillCache(false);
                final Statistics stats = new Statistics();
                final RateLimiter rateLimiter = new RateLimiter(10000000, 10000, 10))
        {
            try
            {
                RocksDBUtils.setDBOptions(options, stats, rateLimiter);
            } catch (final IllegalArgumentException e)
            {
                assert (false);
            }
            assert (options.createIfMissing() == true);
            assert (options.compressionType() == CompressionType.SNAPPY_COMPRESSION);
            assert (options.memTableFactoryName().equals("SkipListFactory"));
            assert (options.tableFactoryName().equals("BlockBasedTable"));

            try (final RocksDB db = RocksDB.open(options, db_path))
            {
                db.put("hello".getBytes(), "world".getBytes());

                byte[] value = db.get("hello".getBytes());
                assert ("world".equals(new String(value)));

                String str = db.getProperty("rocksdb.stats");
                assert (str != null && !str.equals(""));
                System.out.format("Get('hello') = %s\n", new String(value));

                // write batch test
                try (final WriteOptions writeOpt = new WriteOptions())
                {
                    for (int i = 10; i <= 19; ++i)
                    {
                        try (final WriteBatch batch = new WriteBatch())
                        {
                            for (int j = 10; j <= 19; ++j)
                            {
                                batch.put(String.format("%dx%d", i, j).getBytes(),
                                          String.format("%d", i * j).getBytes());
                            }
                            db.write(writeOpt, batch);
                        }
                    }
                }
                for (int i = 10; i <= 19; ++i)
                {
                    for (int j = 10; j <= 19; ++j)
                    {
                        assert (new String(db.get(String.format("%dx%d", i, j).getBytes())).equals(String.format("%d", i
                                * j)));
                        System.out.format("%s ", new String(db.get(String.format("%dx%d", i, j).getBytes())));
                    }
                    System.out.println("");
                }
                System.out.println("passed write batch test");

                value = db.get("10x10".getBytes());
                assert (value != null);
                value = db.get(readOptions, "world".getBytes());
                assert (value == null);

                // iterators test

                try (final RocksIterator iterator = db.newIterator())
                {
                    boolean seekToFirstPassed = false;
                    int i = 0;
                    for (iterator.seekToFirst(); iterator.isValid(); iterator.next())
                    {
                        iterator.status();
                        assert (iterator.key() != null);
                        assert (iterator.value() != null);
                        seekToFirstPassed = true;
                        i++;
                    }
                    if (seekToFirstPassed)
                    {
                        System.out.println("iterator seekToFirst tests passed. with total " + i + " number of keys");
                    }

                    boolean seekToLastPassed = false;
                    for (iterator.seekToLast(); iterator.isValid(); iterator.prev())
                    {
                        iterator.status();
                        assert (iterator.key() != null);
                        assert (iterator.value() != null);
                        seekToLastPassed = true;
                    }

                    if (seekToLastPassed)
                    {
                        System.out.println("iterator seekToLastPassed tests passed.");
                    }

                    iterator.seekToFirst();
                    iterator.seek(iterator.key());
                    assert (iterator.key() != null);
                    assert (iterator.value() != null);

                    System.out.println("iterator seek test passed.");

                }
            } catch (final RocksDBException e)
            {
                System.out.format("[ERROR] caught the unexpected exception -- %s\n", e);
                assert (false);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
