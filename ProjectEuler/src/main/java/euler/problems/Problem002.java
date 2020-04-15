package euler.problems;

import org.rocksdb.Options;
import org.rocksdb.RateLimiter;
import org.rocksdb.RocksDBException;
import org.rocksdb.Statistics;

import euler.dbs.FibDatabaseRocksDB;
import euler.dbs.RocksDBUtils;
import euler.math.Fib;

public class Problem002
{

    public static void main(String[] args)
    {
        try
        {
            Fib fib = new Fib(new FibDatabaseRocksDB(RocksDBUtils.getDbPathAtProjectRoot(),
                                                     new Options(), new Statistics(),
                                                     new RateLimiter(10_000)));
            long sum = 0;
            int i = 1;
            while (fib.get(i) <= 4_000_000)
            {
                if (fib.get(i) % 2 == 0)
                {
                    sum = sum + fib.get(i);
                }
                i++;
            }
            System.out.println(sum + " = 4613732");
        } catch (RocksDBException e)
        {
            e.printStackTrace();
        }

    }

}
