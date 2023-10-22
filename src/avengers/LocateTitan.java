package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

        String locateTitanInputFile = args[0];
        String locateTitanOutputFile = args[1];
        StdIn.setFile(locateTitanInputFile);
        StdOut.setFile(locateTitanOutputFile);
        int s = StdIn.readInt();

        double[] funcArr = new double[s];
        int[][] adjacenceyMatx = new int[s][s];
        for(int i = 0; i < s; i++) {
            funcArr[StdIn.readInt()] = StdIn.readDouble();
        }
        int i = 0;
        while (i < s) {
            int j = 0;
            while (j < s) {
                adjacenceyMatx[i][j] = (int)(StdIn.readInt() / (funcArr[i] * funcArr[j]));
                j++;
            }
            i++;
        }

        int[] minimumCost = new int[s];
        boolean[] dijkstraSet = new boolean[s];

        for (int f = 0; f < minimumCost.length; f++) {
            if (f == 0)
                minimumCost[f] = 0;
            else
                minimumCost[f] = Integer.MAX_VALUE;
        }
        for (int k = 0; k < s; k++) {
            int minimumIndex = -1;
            int minimumDistance = Integer.MAX_VALUE;
            int b = 0;
            do {
                if (!dijkstraSet[b]) {
                    if (minimumCost[b] <= minimumDistance) {
                        minimumDistance = minimumCost[b];
                        minimumIndex = b;
                    }
                }
                b++;
            } while (b < s);


            if (minimumIndex == -1) {

                break;
            }

            dijkstraSet[minimumIndex] = true;
            int j = 0;
            while (j < s) {
                if (adjacenceyMatx[minimumIndex][j] > 0) {
                    if (!dijkstraSet[j]) {
                        int nDistance = minimumCost[minimumIndex] + adjacenceyMatx[minimumIndex][j];
                        if (nDistance < minimumCost[j]) {
                            minimumCost[j] = nDistance;
                        }
                    }
                }
                j++;
            }
        }
        
        StdOut.print(minimumCost[s-1]);
    }

    public static int minimumCost(int[] minimumCost, boolean[]dijkstraSet, int s){
        int m = Integer.MAX_VALUE;
        int vert =-1;
        int i = 0;
        while (i < s) {
            if (!dijkstraSet[i]) {
                if (minimumCost[i] < m) {
                    vert = i;
                    m = minimumCost[i];
                }
            }
            i++;
        }
        System.out.println(vert + " vertex");
        return vert;
        }
}
