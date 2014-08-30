/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roshan.project;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * k=no of clusters
 * size=size of data set
 * dataset[][]=the dataset
 * c1 is the data object of class Clustering
 */
import java.util.*;
import java.io.*;
class Clustering {

public int i = 0, j = 0, l = 0, k = 0, m = 0, r = 0, noofmedoids = 0, size = 0;
int c_id = 1, tempx = 0, tempy = 0, medoid_id_index = 0;
int totalcost, prevcost,presentcost;
int x=0,y=0,z=0;
int medoid_id[] = new int[100];
int[][] dataset = new int[500][2];
int[][] medoidarr = null;
int[][] nonmedoidarr = null;
int[][] prevmedoidarr = null;
int[][] prevnonmedoidarr = null;

Clustering(int k) {

this.noofmedoids = k;

    }
public void read(String str) throws FileNotFoundException
    {

        Scanner s = new Scanner(new File(str));
while (s.hasNextInt()) {
dataset[size][0] = s.nextInt();
dataset[size++][1] = s.nextInt();
        }
medoidarr = new int[size][3];
nonmedoidarr = new int[size - k][3];
    }
public void generate(String str,int len) throws IOException
    {

PrintWriter pr = new PrintWriter(str);  
int temp1,temp2,arsize=0,flag=0,ab=0;
int writearr[][]=new int[len][2];
        temp1 = (int) Math.floor(100 * Math.random());
writearr[arsize][0]=temp1;
        temp2 = (int) Math.floor(100 * Math.random());
writearr[arsize][1]=temp2;
for(arsize=1;arsize<len;arsize++)
        {

while(true)
            {
flag=0;
            temp1 = (int) Math.floor(100 * Math.random());
            temp2 = (int) Math.floor(100 * Math.random());
for(ab=0;ab<arsize;ab++)
            {
if(writearr[ab][0]==temp1&&writearr[ab][1]==temp2)
                {
flag=1;
break;
                }
            }
if(flag==0)
            {
writearr[arsize][0]=temp1;
writearr[arsize][1]=temp2;
break;
            }
            }

        }

for (int ij=0; ij<len ; ij++){            
pr.println(writearr[ij][0]+" " + writearr[ij][1]);        
}        
pr.close();

    }
public void randomele() {
int temprand, flag;
while (true) {
temprand = (int) Math.floor(100 * Math.random());
if (temprand>= size) {
continue;
            } else {
medoid_id[medoid_id_index++] = temprand;

break;
            }
        }
for (i = 1; i <noofmedoids; i++) {
while (true) {
flag = 0;
temprand = (int) Math.floor(100 * Math.random());
if (temprand< size) {
for (j = 0; j < i; j++) {
if (temprand == medoid_id[j]) {
break;
                        } else {
flag++;
                        }
                    }
                }
if (flag == i) {
medoid_id[medoid_id_index++] = temprand;
break;
                }
            }

        }
for (l = 0, j = 0; l <noofmedoids; l++, j++) {

medoidarr[l][0] = c_id++;
medoidarr[l][1] = dataset[medoid_id[j]][0];
medoidarr[l][2] = dataset[medoid_id[j]][1];
        }


    }

public void cluster() {
        m=0;c_id = 0;

for (i = 0; i < size; i++) {
int flag1 = 0;
int dist = 0;
int prev = 999999;

           // c_id = 0;
for (j = 0; j <noofmedoids; j++) {


if (medoidarr[j][1] == dataset[i][0] &&medoidarr[j][2] == dataset[i][1]) {
                    flag1 = 1;

break;
                }

            }
if (flag1 == 1) {
continue;
            } else {
for (j = 0; j <noofmedoids; j++) {


dist = (Math.abs(medoidarr[j][1] - dataset[i][0]) + Math.abs(medoidarr[j][2] - dataset[i][1]));
if (prev>dist) {
prev = dist;
c_id = j + 1;


                    }

                }

                //System.out.println("value of c_id:"+c_id+"m "+m);
nonmedoidarr[m][0] = c_id;
nonmedoidarr[m][1] = dataset[i][0];
nonmedoidarr[m++][2] = dataset[i][1];
               // System.out.println("after value of c_id:"+c_id+"m "+m);

            }

        }//cost();       
    }

public int cost() {
        // prevcost = totalcost;
totalcost = 0;
for (i = 0; i <noofmedoids; i++) {
for (j = 0; j < (size - noofmedoids); j++) {
if (medoidarr[i][0] == nonmedoidarr[j][0]) {
totalcost += (Math.abs(nonmedoidarr[j][1] - medoidarr[i][1]) + Math.abs(nonmedoidarr[j][2] - medoidarr[i][2]));
                }
            }
        }
return totalcost;
    }
public void checkandswap()
    {        
tempx=tempy=0;
for(x=0;x<noofmedoids;x++)
        {

for(y=0;y<size;y++)
            {

for(z=0;z<size-noofmedoids;z++)
                if(dataset[y][0]==nonmedoidarr[z][1]&&dataset[y][1]==nonmedoidarr[z][2]){
tempx=medoidarr[x][1];
tempy=medoidarr[x][2]; 

prevcost=cost();
System.out.println("cost:"+prevcost);
medoidarr[x][1]=nonmedoidarr[z][1];
medoidarr[x][2]=nonmedoidarr[z][2];


cluster();

presentcost=cost();
System.out.println("after swap cost:"+presentcost);
if(presentcost>=prevcost)
                    {
medoidarr[x][1]=tempx;
medoidarr[x][2]=tempy;
cluster();
System.out.println("Old is better");
                    }
else
                    {
                        z=0;
System.out.println("New is better");
                    }

                    }

            }

        }
    }
public void display()
    {
displaymedoids();
displaynonmedoids();
displayclusters();
    }
public void displaymedoids() {
System.out.println("The medoids are:");
System.out.println("------------------------------------------");
System.out.println("Cluster Id\tX\t\t Y");
for (i = 0; i <noofmedoids; i++) {
System.out.print(medoidarr[i][0] + "\t\t" + medoidarr[i][1] + "\t\t" + medoidarr[i][2] + "\n");
        }
System.out.println("------------------------------------------");
System.out.println("------------------------------------------");


}
public void displaynonmedoids()
{
System.out.println("The Non medoids are:");
System.out.println("------------------------------------------");
System.out.println("Cluster Id\tX\t\t Y");
for (i = 0; i < (size - noofmedoids); i++)
System.out.print(nonmedoidarr[i][0] + "\t\t" + nonmedoidarr[i][1] + "\t\t" + nonmedoidarr[i][2] + "\n");
System.out.println("------------------------------------------");
System.out.println("------------------------------------------");
}
public void displayclusters()
        {
System.out.println("The clusters are:");
System.out.println("Cluster Id\tX\t\t Y");
System.out.println("------------------------------------------");
for (i = 1; i <= noofmedoids; i++) {
for(j=0;j<noofmedoids;j++)
if(i==medoidarr[j][0])
System.out.print(medoidarr[j][0] + "\t\t" + medoidarr[j][1] + "\t\t" + medoidarr[j][2] + "\n");
for(j=0;j<(size-noofmedoids);j++)
if(i==nonmedoidarr[j][0])
System.out.print(nonmedoidarr[j][0] + "\t\t" + nonmedoidarr[j][1] + "\t\t" + nonmedoidarr[j][2] + "\n");

        }
System.out.println("------------------------------------------");
System.out.println("------------------------------------------");
        }
}

public class RoshanProject {

public static void main(String[] args) throws FileNotFoundException,IOException {
int noofmedoids = 15, i = 0, j = 0, size = 0;
int dataset[][] = new int[100][2];

        String file="D:\\dwdmdataset.txt";

        Clustering c1 = new Clustering(noofmedoids);
c1.generate(file,50);
c1.read(file);
c1.randomele();
c1.cluster();
System.out.println("init cost:"+c1.cost());
c1.display();
c1.checkandswap();
c1.display();
System.out.println("after swap cost:"+c1.cost());
    }
}
