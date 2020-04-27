package euler.problems;

import euler.math.PrimeDbInMemory;
import euler.math.PrimeSieve;

/**
 * The prime factors of 13195 are 5, 7, 13 and 29.
 * 
 * What is the largest prime factor of the number 600851475143 ?
 * 
 * @author warren
 *
 */
public class Problem003
{

    public static void main(String[] args)
    {
        long input = 600851475143L;
        // long input = 13195;
        int primeUpper = (int) Math.sqrt(input);

        PrimeSieve primes = new PrimeSieve(new PrimeDbInMemory(), primeUpper);

        long startime = System.currentTimeMillis();
        System.out.println("prime compute took " + (System.currentTimeMillis() - startime));

        for (int i = primeUpper; i >= 0; i--)
        {
            if (primes.isPrime(i))
            {
                if (input % i == 0)
                {
                    System.out.println("6857 = " + i);
                    break;
                }
            }
        }
    }
}
