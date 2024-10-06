import java.io.*;
import java.util.*;

public class MatrixMultiplier {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] matrix1 = null;
        int[][] matrix2 = null;

        if (args.length == 2) {
            String fileName1 = args[0];
            String fileName2 = args[1];
            matrix1 = readMatrixFromFile(fileName1);
            matrix2 = readMatrixFromFile(fileName2);
        } else {

            System.out.print("Enter the size of the square matrices (or two file names): ");
            if (scanner.hasNextInt()) {
                int size = scanner.nextInt();
                matrix1 = generateRandomMatrix(size);
                matrix2 = generateRandomMatrix(size);
                writeMatrixToFile("matrix1.txt", matrix1);
                writeMatrixToFile("matrix2.txt", matrix2);
                System.out.println("Random matrices generated and written to matrix1.txt and matrix2.txt.");
            } else {
                String fileName1 = scanner.next();
                String fileName2 = scanner.next();
                matrix1 = readMatrixFromFile(fileName1);
                matrix2 = readMatrixFromFile(fileName2);
            }
        }

        if (matrix1 != null && matrix2 != null) {
            int[][] result = multiplyMatrices(matrix1, matrix2);
            if (result != null) {
                writeMatrixToFile("matrix3.txt", result);
                System.out.println("Matrix multiplication complete. Result written to matrix3.txt.");
            }
        } else {
            System.out.println("Matrix multiplication could not be performed.");
        }
        scanner.close();
    }

    private static int[][] readMatrixFromFile(String fileName) {
        List<int[]> matrixList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.trim().split("\\s+");
                int[] row = Arrays.stream(values).mapToInt(Integer::parseInt).toArray();
                matrixList.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + fileName);
            e.printStackTrace();
            return null;
        }
        return matrixList.toArray(new int[matrixList.size()][]);
    }

    private static int[][] generateRandomMatrix(int size) {
        Random rand = new Random();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rand.nextInt(10);
            }
        }
        return matrix;
    }

    private static void writeMatrixToFile(String fileName, int[][] matrix) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (int[] row : matrix) {
                for (int value : row) {
                    writer.print(value + " ");
                }
                writer.println();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + fileName);
            e.printStackTrace();
        }
    }

    private static int[][] multiplyMatrices(int[][] a, int[][] b) {
        if (a[0].length != b.length) {
            System.err.println("Matrix multiplication not possible. Check dimensions.");
            return null;
        }
        int[][] result = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
}
