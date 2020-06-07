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

public class Problem011
{

    public static void main(String[] args)
    {
        URL resource = Resources.getResource("problem011_input.txt");
        File pFile = new File(resource.getFile());

        try
        {
            int[][] graph = read(pFile);
            int result = maxProduct(graph, 20);
            System.out.println(result + " = 70600674");

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static int[][] read(File input) throws FileNotFoundException, IOException
    {
        List<String> readLines = IOUtils.readLines(new FileInputStream(input),
                                                   Charset.defaultCharset());
        int lineCount = readLines.size();
        int[][] result = new int[lineCount][];
        for (int x = 0; x < lineCount; x++)
        {
            String line = readLines.get(x);
            String[] split = line.split(" ");
            int[] splitInts = new int[split.length];
            for (int i = 0; i < split.length; i++)
            {
                int valueOf = Integer.valueOf(split[i]);
                splitInts[i] = valueOf;
            }
            result[x] = splitInts;
        }
        return result;
    }

    public static int maxProduct(int[][] graph, int dimension)
    {

        int product = 0;
        // HORIZONTAL
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension - 3; j++)
            {
                // System.out.println(graph[i][j] + " " + graph[i][j+1] + " " +
                // graph[i][j+2] + " " + graph[i][j+3]);
                if (product < (graph[i][j] * graph[i][j + 1] * graph[i][j + 2] * graph[i][j + 3]))
                    product = (graph[i][j] * graph[i][j + 1] * graph[i][j + 2] * graph[i][j + 3]);
            }
        }
        // VERTICAL
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension - 3; j++)
            {
                // System.out.println(graph[j][i] + " " +graph[j+1][i] + " " +
                // graph[j+2][i] + " " + graph[j+3][i]);
                if (product < (graph[j][i] * graph[j + 1][i] * graph[j + 2][i] * graph[j + 3][i]))
                    product = (graph[j][i] * graph[j + 1][i] * graph[j + 2][i] * graph[j + 3][i]);
            }
        }
        // System.out.println(product);
        // DIAGONAL NORTHWEST
        for (int i = 0; i < dimension - 3; i++)
        {
            for (int j = 0; j < dimension - 3; j++)
            {
                // System.out.println(graph[i][j] + " " + graph[i+1][j+1] + " "
                // + graph[i+2][j+2] + " " + graph[i+3][j+3]);
                if (product < (graph[i][j] * graph[i + 1][j + 1] * graph[i + 2][j + 2]
                        * graph[i + 3][j + 3]))
                    product = (graph[i][j] * graph[i + 1][j + 1] * graph[i + 2][j + 2]
                            * graph[i + 3][j + 3]);
            }
        }
        // System.out.println(product);
        // DIAGONAL SOUTHWEST
        for (int i = 3; i < dimension; i++)
        {
            for (int j = 3; j < dimension; j++)
            {
                // System.out.println(graph[i][j-3] + " " + graph[i-1][j-2] + "
                // " + graph[i-2][j-1] + " " + graph[i-3][j]);
                if (product < (graph[i][j - 3] * graph[i - 1][j - 2] * graph[i - 2][j - 1]
                        * graph[i - 3][j]))
                    product = (graph[i][j - 3] * graph[i - 1][j - 2] * graph[i - 2][j - 1]
                            * graph[i - 3][j]);
            }
        }
        return product;
    }

}
