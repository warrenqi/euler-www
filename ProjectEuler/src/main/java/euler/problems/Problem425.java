package euler.problems;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import euler.math.DigitUtils;
import euler.math.PrimeDbInMemory;
import euler.math.PrimeSieve;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

public class Problem425
{

    public static boolean detectLeft(String bigger, String smaller)
    {
        return bigger.substring(1)
                     .equals(smaller);
    }

    /**
     * True when either of these 2 conditions are met:
     * <p>
     * (1) A and B have the same length and differ in exactly one digit; for
     * example, 123 ↔ 173.
     * <p>
     * (2) Adding one digit to the left of A (or B) makes B (or A); for example,
     * 23 ↔ 223 and 123 ↔ 23. But NOT 2 and 23.
     * 
     */
    public static boolean isConnected(int a, int b)
    {
        String aStr = String.valueOf(a);
        String bStr = String.valueOf(b);
        boolean leftDigit = false;
        if (DigitUtils.numDigits(a) == DigitUtils.numDigits(b))
        {
            leftDigit = true;
        } else if (DigitUtils.numDigits(a) > DigitUtils.numDigits(b))
        {
            leftDigit = detectLeft(aStr, bStr);
        } else
        {
            leftDigit = detectLeft(bStr, aStr);
        }

        // requring edit distance == 1 also dedups the input x, which is helpful
        // to avoid loopbacks
        return leftDigit && 1 == StringUtils.getLevenshteinDistance(aStr, bStr);
    }

    public static void main(String[] args)
    {
        Problem425 t = new Problem425(10_000);
        TIntSet searchStepCache = new TIntHashSet();
        System.out.println("2 -> " + t.searchStep(2, 100, searchStepCache));
        System.out.println("3 -> " + t.searchStep(3, 100, searchStepCache));
        System.out.println("5 -> " + t.searchStep(5, 100, searchStepCache));
        System.out.println("7 -> " + t.searchStep(7, 100, searchStepCache));
        // System.out.println("13 -> " + t.searchStep(13, 127));
        // System.out.println("113 -> " + t.searchStep(113, 127));
        // System.out.println("103 -> " + t.searchStep(103, 127));
        // System.out.println("107 -> " + t.searchStep(107, 127));

        // now find all prime relatives of 2. to start, limit to 1_000
        t = new Problem425(1_000);
        TIntList primeRelatives = t.primeRelativesChain(2, 97);
        System.out.println("primeRelatives of 2 -> 97 = " + primeRelatives);

        t = new Problem425(1_000);
        primeRelatives = t.primeRelativesChain(2, 127);
        System.out.println("primeRelatives of 2 -> 127 = " + primeRelatives);

        t = new Problem425(1_000);
        primeRelatives = t.primeRelativesChain(2, 103);
        System.out.println("primeRelatives of 2 -> 103 = " + primeRelatives);

        // then eliminate all prime relatives of 2, sum them up to output
        t = new Problem425(1_000);
        TIntSet twoRelatives = t.primeRelatives(2, 130);
        System.out.println(twoRelatives);

        // cache the intermediate chains

        // parallelize the search

    }

    private final PrimeSieve primes;

    private final Map<Integer, TIntList> cache = new HashMap<>();

    public Problem425(int maxPrime)
    {
        primes = new PrimeSieve(new PrimeDbInMemory(), maxPrime);
    }

    /**
     * A single search step towards finding a chain of connected primes. This
     * method avoids loopbacks.
     * 
     * @param x
     * @param maxP
     * @return A list of connected primes less than or equal to maxP, excluding
     *         the "visited" primes of this instance. Empty if none found.
     */
    public TIntList searchStep(int x, int maxP, TIntSet visited)
    {
        TIntList connectedPrimes = connectedPrimes(x, maxP);
        visited.add(x);
        TIntList res = filterVisited(connectedPrimes, visited);
        visited.addAll(connectedPrimes);
        return res;
    }

