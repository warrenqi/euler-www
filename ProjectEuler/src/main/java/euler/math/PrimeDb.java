package euler.math;

import gnu.trove.list.array.TLongArrayList;

public interface PrimeDb
{

    /**
     * set numbers start to end as Prime, inclusive. Use as initialization step
     * in computing new primes
     */
    public void fillSet(long startInclusive, long endInclusive);

    public void put(long i, boolean isPrime);

    public boolean isPrime(long i);

    /** collect all primes <= current watermark */
    public TLongArrayList getPrimesLowerEquals(long num);

    public long getWatermark();

    public void setWatermark(long number);
}
