package euler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rocksdb.Options;
import org.rocksdb.RateLimiter;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.Statistics;

import euler.dbs.FibDatabaseInMemory;
import euler.dbs.FibDatabaseRocksDB;
import euler.math.Fib;

public class TestFib
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

        db_path = FilenameUtils.concat(tempDirectory.toPath().toString(),
                                       "euler_rocsdb_fib_" + System.currentTimeMillis());
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

    private final Fib fibInMem = new Fib(new FibDatabaseInMemory());

    @Test
    public void testGetInMem()
    {

        assertEquals(0L, fibInMem.get(0));
        assertEquals(1L, fibInMem.get(1));
        assertEquals(1L, fibInMem.get(2));
        assertEquals(2L, fibInMem.get(3));
        assertEquals(3L, fibInMem.get(4));
        assertEquals(5L, fibInMem.get(5));
        assertEquals(8L, fibInMem.get(6));
        assertEquals(13L, fibInMem.get(7));
        assertEquals(21L, fibInMem.get(8));
        assertEquals(34L, fibInMem.get(9));
        assertEquals(377L, fibInMem.get(14));
    }

    @Test
    public void testOverflow()
    {
        // long max = 9223372036854775807
        // fib(92) = 7540113804746346429
        assertEquals(7540113804746346429L, fibInMem.get(92));
        assertEquals(-1L, fibInMem.get(93));

    }

    @Test
    public void testGeRocks() throws RocksDBException
    {
        Fib fibRocks = new Fib(new FibDatabaseRocksDB(db_path, new Options(), new Statistics(),
                                                      new RateLimiter(1_000_000)));
        assertEquals(0L, fibRocks.get(0));
        assertEquals(1L, fibRocks.get(1));
        assertEquals(1L, fibRocks.get(2));
        assertEquals(2L, fibRocks.get(3));
        assertEquals(3L, fibRocks.get(4));
        assertEquals(5L, fibRocks.get(5));
        assertEquals(8L, fibRocks.get(6));
        assertEquals(13L, fibRocks.get(7));
        assertEquals(21L, fibRocks.get(8));
        assertEquals(34L, fibRocks.get(9));
        assertEquals(377L, fibRocks.get(14));
    }

}
