package euler.problems;

import euler.math.DigitUtils;

public class Problem023
{

    public static void main(String[] args)
    {
        System.out.println(isAbundant(0));
        System.out.println(isAbundant(12));
        System.out.println("4179871 = " + sweep(28_123));
    }

    public static long sweep(int limit)
    {

        boolean[] abund = abundantNums(limit + 1);
        boolean[] canBeWritten = new boolean[limit + 1];

        for (int i = 1; i < limit + 1; i++)
        {

            int x = 1;
            int y = i;

            // search sorted array for sum in O(N)
            // allow summing the same number (e.g. 12+12).
            while (x <= y)
            {
                if (x + y == i && abund[x] && abund[y])
                {
                    canBeWritten[i] = true;
                    break;
                }
                if (x + y < i)
                {
                    x++;
                } else
                {
                    y--;
                }
            }
        }
        long res = 0;
        for (int idx = 1; idx < canBeWritten.length; idx++)
        {
            if (!canBeWritten[idx])
            {
                res += idx;
            }
        }
        return res;
    }

    public static boolean[] abundantNums(int capacity)
    {
        boolean[] res = new boolean[capacity];
        for (int i = 1; i < capacity; i++)
        {
            res[i] = isAbundant(i);
        }
        return res;
    }

    public static boolean isAbundant(int n)
    {
        return DigitUtils.factorize(n)
                         .stream()
                         .reduce(0, (a, b) -> a + b)
                / 2 > n;
    }
}
