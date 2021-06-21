/*
Name: Vedant Puranik
Roll No: 43152
Class: BE-9
Batch: P9
Lab: COL
Assignment 1: Simplex
*/

#include <iostream>
#include <iomanip>
#include <vector>
#include <map>
#include <climits>
#include <cmath>
#define MAX_COUNT numberOfConstraints + numberOfVariables
using namespace std;

class Simplex
{
    private:
    int numberOfVariables, numberOfConstraints;
    vector <vector <float>> coeffTable; 
    vector <string> variables;
    vector <string> slackVars;
    vector <string> updatedVariables;
    vector <float> b;
    vector <float> c; 
    vector <float> Cb; 
    vector <float> z; 
    vector <float> theta; 
    vector <float> cjZj; 
    float maximum;
    bool isUnbounded;

    public:
    Simplex();  
    bool checkIfNegative(); 
    void updateZ();
    int calcTheta(int varIndex);  
    void calcCjZj(); 
    void updateB(int outgoingVarIndex);    
    void displayTable(); 
    void solve();
};

Simplex::Simplex()
{
        isUnbounded = false;
        maximum = INT_MIN;
        cout<<"For function Z:\n";
        cout<<"Enter number of variables => ";
        cin>>numberOfVariables;
        cout<<"Enter number of constraints => ";
        cin>>numberOfConstraints;

        coeffTable.resize(numberOfConstraints,vector<float>(MAX_COUNT,0.0));
        b.resize(numberOfConstraints,0.0);
        c.resize(MAX_COUNT,0.0); 
        Cb.resize(numberOfConstraints,0.0);
        z.resize(MAX_COUNT+1,0.0);
        cjZj.resize(MAX_COUNT,0.0);
        theta.resize(numberOfConstraints,0);
        variables.resize(numberOfVariables,"x");
        slackVars.resize(numberOfConstraints,"s");
        updatedVariables.resize(numberOfConstraints,"s");
        
        for(int i=0;i<numberOfVariables;i++)
            variables[i] = variables[i] + to_string(i+1);
        
        for(int i=0;i<numberOfConstraints;i++)
        {
            slackVars[i] = slackVars[i] + to_string(i+1);
            updatedVariables[i] = updatedVariables[i] + to_string(i+1);
        }

        cout<<"\nEnter coefficients for Z:\n";
        for(int i=0;i<numberOfVariables;i++)
        {
            cout<<"Enter coefficient of x"<<i+1<<" => ";
            cin>>c[i];
        }

        for(int i= numberOfVariables;i<MAX_COUNT;i++)
            c[i] = 0;
        
        int count = 0, temp = 0;
        for(int i=0;i<numberOfConstraints;i++)
        {
            cout<<"\nFor constraint "<<count+1<<" (1 for (<=), else 0) => ";
            cin>>temp;
            int j;
            for(j=0;j<numberOfVariables;j++)
            {
                cout<<"Enter coefficient of x"<<j+1<<" => ";
                cin>>coeffTable[i][j];
                if(temp==0)
                    coeffTable[i][j] = coeffTable[i][j] * (-1);
            }
            for(j=numberOfVariables;j<MAX_COUNT;j++)
            {
                if(j==numberOfVariables+count)
                    coeffTable[i][j] = 1;
                else
                    coeffTable[i][j] = 0;
            }
            cout<<"Enter the constant for constraint "<<count+1<<" => ";
            cin>>b[i];
            if(temp==0)
                b[i] = b[i] * (-1);
            count++;
        }
        
        for(int i=0;i<MAX_COUNT;i++)
            cjZj[i] = c[i] - z[i];

        cout<<"After Cj - Zj :\n";
        displayTable();
}

