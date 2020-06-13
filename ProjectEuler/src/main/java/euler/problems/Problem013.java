package euler.problems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.common.io.Resources;

/**
 * Work out the first ten digits of the sum of the following one-hundred
 * 50-digit numbers.
 * 
 * <p>
 * Some interesting math behind the mechanics of adding Most Significant Digits
 * together and preserving precision.
 * 
 * To find the leading X digits of the sum, I use the leading X + 1 digits of
 * each component. The +1 is to determine if there is a leading zero or not.
 * Otherwise the result is off by a single digit in the least significant digit
 * position.
 * 
 * <code>

    1000  9999     100 999      10  99      1   9
    1000  9999     100 999      10  99      1   9
    1000  9999     100 999      10  99      1   9
    1000  9999     100 999      10  99      1   9
    1000  9999     100 999      10  99      1   9
    1000  9999     100 999      10  99      1   9
    1000  9999     100 999      10  99      1   9
    1000  9999     100 999      10  99      1   9
    1000  9999     100 999      10  99      1   9
                                    
   09000  89991   0900 8991    090  891    09   81

 * </code>
 * 
 * @author warren
 *
 */

public class Problem013
{

    public static void main(String[] args)
    {
        int DESIRED_PRECISION = 10;

        URL resource = Resources.getResource("problem013_input.txt");
        File pFile = new File(resource.getFile());

        try
        {
            long[] trimmed = trimToLeading(IOUtils.readLines(new FileInputStream(pFile),
                                                             Charset.defaultCharset()),
                                           DESIRED_PRECISION + 1);
            long result = 0;
            for (long num : trimmed)
            {
                result += num;
            }
            System.out.println(Long.toString(result)
                                   .substring(0, DESIRED_PRECISION)
                    + " = 5537376230");
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    static long[] trimToLeading(List<String> lines, int k)
    {
        long[] result = new long[lines.size()];
        for (int i = 0; i < lines.size(); i++)
        {
            String leadingDigits = lines.get(i)
                                        .substring(0, k);
            result[i] = Long.parseLong(leadingDigits);
        }
        return result;
    }

}
