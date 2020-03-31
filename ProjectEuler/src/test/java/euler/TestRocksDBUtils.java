package euler;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.rocksdb.ColumnFamilyDescriptor;

import euler.dbs.RColumns;
import euler.dbs.RocksDBUtils;

public class TestRocksDBUtils
{

    @Test
    public void testKnownColumnDescriptors()
    {
        List<ColumnFamilyDescriptor> knownColumnDescriptors = RocksDBUtils.knownColumnDescriptors();
        assertEquals(knownColumnDescriptors.size(), RColumns.values().length);
        assertEquals("default", new String(knownColumnDescriptors.get(0).columnFamilyName()));
    }

    public void testOrderingOfColumnsUnchanged()
    {
        assertEquals(0, RColumns.DEFAULT.ordinal());
        assertEquals(1, RColumns.FIBS_INT64.ordinal());
        assertEquals(2, RColumns.PRIMES_INT64.ordinal());
    }

}
