package com.github.brankale.jcolorspace.colorspaces;

import com.github.brankale.jcolorspace.colorspace.rgb.Rgb;
import com.github.brankale.jcolorspace.colorspace.rgb.WhitePoint;
import com.github.brankale.jcolorspace.utils.FloatArray;

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
