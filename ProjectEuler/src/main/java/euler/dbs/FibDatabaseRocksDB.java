package euler.dbs;

import org.rocksdb.Options;
import org.rocksdb.RateLimiter;
import org.rocksdb.RocksDBException;
import org.rocksdb.Statistics;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public class FibDatabaseRocksDB extends AbstractRocksDB implements FibDatabase
{

    private final static byte[] HIGHEST_IDX = "highest".getBytes();
    private final static int COLUMN = RocksCols.FIBS_INT64.ordinal();

    public FibDatabaseRocksDB(String dbPath, Options options, Statistics stats,
            RateLimiter rateLimiter) throws RocksDBException
    {
        super(dbPath, options, stats, rateLimiter);
        assert (this.db != null);

        this.db.put(this.cfHandles.get(COLUMN), HIGHEST_IDX, Ints.toByteArray(0));
        this.put(0, 0L);
        this.put(1, 1L);
        this.put(2, 1L);
        this.put(3, 2L);
    }

    /*
     * This method is not thread safe because it doesn't atomically tie the
     * Key/Val put() with the increment of HIGHEST_INX
     */
    @Override
    public void put(int i, long ithFib)
    {
        try (WriteBatch wb = new WriteBatch())
        {
            byte[] key = Ints.toByteArray(i);
            byte[] val = Longs.toByteArray(ithFib);
            wb.put(this.cfHandles.get(COLUMN), key, val);

            int currentHighIdx = getHighestComputedIndex();
            if (currentHighIdx < i)
            {
                wb.put(this.cfHandles.get(COLUMN), HIGHEST_IDX, key);
            }
            this.db.write(new WriteOptions(), wb);
        } catch (RocksDBException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public long get(int i)
    {
        try
        {
            byte[] val = this.db.get(this.cfHandles.get(COLUMN), Ints.toByteArray(i));
            if (val != null && val.length != 0)
            {
                return Longs.fromByteArray(val);
            } else
            {
                return NOT_COMPUTED;
            }
        } catch (RocksDBException e)
        {
            e.printStackTrace();
            return NOT_COMPUTED;
        }
    }

    @Override
    public int getHighestComputedIndex()
    {
        try
        {
            return Ints.fromByteArray(this.db.get(this.cfHandles.get(COLUMN), HIGHEST_IDX));
        } catch (RocksDBException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

}
