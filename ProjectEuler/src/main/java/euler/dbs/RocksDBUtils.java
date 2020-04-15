package euler.dbs;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.rocksdb.BlockBasedTableConfig;
import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyOptions;
import org.rocksdb.CompactionStyle;
import org.rocksdb.CompressionType;
import org.rocksdb.Options;
import org.rocksdb.RateLimiter;
import org.rocksdb.Statistics;

import com.google.common.io.Resources;

public class RocksDBUtils
{

    /**
     * 
     * @return
     */
    public static String getDbPathAtProjectRoot()
    {
        URL resource = Resources.getResource("placeholder.txt");
        File placeholder = new File(resource.getFile());
        Path project_root = placeholder.toPath().getParent().getParent().getParent();
        String db_path = FilenameUtils.concat(project_root.toString(), "rocksdb");
        System.out.println("rocksDB path is at " + db_path);
        return db_path;
    }

    /**
     * Enumerates the known columns in RocksDBColumns Enum. Preserves the index
     * ordering.
     * 
     * @return
     */
    public static List<ColumnFamilyDescriptor> knownColumnDescriptors()
    {
        List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
        for (RocksCols column : RocksCols.values())
        {
            columnFamilyDescriptors.add(new ColumnFamilyDescriptor(column.cName,
                                                                   new ColumnFamilyOptions()));
        }
        return columnFamilyDescriptors;
    }

    public static void setDBOptions(Options db_options, Statistics db_stats,
            RateLimiter db_rateLimiter)
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
