package euler.problems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.common.io.Resources;

public class Problem067
{

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        URL resource = Resources.getResource("problem067_input.txt");
        File pFile = new File(resource.getFile());
        List<String> fileLines = IOUtils.readLines(new FileInputStream(pFile),
                                                   Charset.defaultCharset());
        // parse the input file into numbers
        List<List<Integer>> triangle = new ArrayList<>();
        for (String line : fileLines)
        {
            String[] split = line.split(" ");
            List<Integer> numLine = new ArrayList<>();
            for (String numStr : split)
            {
                numLine.add(Integer.parseInt(numStr));
            }
            triangle.add(numLine);
        }
        compute(triangle);
    }

    static final int LIMIT = 200;

    public static void compute(List<List<Integer>> triangle)
    {
        // makes usage of List.set() nicer
        List<List<Integer>> tmpRes = new ArrayList<>(Collections.nCopies(LIMIT,
                                                                         new ArrayList<>(Collections.nCopies(LIMIT,
                                                                                                             0))));
        // initialize the last level
        tmpRes.set(triangle.size() - 1, new ArrayList<>(triangle.get(triangle.size() - 1)));

        for (int line = triangle.size() - 2; line >= 0; line--)
        {
            // get fresh values
            List<Integer> valsAtLine = triangle.get(line);
            // get intermediate results summed upwards
            List<Integer> valsBelow = tmpRes.get(line + 1);
            System.out.println("valsBelow " + valsBelow);

            List<Integer> newValsAtLine = new ArrayList<>(Collections.nCopies(LIMIT, 0));

            for (int i = 0; i < valsAtLine.size(); i++)
            {
                int lineNode = valsAtLine.get(i);
                int belowLeft = valsBelow.get(i);
                int belowRight = valsBelow.get(i + 1);
                int resAtNode = Math.max(lineNode + belowLeft, lineNode + belowRight);
                newValsAtLine.set(i, resAtNode);
            }
            tmpRes.set(line, newValsAtLine);
        }
        System.out.println("the answer is = " + tmpRes.get(0)
                                                      .get(0));
    }

}
