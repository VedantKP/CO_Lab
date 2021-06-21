/*
Name: Vedant Puranik
Roll No: 43152
Class: BE-9
Batch: P9
Lab: COL
Assignment 5: Dijkstra Algorithm
*/

import java.util.*;
import java.lang.*;
import java.io.*;

class Dijkstra {

	int minDistance(int dist[], Boolean sptSet[], int V)
	{
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < V; v++)
			if (sptSet[v] == false && dist[v] <= min) {
				min = dist[v];
				min_index = v;
			}

		return min_index;
	}

	void printSolution(int dist[], int V)
	{
		System.out.println("Vertex \t\t Distance from Source");
		for (int i = 0; i < V; i++)
			System.out.println(i + " \t\t " + dist[i]);
	}

	void dijkstra(int graph[][], int src, int V)
	{
		int dist[] = new int[V];
		Boolean sptSet[] = new Boolean[V];

		// Initialize all distances as INFINITE and stpSet[] as false
		for (int i = 0; i < V; i++) {
			dist[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}

		// Distance of source vertex from itself is always 0
		dist[src] = 0;

		// Find shortest path for all vertices
		for (int count = 0; count < V - 1; count++) 
        {
			int u = minDistance(dist, sptSet, V);

			sptSet[u] = true;

			for (int v = 0; v < V; v++)
				if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
					dist[v] = dist[u] + graph[u][v];
		}
        
		printSolution(dist, V);
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of vertices for the graph => ");
        int V = sc.nextInt();

        int[][] graph = new int[V][V];

        System.out.println("Enter distance for every vertex pair: ");
        for(int i=0;i<V;i++)
        {
            for(int j=0;j<V;j++)
            {
                System.out.print("Enter distance of (" + i + "," + j + ") => ");
                graph[i][j] = sc.nextInt();
            }
        }

        Dijkstra t = new Dijkstra();
		t.dijkstra(graph, 0, V);

        sc.close();
	}
}

/*
OUTPUT

C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 5>java Dijkstra
Enter number of vertices for the graph =>
9
Enter distance for every vertex pair:
Enter distance of (0,0) => 0
Enter distance of (0,1) => 4
Enter distance of (0,2) => 0
Enter distance of (0,3) => 0
Enter distance of (0,4) => 0
Enter distance of (0,5) => 0
Enter distance of (0,6) => 0
Enter distance of (0,7) => 8
Enter distance of (0,8) => 0
Enter distance of (1,0) => 4
Enter distance of (1,1) => 0
Enter distance of (1,2) => 8
Enter distance of (1,3) => 0
Enter distance of (1,4) => 0
Enter distance of (1,5) => 0
Enter distance of (1,6) => 0
Enter distance of (1,7) => 11
Enter distance of (1,8) => 0
Enter distance of (2,0) => 0
Enter distance of (2,1) => 8
Enter distance of (2,2) => 0
Enter distance of (2,3) => 7
Enter distance of (2,4) => 0
Enter distance of (2,5) => 4
Enter distance of (2,6) => 0
Enter distance of (2,7) => 0
Enter distance of (2,8) => 2
Enter distance of (3,0) => 0
Enter distance of (3,1) => 0
Enter distance of (3,2) => 7
Enter distance of (3,3) => 0
Enter distance of (3,4) => 9
Enter distance of (3,5) => 14
Enter distance of (3,6) => 0
Enter distance of (3,7) => 0
Enter distance of (3,8) => 0
Enter distance of (4,0) => 0
Enter distance of (4,1) => 0
Enter distance of (4,2) => 0
Enter distance of (4,3) => 9
Enter distance of (4,4) => 0
Enter distance of (4,5) => 10
Enter distance of (4,6) => 0
Enter distance of (4,7) => 0
Enter distance of (4,8) => 0
Enter distance of (5,0) => 0
Enter distance of (5,1) => 0
Enter distance of (5,2) => 4
Enter distance of (5,3) => 14
Enter distance of (5,4) => 10
Enter distance of (5,5) => 0
Enter distance of (5,6) => 2
Enter distance of (5,7) => 0
Enter distance of (5,8) => 0
Enter distance of (6,0) => 0
Enter distance of (6,1) => 0
Enter distance of (6,2) => 0
Enter distance of (6,3) => 0
Enter distance of (6,4) => 0
Enter distance of (6,5) => 2
Enter distance of (6,6) => 0
Enter distance of (6,7) => 1
Enter distance of (6,8) => 6
Enter distance of (7,0) => 8
Enter distance of (7,1) => 11
Enter distance of (7,2) => 0
Enter distance of (7,3) => 0
Enter distance of (7,4) => 0
Enter distance of (7,5) => 0
Enter distance of (7,6) => 1
Enter distance of (7,7) => 0
Enter distance of (7,8) => 7
Enter distance of (8,0) => 0
Enter distance of (8,1) => 0
Enter distance of (8,2) => 2
Enter distance of (8,3) => 0
Enter distance of (8,4) => 0
Enter distance of (8,5) => 0
Enter distance of (8,6) => 6
Enter distance of (8,7) => 7
Enter distance of (8,8) => 0

Vertex           Distance from Source
0                0
1                4
2                12
3                19
4                21
5                11
6                9
7                8
8                14

C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 5>
*/