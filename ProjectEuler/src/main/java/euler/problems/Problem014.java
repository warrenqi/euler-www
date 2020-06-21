package euler.problems;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.hash.TLongIntHashMap;

/**
 * 
 * n → n/2 (n is even) <br>
 * 
 * n → 3n + 1 (n is odd) <br>
 * 
 * Sounds like dynamic programming?
 * 
 * 
 * ------- naive programming, generating full lists every time ------- <br>
 * timing Naive = 1869 <br>
 * ------- dynamic programming, memoizing complete lists ------- <br>
 * timing Memoized = 3218 <br>
 * ------- dynamic programming, memoizing only sequence sizes ------- <br>
 * timing SeqCountOnly = 181 <br>
 * 
 * TODO wqi inspect JVM codegen & memory layout for this problem
 * 
 * 
 * @author warren
 *
 */
public class Problem014
{
    static final int LIMIT = 1_000_000;

    public static void main(String[] args)
    {
        Problem014 instance = new Problem014();
        System.out.println(instance.computeMemoizedLists(13)
                + " = [13, 40, 20, 10, 5, 16, 8, 4, 2, 1]");

        System.out.println("------- naive programming, generating full lists every time -------");
        instance.naive();

        System.out.println("------- dynamic programming, memoizing complete lists -------");
        long startMemoizedLists = System.currentTimeMillis();
        int maxMemoizedLists = 0;
        int resultMemoizedLists = 0;
        for (int i = 13; i < LIMIT; i++)
        {
            int sequence_size = instance.computeMemoizedLists(i)
                                        .size();
            if (sequence_size > maxMemoizedLists)
            {
                maxMemoizedLists = sequence_size;
                resultMemoizedLists = i;
            }
        }
        System.out.println("resultMemoizedLists = " + resultMemoizedLists + " = 837799");
        System.out.println("timing Memoized = "
                + (System.currentTimeMillis() - startMemoizedLists));
        System.out.println("cacheHits = " + instance.cacheHit);
        System.out.println("cacheMiss = " + instance.cacheMiss);
        System.out.println("recomputeBase = " + instance.recomputeBase);

        System.out.println("------- dynamic programming, memoizing only sequence sizes -------");
        long startSeqCountOnly = System.currentTimeMillis();
        int maxSeqCountOnly = 0;
        int resSeqSizeOnly = 0;
        for (int i = 13; i < LIMIT; i++)
        {
            int sequence_size = instance.computeMemoizedSeqSizes(i);
            if (sequence_size > maxMemoizedLists)
            {
                maxSeqCountOnly = sequence_size;
                resSeqSizeOnly = i;
            }
        }
        System.out.println("resultMemoizedLists = " + resultMemoizedLists + " = 837799");
        System.out.println("timing SeqCountOnly = "
                + (System.currentTimeMillis() - startSeqCountOnly));

    }

    private final Map<Long, TLongList> cache = new HashMap<>(LIMIT * 2);
    public final AtomicInteger cacheHit = new AtomicInteger();
    public final AtomicInteger cacheMiss = new AtomicInteger();
    public final AtomicInteger recomputeBase = new AtomicInteger();

    public TLongList computeMemoizedLists(long input)
    {
        if (input == 1)
        {
            recomputeBase.incrementAndGet();
            TLongList base = new TLongArrayList(1);
            base.add(1);
            return base;
        }

        if (cache.containsKey(input))
        {
            cacheHit.incrementAndGet();
            return cache.get(input);
        } else
        {
            cacheMiss.incrementAndGet();

            long interm = (input % 2 == 0) ? input / 2 : (3 * input + 1);

            TLongList tail = computeMemoizedLists(interm);
            // a bit of memory optimization here. measured CPU time saving
            // 16seconds -> 5seconds
            TLongList result = new TLongArrayList(1 + tail.size());
            result.add(input);
            result.addAll(tail);

            cache.put(input, result);

            return result;
        }

    }

    private final TLongIntMap cacheSeqSizes = new TLongIntHashMap(LIMIT * 2);

    public int computeMemoizedSeqSizes(long input)
    {
        if (input == 1)
        {
            return 1;
        }

        if (cacheSeqSizes.containsKey(input))
        {
            return cacheSeqSizes.get(input);
        } else
        {
            long interm = (input % 2 == 0) ? input / 2 : (3 * input + 1);

            int tail = computeMemoizedSeqSizes(interm);
            cacheSeqSizes.put(input, tail + 1);
            return tail + 1;
        }
    }

    public void naive()
    {
        long start = System.currentTimeMillis();
        int maxLen = 0;
        int number = 0;
        for (int i = 13; i < LIMIT; i++)
        {
            int size = naiveConstruct(i).size();
            if (size > maxLen)
            {
                maxLen = size;
                number = i;
            }
        }
        System.out.println("result Naive  = " + number);
        System.out.println("timing Naive  = " + (System.currentTimeMillis() - start));
    }

    LinkedList<Long> naiveConstruct(int input)
    {

        LinkedList<Long> res = new LinkedList<>();
        long i = (long) input;
        res.add(i);

        // compute the entire sequence until the last elem == 1
        while (res.getLast() != 1L)
        {
            i = res.getLast();
            long elem = (i % 2 == 0) ? (i / 2) : (3 * i + 1);
            res.add(elem);
        }
        return res;
    }

}
