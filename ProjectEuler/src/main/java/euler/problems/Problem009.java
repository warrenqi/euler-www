package euler.problems;

public class Problem009
{

    public static void main(String[] args)
    {
        for (int i = 1; i < 1000; i++)
        {
            for (int j = i; j < 1000; j++)
            {
                for (int k = j; k < 1000; k++)
                {
                    if (i + j + k == 1000 && i * i + j * j == k * k)
                    {
                        System.out.println("i " + i + " j " + j + " k " + k);
                        System.out.println(i * j * k);

                    }
                }
            }
        }
    }

}
