import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        float[][] matrix = getMatrix();
        float stoppingError = getStoppingError();
        float[] startingSolutionsJacobi = getStartingSolutions(matrix);
        float[] startingSolutionsGauss = copyStartingSolutions(startingSolutionsJacobi);
        jacobi(matrix, startingSolutionsJacobi, stoppingError);
        gaussSeidel(matrix, startingSolutionsGauss, stoppingError);
    }

    public static float[][] getMatrix() throws FileNotFoundException{
        Scanner userInput = new Scanner(System.in);
        System.out.print("1) Read matrix from file\n2) Enter matrix\n~~~~~~~~~~~~~~~~~~~~~~~~\nPlease choose an option: ");
        int choice;
        float[][] matrix = null;
        choice = userInput.nextInt();
        if (choice < 1 || choice > 2){
            do {
                System.out.println("Error: Please enter a valid option");
            } while(choice < 1 || choice > 2);
        }
        else if (choice == 1) {
            boolean isDominant;
            do {
                matrix = getMatrixFromFile();
                isDominant = checkDiagonallyDominant(matrix);
                if (!isDominant)
                    System.out.println("Error: Matrix is not diagonally dominant.\nPlease re-enter a file with a diagonally dominant matrix.\n");
            } while(!isDominant);
        }
        else {
            matrix = getMatrixFromUser();
        }
        System.out.println("The following is your matrix:");
        printMatrix(matrix);
        return matrix;
    }

    public static float[][] getMatrixFromFile() throws FileNotFoundException {
        Scanner userInput = new Scanner(System.in);
        //System.out.print("Please enter file location: ");
        //String fileLocation = userInput.nextLine();
        File file = new File("/Users/jorge/Desktop/matrix.txt");
        Scanner scan = new Scanner(file);
        ArrayList<String> fileMatrix = new ArrayList<String>();
        while (scan.hasNextLine()){
            fileMatrix.add(scan.nextLine());
        }
        int n = fileMatrix.size();
        float[][] matrix = new float[n][n + 1];
        for (int i = 0; i < n; i++){
            String row = fileMatrix.get(i);
            List<String> rowValues = Arrays.asList(row.split(" "));
            for (int j = 0; j < n + 1; j++){
                matrix[i][j] = Float.parseFloat(rowValues.get(j));
            }
        }
        return matrix;
    }

    public static float[][] getMatrixFromUser(){
        Scanner userInput = new Scanner(System.in);
        System.out.print("Please enter the number of linear equations to solve (n <= 10): ");
        int n = userInput.nextInt();
        float[][] matrix = new float[n][n + 1];
        boolean isDominant;
        do {
            for (int i = 0; i < n; i++) {
                System.out.printf("Row %d\n~~~~~~~~\n", i + 1);
                for (int j = 0; j < n + 1; j++) {
                    float value = 0;
                    if (j == 0) {
                        System.out.print("Please enter the 1st coefficient: ");
                        value = userInput.nextFloat();
                        matrix[i][j] = value;
                    } else if (j == 1) {
                        System.out.print("Please enter the 2nd coefficient: ");
                        value = userInput.nextFloat();
                        matrix[i][j] = value;
                    } else if (j == 2) {
                        System.out.print("Please enter the 3rd coefficient: ");
                        value = userInput.nextFloat();
                        matrix[i][j] = value;
                    } else if (j > 2 & j != n) {
                        System.out.printf("Please enter the %dth coefficient: ", j + 1);
                        value = userInput.nextFloat();
                        matrix[i][j] = value;
                    } else {
                        System.out.print("Please enter the b value: ");
                        value = userInput.nextFloat();
                        matrix[i][j] = value;
                    }
                }
                System.out.println();
            }
            isDominant = checkDiagonallyDominant(matrix);
            if (!isDominant)
                System.out.println("Error: Matrix is not diagonally dominant.\nPlease re-enter a diagonally dominant matrix.\n");
        } while (!isDominant);
        return matrix;
    }

    public static boolean checkDiagonallyDominant(float[][] matrix){
        boolean isDominant = true;
        boolean strictlyGreater = false;
        for (int i = 0; i < matrix.length; i++){
            float sum = 0;
            float diagonalValue = Math.abs(matrix[i][i]);
            for (int j = 0; j < matrix.length; j++){
                float currentValue =  Math.abs(matrix[i][j]);
                if (matrix[i][j] != matrix[i][i])
                    sum += currentValue;
            }
            if (sum > diagonalValue) {
                isDominant = false;
                break;
            }
            else if (diagonalValue > sum)
                strictlyGreater = true;
        }
        if (strictlyGreater & isDominant)
            return true;
        else
            return false;
    }

    public static float getStoppingError(){
        Scanner userInput = new Scanner(System.in);
        System.out.print("Please enter the desired stopping error: ");
        float stoppingError = userInput.nextFloat();
        return stoppingError;
    }

    public static float[] getStartingSolutions(float[][] matrix){
        float[] startingSolutions = new float[matrix.length];
        Scanner userInput = new Scanner(System.in);
        for (int i = 0; i < startingSolutions.length; i++){
            System.out.printf("Please enter x%d starting solution: ", i);
            startingSolutions[i] = userInput.nextFloat();
        }
        return startingSolutions;
    }

    public static float[] copyStartingSolutions(float[] startingSolutions){
        float[] copy = new float[startingSolutions.length];
        for (int i = 0; i < startingSolutions.length; i++)
            copy[i] = startingSolutions[i];
        return copy;
    }

    public static void jacobi(float[][] matrix, float[] startingSolutions, float stoppingError){
        System.out.print("\nJacobi Iteration Method\n~~~~~~~~~~~~~~~~~~~~~~~\nIteration #  |  x0  |  x...  |  xn  |  " +
                "(L2 < Error)?\n---------------------------------------------------\n");
        float newSolutions[] = new float[startingSolutions.length];
        double prevL2 = 0;
        for(int i = 0; i < 50; i++){
            for (int j = 0; j < startingSolutions.length; j++) {
                float sum = getSums(matrix, startingSolutions, j);
                newSolutions[j] = (matrix[j][matrix[0].length - 1] - sum) / matrix[j][j];
            }
            for (int j = 0; j < startingSolutions.length; j++)
                startingSolutions[j] = newSolutions[j];
            printIteration(newSolutions, i, stoppingError);
            if (checkForBreak(prevL2, getL2(newSolutions), stoppingError, newSolutions))
                break;
            else if (i == 49)
                System.out.print("Error was not reached after 50 iterations...\n");
            prevL2 = getL2(startingSolutions);
        }
        printSolutions(newSolutions);
    }

    public static void gaussSeidel(float[][] matrix, float[] startingSolutions, float stoppingError){
        System.out.print("\nGauss-Seidel Method\n~~~~~~~~~~~~~~~~~~~~~~~\nIteration #  |  x0  |  x...  |  xn  |  (L2 " +
                "< Error)?\n---------------------------------------------------\n");
        double prevL2 = 0;
        for(int i = 0; i < 50; i++){
            for (int j = 0; j < startingSolutions.length; j++) {
                float sum = getSums(matrix, startingSolutions, j);
                startingSolutions[j] = (matrix[j][matrix[0].length - 1] - sum) / matrix[j][j];
            }
            printIteration(startingSolutions, i, stoppingError);
            if (checkForBreak(prevL2, getL2(startingSolutions), stoppingError, startingSolutions))
                break;
            else if (i == 49)
                System.out.print("Error was not reached after 50 iterations...\n");
            prevL2 = getL2(startingSolutions);
        }
        printSolutions(startingSolutions);
    }

    public static void printIteration(float[] solutions, int it, float stoppingError){
        if (it < 9)
            System.out.printf("%d   |", it + 1);
        else
            System.out.printf("%d  |", it + 1);
        for (int i = 0; i < solutions.length; i++) {
            System.out.printf("  %.4f  |", solutions[i]);
        }
        System.out.printf("  %.4f < %.4f\n", getL2(solutions), stoppingError);
    }

    public static boolean checkError(double l2, float stoppingError){
        return l2 < stoppingError;
    }

    public static boolean checkConvergence(double prevL2, double currentL2){
        return prevL2 == currentL2;
    }

    public static boolean checkForBreak(double prevL2, double l2, float stoppingError, float[] solutions){
        if (checkError(getL2(solutions), stoppingError)) {
            System.out.print("L2 is less than stopping error\nStopping iteration...");
            return true;
        }
        else if (checkConvergence(prevL2, getL2(solutions))) {
            System.out.print("Note: Answers converged\nStopping iteration...");
            return true;
        }
        else
            return false;
    }

    public static double getL2(float[] solutions){
        double l2 = 0;
        for (int i = 0; i < solutions.length; i++)
            l2 += solutions[i] * solutions[i];
        return Math.sqrt(l2);
    }

    public static float getSums(float[][] matrix, float[] solutions, int i){
        float sum = 0;
        for (int j = 0; j < matrix[0].length - 1; j++) {
            if (i != j)
                sum += matrix[i][j] * solutions[j];
        }
        return sum;
    }

    public static void printSolutions(float[] solutions){
        System.out.print("\nAnswers are: [");
        for (int i = 0; i < solutions.length; i++)
            System.out.printf(" %.4f", solutions[i]);
        System.out.print(" ]\n");
    }

    public static void printMatrix(float[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (j != matrix.length) {
                    if (matrix[i][j] < 0.0)
                        System.out.printf("%.2f ", matrix[i][j]);
                    else
                        System.out.printf(" %.2f ", matrix[i][j]);
                }
                else {
                    if (matrix[i][j] < 0.0)
                        System.out.printf("\t| %.2f", matrix[i][j]);
                    else
                        System.out.printf("\t|  %.2f", matrix[i][j]);
                }
            }
            System.out.println();
        }
    }
}
