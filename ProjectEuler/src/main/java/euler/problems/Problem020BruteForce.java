package euler.problems;

import java.math.BigInteger;

public class Problem020BruteForce
{

    public static void main(String[] args)
    {
        BigInteger product = new BigInteger("1");
        for (int i = 1; i < 100; i++)
        {
            product = product.multiply(new BigInteger(Integer.toString(i)));
        }
        int sum = 0;
        int offset = (int) '0';
        char[] chararray = product.toString().toCharArray();
        for (int i = 0; i < chararray.length; i++)
        {
            sum += ((int) chararray[i]) - offset;
        }
        System.out.println("648 = " + sum);
    }

}
