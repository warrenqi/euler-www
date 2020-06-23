package euler.problems;

import java.math.BigInteger;

public class Problem016
{

    public static void main(String[] args)
    {
        BigInteger num = new BigInteger("2");
        num = num.pow(1000);
        System.out.println("num = " + num);
        long sum = 0;
        String numStr = num.toString();
        int offset = (int) '0';
        for (int i = 0; i < numStr.length(); i++)
        {
            char charAt = numStr.charAt(i);
            sum += ((int) charAt - offset);
        }
        System.out.println("sum = " + sum);
    }

}
