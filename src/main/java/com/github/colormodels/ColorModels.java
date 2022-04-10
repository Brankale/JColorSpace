package com.github.colormodels;

import com.github.ColorModel;

public class ColorModels {
    private ColorModels() {
        // hide constructor
    }

    public static final ColorModel XYZ = new ColorModel() {
        @Override
        protected int componentCount() {
            return 3;
        }
    };

    public static final ColorModel RGB = new ColorModel() {
        @Override
        protected int componentCount() {
            return 3;
        }
    };

    public static final ColorModel Lab = new ColorModel() {
        @Override
        protected int componentCount() {
            return 3;
        }
    };
}
