package euler.problems;

import euler.math.DigitUtils;

public class Problem097
{
    public static void main(String[] args)
    {

        /*
         * we want the last 10 digits of the result, but the trick is, when
         * multiplying by 2 every loop, we can ignore what happens to the higher
         * digits
         */

        long result = 28433;
        int exponent = 7830457;

        for (int i = 0; i < exponent; i++)
        {
            if (DigitUtils.numDigits(result) <= 16)
            {
                result = result << 1;
            } else
            {
                result = result % 1_000_000_000_000_000_000L;
                result = result << 1;
            }
        }

        System.out.println("truncated prime num = " + (result + 1));
        System.out.println("last 10 digits = 8739992577 = " + (result + 1) % 10_000_000_000L);

    }

}
