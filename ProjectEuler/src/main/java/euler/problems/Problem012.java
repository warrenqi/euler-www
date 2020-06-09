package euler.problems;

import java.util.Arrays;
import java.util.Set;

import euler.math.DigitUtils;

public class Problem012
{

    public static void main(String[] args)
    {
        int[] triangleNums = DigitUtils.getTriangleNums(20);
        System.out.println("Triangle numbers = " + Arrays.toString(triangleNums));

        triangleNums = DigitUtils.getTriangleNums(1_000_000);
        for (int x : triangleNums)
        {
            Set<Integer> factors = DigitUtils.factorize(x);
            if (factors.size() > 500)
            {
                System.out.println(x);
                System.out.println(factors);
                System.exit(0);
            }
        }
        System.out.println("nothing found");
    }

}
