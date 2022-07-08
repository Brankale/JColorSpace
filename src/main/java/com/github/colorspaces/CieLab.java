package com.github.colorspaces;

import com.github.AbstractColorSpace;
import com.github.Illuminant;
import com.github.colormodels.ColorModels;
import com.github.utils.FloatArray;

/**
 * Illuminant for CieLab is assumed to be D65
 */
public class CieLab extends AbstractColorSpace {
    private static final double E = 0.008856;
    private static final double K = 903.3;

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
        double xw = x / Illuminant.D65.x;
        double yw = y / Illuminant.D65.y;
        double zw = z / Illuminant.D65.z;

        double fx, fy, fz;

        if (xw > E)
            fx = Math.cbrt(xw);
        else
            fx = (K * xw + 16) / 116.0;

        if (yw > E)
            fy = Math.cbrt(yw);
        else
            fy = (K * yw + 16) / 116.0;

        if (zw > E)
            fz = Math.cbrt(zw);
        else
            fz = (K * zw + 16) / 116.0;

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

        double xr, yr, zr;

        if (Math.pow(fx, 3) > E)
            xr = Math.pow(fx, 3);
        else
            xr = (116 * fx  - 16) / K;

        if (l > (K * E))
            yr = Math.pow(fy, 3);
        else
            yr = l / K;

        if (Math.pow(fz, 3) > E)
            zr = Math.pow(fz, 3);
        else
            zr = (116 * fz  - 16) / K;

        FloatArray array = new FloatArray(3);
        array.set(0, (float) (xr * Illuminant.D65.x));
        array.set(0, (float) (yr * Illuminant.D65.y));
        array.set(0, (float) (zr * Illuminant.D65.z));
        return array;
    }
}
