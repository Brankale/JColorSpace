package com.github.brankale.jcolorspace.colorspaces;

import com.github.brankale.jcolorspace.colorspace.ColorModel;
import com.github.brankale.jcolorspace.colorspace.ColorSpace;
import com.github.brankale.jcolorspace.colorspace.rgb.Illuminant;
import com.github.brankale.jcolorspace.colorspace.rgb.WhitePoint;
import com.github.brankale.jcolorspace.utils.FloatArray;

/**
 * Illuminant for CieLab is assumed to be D65
 */
// TODO: make this class not public
public class CieLab extends ColorSpace {
    private static final double E = 0.008856;
    private static final double K = 903.3;
    private static final WhitePoint illuminant = Illuminant.D65;

    public CieLab() {
        super("CIE Lab", ColorModel.Lab);
    }

    @Override
    public float getMinValue(int component) {
        return switch (component) {
            case 0 -> 0;
            case 1, 2 -> -128.0f;
            default -> throw new IllegalArgumentException("CieLab has only three components." +
                    "Requested component: " + component + ".");
        };
    }

    @Override
    public float getMaxValue(int component) {
        return switch (component) {
            case 0 -> 100;
            case 1, 2 -> 128.0f;
            default -> throw new IllegalArgumentException("CieLab has only three components." +
                    "Requested component: " + component + ".");
        };
    }

    /**
     * @param x The X component of the color value
     * @param y The Y component of the color value
     * @param z The Z component of the color value
     * @return a FloatArray of length three with L, a, b values.
     */
    @Override
    public FloatArray fromXyz(float x, float y, float z) {
        double xw = x / illuminant.x;
        double yw = y / illuminant.y;
        double zw = z / illuminant.z;

        double fx = xw > E ? Math.cbrt(xw) : (K * xw + 16) / 116.0;
        double fy = yw > E ? Math.cbrt(yw) : (K * yw + 16) / 116.0;
        double fz = zw > E ? Math.cbrt(zw) : (K * zw + 16) / 116.0;

        double l = 116 * fy - 16;
        double a = 500 * (fx - fy);
        double b = 200 * (fy - fz);

        return new FloatArray((float) l, (float) a, (float) b);
    }

    @Override
    public FloatArray fromXyz(FloatArray v) {
        return fromXyz(v.get(0), v.get(1), v.get(2));
    }

    @Override
    public FloatArray toXyz(float l, float a, float b) {
        double fy = (l + 16) / 116.0;
        double fx = (a / 500.0) + fy;
        double fz = fy - (b / 200.0);

        double xr = Math.pow(fx, 3) > E ? Math.pow(fx, 3) : (116 * fx  - 16) / K;
        double yr = l > (K * E) ? Math.pow(fy, 3) : l / K;
        double zr = Math.pow(fz, 3) > E ? Math.pow(fz, 3) : (116 * fz  - 16) / K;

        FloatArray array = new FloatArray(3);
        array.set(0, (float) (xr * illuminant.x));
        array.set(1, (float) (yr * illuminant.y));
        array.set(2, (float) (zr * illuminant.z));
        return array;
    }

    @Override
    public FloatArray toXyz(FloatArray v) {
        return toXyz(v.get(0), v.get(1), v.get(2));
    }
}
