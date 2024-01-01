package com.github.brankale.jcolorspace.colorspace.rgb;

/**
 * Class for constructing white points used in RGB color space.
 * The value is stored in the CIE XYZ color space.
 * Y component of white point is assumed to be 1.
 */
public class WhitePoint {

    public final float x;
    public final float y;
    public final float z;

    /**
     * Construct WhitePoint with CIE xyY coordinates.
     * Y is omitted because is implicitly assumed to be 1.
     *
     * @param x The x coordinate of CIExyY
     * @param y The y coordinate of CIExyY
     */
    public WhitePoint(float x, float y) {
        // X = (x * Y) / y
        this.x = x / y;
        // Y is assumed to be 1
        this.y = 1.0f;
        // Z = (z * Y) / y, where z = 1 - x - y
        this.z = (1 - x - y) / y;
    }

    /**
     * Construct WhitePoint with CIE XYZ coordinates.
     *
     * @param x X coordinate of CIE XYZ.
     * @param y Y coordinate of CIE XYZ. Must always be 1.
     * @param z Z coordinate of CIE XYZ.
     *
     * @throws IllegalArgumentException if Y is different from 1.
     */
    public WhitePoint(float x, float y, float z) {
        if (y != 1.0f)
            throw new IllegalArgumentException("White point cannot have Y != 1.");

        this.x = x;
        this.y = 1.0f;
        this.z = z;
    }

}
