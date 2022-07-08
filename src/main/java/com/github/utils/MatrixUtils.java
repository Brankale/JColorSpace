package com.github.utils;

import org.ejml.simple.SimpleMatrix;

public class MatrixUtils {

    private MatrixUtils() {
        // avoid instantiation
    }

    public static SimpleMatrix toSimpleMatrix(FloatArray array, int rows, int cols) {
        SimpleMatrix matrix = new SimpleMatrix(rows, cols);
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                float value = array.get((row * cols) + col);
                matrix.set(row, col, value);
            }
        }
        return matrix;
    }

    public static FloatArray toFloatArray(SimpleMatrix matrix) {
        int rows = matrix.numRows();
        int cols = matrix.numCols();

        FloatArray array = new FloatArray(rows * cols);
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                double value = matrix.get(row, col);
                array.set((row * cols) + col, (float) value);
            }
        }
        return array;
    }

}
