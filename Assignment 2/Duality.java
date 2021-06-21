/*
Name: Vedant Puranik
Roll No: 43152
Class: BE-9
Batch: P9
Lab: COL
Assignment 2: Duality Theory
*/

import java.util.*;
import java.lang.*;

public class Duality{
    
    public void displayProblem(int[][] M, int n, int m, int Z[], int problemType, char zOrW, char v)
    {
        if(problemType==1)
		    System.out.print("\nMax\t" + zOrW + " = ");
	    else if(problemType==2)
            System.out.print("\nMin\t" + zOrW + " = ");

        for(int i=0;i<n;i++)
        {
            System.out.print(Z[i] + "" + v + "" + (i+1));
            if(i==(n-1))
                System.out.print("");
            else System.out.print(" + ");   
        }

        System.out.println("");
        System.out.println("");
        System.out.print("Subject to:");

        for(int i=0; i<m; i++)
	    {
            System.out.println();
            for(int j=0; j<n; j++)
            {
                System.out.print(M[i][j] + "" + v + "" + (j+1));
                if(j==(n-1))
                    System.out.print("");
                else System.out.print(" + ");
            }
            if(problemType==1)
                System.out.print(" <= ");
            else if(problemType==2)
                System.out.print(" >= ");
            System.out.print(M[i][n]);
        }
    }
    
    public void printAnalysis(List<String> heading, int[][] analysisTable)
    {
        Iterator<String> it = heading.iterator();
        while(it.hasNext())
            System.out.print(it.next() + "\t");

        System.out.println();

        for(int i=0;i<2;i++)
        {
            if(i==0)
                System.out.print("Primal");
            else System.out.print("Dual");
            
            for(int j=0;j<2;j++)
            {
                System.out.print("\t\t" + analysisTable[i][j]);
            }
            if(analysisTable[i][2] == 1)
                System.out.print("\t" + "Max");
            else System.out.print("\t" + "Min");
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        System.out.println("");
        System.out.println("***Duality Theory***");
        System.out.println("");

        int m,n,problemType;
        int[] Z = new int[10];
        int[] W = new int[10];
        int[][] analysisTable = new int[5][5];
	    int[][] M = new int[10][10];
        int[][] Md = new int[10][10];
        Scanner sc = new Scanner(System.in);
        List<String> heading = new ArrayList<String>();
        heading.add("");
        heading.add("Constraints");
        heading.add("Dec Vars");
        heading.add("");

        System.out.print("Enter type of problem (1 = Max, 2 = Min) => ");
        problemType = sc.nextInt();
        System.out.println();

        System.out.print("Enter number of decision variables => ");
        n = sc.nextInt();
        System.out.println();

        System.out.print("Enter number of constraints => ");
        m = sc.nextInt();
        System.out.println();

        analysisTable[0][0] = m;
        analysisTable[0][1] = n;
        analysisTable[0][2] = problemType;

        analysisTable[1][0] = n;
        analysisTable[1][1] = m;

        if(analysisTable[0][2]==1)
            analysisTable[1][2] = 2;
        else
            analysisTable[1][2] = 1;
            
        System.out.println("Enter co-efficients for objective function Z:");
        for(int i=0;i<n;i++)
        {
            System.out.print("x" + (i+1) + " => ");
            Z[i] = sc.nextInt();
        }
        
        for(int i=0;i<m;i++)
		    for(int j=0;j<(n+m+1);j++)
			    M[i][j]=0;
        
        //input constraints and store it in matrix M
	    for(int i=0; i<m; i++)
	    {
            System.out.println("\nCoefficient of constraint " + (i+1) + ":");
            for(int j=0;j<n;j++)
            {
                System.out.print("Coefficient of x" + (j+1) + " => ");
                M[i][j] = sc.nextInt();
            }
            System.out.print("\nEnter solution of constraint " + (i+1) + " => ");
            M[i][n] = sc.nextInt();					//last column of matrix stores the solution
        }

        Duality duality = new Duality();
        
        System.out.println("");
        System.out.println("");
        System.out.println("******Primal Problem******");
        
        duality.displayProblem(M,analysisTable[0][1],analysisTable[0][0],Z,analysisTable[0][2],'Z','x'); 

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("******Analysis Table******");
        System.out.println("");

        duality.printAnalysis(heading, analysisTable);
       
        //assigning W
        for(int i=0; i<analysisTable[1][1]; i++)
        {
            W[i]=M[i][n];
        }

        //assigning new constraints
        for(int i=0; i<analysisTable[1][0]; i++)
        {
            for(int j=0; j<analysisTable[1][1]; j++)
            {
                Md[i][j]=M[j][i];
            }
            Md[i][analysisTable[1][1]]=Z[i];
        }

        System.out.println("");
        System.out.println("");
        System.out.println("******Dual Problem******");
        
        duality.displayProblem(Md,analysisTable[1][1],analysisTable[1][0],W,analysisTable[1][2],'W','y');
        System.out.println("");
        System.out.println("");
        sc.close();
    }
}

/*

***Duality Theory***

Enter type of problem (1 = Max, 2 = Min) => 1

Enter number of decision variables => 2

Enter number of constraints => 3

Enter co-efficients for objective function Z:
x1 => 3
x2 => 5

Coefficient of constraint 1:
Coefficient of x1 => 2
Coefficient of x2 => 6

Enter solution of constraint 1 => 50

Coefficient of constraint 2:
Coefficient of x1 => 3
Coefficient of x2 => 2

Enter solution of constraint 2 => 35

Coefficient of constraint 3:
Coefficient of x1 => 5
Coefficient of x2 => -3

Enter solution of constraint 3 => 10


******Primal Problem******

Max     Z = 3x1 + 5x2

Subject to:
2x1 + 6x2 <= 50
3x1 + 2x2 <= 35
5x1 + -3x2 <= 10


******Analysis Table******

        Constraints     Dec Vars
Primal          3               2       Max
Dual            2               3       Min


******Dual Problem******

Min     W = 50y1 + 35y2 + 10y3

Subject to:
2y1 + 3y2 + 5y3 >= 3
6y1 + 2y2 + -3y3 >= 5

*/