void Simplex::displayTable()
{
    cout<<"\tcj";
    for(auto i : c)
        cout<<"\t"<<i;
    cout<<"\n";
    int count = 1;
    
    cout<<"Cb\t"<<"Basis\t";
    
    for(auto i: variables)
        cout<<i<<"\t";

    for(auto i: slackVars)
        cout<<i<<"\t";

    cout<<"b\t"<<"theta\n";

    for(int i=0;i<numberOfConstraints;i++)
    {
        cout<<Cb[i]<<"\t";
        cout<<updatedVariables[i]<<"\t";
        for(int j=0;j<numberOfVariables+numberOfConstraints;j++)
            cout<<setprecision(3)<<coeffTable[i][j]<<"\t";
        
        cout<<b[i]<<"\t"<<theta[i]<<"\n";
        count++;
    }

    cout<<"\tz\t";
    for(auto i: z)
        cout<<i<<"\t";
    
    cout<<"\n";
    cout<<"\tcj-zj\t";
    for(auto i: cjZj)
        cout<<i<<"\t";

    cout<<"\n";
}

bool Simplex::checkIfNegative()
{
    bool allNegative = true;
    for(int i=0;i<MAX_COUNT;i++)
    {
        if(cjZj[i] > 0)
        {
            allNegative = false;
            return allNegative;
        }
        else
            allNegative = true;
    }
    return allNegative;
}

int Simplex::calcTheta(int varIndex)
{
    int minTheta = INT_MAX;
    int minThetaIndex = -1;
    
    for(int i=0;i<numberOfConstraints;i++)
        theta[i] = b[i]/coeffTable[i][varIndex];
    
    for(int i=0;i<numberOfConstraints;i++)
    {
        if(theta[i]<minTheta)
        {
            minThetaIndex = i;
            minTheta = theta[i];
        }
    }
    return minThetaIndex;
}

void Simplex::updateZ()
{
    for(int i=0;i<MAX_COUNT;i++)
    {
        float temp=0.0;
        for(int j=0;j<numberOfConstraints;j++)
            temp += Cb[j] * coeffTable[j][i];
        
        z[i] = temp;
    }
    
    float maxProfit = 0.0;
    for(int i = 0; i < numberOfConstraints; i++)
        maxProfit += Cb[i] * b[i];
    
    z[MAX_COUNT] = maxProfit;
}

void Simplex::calcCjZj()
{
    for(int i=0;i<MAX_COUNT;i++)
        cjZj[i] = c[i] - z[i];
    
    cout<<"Cj-Zj calculated\n";
}

void Simplex::updateB(int outgoingVarIndex)
{
    b[outgoingVarIndex] = theta[outgoingVarIndex];
}

void Simplex::solve()
{
    int iteration = 1;
    while(!checkIfNegative())
    {
        cout<<"\n\nIteration => \t"<<iteration<<"\n";
        float maxProfit = -999999.99;
        int incomingVarIndex = -1; 
        int outgoingVarIndex = -1;
        
        for(int i=0;i<MAX_COUNT;i++)
        {
            cjZj[i] = c[i] - z[i];
            if(cjZj[i]>maxProfit)
            {
                maxProfit = cjZj[i];
                incomingVarIndex = i;
            }              
        }
        
        outgoingVarIndex = calcTheta(incomingVarIndex);

        cout<<"Updating variables"<<"\n";
        if(incomingVarIndex < numberOfVariables)
            updatedVariables[outgoingVarIndex] = variables[incomingVarIndex];
        else
            updatedVariables[outgoingVarIndex] = slackVars[incomingVarIndex];
    
        cout<<"Updating Cb"<<"\n";
        Cb[outgoingVarIndex] = c[incomingVarIndex];
        
        vector <float> temp;
        temp.resize(MAX_COUNT,0.0);
        
        for(int i=0;i<MAX_COUNT;i++)
            temp[i] = coeffTable[outgoingVarIndex][i] / coeffTable[outgoingVarIndex][incomingVarIndex];
        
        for(int i=0;i<MAX_COUNT;i++)
            coeffTable[outgoingVarIndex][i] = temp[i];
        
        temp.clear();
        
        updateB(outgoingVarIndex);
        
        cout<<"Updating the coeffTable"<<"\n";
        for(int i = 0; i < numberOfConstraints; i++)
        {
            float coefficient = (float) coeffTable[i][incomingVarIndex];
            if(i!=outgoingVarIndex)
            {
                cout<<"Calculating "<<updatedVariables[i]<<" - "<<coefficient<<"*"<<updatedVariables[outgoingVarIndex]<<"\n";
                for(int j=0;j<MAX_COUNT;j++)
                    coeffTable[i][j] = coeffTable[i][j] - coefficient * coeffTable[outgoingVarIndex][j];
                
                b[i] = b[i] - coefficient * b[outgoingVarIndex];
            }
        }

        updateZ();
        calcCjZj();
        cout<<"\nTable at the end of iteration "<<iteration<<" is: \n\n";
        displayTable();
        iteration++;
    }

    cout<<"\n\nOptimal solution found!\n";
    map<string, float> FinalSolution;
    for(int i=0;i<updatedVariables.size();i++)
    {
        if(updatedVariables[i].find("x")!=string::npos)
            FinalSolution.insert(pair<string, float> (updatedVariables[i], b[i]));
    }

    FinalSolution.insert(pair<string, int>(string("Max Profit"),z[MAX_COUNT]));
    cout<<"\nFinal Answer : "<<"\n\n";
    for(auto i: FinalSolution)
        cout<<i.first<<" => "<<i.second<<"\n";
    
}

