package euler.problems;

import org.apache.commons.lang3.StringUtils;

import euler.math.DigitUtils;

/**
 * The decimal number, 585 = 10010010012 (binary), is palindromic in both bases.
 * 
 * Find the sum of all numbers, less than one million, which are palindromic in
 * base 10 and base 2.
 * 
 * (Please note that the palindromic number, in either base, may not include
 * leading zeros.)
 * 
 */
public class Problem036
{
    public static void main(String[] args)
    {
        long sum = 0;
        for (int i = 1; i < 1000000; i++)
        {
            String binaryString = Integer.toBinaryString(i);
            if (DigitUtils.isPalindrome(i)
                    && binaryString.equals(StringUtils.reverse(binaryString)))
            {
                sum += i;
            }

        }
        System.out.println("872187 = " + sum);
    }

}
