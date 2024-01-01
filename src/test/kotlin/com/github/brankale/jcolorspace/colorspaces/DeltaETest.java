package com.github.brankale.jcolorspace.colorspaces;

import com.github.brankale.jcolorspace.colorspace.ColorSpace;
import com.github.brankale.jcolorspace.colorspace.ColorSpaceUtils;
import com.github.brankale.jcolorspace.colorspace.connector.Connector;
import com.github.brankale.jcolorspace.colorspace.rgb.Rgb;
import com.github.brankale.jcolorspace.colorspace.rgb.WhitePoint;
import com.github.brankale.jcolorspace.utils.FloatArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    private static class TestColorspace extends Rgb {
        public TestColorspace() {
            super(
                    "Nintendo DS Lite colorspace",
                    new FloatArray(0.6068f, 0.3449f, 0.3314f, 0.6124f, 0.1451f, 0.0893f),
                    new WhitePoint(0.3093f, 0.3193f),
                    1.85
            );
        }
    }

    @Test
    void ColorspaceRoundingErrorTest() {
        ColorSpace DS_LITE = new TestColorspace();
        // Color(0, 0, 32)
        FloatArray v = new FloatArray(0, 0, 32.0f / 255.0f);
        Connector connector = DS_LITE.connect(ColorSpaces.DCI_P3);
        FloatArray converted = connector.transform(v);

        assertFalse(Double.isNaN(converted.get(0)));
    }

    @Test
    void RgbColorspaceRoundingErrorTest() {
        Rgb DS_LITE = new TestColorspace();
        // Color(0, 0, 32)
        FloatArray v = new FloatArray(0, 0, 32.0f / 255.0f);
        Connector connector = DS_LITE.connect(ColorSpaces.DCI_P3);
        FloatArray converted = connector.transform(v);

        assertFalse(Double.isNaN(converted.get(0)));
    }

}
