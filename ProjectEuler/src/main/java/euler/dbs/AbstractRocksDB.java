package euler.dbs;

import java.util.ArrayList;
import java.util.List;

import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.DBOptions;
import org.rocksdb.Options;
import org.rocksdb.RateLimiter;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.Statistics;

/**
 * Makes use-case specific DB implementations easier
 * 
 * @author warren
 *
 */
public class AbstractRocksDB
{

    private final String dbPath;
    private final Options options;
    private final Statistics stats;
    private final RateLimiter rateLimiter;
    final RocksDB db;
    final List<ColumnFamilyHandle> cfHandles = new ArrayList<ColumnFamilyHandle>();

    static
    {
        RocksDB.loadLibrary();
    }

    public AbstractRocksDB(String dbPath, Options options, Statistics stats, RateLimiter rateLimiter)
            throws RocksDBException
    {
        this.dbPath = dbPath;
        this.options = options; // TODO wqi maybe make options configgable
        this.stats = stats;
        this.rateLimiter = rateLimiter;
        RocksDBUtils.setDBOptions(this.options, stats, rateLimiter);
        List<ColumnFamilyDescriptor> columnFamilyDescriptors = RocksDBUtils.knownColumnDescriptors();
        DBOptions dbOptions = new DBOptions(this.options);
        this.db = RocksDB.open(dbOptions, this.dbPath, columnFamilyDescriptors, cfHandles);
    }

    public void close()
    {
        if (this.db != null)
        {
            this.db.close();
        }
    }

}
