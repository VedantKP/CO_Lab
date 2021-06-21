/*
Name: Vedant Puranik
Roll No: 43152
Class: BE-9
Batch: P9
Lab: COL
Assignment 5: Floyd-Warshall Algorithm
*/

import java.util.*;
import java.lang.*;
import java.io.*;


class FloydWarshall
{
	final static int INF = 99999;

	void floydWarshall(int graph[][],int V)
	{
		int dist[][] = new int[V][V];
		int i, j, k;

		for (i = 0; i < V; i++)
			for (j = 0; j < V; j++)
				dist[i][j] = graph[i][j];

		for (k = 0; k < V; k++)
		{
			// Pick all vertices as source one by one
			for (i = 0; i < V; i++)
			{
				// Pick all vertices as destination for the
				// above picked source
				for (j = 0; j < V; j++)
				{
					// If vertex k is on the shortest path from
					// i to j, then update the value of dist[i][j]
					if (dist[i][k] + dist[k][j] < dist[i][j])
						dist[i][j] = dist[i][k] + dist[k][j];
				}
			}
		}

		// Print the shortest distance matrix
		printSolution(dist, V);
	}

	void printSolution(int dist[][],int V)
	{
		System.out.println("The following matrix shows the shortest distances between every pair of vertices");
		for (int i=0; i<V; ++i)
		{
			for (int j=0; j<V; ++j)
			{
				if (dist[i][j]==INF)
					System.out.print("INF ");
				else
					System.out.print(dist[i][j]+" ");
			}
			System.out.println();
		}
	}

	public static void main (String[] args)
	{
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of vertices for the graph => ");
        int V = sc.nextInt();

        int[][] graph = new int[V][V];

        System.out.println("Enter weight for every edge (99999 for infinity): ");
        for(int i=0;i<V;i++)
        {
            for(int j=0;j<V;j++)
            {
                System.out.print("Enter weight of (" + i + "," + j + ") => ");
                graph[i][j] = sc.nextInt();
            }
        }
		
		FloydWarshall fw = new FloydWarshall();

		fw.floydWarshall(graph,V);
	}
}

/*
C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 5>java FloydWarshall
Enter number of vertices for the graph =>
4
Enter weight for every edge (99999 for infinity):
Enter weight of (0,0) => 0
Enter weight of (0,1) => 5
Enter weight of (0,2) => 8
Enter weight of (0,3) => 9
Enter weight of (1,0) => 99999
Enter weight of (1,1) => 0
Enter weight of (1,2) => 3
Enter weight of (1,3) => 4
Enter weight of (2,0) => 99999
Enter weight of (2,1) => 99999
Enter weight of (2,2) => 0
Enter weight of (2,3) => 1
Enter weight of (3,0) => 99999
Enter weight of (3,1) => 99999
Enter weight of (3,2) => 99999
Enter weight of (3,3) => 0
The following matrix shows the shortest distances between every pair of vertices
0 5 8 9
INF 0 3 4
INF INF 0 1
INF INF INF 0

C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 5>
*/