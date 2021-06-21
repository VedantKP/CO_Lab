/*
Name: Vedant Puranik
Roll No: 43152
Class: BE-9
Batch: P9
Lab: COL
Assignment 6: Maximal Flow using Ford-Fulkerson algorithm
*/

import java.io.*;
import java.lang.*;
import java.util.*;

class MaximalFlow {

	boolean bfs(int rGraph[][], int s, int t, int parent[], int V)
	{
		
		boolean visited[] = new boolean[V];
		for (int i = 0; i < V; ++i)
			visited[i] = false;

		// Create a queue, enqueue source vertex and mark source vertex as visited
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(s);
		visited[s] = true;
		parent[s] = -1;

		// BFS Loop
		while (queue.size() != 0) 
        {
	        int u = queue.poll();

			for (int v = 0; v < V; v++) 
            {
				if (visited[v] == false && rGraph[u][v] > 0) 
                {
					// If we find a connection to the sink node, then there is no point in BFS anymore We just have to set its parent and can return true
					if (v == t) 
                    {
						parent[v] = u;
						return true;
					}
					queue.add(v);
					parent[v] = u;
					visited[v] = true;
				}
			}
		}

		// If sink not reached, return false
		return false;
	}

	int fordFulkerson(int graph[][], int s, int t, int V)
	{
		int u, v;

		int rGraph[][] = new int[V][V];

		for (u = 0; u < V; u++)
			for (v = 0; v < V; v++)
				rGraph[u][v] = graph[u][v];

		// This array is filled by BFS and to store path
		int parent[] = new int[V];

		int max_flow = 0; // Zero initial flow

		// Augment the flow while tere is path from source to sink
		while (bfs(rGraph, s, t, parent, V)) 
        {
			int path_flow = Integer.MAX_VALUE;
			for (v = t; v != s; v = parent[v]) 
            {
				u = parent[v];
				path_flow
					= Math.min(path_flow, rGraph[u][v]);
			}

			for (v = t; v != s; v = parent[v]) 
            {
				u = parent[v];
				rGraph[u][v] -= path_flow;
				rGraph[v][u] += path_flow;
			}
			max_flow += path_flow;
		}
		return max_flow;
	}

	// Driver program to test above functions
	public static void main(String[] args) throws java.lang.Exception
	{
		MaximalFlow m = new MaximalFlow();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of vertices in graph => ");
        int V = sc.nextInt();

        int[][] graph = new int[V][V];

        System.out.println("Enter flow for every edge: ");
        for(int i=0;i<V;i++)
        {
            for(int j=0;j<V;j++)
            {
                System.out.print("Enter flow from " + i + " to " + j + " => ");
                graph[i][j] = sc.nextInt();
            }
        }

		System.out.println("\nThe maximum possible flow is " + m.fordFulkerson(graph, 0, V-1, V));

        sc.close();
	}
}

/*
OUTPUT

C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 6>javac MaximalFlow.java

C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 6>java MaximalFlow
Enter number of vertices in graph =>
6
Enter flow for every edge:
Enter flow from 0 to 0 => 0
Enter flow from 0 to 1 => 16
Enter flow from 0 to 2 => 13
Enter flow from 0 to 3 => 0
Enter flow from 0 to 4 => 0
Enter flow from 0 to 5 => 0
Enter flow from 1 to 0 => 0
Enter flow from 1 to 1 => 0
Enter flow from 1 to 2 => 10
Enter flow from 1 to 3 => 12
Enter flow from 1 to 4 => 0
Enter flow from 1 to 5 => 0
Enter flow from 2 to 0 => 0
Enter flow from 2 to 1 => 4
Enter flow from 2 to 2 => 0
Enter flow from 2 to 3 => 0
Enter flow from 2 to 4 => 14
Enter flow from 2 to 5 => 0
Enter flow from 3 to 0 => 0
Enter flow from 3 to 1 => 0
Enter flow from 3 to 2 => 9
Enter flow from 3 to 3 => 0
Enter flow from 3 to 4 => 0
Enter flow from 3 to 5 => 20
Enter flow from 4 to 0 => 0
Enter flow from 4 to 1 => 0
Enter flow from 4 to 2 => 0
Enter flow from 4 to 3 => 7
Enter flow from 4 to 4 => 0
Enter flow from 4 to 5 => 4
Enter flow from 5 to 0 => 0
Enter flow from 5 to 1 => 0
Enter flow from 5 to 2 => 0
Enter flow from 5 to 3 => 0
Enter flow from 5 to 4 => 0
Enter flow from 5 to 5 => 0

The maximum possible flow is 23

C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 6>

*/