    /**
     * 
     * @param x
     *            - current step in the search
     * @param maxP
     *            - the input maximum P from the problem statement
     * @return
     */
    public TIntList connectedPrimes(int x, int maxP)
    {
        if (cache.containsKey(x))
        {
            return cache.get(x);
        }

        // get range of possible connected numbers
        // fetch primes in that range
        // filter to isConnected

        TIntList res = new TIntArrayList();
        Pair<Integer, Integer> range = getRange(x);
        int rangeEnd = Math.min(maxP, range.getRight());

        for (int i = range.getLeft(); i <= rangeEnd; i++)
        {
            if (this.primes.isPrime(i) && isConnected(x, i))
            {
                res.add(i);
            }
        }
        cache.put(x, res);

        return res;
    }

    public TIntList filterVisited(TIntList connectedPrimes, TIntSet visited)
    {
        TIntList res = new TIntArrayList(connectedPrimes.size());
        for (int i = 0; i < connectedPrimes.size(); i++)
        {
            if (!visited.contains(connectedPrimes.get(i)))
            {
                res.add(connectedPrimes.get(i));
            }
        }
        return res;
    }

    public Pair<Integer, Integer> getRange(int x)
    {
        int numDigits = DigitUtils.numDigits(x);
        // if x in range [0,9], min = 2; max = 99
        // if x in range [10,99], e.g. 17, min = 7; max = 917 (or just 999)
        int min = 2;
        min = Math.max(min, (int) Math.pow(10, numDigits - 2));

        int max = Integer.MAX_VALUE;
        max = Math.min(max, (int) Math.pow(10, numDigits + 1) - 1);
        return new ImmutablePair<Integer, Integer>(min, max);
    }

    /**
     * 
     * @param x
     * @param target
     * @return The shortest chain (by BFS) of primes between X and Target, each
     *         pair "connected", with no prime in this chain larger than Target.
     *         Empty list if no such chain of primes exists between X and
     *         Target.
     * 
     */
    public TIntList primeRelativesChain(int x, int target)
    {
        // for now, use a new searchStepCache for every chain discovery
        TIntSet searchStepCache = new TIntHashSet();

        // Use a node -> parent mapping to back trace our steps
        TIntIntMap leafToRoots = new TIntIntHashMap();

        TIntList queue = new TIntArrayList();
        queue.add(x);
        boolean found = false;
        while (queue.size() > 0)
        {
            int val = queue.removeAt(0);

            TIntList valConnectedPrimes = searchStep(val, target, searchStepCache);

            if (!valConnectedPrimes.isEmpty())
            {

                for (int i = 0; i < valConnectedPrimes.size(); i++)
                {
                    int connecteePrime = valConnectedPrimes.get(i);
                    leafToRoots.put(connecteePrime, val);
                }
                if (valConnectedPrimes.contains(target))
                {
                    found = true;
                    break;
                } else
                {
                    queue.addAll(valConnectedPrimes);
                }
            }

        }
        TIntList res = new TIntArrayList();

        if (found)
        {
            int tracer = target;
            while (leafToRoots.containsKey(tracer))
            {
                res.add(tracer);
                // at every step, find parent of current step
                tracer = leafToRoots.get(tracer);
            }
            res.add(x);
            res.reverse();
        }

        return res;
    }

    /**
     * 
     * @param x
     *            - usually set to 2 in the problem
     * @param maxP
     * @return set of primes who are relatives of x
     */
    public TIntSet primeRelatives(int x, int maxP)
    {
        TIntSet res = new TIntHashSet();
        for (int i = maxP; i > 3; i--)
        {
            if (primes.isPrime(i) && !primeRelativesChain(x, i).isEmpty())
            {
                res.add(i);
            }
        }
        return res;
    }

    public TIntSet primeSetDiff(int startingPrime, TIntSet relatives, int maxP)
    {
        TIntSet res = new TIntHashSet();
        for (int i = startingPrime; i < maxP; i++)
        {
            if (primes.isPrime(i) && !relatives.contains(i))
            {
                res.add(i);
            }
        }
        return res;
    }

}
