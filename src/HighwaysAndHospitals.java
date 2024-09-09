import java.util.ArrayList;

/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: Beckett Porter
 * Started on: 9/9/2024
 */

public class HighwaysAndHospitals
{
    /**
     * TODO: Complete this function, cost(), to return the minimum cost to provide
     *  hospital access for all citizens in Menlo County.
     */
    public static long cost(int n, int hospitalCost, int highwayCost, int cities[][])
    {

        for (int num : getSurroundingCities(3, cities))
        {
            System.out.println(num);
        }



        return 0;
    }

    // Helper method I made to give me the cities that directly connect to a given city.
    private static int[] getSurroundingCities(int startingCity, int[][] citiesAr)
    {
        ArrayList<Integer> ar = new ArrayList<>();

        // Go through each element in the array to see if either the first column or
        // second column contains the target city.
        for (int i = 0; i < citiesAr.length; i++)
        {
            for (int j = 0; j < citiesAr[0].length; j++)
            {
                if (citiesAr[i][j] == startingCity)
                {
                    // I did this Math.Abs trick here to always give me the other value relating to the target city
                    // given that there are always 2 columns.
                    ar.add(citiesAr[i][Math.abs(j - 1)]);
                }
            }
        }

        // Convert the ArrayList to a normal array to return it.
        int[] newAr = new int[ar.size()];
        for (int i = 0; i < newAr.length; i++)
        {
            newAr[i] = ar.get(i);
        }

        return newAr;
    }
}