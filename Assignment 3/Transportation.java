/*
Name: Vedant Puranik
Roll No: 43152
Class: BE-9
Batch: P9
Lab: COL
Assignment 3: Transportation Problem
*/

import java.util.*;
import java.util.stream.IntStream;
import java.lang.*;

public class Transportation{

    public void printMatrix(int[][] costMatrix, int rows, int cols, int[] demand, int[] supply)
    {
        for(int i=0;i<cols;i++)
            System.out.print("\t");
        System.out.print("\tSupply");

        for(int i=0;i<rows;i++)
        {
            System.out.println();
            for(int j=0;j<cols;j++)
                System.out.print("\t" + costMatrix[i][j]);    
            System.out.print("\t" + supply[i]); 
        }
        System.out.println("");
        System.out.print("Demand");
        for(int i=0;i<cols;i++)
            System.out.print("\t" + demand[i]);
        System.out.println("");    
    }

    public void leastCost(int[][] costMatrix, int rows, int cols, int[] demand, int[] supply)
    {
        int iLeast = 0, jLeast = 0;
        int checkZeros = 0;
        int allocationValue = 0;
        int totalCost = 0;
        int allocationCounter = 0;

        int[][] allocations = new int[rows][cols]; 

        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++)
                allocations[i][j] = 0;

        System.out.println("\n\nCost Matrix is => ");
        printMatrix(costMatrix, rows, cols, demand, supply);

        while(true)
        {
            checkZeros = 0;
            int min = Integer.MAX_VALUE;
            iLeast = 0;
            jLeast = 0;
            for(int i=0;i<rows;i++)
            {
                for(int j=0;j<cols;j++)
                {
                    if((costMatrix[i][j] <= min) && supply[i]!=0 && demand[j]!=0 && allocations[i][j]==0)
                    {
                        iLeast = i;
                        jLeast = j;
                        min = costMatrix[iLeast][jLeast];
                    }
                    else
                         checkZeros++;
                }
            }
            
            if(checkZeros == (rows*cols)) //Check if all allocations done i.e. demand and supply are empty arrays
                break;

            allocationCounter++;

            if(demand[jLeast] < supply[iLeast])
                allocationValue = demand[jLeast];
            else
                allocationValue = supply[iLeast];    
 
            totalCost = totalCost + costMatrix[iLeast][jLeast]*allocationValue;    
            allocations[iLeast][jLeast] = allocationValue;
            supply[iLeast] = supply[iLeast] - allocationValue;
            demand[jLeast] = demand[jLeast] - allocationValue; 
        }

        System.out.println("\n\nAllocation Matrix is => ");
        printMatrix(allocations, rows, cols, demand, supply);

