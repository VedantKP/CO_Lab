/*
Name: Vedant Puranik
Roll No: 43152
Class: BE-9
Batch: P9
Lab: COL
Assignment 7: 0/1 Knapsack
*/

import java.util.*;
import java.lang.*;

public class Knapsack{
    
    int max(int a, int b) { return (a > b)? a : b; }

    int knapSack(int W, int[] wt, int[] val, int n)
    {
        int i, w;
        int K[][] = new int[n+1][W+1];
    
        for (i = 0; i <= n; i++)
        {
            for (w = 0; w <= W; w++)
            {
                if (i==0 || w==0)
                    K[i][w] = 0;
                else if (wt[i-1] <= w)
                        K[i][w] = max(val[i-1] + K[i-1][w-wt[i-1]],K[i-1][w]);
                else
                        K[i][w] = K[i-1][w];
            }
        }
        return K[n][W];
    }

    public static void main(String[] args)
    {
        int i, n, W;
        int[] val = new int[20];
        int[] wt = new int[20];

        Scanner sc = new Scanner(System.in);
        Knapsack bag = new Knapsack();

        System.out.print("Enter number of items => ");
        n = sc.nextInt();
    
        System.out.println("Enter value and weight of items => ");
        for(i = 0;i < n; ++i)
        {
    	    System.out.print("Value of item " + i + " => ");
            val[i] = sc.nextInt();
            System.out.print("Weight of item " + i + " => ");
            wt[i] = sc.nextInt();
        }
 
        System.out.println("Enter size of knapsack => ");
        W = sc.nextInt();
        
        System.out.println("\nAnswer => " + bag.knapSack(W, wt, val, n));

        sc.close();
    }
}

/*
OUTPUT

C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 7>java Knapsack
Enter number of items => 5
Enter value and weight of items =>
Value of item 0 => 1
Weight of item 0 => 1
Value of item 1 => 6
Weight of item 1 => 2
Value of item 2 => 18
Weight of item 2 => 5
Value of item 3 => 22
Weight of item 3 => 6
Value of item 4 => 28
Weight of item 4 => 7
Enter size of knapsack =>
11

Answer => 40

C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 7>

*/