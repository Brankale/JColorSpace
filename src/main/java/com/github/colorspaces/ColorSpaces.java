package com.github.colorspaces;

import com.github.ColorSpace;
import com.github.Illuminant;
import com.github.colormodels.ColorModels;
import com.github.utils.FloatArray;

public class ColorSpaces {
    private ColorSpaces() {
        // hide constructor
    }

    public static final ColorSpace CieXyY = new ColorSpace("CIE xyY",
            ColorModels.XYZ) {
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
            FloatArray array = new FloatArray(3);
            array.set(0, x / (x + y + z));
            array.set(1, y / (x + y + z));
            array.set(2, y);
            return array;
        }

        @Override
        public FloatArray fromXyz(FloatArray v) {
            return fromXyz(v.get(0), v.get(1), v.get(2));
        }

        @Override
        public FloatArray toXyz(float x, float y, float Y) {
            FloatArray array = new FloatArray(3);
            float z = 1 - x - y;

            array.set(0, (x * Y) / y);
            array.set(1, Y);
            array.set(2, (z * Y) / y);
            return array;
        }

        @Override
        public FloatArray toXyz(FloatArray v) {
            return toXyz(v.get(0), v.get(1), v.get(2));
        }
    };

    /**
     * Illuminant for CieLab is assumed to be D65
     */
    public static final ColorSpace CieLab = new ColorSpace("Lab",
            ColorModels.Lab) {
        private static final double E = 0.008856;
        private static final double K = 903.3;

        // D65 Illuminant converted in CIE XYZ coordinates
        private static final FloatArray whitePoint = ColorSpaces.CieXyY.toXyz(
                Illuminant.D65.x,
                Illuminant.D65.y,
                1                   // Y is assumed to be 1 for the white point
        );

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
            double xw = x / whitePoint.get(0);
            double yw = y / whitePoint.get(1);
            double zw = z / whitePoint.get(2);

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
        public FloatArray fromXyz(FloatArray v) {
            return fromXyz(v.get(0), v.get(1), v.get(2));
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
            array.set(0, (float) (xr * whitePoint.get(0)));
            array.set(0, (float) (yr * whitePoint.get(1)));
            array.set(0, (float) (zr * whitePoint.get(2)));
            return array;
        }

        @Override
        public FloatArray toXyz(FloatArray v) {
            return null;
        }
    };

}
