package com.github.conversions;

import com.github.colorspace.connector.Connector;
import com.github.colorspaces.ColorSpaces;
import com.github.utils.FloatArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SrgbToCieLabTest {

    private static final double DELTA = 0.005;

    private static Connector connector;

    @BeforeAll
    static void setup() {
        connector = ColorSpaces.SRGB.connect(ColorSpaces.CIE_LAB);
    }

    @Test
    void shouldReturnCorrectLabValuesForBlack() {
        FloatArray rgb = new FloatArray(0, 0, 0);
        FloatArray lab = connector.transform(rgb);

        assertEquals(0, lab.get(0), DELTA);
        assertEquals(0, lab.get(1), DELTA);
        assertEquals(0, lab.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectLabValuesForWhite() {
        FloatArray rgb = new FloatArray(1, 1, 1);
        FloatArray lab = connector.transform(rgb);

        assertEquals(100, lab.get(0), DELTA);
        assertEquals(0, lab.get(1), DELTA);
        assertEquals(0, lab.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectLabValuesForRed() {
        FloatArray rgb = new FloatArray(1, 0, 0);
        FloatArray lab = connector.transform(rgb);

        assertEquals(53.24, lab.get(0), DELTA);
        assertEquals(80.09, lab.get(1), DELTA);
        assertEquals(67.20, lab.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectLabValuesForGreen() {
        FloatArray rgb = new FloatArray(0, 1, 0);
        FloatArray lab = connector.transform(rgb);

        assertEquals(87.73, lab.get(0), DELTA);
        assertEquals(-86.18, lab.get(1), DELTA);
        assertEquals(83.18, lab.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectLabValuesForBlue() {
        FloatArray rgb = new FloatArray(0, 0, 1);
        FloatArray lab = connector.transform(rgb);

        assertEquals(32.30, lab.get(0), DELTA);
        assertEquals(79.19, lab.get(1), DELTA);
        assertEquals(-107.86, lab.get(2), DELTA);
    }

}
