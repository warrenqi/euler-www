package euler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import euler.math.PrimeDbInMemory;
import euler.math.PrimeSieve;

public class TestPrimeSieve
{

    @Test
    public void test()
    {
        PrimeSieve ps = new PrimeSieve(new PrimeDbInMemory());
        assertTrue(ps.isPrime(2L, false));
        assertTrue(ps.isPrime(3L, true));
        assertFalse(ps.isPrime(4L, true));
        assertTrue(ps.isPrime(5L, true));
        assertTrue(ps.isPrime(37L, true));
        assertFalse(ps.isPrime(38L, true));
        assertTrue(ps.isPrime(5L, true));
        assertTrue(ps.isPrime(101L, true));
        assertFalse(ps.isPrime(102L, true));
        assertTrue(ps.isPrime(103L, true));
    }

    @Test
    public void testTwoPrimes()
    {
        PrimeSieve ps = new PrimeSieve(new PrimeDbInMemory());
        long starttime = System.currentTimeMillis();
        ps.isPrime(200000L, true);
        System.out.println("time for 200_000 prime calcs = "
                + (System.currentTimeMillis() - starttime));
        assertTrue(ps.isPrime(104743L, false));
    }

}
