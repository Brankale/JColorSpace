package com.github.colorspaces;

import com.github.Illuminant;
import com.github.Primaries;
import com.github.Rgb;

public class Srgb extends Rgb {
    public Srgb() {
        super(
                "sRGB IEC61966-2.1",
                Primaries.BT709,
                Illuminant.D65,
                d -> {
                    if (d < 0.0031308)
                        return 12.92 * d;
                    else
                        return 1.055 * Math.pow(d, 1 / 2.4) - 0.055;
                },
                d -> {
                    if (d < 0.04045)
                        return d / 12.92;
                    else
                        return Math.pow(((d + 0.055) / 1.055), 2.4);
                }
        );
    }
}
