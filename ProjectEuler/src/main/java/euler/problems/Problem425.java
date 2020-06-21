package euler.problems;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import euler.math.DigitUtils;
import euler.math.PrimeDbInMemory;
import euler.math.PrimeSieve;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

public class Problem425
{

    public static void main(String[] args)
    {
        Problem425 t = new Problem425(10_000);
        System.out.println("2 -> " + t.connectedPrimes(2, 127));
        System.out.println("3 -> " + t.connectedPrimes(3, 127));
        System.out.println("13 -> " + t.connectedPrimes(13, 127));
        System.out.println("113 -> " + t.connectedPrimes(113, 127));
        // TODO wqi break loopbacks now there's a cycle in this graph
        System.out.println("103 -> " + t.connectedPrimes(103, 127));

    }

    private final PrimeSieve primes;

    public Problem425(int maxPrime)
    {
        primes = new PrimeSieve(new PrimeDbInMemory(), maxPrime);
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
     *            - current step in the search
     * @param maxP
     *            - the input maximum P from the problem statement
     * @return
     */
    public TIntList connectedPrimes(int x, int maxP)
    {
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

        return res;
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

    public static boolean detectLeft(String bigger, String smaller)
    {
        return bigger.substring(1)
                     .equals(smaller);
    }

}
