package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class LongestCommonSubsequence {

    private int[][] matrix;
    private char[][] direction;
    private char[] firstStrCharArray;
    private char[] secondStrCharArray;

    public LongestCommonSubsequence(String firstStr, String secondStr) {
        if (firstStr == null || secondStr == null) {
            throw new IllegalArgumentException("Argument cannot be null!");
        }
        if (firstStr.length() < 1 || secondStr.length() < 1) {
            throw new IllegalArgumentException("Both strings must be at least 1 character long!");
        }
        this.matrix = new int[secondStr.length() + 1][firstStr.length() + 1];
        iniciateMatrix(matrix);
        this.direction = new char[secondStr.length()][firstStr.length()];
        this.firstStrCharArray = firstStr.toCharArray();
        this.secondStrCharArray = secondStr.toCharArray();
        prepareMatrix();
    }

    public String findLCS() {
        StringBuilder result = new StringBuilder();
        int j = firstStrCharArray.length;
        int i = secondStrCharArray.length;
        int length = matrix[i][j];
        while (result.length() < length) {
            if (direction[i - 1][j - 1] == '\\') {
                result.append(secondStrCharArray[i - 1]);
                i--;
                j--;
            } else if (direction[i - 1][j - 1] == '<') {
                j--;
            } else {
                i--;
            }
        }
        return result.reverse().toString();
    }

    public void display() {
        if (firstStrCharArray.length > 31 || secondStrCharArray.length > 31) {
            System.out.println("Cannot display matrix for words longer than 31 characters.");
            return;
        }
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        List<Integer> xCords = new ArrayList<>();
        List<Integer> yCords = new ArrayList<>();
        getPathXY(xCords, yCords);
        System.out.print("  ");
        for (int i = 0; i < firstStrCharArray.length + 1; i++) {
            if (i != 0) {
                printChar(firstStrCharArray[i - 1]);
            } else {
                System.out.print("  j");
            }
        }
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            if (i != 0) {
                printChar(secondStrCharArray[i - 1]);
            } else {
                System.out.print("  i");
            }
            for (int j = 0; j < matrix[0].length; j++) {
                if (i > 0 && j > 0) {
                    if (xCords.size() > 0 && j - 1 == xCords.get(0) && i - 1 == yCords.get(0)) {
                        xCords.remove(0);
                        yCords.remove(0);
                        System.out.print(ANSI_RED + direction[i - 1][j - 1] + matrix[i][j] + " " + ANSI_RESET);
                    } else {
                        System.out.print(" " + matrix[i][j] + " ");
                    }
                } else {
                    System.out.print(" " + matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private void iniciateMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = 0;
        }
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[0][i] = 0;
        }
    }

    private void prepareMatrix() {
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (secondStrCharArray[i - 1] == firstStrCharArray[j - 1]) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                    direction[i - 1][j - 1] = '\\';
                } else {
                    if (matrix[i - 1][j] >= matrix[i][j - 1]) {
                        matrix[i][j] = matrix[i - 1][j];
                        direction[i - 1][j - 1] = '^';
                    } else {
                        matrix[i][j] = matrix[i][j - 1];
                        direction[i - 1][j - 1] = '<';
                    }
                }
            }
        }
    }

    private void printChar(char c) {
        if (c == '\n') {
            System.out.print(" \\n");
        } else if (c == '\t') {
            System.out.print(" \\t");
        } else if (c == '\r') {
            System.out.print(" \\r");
        } else if (c == '\f') {
            System.out.print(" \\f");
        } else {
            System.out.print("  " + c);
        }
    }

    private void getPathXY(List<Integer> xCords, List<Integer> yCords) {
        int counter = 0;
        int j = firstStrCharArray.length;
        int i = secondStrCharArray.length;
        xCords.add(j - 1);
        yCords.add(i - 1);
        i--;
        j--;
        int length = matrix[i][j];
        while (counter < length) {
            if (direction[i][j] == '\\') {
                counter++;
                i--;
                j--;
            } else if (direction[i][j] == '<') {
                j--;
            } else {
                i--;
            }
            xCords.add(j);
            yCords.add(i);
        }
        Collections.reverse(xCords);
        Collections.reverse(yCords);
        if (xCords.get(0) < 0 || yCords.get(0) < 0) {
            xCords.remove(0);
            yCords.remove(0);
        }
    }
}
