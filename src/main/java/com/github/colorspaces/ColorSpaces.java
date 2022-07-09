package com.github.colorspaces;

import com.github.colorspace.ColorSpace;
import com.github.colorspace.rgb.Rgb;

public class ColorSpaces {
    private ColorSpaces() {
        // hide constructor
    }

    public static final ColorSpace CIE_LAB = new CieLab();
    public static final Rgb SRGB = new Srgb();
    public static final Rgb DCI_P3 = new DciP3();

}
