package com.github.colorspaces;

import com.github.AbstractColorSpace;
import com.github.Illuminant;
import com.github.WhitePoint;
import com.github.colormodels.ColorModels;
import com.github.utils.FloatArray;

/**
 * Illuminant for CieLab is assumed to be D65
 */
public class CieLab extends AbstractColorSpace {
    private static final double E = 0.008856;
    private static final double K = 903.3;
    private static final WhitePoint illuminant = Illuminant.D65;

    public CieLab() {
        super("CIE Lab", ColorModels.Lab);
    }

    @Override
    public float getMinValue(int component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getMaxValue(int component) {
        throw new UnsupportedOperationException();
    }

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

        FloatArray array = new FloatArray(3);
        array.set(0, (float) l);
        array.set(1, (float) a);
        array.set(2, (float) b);
        return array;
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
        array.set(0, (float) (yr * illuminant.y));
        array.set(0, (float) (zr * illuminant.z));
        return array;
    }
}
