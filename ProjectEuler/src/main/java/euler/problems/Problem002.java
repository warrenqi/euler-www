package euler.problems;

import euler.dbs.FibDatabaseInMemory;
import euler.math.Fib;

public class Problem002
{

    public static void main(String[] args)
    {
        Fib fib = new Fib(new FibDatabaseInMemory());

        long sum = 0;
        int i = 1;
        while (fib.get(i) <= 4_000_000)
        {
            if (fib.get(i) % 2 == 0)
            {
                sum = sum + fib.get(i);
            }
            i++;
        }
        System.out.println(sum + " = 4613732");
    }

}
