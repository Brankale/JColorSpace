package com.github.colorspaces;

import com.github.Rgb;
import com.github.WhitePoint;
import com.github.utils.FloatArray;

public class DciP3 extends Rgb {
    public DciP3() {
        super(
                "SMPTE RP 431-2-2007 DCI (P3)",
                new FloatArray(0.680f, 0.320f, 0.265f, 0.690f, 0.150f, 0.060f),
                new WhitePoint(0.314f, 0.351f),
                d -> Math.pow(d, 1 / 2.6),
                d -> Math.pow(d, 2.6)
        );
    }
}
