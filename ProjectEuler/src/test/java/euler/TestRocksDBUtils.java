package euler;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.rocksdb.ColumnFamilyDescriptor;

import euler.dbs.RocksCols;
import euler.dbs.RocksDBUtils;

public class TestRocksDBUtils
{

    @Test
    public void testKnownColumnDescriptors()
    {
        List<ColumnFamilyDescriptor> knownColumnDescriptors = RocksDBUtils.knownColumnDescriptors();
        assertEquals(knownColumnDescriptors.size(), RocksCols.values().length);
        assertEquals("default", new String(knownColumnDescriptors.get(0).columnFamilyName()));
    }

    public void testOrderingOfColumnsUnchanged()
    {
        assertEquals(0, RocksCols.DEFAULT.ordinal());
        assertEquals(1, RocksCols.FIBS_INT64.ordinal());
        assertEquals(2, RocksCols.PRIMES_INT64.ordinal());
    }

}