int main()
{
    Simplex S;
    S.solve();
    return 0;
}

/*
For function Z:
Enter number of variables => 2
Enter number of constraints => 3

Enter coefficients for Z:
Enter coefficient of x1 => 2
Enter coefficient of x2 => 5

For constraint 1 (1 for (<=), else 0) => 1
Enter coefficient of x1 => 1
Enter coefficient of x2 => 4
Enter the constant for constraint 1 => 24

For constraint 2 (1 for (<=), else 0) => 1
Enter coefficient of x1 => 3
Enter coefficient of x2 => 1
Enter the constant for constraint 2 => 21

For constraint 3 (1 for (<=), else 0) => 1
Enter coefficient of x1 => 1
Enter coefficient of x2 => 1
Enter the constant for constraint 3 => 9
After Cj - Zj :
        cj      2       5       0       0       0
Cb      Basis   x1      x2      s1      s2      s3      b       theta
0       s1      1       4       1       0       0       24      0
0       s2      3       1       0       1       0       21      0
0       s3      1       1       0       0       1       9       0
        z       0       0       0       0       0       0
        cj-zj   2       5       0       0       0


Iteration =>    1
Updating variables
Updating Cb
Updating the coeffTable
Calculating s2 - 1*x2
Calculating s3 - 1*x2
Cj-Zj calculated

Table at the end of iteration 1 is:

        cj      2       5       0       0       0
Cb      Basis   x1      x2      s1      s2      s3      b       theta
5       x2      0.25    1       0.25    0       0       6       6
0       s2      2.75    0       -0.25   1       0       15      21
0       s3      0.75    0       -0.25   0       1       3       9
        z       1.25    5       1.25    0       0       30
        cj-zj   0.75    0       -1.25   0       0


Iteration =>    2
Updating variables
Updating Cb
Updating the coeffTable
Calculating x2 - 0.25*x1
Calculating s2 - 2.75*x1
Cj-Zj calculated

Table at the end of iteration 2 is:

        cj      2       5       0       0       0
Cb      Basis   x1      x2      s1      s2      s3      b       theta
5       x2      0       1       0.333   0       -0.333  5       24
0       s2      0       0       0.667   1       -3.67   4       5.45
2       x1      1       0       -0.333  0       1.33    4       4
        z       2       5       1       0       1       33
        cj-zj   0       0       -1      0       -1


Optimal solution found!

Final Answer :

Max Profit => 33
x1 => 4
x2 => 5

*/