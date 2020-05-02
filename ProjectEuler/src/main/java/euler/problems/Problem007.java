package euler.problems;

import java.util.ArrayList;
import java.util.List;

import euler.math.PrimeDbInMemory;
import euler.math.PrimeSieve;

public class Problem007
{

    public static void main(String[] args)
    {
        List<Integer> res = new ArrayList<>(10_000);
        PrimeSieve ps = new PrimeSieve(new PrimeDbInMemory(), 200_000);

        for (int i = 1; i <= 200_000; i++)
        {
            if (ps.isPrime(i))
            {
                res.add(i);
            }
        }
        System.out.println("104743 = " + res.get(10_000));

    }

}
