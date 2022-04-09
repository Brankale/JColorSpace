package com.github.utils;

/**
 * A color model is required by a ColorSpace to describe the way colors can be
 * represented as tuples of numbers.
 *
 * A common color model is the RGB color model which defines a color as
 * represented by a tuple of 3 numbers (red, green and blue).
 */
public enum ColorModel {
    XYZ, RGB, Lab
}