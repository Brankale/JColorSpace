package com.github.colorspace;

import com.github.utils.FloatArray;

public abstract class ColorSpace {

    private final String name;
    private final ColorModel colorModel;

    protected ColorSpace(String name, ColorModel colorModel) {
        this.name = name;
        this.colorModel = colorModel;
    }

    public final String getName() {
        return name;
    }

    public final ColorModel getColorModel() {
        return colorModel;
    }

    /**
     * Returns the minimum valid value for the specified component of this
     * color space's color model.
     *
     * @param component The index of the component, from 0 to 3, inclusive.
     * @return A floating point value lower than getMaxValue.
     */
    public abstract float getMinValue(int component);

    /**
     * Returns the maximum valid value for the specified component of this
     * color space's color model.
     *
     * @param component The index of the component, from 0 to 3, inclusive
     * @return A floating point value greater than getMinValue.
     */
    public abstract float getMaxValue(int component);

    /**
     * Converts tristimulus values from the CIE XYZ space to this color
     * space's color model.
     *
     * @param x The X component of the color value
     * @param y The Y component of the color value
     * @param z The Z component of the color value
     * @return A new array whose size is equal to the number of color
     * components as returned by ColorModel.componentCount.
     */
    public FloatArray fromXyz(float x, float y, float z) {
        return fromXyz(new FloatArray(x, y, z));
    }

    /**
     * Converts tristimulus values from the CIE XYZ space to this color
     * space's color model.
     *
     * @param v An array of color components containing the XYZ values to
     *          convert from.
     * @return A new array whose size is equal to the number of color
     * components as returned by ColorModel.componentCount.
     */
    public abstract FloatArray fromXyz(FloatArray v);

    /**
     * Converts a color value from this color space's model to tristimulus
     * CIE XYZ values. If the color model of this color space is not RGB, it
     * is assumed that the target CIE XYZ space uses a D50 standard illuminant.
     *
     * @param r The first component of the value to convert from
     *          (typically R in RGB)
     * @param g The second component of the value to convert from
     *          (typically G in RGB)
     * @param b The third component of the value to convert from
     *          (typically B in RGB)
     * @return A new array of 3 floats, containing tristimulus XYZ values
     */
    public FloatArray toXyz(float r, float g, float b) {
        return toXyz(new FloatArray(r, g, b));
    }

    /**
     * Converts a color value from this color space's model to tristimulus
     * CIE XYZ values. If the color model of this color space is not RGB, it
     * is assumed that the target CIE XYZ space uses a D50 standard illuminant.
     *
     * @param v An array of color components containing the color space's
     *          color value to convert to XYZ, and large enough to hold the
     *          resulting tristimulus XYZ values, at least 3 values.
     * @return A new array of 3 floats, containing tristimulus XYZ values
     */
    public abstract FloatArray toXyz(FloatArray v);

}
