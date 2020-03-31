package euler.dbs;

import gnu.trove.map.hash.TIntLongHashMap;

public class FibDatabaseInMemory implements FibDatabase
{

    private TIntLongHashMap map = new TIntLongHashMap();
    private int threadUnsafeMax = 0;

    public FibDatabaseInMemory()
    {
        this.put(0, 0);
        this.put(1, 1);
        this.put(2, 1);
        this.put(3, 2);
    }

    @Override
    public void put(int i, long ithFib)
    {
        map.put(i, ithFib);
        threadUnsafeMax = (threadUnsafeMax < i) ? i : threadUnsafeMax;
    }

    @Override
    public long get(int i)
    {
        if (map.containsKey(i))
        {
            return map.get(i);
        } else
        {
            return NOT_COMPUTED;
        }
    }

    @Override
    public int getHighestComputedIndex()
    {
        return threadUnsafeMax;
    }

}
