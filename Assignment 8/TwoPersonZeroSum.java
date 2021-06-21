import java.lang.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class TwoPersonZeroSum{

    private Boolean hasSaddlePoint(int miniMax, int maxiMin)
    {
        if(miniMax == maxiMin)
            return true;
        return false;
    }

    private int[] getColumn(Integer[][] payOffMatrix, int column)
    {
        return IntStream.range(0, payOffMatrix.length).map(i -> payOffMatrix[i][column]).toArray();
    }

    private int locateElement(Integer[] arr, int element)
    {
        return IntStream.range(0, arr.length).filter(i -> element == arr[i]).findFirst().orElse(-1);
    }

    public void findOptimalStrategy(Integer[][] payOffMatrix)
    {
        Integer[] maxiMinArray = new Integer[payOffMatrix.length];
        Integer[] miniMaxArray = new Integer[payOffMatrix[0].length];
        int maxiMin,maxiMinIndex;
        int miniMax,miniMaxIndex;

        for(int i=0;i<maxiMinArray.length;i++)
            maxiMinArray[i] = Collections.min(Arrays.asList(payOffMatrix[i]));

        for(int j=0;j<miniMaxArray.length;j++)
            miniMaxArray[j] = Collections.max(Arrays.stream(getColumn(payOffMatrix,j)).boxed().collect(Collectors.toList()));

        /*System.out.println("maxiMinArray is => ");
        for(int i=0;i<maxiMinArray.length;i++)
            System.out.print(maxiMinArray[i] + " ");

        System.out.println("\nminiMaxArray is => ");
        for(int i=0;i<miniMaxArray.length;i++)
            System.out.print(miniMaxArray[i] + " ");
        */

        maxiMin = Collections.max(Arrays.asList(maxiMinArray));
        miniMax = Collections.min(Arrays.asList(miniMaxArray));
        
        System.out.println();
        System.out.println("Value of maxiMin => " + maxiMin);
        System.out.println("Value of miniMax => " + miniMax);
        System.out.println();

        if(hasSaddlePoint(miniMax,maxiMin))
        {
            System.out.println("As maxiMin=miniMax, saddle point exists!");

            maxiMinIndex = locateElement(maxiMinArray, maxiMin);
            miniMaxIndex = locateElement(miniMaxArray, miniMax);

            System.out.println("Saddle point at location => (" + maxiMinIndex + "," + miniMaxIndex + ")");
            System.out.println("Value of the game => " + payOffMatrix[maxiMinIndex][miniMaxIndex]);
        }
        else
        {
            System.out.println("As maxiMin is not equal to miniMax, saddle point does not exist!");
            System.out.println("Optimal strategy does not exist!");
        }
    }

    public static void main(String[] args)
    {
        int rows, cols;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rows => ");
        rows = sc.nextInt();
        System.out.print("Enter number of columns => ");
        cols = sc.nextInt();
        
        Integer[][] payoffMatrix = new Integer[rows][cols];

        System.out.println("\nEnter payoff matrix:");

        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
            {
                System.out.print("Enter cell[" + i + "][" + j + "] => ");
                payoffMatrix[i][j] = sc.nextInt();
            }
        }

        TwoPersonZeroSum game = new TwoPersonZeroSum();
        game.findOptimalStrategy(payoffMatrix);

        sc.close();
    }
}

/*
OUTPUT

Enter number of rows => 2
Enter number of columns => 3

Enter payoff matrix:
Enter cell[0][0] => 2
Enter cell[0][1] => 3
Enter cell[0][2] => 4
Enter cell[1][0] => 1
Enter cell[1][1] => 2
Enter cell[1][2] => 3

Value of maxiMin => 2
Value of miniMax => 2

As maxiMin=miniMax, saddle point exists!
Saddle point at location => (0,0)       
Value of the game => 2
*/