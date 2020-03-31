package euler.dbs;

import java.util.ArrayList;
import java.util.List;

import org.rocksdb.BlockBasedTableConfig;
import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyOptions;
import org.rocksdb.CompactionStyle;
import org.rocksdb.CompressionType;
import org.rocksdb.Options;
import org.rocksdb.RateLimiter;
import org.rocksdb.Statistics;

public class RocksDBUtils
{
    /**
     * Enumerates the known columns in RocksDBColumns Enum. Preserves the index
     * ordering.
     * 
     * @return
     */
    public static List<ColumnFamilyDescriptor> knownColumnDescriptors()
    {
        List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
        for (RColumns column : RColumns.values())
        {
            columnFamilyDescriptors.add(new ColumnFamilyDescriptor(column.cName, new ColumnFamilyOptions()));
        }
        return columnFamilyDescriptors;
    }

    public static void setDBOptions(Options db_options, Statistics db_stats, RateLimiter db_rateLimiter)
    {
        db_options.setCreateIfMissing(true).setCompressionType(CompressionType.SNAPPY_COMPRESSION)
                  .setCompactionStyle(CompactionStyle.UNIVERSAL).setKeepLogFileNum(7L);
        db_options.setStatistics(db_stats);
        db_options.setRateLimiter(db_rateLimiter);
        db_options.setCreateMissingColumnFamilies(true);

        final BlockBasedTableConfig table_options = new BlockBasedTableConfig();
        db_options.setTableFormatConfig(table_options);
    }

}
