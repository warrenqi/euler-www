package euler.problems;

public class Problem006
{
    public static void main(String[] args)
    {

        System.out.println("correct answer = -25164150");
        System.out.println("simpleDiff = " + simpleDiff());
        System.out.println("closed form = " + closedForm(100));
    }

    public static int simpleDiff()
    {
        int bound = 100;
        int sumofsquare = 0;
        int squareofsum = 0;
        for (int i = 1; i <= bound; i++)
        {
            sumofsquare += i * i;
            squareofsum += i;
        }
        squareofsum = squareofsum * squareofsum;
        return sumofsquare - squareofsum;
    }

    /**
     * The sum of squares is apparently called
     * https://en.m.wikipedia.org/wiki/Square_pyramidal_number
     * 
     * The closed form is n(n+1)(2n+1)/6
     * 
     * Its derivation is cool.
     * 
     * 
     * The sum of 1...N is the Triangular number of N.
     * 
     * The closed form is n(n+1)/2
     * 
     * The difference after some algebra is:
     * 
     * (-3 n^4 - 2 n^3 + 3 n^2 + 2 n)/12
     */
    public static int closedForm(int n)
    {
        return ((-3) * n * n * n * n - 2 * n * n * n + 3 * n * n + 2 * n) / 12;
    }

}
