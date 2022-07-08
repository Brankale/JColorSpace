package com.github.colorspaces;

import com.github.ColorSpace;
import com.github.Illuminant;
import com.github.Primaries;
import com.github.Rgb;

public class ColorSpaces {
    private ColorSpaces() {
        // hide constructor
    }

    public static final ColorSpace CieLab = new CieLab();
    public static final Rgb sRGB = new Srgb();

}
