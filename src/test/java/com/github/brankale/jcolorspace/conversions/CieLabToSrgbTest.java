package com.github.brankale.jcolorspace.conversions;

import com.github.brankale.jcolorspace.colorspace.connector.Connector;
import com.github.brankale.jcolorspace.colorspaces.ColorSpaces;
import com.github.brankale.jcolorspace.utils.FloatArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CieLabToSrgbTest {

    private static final double DELTA = 0.005;

    private static Connector connector;

    @BeforeAll
    static void setup() {
        connector = ColorSpaces.CIE_LAB.connect(ColorSpaces.SRGB);
    }

    @Test
    void shouldReturnCorrectRgbValuesForBlack() {
        FloatArray lab = new FloatArray(0, 0, 0);
        FloatArray rgb = connector.transform(lab);

        assertEquals(0, rgb.get(0), DELTA);
        assertEquals(0, rgb.get(1), DELTA);
        assertEquals(0, rgb.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectRgbValuesForWhite() {
        FloatArray lab = new FloatArray(100, 0, 0);
        FloatArray rgb = connector.transform(lab);

        assertEquals(1, rgb.get(0), DELTA);
        assertEquals(1, rgb.get(1), DELTA);
        assertEquals(1, rgb.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectRgbValuesForRed() {
        FloatArray lab = new FloatArray(53.24f, 80.09f, 67.20f);
        FloatArray rgb = connector.transform(lab);

        assertEquals(1, rgb.get(0), DELTA);
        assertEquals(0, rgb.get(1), DELTA);
        assertEquals(0, rgb.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectRgbValuesForGreen() {
        FloatArray lab = new FloatArray(87.73f, -86.18f, 83.18f);
        FloatArray rgb = connector.transform(lab);

        assertEquals(0, rgb.get(0), DELTA);
        assertEquals(1, rgb.get(1), DELTA);
        assertEquals(0, rgb.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectRgbValuesForBlue() {
        FloatArray lab = new FloatArray(32.30f, 79.19f, -107.86f);
        FloatArray rgb = connector.transform(lab);

        assertEquals(0, rgb.get(0), DELTA);
        assertEquals(0, rgb.get(1), DELTA);
        assertEquals(1, rgb.get(2), DELTA);
    }

}
