import java.util.ArrayList;

/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: Beckett Porter
 * Started on: 9/9/2024
 * Completed on: 9/19/2024
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
        // Otherwise, build one hospital per cluster and build highways to connect to every other city.
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

    // Helper method which uses union find to return the number of city clusters.
    private static int getNumCityClusters(int[][] citiesAr, int numCities)
    {
        // Index = city, number at index = parent of city
        int[] unionMap = new int[numCities + 1];

        int[] orderMap = new int[numCities + 1];

        // Set each index to have themselves as the root initially
        for (int i = 0; i < numCities + 1; i++)
        {
            unionMap[i] = i;
        }

        int numClusters = 0;

        // For each edge
        for (int i = 0; i < citiesAr.length; i++)
        {
            int city1Root = findCityRoot(citiesAr[i][0], unionMap);
            int city2Root = findCityRoot(citiesAr[i][1], unionMap);

            // Get the order of root 1 and root 2 from the orderMap.
            int root1Order = orderMap[city1Root];
            int root2Order = orderMap[city2Root];

            // If the cities have different roots, connect them, taking into account weight balancing
            if (city1Root != city2Root)
            {
                // Check which has a higher order and make that one the root. (Weight balancing)
                if (root1Order > root2Order)
                {
                    unionMap[city2Root] = city1Root;
                }
                else if (root2Order > root1Order)
                {
                    unionMap[city1Root] = city2Root;
                }
                else
                {
                    unionMap[city2Root] = city1Root;
                    orderMap[city1Root] = root1Order + 1;
                }
            }
        }
        // After that, go through unionMap and for each root in the array, that's an individual cluster
        for (int i = 1; i <= unionMap.length - 1; i++)
        {
            if (unionMap[i] == i)
            {
                numClusters++;
            }
        }
        return numClusters;
    }

    // Helper method that returns the root of the city
    private static int findCityRoot(int city, int[] map)
    {
        int index = city;

        while (map[index] != index)
        {
            index = map[index];
        }

        // Path compression
        while (map[city] != city)
        {
            int temp = map[city];
            map[city] = map[index];
            city = temp;
        }
        return index;
    }
}