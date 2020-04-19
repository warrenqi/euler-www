package euler.math;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicLong;

import gnu.trove.list.array.TLongArrayList;

public class PrimeDbInMemory implements PrimeDb
{
    private final BitSet db = new BitSet();
    private final AtomicLong watermark = new AtomicLong();

    @Override
    public void fillSet(long startInclusive, long endInclusive)
    {
        db.set((int) startInclusive, (int) endInclusive + 1);
    }

    @Override
    public void put(long i, boolean isPrime)
    {
        if (i >= Integer.MAX_VALUE)
        {
            throw new IllegalArgumentException("limit int32");
        }
        db.set((int) i, isPrime);
    }

    @Override
    public boolean isPrime(long i)
    {
        if (i >= db.size())
        {
            return false;
        }
        return db.get((int) i);
    }

    @Override
    public TLongArrayList getPrimesLowerEquals(long num)
    {
        TLongArrayList result = new TLongArrayList(this.db.cardinality());

        for (int i = this.db.nextSetBit(2); i >= 0; i = this.db.nextSetBit(i + 1))
        {
            result.add(i);
            if (i == Integer.MAX_VALUE)
                break;
        }
        return result;
    }

    @Override
    public long getWatermark()
    {
        return watermark.get();
    }

    @Override
    public void setWatermark(long number)
    {
        long curMark;
        if ((curMark = this.watermark.get()) < number)
        {
            this.watermark.compareAndSet(curMark, number);
        }
    }

}
