package euler.math;

import java.util.logging.Logger;

import gnu.trove.iterator.TLongIterator;
import gnu.trove.list.array.TLongArrayList;

public class PrimeSieve
{
    private static final Logger LOG = Logger.getLogger(PrimeSieve.class.getName());

    private PrimeDb db;

    public PrimeSieve(PrimeDb db)
    {
        this.db = db;
        this.db.put(0, true);
        this.db.put(1, true);
        this.db.put(2, true);
        this.db.setWatermark(2L);
    }

    public boolean isPrime(long number, boolean computeIfMissing)
    {
        if (computeIfMissing && number >= this.db.getWatermark())
        {
            computeAndStore(number);
        }
        return this.db.isPrime(number);
    }

    public void computeAndStore(long number)
    {
        long curWatermark = this.db.getWatermark();
        this.db.fillSet(curWatermark + 1, number);

        TLongArrayList primesLower = this.db.getPrimesLowerEquals(curWatermark);

        TLongIterator iterator = primesLower.iterator();
        while (iterator.hasNext())
        {
            long curPrime = iterator.next();
            sieve(curPrime, number);
        }

        for (long numAboveMark = curWatermark; numAboveMark <= number; numAboveMark++)
        {
            if (this.db.isPrime(numAboveMark))
            {
                sieve(numAboveMark, number);
            } else
            {
                continue;
            }
        }
        this.db.setWatermark(number);
    }

    /** eliminate all multiples of inputPrime until upperbound, inclusive */
    public void sieve(long inputPrime, long upperbound)
    {
        long idx = inputPrime;
        long multiplier = inputPrime;
        long multiple;
        while ((multiple = idx * multiplier) <= upperbound)
        {
            this.db.put(multiple, false);
            multiplier++;
        }
    }

}
