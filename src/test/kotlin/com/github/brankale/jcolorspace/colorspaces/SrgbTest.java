package com.github.brankale.jcolorspace.colorspaces;

import com.github.brankale.jcolorspace.colorspace.ColorSpace;
import com.github.brankale.jcolorspace.utils.FloatArray;
import org.junit.jupiter.api.Test;

/**
 * rgb values should be in range [0, 1].
 * Suppose we have 8bit for a channel ==> value = x / 2^8,
 * where x is the integer value of channel.
 *
 * e.g. if x = 255 ==> value = 255 / 2^8 = 1
 */
class SrgbTest {

    private static final double DELTA = 0.0011;

    // sRGB to CIE XYZ

    @Test
    void shouldReturnCorrectXyzValuesForBlack() {
        ColorSpace srgb = ColorSpaces.SRGB;
        FloatArray xyz = srgb.toXyz(0, 0, 0);
        assertEquals(0, xyz.get(0), DELTA);
        assertEquals(0, xyz.get(1), DELTA);
        assertEquals(0, xyz.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectXyzValuesForWhite() {
        ColorSpace srgb = ColorSpaces.SRGB;
        FloatArray xyz = srgb.toXyz(1, 1, 1);
        assertEquals(0.9505, xyz.get(0), DELTA);
        assertEquals(1, xyz.get(1), DELTA);
        assertEquals(1.089, xyz.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectXyzValuesForRed() {
        ColorSpace srgb = ColorSpaces.SRGB;
        FloatArray xyz = srgb.toXyz(1, 0, 0);
        assertEquals(0.4124, xyz.get(0), DELTA);
        assertEquals(0.2126, xyz.get(1), DELTA);
        assertEquals(0.0193, xyz.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectXyzValuesForGreen() {
        ColorSpace srgb = ColorSpaces.SRGB;
        FloatArray xyz = srgb.toXyz(0, 1, 0);
        assertEquals(0.3576, xyz.get(0), DELTA);
        assertEquals(0.7152, xyz.get(1), DELTA);
        assertEquals(0.1192, xyz.get(2), DELTA);
    }

    @Test
    void shouldReturnCorrectXyzValuesForBlue() {
        ColorSpace srgb = ColorSpaces.SRGB;
        FloatArray xyz = srgb.toXyz(0, 0, 1);
        assertEquals(0.1805, xyz.get(0), DELTA);
        assertEquals(0.0722, xyz.get(1), DELTA);
        assertEquals(0.9505, xyz.get(2), DELTA);
    }

    // CIE XYZ to sRGB

    @Test
    void shouldReturnBlackFromXyz() {
        ColorSpace srgb = ColorSpaces.SRGB;
        FloatArray xyz = srgb.fromXyz(0, 0, 0);
        assertEquals(0, xyz.get(0), DELTA);
        assertEquals(0, xyz.get(1), DELTA);
        assertEquals(0, xyz.get(2), DELTA);
    }

    @Test
    void shouldReturnWhiteFromXyz() {
        ColorSpace srgb = ColorSpaces.SRGB;
        FloatArray xyz = srgb.fromXyz(0.9505f, 1f, 1.089f);
        assertEquals(1, xyz.get(0), DELTA);
        assertEquals(1, xyz.get(1), DELTA);
        assertEquals(1, xyz.get(2), DELTA);
    }

    @Test
    void shouldReturnRedFromXyz() {
        ColorSpace srgb = ColorSpaces.SRGB;
        FloatArray xyz = srgb.fromXyz(0.4124f, 0.2126f, 0.0193f);
        assertEquals(1, xyz.get(0), DELTA);
        assertEquals(0, xyz.get(1), DELTA);
        assertEquals(0, xyz.get(2), DELTA);
    }

    @Test
    void shouldReturnGreenFromXyz() {
        ColorSpace srgb = ColorSpaces.SRGB;
        FloatArray xyz = srgb.fromXyz(0.3576f, 0.7152f, 0.1192f);
        assertEquals(0, xyz.get(0), DELTA);
        assertEquals(1, xyz.get(1), DELTA);
        assertEquals(0, xyz.get(2), DELTA);
    }

    @Test
    void shouldReturnBlueFromXyz() {
        ColorSpace srgb = ColorSpaces.SRGB;
        FloatArray xyz = srgb.fromXyz(0.1805f, 0.0722f, 0.9505f);
        assertEquals(0, xyz.get(0), DELTA);
        assertEquals(0, xyz.get(1), DELTA);
        assertEquals(1, xyz.get(2), DELTA);
    }

}
