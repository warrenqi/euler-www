package euler.problems;

import euler.math.DigitUtils;
import euler.math.PrimeDbInMemory;
import euler.math.PrimeSieve;
import gnu.trove.list.array.TIntArrayList;

/**
 * 
 * The number 3797 has an interesting property. Being prime itself, it is
 * possible to continuously remove digits from left to right, and remain prime
 * at each stage: 3797, 797, 97, and 7. Similarly we can work from right to
 * left: 3797, 379, 37, and 3.
 * 
 * Find the sum of the only eleven primes that are both truncatable from left to
 * right and right to left.
 * 
 * NOTE: 2, 3, 5, and 7 are not considered to be truncatable primes.
 * 
 * @author warren
 *
 */
public class Problem037
{
    public TIntArrayList result = new TIntArrayList();

    public Problem037(int upperLimit)
    {
        PrimeSieve primes = new PrimeSieve(new PrimeDbInMemory(), upperLimit);
        TIntArrayList primeList = new TIntArrayList();

        for (int i = 2; i < upperLimit; i++)
        {
            if (primes.isPrime(i))
                primeList.add(i);
        }

        for (int i = 0; i < primeList.size(); i++)
        {
            if (truncateAndTest(primeList.get(i), primes))
            {
                result.add(primeList.get(i));
            }
        }
    }

    public static void main(String[] args)
    {
        Problem037 test = new Problem037(1_000_000);
        int sum = 0;
        for (int i = 0; i < test.result.size(); i++)
        {
            if (test.result.get(i) < 10)
                continue; // skip 2,3,5,7
            else
                sum += test.result.get(i);
        }
        System.out.println("748317 = " + sum);
    }

    static boolean isAllPrime(TIntArrayList nums, PrimeSieve primes)
    {
        boolean res = true;
        for (int i = 0; i < nums.size(); i++)
        {
            res = res && primes.isPrime(nums.get(i));
        }
        return res;
    }

    boolean truncateAndTest(int num, PrimeSieve primes)
    {
        boolean fromLeft = isAllPrime(DigitUtils.truncateDigitsFromLeft(num), primes);

        boolean fromRight = isAllPrime(DigitUtils.truncateDigitsFromRight(num), primes);

        return fromLeft && fromRight;
    }

}
