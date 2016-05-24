package com.andersoncarvalho.pidtp.service.util;

/**
 * Created by anderson on 24/05/16.
 */
public class MatrixShift {
    public static void execute (double[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;

        double[][] shifted = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int y = (i + height / 2) % height;
                int x = (j + width / 2) % width;
                shifted[y][x] = matrix[i][j];
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = shifted[i][j];
            }
        }
    }
}
