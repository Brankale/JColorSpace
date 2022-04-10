package com.github.utils;

import java.util.Arrays;

public class FloatArray {

    private final float[] array;

    /**
     * Create a float array of the specified size
     *
     * @param size of teh array
     */
    public FloatArray(int size) {
        array = new float[size];
    }

    public FloatArray(float... v) {
        array = Arrays.copyOf(v, v.length);
    }

    /**
     * @return the size of the array
     */
    public int size() {
        return array.length;
    }

    /**
     * Set a value at the specified index
     *
     * @param index of the array
     * @param value to set
     * @throws IndexOutOfBoundsException if the index is greater than the size
     */
    public void set(int index, float value) {
        array[index] = value;
    }

    /**
     * Get the value at the specified index
     *
     * @param index of the array
     * @return the value stored at the index
     * @throws IndexOutOfBoundsException if the index is greater than the size
     */
    public float get(int index) {
        return array[index];
    }

}
