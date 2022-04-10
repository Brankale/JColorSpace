package com.github;

/**
 * Class for constructing white points used in RGB color space.
 * The value is stored in the CIE XYZ color space.
 * The Y component of the white point is assumed to be 1.
 */
public class WhitePoint {

    public final float x;
    public final float y;

    /**
     * Create a WhitePoint given CIE xyY coordinates.
     *
     * @param x The x coordinate of CIExyY
     * @param y The y coordinate of CIExyY
     */
    public WhitePoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Illuminant for CIE XYZ white point
     */
    public WhitePoint(float x, float y, float z) {
        throw new UnsupportedOperationException();
    }

}
