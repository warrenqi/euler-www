package euler.math;

import java.util.logging.Logger;

import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.util.ArithmeticUtils;

import euler.dbs.FibDatabase;

public class Fib
{

    private static final Logger LOGGER = Logger.getLogger(Fib.class.getName());

    private FibDatabase database;

    public Fib(FibDatabase database)
    {
        this.database = database;

    }

    /**
     * Gets the ith Fib number. get(0) returns 0.
     * 
     * @param i
     * @return
     */
    public long get(int i)
    {
        // lookup database
        long dbResult = this.database.get(i);
        if (dbResult == FibDatabase.NOT_COMPUTED)
        {
            long compute = computeAndStore(i);
            return compute;
        } else
        {
            return dbResult;
        }
    }

    public long computeAndStore(int inp)
    {
        long timestart = System.currentTimeMillis();
        int difference = inp - this.database.getHighestComputedIndex();

        if (difference > 0)
        {
            int highestIndex = this.database.getHighestComputedIndex();
            int secondHigest = highestIndex - 1;

            // interval to compute = [ N-1, N, ... i]
            // ex: i = 5, highest = 2, we need the interval [1,2,_,_,_]
            int length = 2 + (inp - highestIndex);
            long[] temp = new long[length];
            temp[0] = this.database.get(secondHigest);
            temp[1] = this.database.get(highestIndex);
            for (int j = 2; j < length; j++)
            {
                try
                {
                    long tempj = ArithmeticUtils.addAndCheck(temp[j - 2], temp[j - 1]);
                    temp[j] = tempj;
                    this.database.put(highestIndex + (j - 1), temp[j]);
                } catch (MathArithmeticException e)
                {
                    LOGGER.info("long overflow detected when adding ");
                    break;
                }
            }

        }
        LOGGER.info("Requested i = " + inp + " and FibDB highIdx = " + (inp - difference) + " Computing FibDB in ms:\t"
                + (System.currentTimeMillis() - timestart));
        return this.database.get(inp);
    }

}
