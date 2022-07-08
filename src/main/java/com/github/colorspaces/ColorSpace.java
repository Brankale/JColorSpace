package com.github.colorspaces;

import com.github.AbstractColorSpace;

public class ColorSpace {
    private ColorSpace() {
        // hide constructor
    }

    public static final AbstractColorSpace CieLab = new CieLab();

}
