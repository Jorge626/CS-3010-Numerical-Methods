package com.company;
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
        gaussianElimination(matrix);
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
            matrix = getMatrixFromFile();
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
                } else if (j > 2 & j != n ) {
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
        return matrix;
    }

    public static float[][] gaussianElimination(float[][] matrix){
        // Locate largest element in first row
        float[][] largestCoefficients = getLargestCoefficient(matrix);
        for (int i = 0; i < matrix.length - 1; i++) {
            float[][] column = getColumn(matrix, i);
            float[][] scaledRatios = getScaledRatios(column, largestCoefficients, i);
            System.out.println("~~~~~~~~~~~~~~~~~\nScaled Ratios:");
            printMatrix(scaledRatios);
            int pivotRow = 0;
            float largestValue = scaledRatios[0][0];
            for (int j = 1; j < scaledRatios.length; j++) {
                if (scaledRatios[j][0] > largestValue)
                    pivotRow = j;
            }
            if (pivotRow != 0) {
                System.out.printf("~~~~~~~~~~~~~~~~~~~~\nPivot row is %d\nNew matrix is:\n", pivotRow + 1);
                pivotRows(matrix, i, pivotRow);
                pivotRows(largestCoefficients, i, pivotRow);
                printMatrix(matrix);
            }
            else
                System.out.print("No pivot\n");
            System.out.print("Making zeroes...\n");
            matrix = makeZeroes(matrix, i);
            System.out.print("New matrix is:\n");
            printMatrix(matrix);
        }
        System.out.println("Performing backwards substitution...");
        float[] x = backSubstitution(matrix);
        for (int i = 0; i < x.length; i++)
            System.out.printf("x_%d = %.2f\n", i, x[i]);
        return matrix;
    }

    public static float[][] getColumn(float[][] matrix, int columnNumber){
        float[][] column = new float[matrix.length - columnNumber][1];
        int counter = columnNumber;
        for (int i = 0; i < matrix.length - columnNumber; i++) {
            column[i][0] = matrix[counter][columnNumber];
            counter++;
        }
        return column;
    }

    public static float[][] getLargestCoefficient(float[][] matrix){
        float[][] largestCoefficients = new float[matrix.length][1];
        for (int i = 0; i < matrix.length; i++) {
            float largestValue = 0;
            for (int j = 0; j < matrix.length; j++){
                if (largestValue < Math.abs(matrix[i][j]))
                    largestValue = Math.abs(matrix[i][j]);
            }
            largestCoefficients[i][0] = largestValue;
        }
        return largestCoefficients;
    }

    public static float[][] getScaledRatios(float[][] column, float[][] largestCoefficients, int iteration){
        float[][] scaledRatios = new float[column.length][1];
        int counter = iteration;
        for(int i = 0; i < column.length; i++) {
            scaledRatios[i][0] = Math.abs(column[i][0]) / largestCoefficients[counter][0];
            counter++;
        }
        return scaledRatios;
    }

    public static void pivotRows(float[][] matrix, int row, int pivotRow){
        for(int i = 0; i < matrix[0].length; i++) {
            float tempValue = matrix[row][i];
            matrix[row][i] = matrix[pivotRow][i];
            matrix[pivotRow][i] = tempValue;
        }
    }

    public static float[][] makeZeroes(float[][] matrix, int column){
        float[] rowWithOne = new float[matrix[0].length];
        for (int i = column; i < matrix.length; i++){
            float multiple = matrix[i][column];
            if (i == column){
                for (int j = column; j < matrix[0].length; j++) {
                    matrix[i][j] = matrix[i][j] / multiple;
                    rowWithOne[j] = matrix[i][j];
                }
            }
            else if (i > column){
                float[] tempRow = rowWithOne;
                for (int j = 0; j < tempRow.length; j++)
                    tempRow[j] = tempRow[j] * (-multiple);

                for (int j = 0; j < matrix[0].length; j++)
                    matrix[i][j] = tempRow[j] + matrix[i][j];
            }
        }
        return matrix;
    }

    public static float[] backSubstitution(float[][] matrix){
        int length = matrix.length;
        float[] x = new float[length];
        if (matrix[length - 1][matrix[0].length - 2] != 1) {
            matrix[length - 1][matrix[0].length - 1] = matrix[length - 1][matrix[0].length - 1] / matrix[length - 1][matrix[0].length - 2];
            matrix[length - 1][matrix[0].length - 2] = matrix[length - 1][matrix[0].length - 2] / matrix[length - 1][matrix[0].length - 2];
            x[length - 1] = matrix[length - 1][matrix[0].length - 1];
        }
        for (int i = length - 2; i > -1; i--){
            float sum = -1;
            for (int j = 0; j < length; j++){
                if (x[j] != 0)
                    matrix[i][j] = matrix[i][j] * x[j];
                sum += matrix[i][j];
            }
            x[i] = sum + matrix[i][matrix.length];
        }
        return x;
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
