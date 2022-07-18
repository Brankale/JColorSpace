package com.github.brankale.jcolorspace.colorspace;

public class ColorModels {
    private ColorModels() {
        // hide constructor
    }

    public static final ColorModel XYZ = () -> 3;

    public static final ColorModel RGB = () -> 3;

    public static final ColorModel Lab = () -> 3;
}
