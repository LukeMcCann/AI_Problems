import java.util.ArrayList;

public class NQueens_CNF {

    static final int N = 8;
    static final int VARS = N*N;
    static int clauses = 0;
    static ArrayList<String> DIMACS_CNF = new ArrayList();
    static int[] solution = {-1,-2,-3,-4,-5,6,-7,-8,-9,-10,-11,-12,-13,-14,-15,16,17,-18,-19,-20,-21,-22,-23,-24,-25,-26,-27,-28,-29,-30,-31,32,-33,-34,-35,-36,-37,38,-39,-40,-41,42,-43,-44,-45,-46,-47,-48,-49,-50,-51,-52,-53,54,-55,-56,57,-58,-59,-60,-61,-62,-63,-64,0};

    /**
     * Generate DIMACS CNF format and print any given SAT solution
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Generate DIMACS CNF format 
        addFacts();
        atLeastOneQueenInRow();
        atMostOneQueenInRow();
        atMostOneQueenInColumn();
        diagonalBottomSemiSquareOfUpLeftToBottomRight();
        diagonalUpperSemiSquareOfUpLeftToBottomRight();
        diagonalUpperSemiSquareOfUpRightToBottomLeft();
        diagonalBottomSemiSquareOfUpRightToBottomLeft();
        print_DIMACS_CNF_format();

        // Print a SAT solution
        printSATSolutionBoard(solution);

        System.out.println(DIMACS_CNF);
    }

    /**
     * Convert given row and column to a variable
     *
     * @param row [1...N]
     * @param column [1...N]
     * @return variable number
     */
    private static int toVariable(int row, int column)
    {
        return (N*(row-1) + (column-1) + 1);
    }

    private static void addFacts()
    {
        // Facts
        DIMACS_CNF.add("c Pre-assigned entries");
        // Update the number of facts according to the number of added DIMACS CNF clauses
        int facts=0;
        //DIMACS_CNF.add(toVariable(1, 1) + " 0");
        clauses += facts;
    }

    private static void atLeastOneQueenInRow()
    {
        // There is at least one queen in each column   
        DIMACS_CNF.add("c There is at least one queen in each row:");
        String str;
        for (int row = 1; row <= N; row++)
        {
            str = "";
            for (int column = 1; column <= N; column++)
            {
                str += toVariable(row,column) + " ";
            }
            DIMACS_CNF.add(str + "0");
            clauses++;
        }
    }

    private static void atMostOneQueenInRow()
    {
        // There is at most one queen in each row
        DIMACS_CNF.add("c There is at most one queen in each row:");
        for(int row = 1; row <= N; row++)
        {
            for(int column = 1; column <= N-1; column++)
            {
                for(int i = column+1; i <= N; i++)
                {
                    DIMACS_CNF.add(" -" + toVariable(row, column) + (" -" + toVariable( row, i) + " 0"));
                    clauses++;
                }
            }
        }
    }


    private static void atMostOneQueenInColumn()
    {
        // There is at most one queen in each column
    }

    private static void diagonalBottomSemiSquareOfUpLeftToBottomRight()
    {
        // There is at most one queen in each diagonal
        DIMACS_CNF.add("c Diagonal bottom semi-square of up-left to bottom-right:");
        for (int d = 0; d <= N-2; d++)
        {
            for (int j = 1; j <= N-d; j++)
            {
                for (int k = j+1; k <= N-d; k++)
                {
                    DIMACS_CNF.add("-" + toVariable(d+j,j) + " -" + toVariable(d+k,k) + " 0");
                    clauses++;
                }
            }
        }
    }

    private static void diagonalUpperSemiSquareOfUpLeftToBottomRight(){
        // There is at most one queen in each diagonal
        DIMACS_CNF.add("c Diagonal upper semi-square of up-left to bottom-right:");
        for (int d = -(N-1); d <= -1; d++)
        {
            for (int j = 1; j <= N+d; j++)
            {
                for (int k = j+1; k <= N+d; k++)
                {
                    DIMACS_CNF.add("-" + toVariable(j,j-d) + " -" + toVariable(k,k-d) + " 0");
                    clauses++;
                }
            }
        }
    }

    private static void diagonalUpperSemiSquareOfUpRightToBottomLeft()
    {
        // There is at most one queen in each diagonal
        DIMACS_CNF.add("c Diagonal upper semi-square of up-right to bottom-left:");
        for (int d = 3; d <= N+1; d++)
        {
            for (int j = 1; j <= d-1; j++)
            {
                for (int k = j+1; k <= d-1; k++)
                {
                    DIMACS_CNF.add("-" + toVariable(j,d-j) + " -" + toVariable(k,d-k) + " 0");
                    clauses++;
                }
            }
        }
    }

    private static void diagonalBottomSemiSquareOfUpRightToBottomLeft()
    {
        // There is at most one queen in each diagonal
        DIMACS_CNF.add("c Diagonal bottom semi-square of up-right to bottom-left:");
        for (int d = N+2; d <= 2*N-1; d++)
        {
            for (int j = d-N; j <= N; j++)
            {
                for (int k = j+1; k <= N; k++)
                {
                    DIMACS_CNF.add("-" + toVariable(j,d-j) + " -" + toVariable(k,d-k) + " 0");
                    clauses++;
                }
            }
        }
    }

    /**
     * Print DIMACS CNF format
     */
    private static void print_DIMACS_CNF_format()
    {
        // Print DIMACS CNF format 
        System.out.println("==========================================");
        System.out.println("===== Beginning of DIMACS CNF format =====");
        System.out.println("==========================================");
        System.out.println("c row range: [1..." + N + "]");
        System.out.println("c column range: [1..." + N + "]");
        System.out.println("c board[row][column]: variable");
        for (int row = 1; row <= N; row++)
        {
            for (int column = 1; column <= N; column++)
            {
                System.out.println("c board[" + row + "][" + column + "]: " + toVariable(row,column));
            }
        }
        System.out.println("c #vars: " + VARS);
        System.out.println("c #clauses: " + clauses);
        System.out.println("p cnf " + VARS + " " + clauses);
        for (int i = 0; i < DIMACS_CNF.size(); i++)
        {
            System.out.println(DIMACS_CNF.get(i));
        }
        System.out.println("====================================");
        System.out.println("===== End of DIMACS CNF format =====");
        System.out.println("====================================");
        System.out.println("");
    }

    /**
     * Print resulting board based on a set of variables from a SAT solution
     * @param variables
     */
    private static void printSATSolutionBoard(int[] variables)
    {
        int tmp;
        int row;
        int column;

        int[][] tmpBoard = new int [N][N];
        for (int i = 0; i < variables.length; i++)
        {
            if(variables[i] > 0){
                tmp = variables[i]-1;
                row = tmp/N;
                column = tmp%N;
                tmpBoard[row][column] = 1;
            }
        }

        System.out.println("=======================");
        System.out.println("===== Given board =====");
        System.out.println("=======================");
        for (row = 0; row < N; row++)
        {
            System.out.print("    ");
            for (column = 0; column < N; column++)
            {
                System.out.print(((tmpBoard[row][column])==1? "Q":"-") + " ");
            }
            System.out.println();
        }
        System.out.println("=======================");
    }
}