        System.out.println("\n\nTotal Cost is => " + totalCost);
        if(allocationCounter == (rows+cols-1))
            System.out.println("This is a non-degenerate solution");
        else
            System.out.println("This is a degenerate solution");    
    }

    public void vogelsApprox(int[][] costMatrix, int rows, int cols, int[] demand, int[] supply)
    {
        int[] demandDiff = new int[cols];
        int[] supplyDiff = new int[rows];
        boolean flagRows = false;
        boolean flagCols = false;
        int lowest = 0;
        int secondLowest = 0;
        int totalCost = 0;
        int iLeast = 0, jLeast = 0;
        int allocationCounter = 0;

        int[][] allocations = new int[rows][cols]; 

        for(int i=0;i<rows;i++)
            supplyDiff[i] = Integer.MIN_VALUE;
        
        for(int j=0;j<cols;j++)
            demandDiff[j] = Integer.MIN_VALUE;

        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++)
                allocations[i][j] = 0;


        System.out.println("Cost Matrix is =>\n");
        printMatrix(costMatrix, rows, cols, demand, supply);
        
        while(true)
        {       
            flagCols = false;
            flagRows = false;
            //Traverse row wise....find diff between smallest and second smallest element per row
            for(int i=0;i<rows;i++)
            {
                lowest = Integer.MAX_VALUE;
                secondLowest = Integer.MAX_VALUE;
                if(supply[i]==0)
                    continue;
                for(int j=0;j<cols;j++)
                {
                    if(demand[j] == 0)
                        continue;
                    if(allocations[i][j]==0)
                    {
                        if(costMatrix[i][j] <= lowest && secondLowest==Integer.MAX_VALUE && lowest==Integer.MAX_VALUE)
                        {
                            lowest = costMatrix[i][j];
                        }
                        else if(costMatrix[i][j] <= lowest)
                        {
                            secondLowest = lowest;
                            lowest = costMatrix[i][j];                        
                        }
                        else if(costMatrix[i][j] <= secondLowest && costMatrix[i][j] >= lowest)
                        {
                            secondLowest = costMatrix[i][j];
                        }
                        flagRows = true;
                    }
                }
                if(secondLowest == Integer.MAX_VALUE)
                    supplyDiff[i] = lowest;
                else
                    supplyDiff[i] = secondLowest - lowest;
            }


            //Traverse column wise....find diff between the smallest and second smallest element per column
            for(int j=0;j<cols;j++)
            {
                lowest = Integer.MAX_VALUE;
                secondLowest = Integer.MAX_VALUE;
                if(demand[j] == 0)
                    continue;
                for(int i=0;i<rows;i++)
                {
                    if(supply[i] == 0)
                        continue;
                    if(allocations[i][j]==0)
                    {
                        if(costMatrix[i][j] <= lowest && secondLowest==Integer.MAX_VALUE && lowest==Integer.MAX_VALUE)
                        {
                            lowest = costMatrix[i][j];
                        }
                        else if(costMatrix[i][j] <= lowest)
                        {
                            secondLowest = lowest;
                            lowest = costMatrix[i][j];                        
                        }
                        else if(costMatrix[i][j] <= secondLowest && costMatrix[i][j] >= lowest)
                        {
                            secondLowest = costMatrix[i][j];
                        }
                        flagCols = true;
                    }
                }
                
                if(secondLowest == Integer.MAX_VALUE)
                    demandDiff[j] = lowest;
                else
                    demandDiff[j] = secondLowest - lowest;
            }

            if(flagRows == false && flagCols == false)
                break;

            allocationCounter++;
            
            int greatest = Integer.MIN_VALUE;
            int supplyGreatestIndex = 0;
            int demandGreatestIndex = 0;
        
            for(int i=0;i<rows;i++)
                if(supplyDiff[i]!=Integer.MIN_VALUE && supplyDiff[i] >= greatest)
                {
                    greatest = supplyDiff[i];
                    supplyGreatestIndex = i;
                }
            
            greatest = Integer.MIN_VALUE;    
            for(int j=0;j<cols;j++)
                if(demandDiff[j]!=Integer.MIN_VALUE && demandDiff[j] >= greatest)
                {
                    greatest = demandDiff[j];
                    demandGreatestIndex = j;
                }
            
            int allocationValue = 0;
                
            if(demandDiff[demandGreatestIndex] >= supplyDiff[supplyGreatestIndex])
            {
                int leastRowIndex = 0;
                lowest = Integer.MAX_VALUE;
                for(int i=0;i<rows;i++)
                {
                    if(costMatrix[i][demandGreatestIndex] <= lowest && allocations[i][demandGreatestIndex] == 0 && supply[i] != 0)
                    {
                        lowest = costMatrix[i][demandGreatestIndex];
                        leastRowIndex = i;
                    }
                }
                iLeast = leastRowIndex;
                jLeast = demandGreatestIndex;
            }    
            else
            {
                int leastColumnIndex = 0;
                lowest = Integer.MAX_VALUE;
                for(int j=0;j<cols;j++)
                {
                    if(costMatrix[supplyGreatestIndex][j] <= lowest && allocations[supplyGreatestIndex][j] == 0 && demand[j]!=0)
                    {
                        lowest = costMatrix[supplyGreatestIndex][j];
                        leastColumnIndex = j;
                    }
                }
                iLeast = supplyGreatestIndex;
                jLeast = leastColumnIndex;
            }

            
            if(demand[jLeast] < supply[iLeast])
                allocationValue = demand[jLeast];
            else
                allocationValue = supply[iLeast];    

            totalCost = totalCost + costMatrix[iLeast][jLeast]*allocationValue;    
            allocations[iLeast][jLeast] = allocationValue;
            supply[iLeast] = supply[iLeast] - allocationValue;
            demand[jLeast] = demand[jLeast] - allocationValue;

            for(int i=0;i<rows;i++)
                if(supply[i]==0)
                    supplyDiff[i] = Integer.MIN_VALUE;

            for(int j=0;j<cols;j++)
                if(demand[j]==0)
                    demandDiff[j] = Integer.MIN_VALUE; 
      
        } 
        
        System.out.println("\nFinal Allocation Matrix is => ");
        printMatrix(allocations, rows, cols, demand, supply);

        System.out.println("\nTotal Cost is => " + totalCost);
        if(allocationCounter == (rows+cols-1))
            System.out.println("This is a non-degenerate solution");
        else
            System.out.println("This is a degenerate solution");    
        
    }

    public static void main(String[] args)
    {
        int rows,cols,demandSum,supplySum,valueCol=0,valueRow=0;
        char option;
        Transportation transport = new Transportation();

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rows => ");
        rows = sc.nextInt();
        System.out.print("Enter number of cols => ");
        cols = sc.nextInt();

        int[] demandTemp = new int[cols];
        int[] supplyTemp = new int[rows];

        int[][] costMatrixTemp = new int[rows][cols];
        System.out.println("Enter cost matrix: ");
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
            {
                System.out.print("Element [" + i + "][" + j + "] => ");
                costMatrixTemp[i][j] = sc.nextInt();
            }
        }
        
        System.out.println("Enter demand by column: ");
        for(int i=0;i<cols;i++)
        {
            System.out.print("For column " + (i+1) + " => ");
            demandTemp[i] = sc.nextInt();
        }

        System.out.println("Enter supply by row: ");
        for(int i=0;i<rows;i++)
        {
            System.out.print("For row " + (i+1) + " => ");
            supplyTemp[i] = sc.nextInt();
        }

        demandSum = IntStream.of(demandTemp).sum();
        supplySum = IntStream.of(supplyTemp).sum();
        //-

        if(demandSum > supplySum)
        {
            System.out.println("As demand is greater than supply, adding an extra row to make them equal");
            rows = rows + 1;
            valueRow = demandSum - supplySum;
        }
        else if(demandSum < supplySum)
        {
            System.out.println("As supply is greater than demand, adding an extra column to make them equal");
            cols = cols + 1;
            valueCol = supplySum - demandSum;
        }

        int[] demand = new int[cols];
        int[] supply = new int[rows];

        for(int i=0;i<supplyTemp.length;i++)
            supply[i] = supplyTemp[i];
        for(int i=0;i<demandTemp.length;i++)
            demand[i] = demandTemp[i];
        
        if(demandSum > supplySum)
            supply[supply.length-1] = valueRow;    
        else if(supplySum > demandSum)
            demand[demand.length-1] = valueCol;

        System.out.println("Demand is => ");
        for(int i=0;i<cols;i++)
            System.out.print(demand[i] + " ");

        System.out.println("");
        System.out.println("Supply is => ");
        for(int i=0;i<rows;i++)
            System.out.print(supply[i] + " ");    

        //- 
        int[][] costMatrix = new int[rows][cols];
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
            {
                if(((demandSum > supplySum) && i==rows-1) || ((demandSum < supplySum) && j==cols-1))
                    costMatrix[i][j] = 0;
                else
                    costMatrix[i][j] = costMatrixTemp[i][j];    
            } 
        }
    
        System.out.println("\n\nCost Matrix is => ");
        transport.printMatrix(costMatrix, rows, cols, demand, supply);

        System.out.println("\nWhich method do you want to use to find the feasible solution?\na.Least Cost\nb.Vogel's Approximation\nc.Exit");
        System.out.print("Enter option => ");
        option = sc.next().charAt(0);
        switch(option)
        {
            case 'a':
                transport.leastCost(costMatrix, rows, cols, demand, supply);
                break;
            
            case 'b':
                transport.vogelsApprox(costMatrix, rows, cols, demand, supply);
                break;

            case 'c':
                sc.close();
                return;
                
            default:
                System.out.println("Wrong Option");
                break;     
        }
        sc.close();
    }
}

