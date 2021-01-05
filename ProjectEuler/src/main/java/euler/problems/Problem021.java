package euler.problems;

import java.util.SortedSet;

import euler.math.DigitUtils;

public class Problem021
{

    public static void main(String[] args)
    {
        System.out.println("31626 = " + cacheFactors(10_000));
        System.out.println("31626 = " + bruteForce(10_000));
    }
    
    public static long cacheFactors(int limit) {
        long startMs = System.currentTimeMillis();
        int sumOfPairs = 0;
        long[] cache = new long[limit];
        for (int i = 1; i < limit; i++) {
            for (int j = 1; j < limit; j++) {
                if (cache[i] == 0) {
                    cache[i] = sumOfFactors(i);
                }
                if (cache[j] == 0) {
                    cache[j] = sumOfFactors(j);
                }
                if (cache[i] == j && cache[j] == i && i != j) {
                    System.out.println("i = " + i + " j = " + j);
                    sumOfPairs += i;
                    sumOfPairs += j;
                }
            }
        }
        System.out.println("cacheFactors ms: " + (System.currentTimeMillis() - startMs));
        return sumOfPairs / 2;
    }

    public static long bruteForce(int limit)
    {
        long startMs = System.currentTimeMillis();
        int sumOfPairs = 0;
        for (int i = 1; i < limit; i++)
        {
            for (int j = 1; j < limit; j++)
            {
                if (sumOfFactors(i) == j && sumOfFactors(j) == i && i != j)
                {
                    System.out.println("i = " + i + " j = " + j);
                    sumOfPairs += i;
                    sumOfPairs += j;
                }
            }
        }
        System.out.println("bruteForce ms: " + (System.currentTimeMillis() - startMs));
        return sumOfPairs / 2;
    }

    public static long sumOfFactors(int n)
    {
        SortedSet<Integer> factorsIncludingN = DigitUtils.factorize(n);
        return factorsIncludingN.headSet(n - 1)
                                .stream()
                                .reduce(0, (a, b) -> a + b);
    }

}
