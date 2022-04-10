package com.github.colorspaces;

import com.github.ColorSpace;
import com.github.colormodels.ColorModels;
import com.github.utils.FloatArray;

public class CieXyY extends ColorSpace {
    public CieXyY() {
        super("CIE xyY", ColorModels.XYZ);
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
}
