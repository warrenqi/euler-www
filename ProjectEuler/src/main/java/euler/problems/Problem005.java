package euler.problems;

/**
 * 
 * 
 * 2520 is the smallest number that can be divided by each of the numbers from 1
 * to 10 without any remainder.
 * 
 * What is the smallest positive number that is evenly divisible by all of the
 * numbers from 1 to 20?
 * 
 * @author warren
 *
 */
public class Problem005
{
    /**
     * Brute force solution :)
     * 
     * The possible divisors are
     * [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20] but we only need to
     * test division against number 11 to 20. because if a number is divisible
     * by 12, it is also divisible by 2,3,4,6. if a number is divisible by 15,
     * it is also divisible by 3,5. use this pattern to reduce divisors.
     */
    public static void main(String[] args)
    {
        long number = 11L;
        while (true)
        {
            boolean alldivisible = (number % 11 == 0) && (number % 12 == 0) && (number % 13 == 0)
                    && (number % 14 == 0) && (number % 15 == 0) && (number % 16 == 0)
                    && (number % 17 == 0) && (number % 18 == 0) && (number % 19 == 0)
                    && (number % 20 == 0);

            if (alldivisible)
            {
                System.out.println("result = " + number);
                break;
            }
            number++;
        }
    }
}
