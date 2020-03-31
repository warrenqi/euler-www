package euler.dbs;

public interface FibDatabase
{
    public static final long NOT_COMPUTED = -1L;
    
    public void put(int i, long ithFib);

    public long get(int i);
    
    public int getHighestComputedIndex();

}
