package euler.problems;

import euler.math.PrimeDbInMemory;
import euler.math.PrimeSieve;

/**
 * Find the sum of all the primes below two million
 * 
 * @author warren
 *
 */
public class Problem010
{

    public static void main(String[] args)
    {
        PrimeSieve ps = new PrimeSieve(new PrimeDbInMemory(), 2_000_000);

        long sum = 2L;
        for (int i = 3; i < 2_000_000; i += 2)
        {
            if (ps.isPrime(i))
            {
                sum += i;
            }
        }
        System.out.println("142913828922 = " + sum);
    }

}
