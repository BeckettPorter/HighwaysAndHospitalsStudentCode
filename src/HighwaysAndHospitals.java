import java.util.ArrayList;

/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: Beckett Porter
 * Started on: 9/9/2024
 * Completed on:
 */

public class HighwaysAndHospitals
{
    /**
     * TODO: Complete this function, cost(), to return the minimum cost to provide
     *  hospital access for all citizens in Menlo County.
     */
    public static long cost(int n, int hospitalCost, int highwayCost, int cities[][])
    {
        // If the highway cost is greater than a hospital, it'll always be cheaper just to build hospitals in every
        // city. If it is equal, it is the same as building one hospital and roads to all surrounding cities.
        // Either way, I can just return the number of cities * the hospital cost.
        if (highwayCost >= hospitalCost)
        {
            return (long) n * hospitalCost;
        }

        // Otherwise, this means the highways cost less than hospitals. This means the optimal approach is to place
        // one hospital per cluster of cities connected by highways.

        // 2D array of arraylists, [i][0] gives you the cluster index, and [0][j] gives you the cities in the cluster.
        ArrayList<ArrayList<Integer>> cityClusters = getCityClusters(cities, n);

        long totalCost = 0;

        // Check if the city clusters array is empty and just multiply n by hospital cost because I'd need to
        // build hospitals on each of these non-connected cities. I probably could have figured out a way
        // to do this without this if statement, but I couldn't figure it out.
        if (cityClusters.isEmpty())
        {
            totalCost += (long) hospitalCost * n;
        }
        // Check for individual cities that do not connect to any other cities. Must build a hospital on each of these.
        else if (n > cityClusters.size() + 1)
        {
            // I do this clamp to make sure it is at minimum 1 non-connected hospital because it wouldn't make sense
            // for n to be larger than the cityClusters size if there isn't a non-connected city somewhere.
            totalCost += (long) hospitalCost * Math.clamp((n - cityClusters.size() - 1), 1, Long.MAX_VALUE);
        }

        System.out.println("NEW TEST");

        for (int i = 0; i < cityClusters.size(); i++)
        {
            for (int j = 0; j < cityClusters.get(i).size(); j++)
            {
                if (j == 1)
                {
                    totalCost += hospitalCost;
                }
                else
                {
                    totalCost += highwayCost;
                }
                System.out.println("cluster: " + i + " City: " + cityClusters.get(i).get(j));
            }
        }
        return totalCost;
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
                    // I did this Math.Abs trick here to always give me the corresponding value relating to the target city
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

    private static ArrayList<ArrayList<Integer>> getCityClusters(int[][] citiesAr, int numCities)
    {
        ArrayList<ArrayList<Integer>> cityClusters = new ArrayList<>();

        int index = 0;

        for (int i = 0; i < citiesAr.length; i++)
        {
            cityClusters.add(index, new ArrayList<>());
            ArrayList<Integer> currentCluster = cityClusters.get(index);

            for (int j = 0; j < citiesAr[0].length; j++)
            {
                int[] surroundingCities = getSurroundingCities(citiesAr[index][j], citiesAr);
                for (int surroundingCity : surroundingCities)
                {
                    // Maybe make this into a helper function and do an if statement
                    if (!doesNestedArraylistContainCity(surroundingCity, cityClusters))
                    {
                        currentCluster.add(surroundingCity);
                    }
                }
            }
            // If there are any cities left in the citiesArray that haven't been added to a cluster, do that.
            index++;
        }


        return cityClusters;
    }

    private static boolean doesNestedArraylistContainCity(int surroundingCity,
                                                          ArrayList<ArrayList<Integer>> cityClusters)
    {
        boolean contains = false;
        for (int i = 0; i < cityClusters.size(); i++)
        {
            if (cityClusters.get(i).contains(surroundingCity))
            {
                contains = true;
            }
        }
        return contains;
    }
}