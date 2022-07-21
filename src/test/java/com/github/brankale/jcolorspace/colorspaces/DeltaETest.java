package com.github.brankale.jcolorspace.colorspaces;

import com.github.brankale.jcolorspace.colorspace.ColorSpaceUtils;
import com.github.brankale.jcolorspace.utils.FloatArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeltaETest {

    private static final double DELTA = 0.01;

    @Test
    void shouldReturnMaxDeltaE() {
        FloatArray color1 = new FloatArray(0, 0, 0);
        FloatArray color2 = new FloatArray(1, 1, 1);
        float deltaE = ColorSpaceUtils.deltaE(ColorSpaces.SRGB, color1, color2);
        assertEquals(100, deltaE);
    }

    @Test
    void shouldReturnMinDeltaE() {
        FloatArray color = new FloatArray(0, 0, 0);
        float deltaE = ColorSpaceUtils.deltaE(ColorSpaces.SRGB, color, color);
        assertEquals(0, deltaE);
    }

    @Test
    void shouldReturnCorrectDeltaE() {
        FloatArray color1 = new FloatArray(0, 0, 0);
        FloatArray color2 = new FloatArray((float)(10/255.0), (float)(10/255.0), (float)(10/255.0));
        float deltaE = ColorSpaceUtils.deltaE(ColorSpaces.SRGB, color1, color2);
        assertEquals(1.58, deltaE, DELTA);
    }

}
