package com.github;

/**
 * Class for constructing white points used in RGB color space.
 * The value is stored in the CIE XYZ color space.
 * The Y component of the white point is assumed to be 1.
 */
public class WhitePoint {

    public final float x;
    public final float y;
    public final float z;

    /**
     * Create a WhitePoint given CIE xyY coordinates.
     *
     * @param x The x coordinate of CIExyY
     * @param y The y coordinate of CIExyY
     */
    public WhitePoint(float x, float y) {
        // X = (x * Y) / y
        this.x = x / y;
        // Y is assumed to be 1
        this.y = 1.0f;
        // Z = (z * Y) / y, where z = 1 - x -y
        this.z = (1 - x - y) / y;
    }

    /**
     * Illuminant for CIE XYZ white point.
     */
    public WhitePoint(float x, float y, float z) {
        if (y != 1.0f)
            System.err.println("white point: trying to set Y != 1");
        this.x = x;
        this.y = 1.0f;
        this.z = z;
    }

}
