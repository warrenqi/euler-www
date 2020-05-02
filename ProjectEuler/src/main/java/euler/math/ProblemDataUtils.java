package euler.math;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import gnu.trove.list.array.TShortArrayList;

public class ProblemDataUtils
{
    /**
     * 
     * @param file
     *            input file containing all numbers, on multiple lines
     * @return TShortArrayList of every number as a single element
     */
    public static TShortArrayList asSequenceOfDigits(File file)
    {
        int offset = (int) '0';
        TShortArrayList res = new TShortArrayList();
        try (LineIterator lit = IOUtils.lineIterator(IOUtils.toBufferedReader(new FileReader(file))))
        {
            while (lit.hasNext())
            {
                String nextLine = lit.nextLine();
                for (char c : nextLine.toCharArray())
                {
                    res.add((short) (c - offset));
                }
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return res;
    }

}
