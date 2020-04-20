package euler.problems;

import euler.math.DigitUtils;

public class Problem004
{
    public static void main(String[] args)
    {
        int resultsofar = 0;
        for (int i = 100; i < 999; i++)
        {
            for (int j = 100; j < 999; j++)
            {
                int result = i * j;
                if (DigitUtils.isPalindrome(result) && result > resultsofar)
                    resultsofar = result;
            }
        }
        System.out.println(resultsofar);
    }

}
