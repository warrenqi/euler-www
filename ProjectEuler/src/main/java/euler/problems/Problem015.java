package euler.problems;

/**
 * Starting in the top left corner of a 2×2 grid, and only being able to move to
 * the right and down, there are exactly 6 routes to the bottom right corner.
 * 
 * (horizontal x vertical)
 * 
 * For a 1x1 grid, there are only 2 ways.
 * <p>
 * For a 2x1 grid, there are 3 ways.
 * <p>
 * For a 3x1 grid, there are 4 ways.
 * <p>
 * For a 2x2 grid, there are 6 ways.
 * <p>
 * For a 3x2 grid, there are 10 ways. Same for 2x3 grid.
 * <p>
 * Result value at (X, Y) = value(X, Y-1) + value(X-1, Y)
 * 
 * @author warren
 *
 */
public class Problem015
{

    public static void main(String[] args)
    {
        System.out.println("2x2 grid simple method output = " + simpleGrid(2));
        System.out.println("20x20 grid simple method output = " + simpleGrid(20));
        System.out.println("20x20 grid optimal output = " + computeOptimal(20));
    }

    /**
     * O(X*Y) time and memory
     */
    public static long simpleGrid(int gridsize)
    {
        int arraysize = gridsize + 1; // for a 1x1 grid, the result = 2
        long[][] res = setupBorder(arraysize);
        for (int x = 1; x < arraysize; x++)
        {
            for (int y = 1; y < arraysize; y++)
            {
                res[x][y] = res[x - 1][y] + res[x][y - 1];
            }
        }

        return res[gridsize][gridsize];
    }

    public static long[][] setupBorder(int arraysize)
    {
        long[][] res = new long[arraysize][arraysize];
        for (int i = 0; i < arraysize; i++)
        {
            res[i][0] = 1;
            res[0][i] = 1;
        }
        return res;
    }

    /**
     * Optimal solution:
     * <p>
     * 
     * Movement can be represented as a sequence of R number of Right moves, and
     * D number of Down moves movements, total length of sequence is fixed, =
     * gridsize + gridsize = R + D.
     * <p>
     * R == D (square grid)
     * <p>
     * ( (2 * R) choose R)
     */
    public static long computeOptimal(int gridsize)
    {
        long res = 1;
        for (int i = 1; i <= gridsize; i++)
        {
            res = res * (gridsize + i) / i;
        }

        return res;
    }

}
