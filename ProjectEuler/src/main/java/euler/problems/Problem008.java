package euler.problems;

import java.io.File;
import java.net.URL;

import com.google.common.io.Resources;

import euler.math.ProblemDataUtils;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TShortArrayList;

/**
 * Find the thirteen adjacent digits in the 1000-digit number (in file) that
 * have the greatest product.
 * 
 * Return the value of the product.
 * 
 * @author warren
 *
 */
public class Problem008
{

    public static void main(String[] args)
    {
        URL resource = Resources.getResource("problem008_input.txt");
        File pFile = new File(resource.getFile());

        long warmup = biggestContiguousProduct(ProblemDataUtils.asSequenceOfDigits(pFile), 2);

        long start2 = System.nanoTime();
        long simpleSol2 = biggestContiguousProduct(ProblemDataUtils.asSequenceOfDigits(pFile), 4);
        System.out.println("simple solution2 took " + (System.nanoTime() - start2));

        long start3 = System.nanoTime();
        long simpleSol3 = biggestContiguousProduct(ProblemDataUtils.asSequenceOfDigits(pFile), 16);
        System.out.println("simple solution3 took " + (System.nanoTime() - start3));

        long start4 = System.nanoTime();
        long simpleSol4 = biggestContiguousProduct(ProblemDataUtils.asSequenceOfDigits(pFile), 32);
        System.out.println("simple solution4 took " + (System.nanoTime() - start4));
    }

    /**
     * simple solution
     */
    public static long biggestContiguousProduct(TShortArrayList inputNumbers, int k)
    {
        long maxProduct = 0;
        for (int i = 0; i < inputNumbers.size() - k + 1; i++)
        {
            long product = 1;
            TShortList subList = inputNumbers.subList(i, i + k);
            for (int j = 0; j < subList.size(); j++)
            {
                if (subList.get(j) == 0)
                {
                    break;
                } else
                {
                    product *= subList.get(j);
                }
            }
            maxProduct = (product > maxProduct) ? product : maxProduct;
        }
        return maxProduct;
    }

}
