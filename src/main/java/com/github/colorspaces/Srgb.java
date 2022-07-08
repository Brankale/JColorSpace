package com.github.colorspaces;

import com.github.Rgb;
import com.github.WhitePoint;
import com.github.utils.FloatArray;

import java.util.function.DoubleUnaryOperator;

public class Srgb extends Rgb {
    public Srgb(
            String name,
            FloatArray primaries,
            WhitePoint whitePoint,
            DoubleUnaryOperator oetf,
            DoubleUnaryOperator eotf
    ) {
        super(name, primaries, whitePoint, oetf, eotf);
    }
}
