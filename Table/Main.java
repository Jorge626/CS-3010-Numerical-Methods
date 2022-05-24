import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        float[][] polynomial = getPolynomial();
        float[][] dividedDiff = getDivideDifferences(polynomial);
        printDividedDiff(dividedDiff);
        printInterpolatingPoly(dividedDiff);
    }

    public static float[][] getPolynomial() throws FileNotFoundException {
        File file = new File("/Users/jorge/Desktop/data.txt");
        Scanner scan = new Scanner(file);
        ArrayList<String> fileMatrix = new ArrayList<String>();
        while (scan.hasNextLine()){
            fileMatrix.add(scan.nextLine());
        }
        int n = fileMatrix.size();
        String x = fileMatrix.get(0);
        List<String> xColumns = Arrays.asList(x.split(" "));
        float[][] polynomial = new float[n][xColumns.size()];
        for (int i = 0; i < n; i++){
            String row = fileMatrix.get(i);
            List<String> rowValues = Arrays.asList(row.split(" "));
            for (int j = 0; j < xColumns.size(); j++){
                polynomial[i][j] = Float.parseFloat(rowValues.get(j));
            }
        }
        return polynomial;
    }

    public static float[][] getDivideDifferences(float[][] polynomial) {
        int c = polynomial[0].length;
        int r = 2 + (c - 1);
        float[][] dividedDiff = new float[r][c];
        for (int i = 0; i < polynomial.length; i++)
            for (int j = 0; j < c; j++)
                dividedDiff[i][j] = polynomial[i][j];

        int it = 0;
        int totalDiffs = c;
        for (int i = 2; i < dividedDiff.length; i++) {
            totalDiffs--;
            it++;
            int it2 = it;
            for (int j = 0; j < totalDiffs; j++) {
                float f1 = dividedDiff[i - 1][j];
                float f2 = dividedDiff[i - 1][j + 1];
                float x1 = dividedDiff[0][j];
                float x2 = dividedDiff[0][it2];
                dividedDiff[i][j] = (f2 - f1) / (x2 - x1);
                it2++;
            }
        }

        return dividedDiff;
    }

    public static void printDividedDiff(float[][] dividedDiff){
        System.out.print("Divided Difference Table\n~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.printf("%-12s%-12s", "x", "f[]");
        int it = 1;
        for(int i = 0; i < dividedDiff.length - 2; i++) {
            String s = "f[";
            for (int j = 0; j < it; j++){
                s = s.concat(",");
            }
            s = s.concat("]");
            System.out.printf("%-12s", s);
            it++;
        }

        for(int i = 0; i < dividedDiff[0].length; i++){
            System.out.println();
            for (int j = 0; j < dividedDiff.length; j++){
                if (dividedDiff[j][i] != 0 || j == 0)
                    System.out.printf("%-12.2f", dividedDiff[j][i]);
            }
        }
    }

    public static void printInterpolatingPoly(float[][] dividedDiff){
        String s = "";
        System.out.println("\n\nInterpolating Form\n~~~~~~~~~~~~~~~~~~");
        for (int i = 1; i < dividedDiff.length; i++){
            String y = String.valueOf(dividedDiff[i][0]);
            if (i + 1 == dividedDiff.length)
                System.out.printf("%s%s", y, s);
            else
                System.out.printf("%s%s + ", y, s);
            s = s.concat("(x - ");
            s = s.concat(String.valueOf(dividedDiff[0][i - 1]));
            s = s.concat(")");
        }
    }
}

