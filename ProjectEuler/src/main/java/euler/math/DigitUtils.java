package euler.math;

import org.apache.commons.lang3.StringUtils;

import gnu.trove.list.array.TIntArrayList;

public class DigitUtils
{
    public static void main(String[] args)
    {
        System.out.println(truncateDigitsFromLeft(123456789));
        System.out.println(truncateDigitsFromRight(123456789));
        System.out.println(numDigits(999) + " = 3");
        System.out.println(numDigits(1000) + " = 4");
    }
    
    public static int numDigits(long input) {
        return 1 + (int) Math.log10(input);
    }

    public static TIntArrayList truncateDigitsFromLeft(int input)
    {
        TIntArrayList truncations = new TIntArrayList();

        int modulus = 10;
        int truncated_input;
        while ((truncated_input = input % modulus) != input)
        {
            truncations.add(truncated_input);
            modulus = modulus * 10;
        }
        truncations.add(input);
        return truncations;
    }

    public static TIntArrayList truncateDigitsFromRight(int input)
    {
        TIntArrayList truncations = new TIntArrayList();
        truncations.add(input);

        int truncated_input;
        int divisor = 10;
        while ((truncated_input = input / divisor) != 0)
        {
            truncations.add(truncated_input);
            divisor = divisor * 10;
        }
        return truncations;
    }

    public static boolean isPalindrome(long number)
    {
        String tmp = String.valueOf(number);
        return tmp.equals(StringUtils.reverse(tmp));
    }

}
