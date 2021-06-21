/*
Name: Vedant Puranik
Roll No: 43152
Class: BE-9
Batch: P9
Lab: COL
Assignment 4: Hungarian Method to solve the assignment problem
*/

#include<stdio.h>
#include<iomanip>
#include<iostream>
using namespace std;

#define MAX 50
enum boolean{FALSE,TRUE};

class HungarianMethod{
    int data[MAX][MAX];
    int allocation[MAX][MAX];
    int no_of_rows,no_of_columns;
    int bal_stat;

    public:
        HungarianMethod(){
            int i,j;
            for(i=0;i<MAX;i++){
                for(j=0;j<MAX;j++){
                    data[i][j]=0;
                    allocation[i][j]=0;
                }
            }
            no_of_rows=no_of_columns=bal_stat=0;
        }
        void setRow(int no){no_of_rows=no;}
        void setColumn(int no){no_of_columns=no;}
        void getData();
        void makeAllocation();
        void display();
        void rowMinima(int [][MAX],int,int);
        void columnMinima(int [][MAX],int,int);
        boolean checkValue(int,int,int [][MAX]);
};

void HungarianMethod::getData(){
    int i,j;
    cout<<"Enter cost matrix =>\n";
    for(i=0;i<no_of_rows;i++){
        cout<<"Enter row "<<i+1<<"=> ";
        for(j=0;j<no_of_columns;j++)
            cin>>data[i][j];
    }
}
void copyArray(int startRow,int startCol,int endRow,int endCol,int temp[][MAX],int start1row,int start1col,int ans[][MAX]){
    int i,j,k,l;
    for(i=startRow,k=start1row;i<endRow;i++,k++)
        for(j=startCol,l=start1col;j<endCol;j++,l++)
            ans[k][l]=temp[i][j];
}
int getMinVal(int temp[],int no){
    int min=temp[0];
    for(int i=0;i<no;i++)
        if(min>temp[i])
            min=temp[i];
    return min;
}
int getPosition(int temp[],int no,int value){
    for(int i=0;i<no;i++)
        if(temp[i]==value)
            return i;
    return -1;
}
int countVal(int temp[],int no,int value){
    int i,sum=0;
    for(i=0;i<no;i++)
        if(temp[i]==value)
            sum++;
    return sum;
}
void HungarianMethod::rowMinima(int temp[][MAX],int row,int col){
    int i,j,min;
    for(i=0;i<row;i++){
        min=9999;
        for(j=0;j<col;j++)
            if(min>temp[i][j])
                min=temp[i][j];
        for(j=0;j<col;j++)
            temp[i][j]-=min;
    }
}
void HungarianMethod::columnMinima(int temp[][MAX],int row,int col){
    int i,j,min;
    for(i=0;i<row;i++){
        min=9999;
        for(j=0;j<col;j++)
            if(min>temp[j][i])
                min=temp[j][i];
        for(j=0;j<col;j++)
            temp[j][i]-=min;
    }
}
boolean HungarianMethod::checkValue(int row,int col,int temp[][MAX]){
    int i,j;
    for(i=0;i<row;i++)
        for(j=0;j<col;j++)
            if(temp[i][j]==0)
                return TRUE;
    return FALSE;

}
void HungarianMethod::makeAllocation(){
    int temp_data[MAX][MAX]={0};
    int i,j;
    if(no_of_rows>no_of_columns){
        for(i=0;i<no_of_rows;i++)
            data[i][no_of_columns]=0;
        no_of_columns++;
        bal_stat=1;
    }else if(no_of_rows<no_of_columns){
        for(i=0;i<no_of_columns;i++)
            data[no_of_rows][i]=0;
        no_of_rows++;
        bal_stat=2;
    }
    copyArray(0,0,no_of_rows,no_of_columns,data,0,0,temp_data);
    rowMinima(temp_data,no_of_rows,no_of_columns);
    columnMinima(temp_data,no_of_rows,no_of_columns);
    int min,pos,count;
    int tempCol[MAX]={0};
    while(checkValue(no_of_rows,no_of_columns,temp_data)){
        for(i=0;i<no_of_rows;i++){
            count=countVal(temp_data[i],no_of_columns,0);
            if(count==1){
                pos=getPosition(temp_data[i],no_of_columns,0);
                allocation[i][pos]=data[i][pos];
                for(j=0;j<no_of_rows;j++)
                    if(temp_data[j][pos]==0)
                        temp_data[j][pos]=9999;
            }
        }
        for(i=0;i<no_of_rows;i++){
            for(j=0;j<no_of_columns;j++)
                tempCol[j]=temp_data[j][i];
            count=countVal(tempCol,no_of_rows,0);
            if(count==1){
                pos=getPosition(tempCol,no_of_rows,0);
                allocation[i][pos]=data[i][pos];
                for(j=0;j<no_of_columns;j++)
                    if(temp_data[pos][j]==0)
                        temp_data[pos][j]=9999;
            }

        }
    }
}
void HungarianMethod::display(){
    int i,j;
    cout<<"\nGiven Cost Matrix =>\n";
    for(i=0;i<no_of_rows;i++)
        cout<<"\t"<<char(65+i);
    cout<<endl;
    for(i=0;i<no_of_rows;i++){
        cout<<i+1;
        for(j=0;j<no_of_columns;j++)
            cout<<"\t"<<data[i][j];
        cout<<endl;
    }
    if(bal_stat!=0){
        cout<<"\n\nThe given cost matrix is not a square matrix\n";
        cout<<"Hence this is an unbalanced problem and as a solution";
        cout<<"\n we have to add an extra "<<((bal_stat==1)?" column":"row")<<" with 0 value in each\n";
    }
    cout<<"\n\nOpportunity Matrix => \n";
    rowMinima(data,no_of_rows,no_of_columns);
    columnMinima(data,no_of_rows,no_of_columns);
    for(i=0;i<no_of_rows;i++){
        for(j=0;j<no_of_columns;j++)
            cout<<"\t"<<data[i][j];
        cout<<endl;
    }
    int sum=0;
    cout<<"\n\nJobs\t:\tMachine\t:\tCost\n";
    for(i=0;i<no_of_rows;i++)
        for(j=0;j<no_of_columns;j++)
            if(allocation[i][j]!=0){
                cout<<i+1<<"\t:\t"<<char(65+j)<<"\t:\t"<<allocation[i][j];
                sum+=allocation[i][j];
                cout<<endl;
            }
    cout<<"\nTotal Assignment Cost => "<<sum<<"";
}

int main(){
    HungarianMethod hm;
    int row,col;

    cout<<"Enter number of rows => ";
    cin>>row;
    cout<<"Enter number of columns =>";
    cin>>col;

    hm.setRow(row);
    hm.setColumn(col);
    hm.getData();
    hm.makeAllocation();
    hm.display();
    return 0;
} 

/*
OUTPUT
C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 4>g++ HungarianMethod.cpp

C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 4>a.exe
enter no of row :3
enter no of column :3
enter cost Metrix :
enter 1 row :120 100 80
enter 2 row :80 90 100
enter 3 row :110 140 120

Given Cost Metrix :
        A       B       C
1       120     100     80
2       80      90      100
3       110     140     120


Opportunity Matrix :
        40      10      0
        0       0       20
        0       20      10


Jobs    :       Machine :       Cost
1       :       C       :       80
2       :       B       :       90
3       :       A       :       110

Total Assignment Cost = 280 RS.

C:\Users\vedan\Documents\College\Final_Year\CO\Assignment 4>

*/
