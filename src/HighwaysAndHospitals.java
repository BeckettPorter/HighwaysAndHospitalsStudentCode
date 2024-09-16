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

        long totalCost = 0;

        int clusterNum = getNumCityClusters(cities, n);

        // If each city is individual and not connected, build hospital on each one.
        if (clusterNum == n)
        {
            return (long) hospitalCost * n;
        }

        for (int i = 0; i < clusterNum; i++)
        {
            totalCost += hospitalCost;
        }
        for (int j = 0; j < n - clusterNum; j++)
        {
            totalCost += highwayCost;
        }

        return totalCost;
    }


    private static int getNumCityClusters(int[][] citiesAr, int numCities)
    {
        // Index = city, number at index = parent of city, or if 0 then root
        int[] unionMap = new int[numCities + 1];

        // Set each index to have -1 as the root initially
        for (int i = 0; i < numCities + 1; i++)
        {
            unionMap[i] = -1;
        }

        int numCluster = 0;

        for (int i = 0; i < citiesAr.length; i++)
        {
            int city1Root = findCityRoot(citiesAr[i][0], unionMap);
            int city2Root = findCityRoot(citiesAr[i][1], unionMap);

            // If the cities have different roots, connect them
            if (city1Root != city2Root)
            {
                unionMap[city1Root] = city2Root;
            }
        }
        // After that, go through unionMap and for each -1 in the array, that's an individual cluster
        for (int i = 0; i < unionMap.length; i++)
        {
            if (unionMap[i] == -1)
            {
                numCluster++;
            }
        }
        return numCluster;
    }

    // Helper method that returns the root of the city
    private static int findCityRoot(int city, int[] map)
    {
        int index = city;

        while (map[index] != -1)
        {
            index = map[index];
        }
        return index;
    }
}