/*
Least Cost Output

Enter number of rows => 3
Enter number of cols => 4
Enter cost matrix: 
Element [0][0] => 2
Element [0][1] => 3
Element [0][2] => 11
Element [0][3] => 7
Element [1][0] => 1
Element [1][1] => 0
Element [1][2] => 6
Element [1][3] => 1
Element [2][0] => 5
Element [2][1] => 8
Element [2][2] => 15
Element [2][3] => 9
Enter demand by column: 
For column 1 => 7
For column 2 => 5
For column 3 => 3
For column 4 => 2
Enter supply by row: 
For row 1 => 6
For row 2 => 1
For row 3 => 10
Demand is => 
7 5 3 2 
Supply is =>
6 1 10

Cost Matrix is =>
                                        Supply
        2       3       11      7       6
        1       0       6       1       1
        5       8       15      9       10
Demand  7       5       3       2

Which method do you want to use to find the feasible solution?
a.Least Cost
b.Vogel's Approximation
c.Exit
Enter option => a

Cost Matrix is =>
                                        Supply
        2       3       11      7       6
        1       0       6       1       1
        5       8       15      9       10
Demand  7       5       3       2


Allocation Matrix is =>
                                        Supply
        6       0       0       0       0
        0       1       0       0       0
        1       4       3       2       0
Demand  0       0       0       0


Total Cost is => 112
This is a non-degenerate solution

--------------------------------------------------------------------------
Vogel's Approximation output:

Enter number of rows => 5
Enter number of cols => 3
Enter cost matrix: 
Element [0][0] => 2
Element [0][1] => 7
Element [0][2] => 4
Element [1][0] => 3
Element [1][1] => 3
Element [1][2] => 7
Element [2][0] => 5
Element [2][1] => 4
Element [2][2] => 1
Element [3][0] => 1
Element [3][1] => 6
Element [3][2] => 2
Element [4][0] => 0
Element [4][1] => 0
Element [4][2] => 0
Enter demand by column: 
For column 1 => 70
For column 2 => 90
For column 3 => 180
Enter supply by row: 
For row 1 => 50
For row 2 => 70
For row 3 => 80
For row 4 => 100
For row 5 => 40
Demand is => 
70 90 180
Supply is =>
50 70 80 100 40 

Cost Matrix is =>
                                Supply
        2       7       4       50
        3       3       7       70
        5       4       1       80
        1       6       2       100
        0       0       0       40
Demand  70      90      180

Which method do you want to use to find the feasible solution?
a.Least Cost
b.Vogel's Approximation
c.Exit
Enter option => b
Cost Matrix is =>

                                Supply
        2       7       4       50
        3       3       7       70
        5       4       1       80
        1       6       2       100
        0       0       0       40
Demand  70      90      180

Final Allocation Matrix is =>
                                Supply
        50      0       0       0
        20      50      0       0
        0       0       80      0
        0       0       100     0
        0       40      0       0
Demand  0       0       0

Total Cost is => 590
This is a degenerate solution
*/