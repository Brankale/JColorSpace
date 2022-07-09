package com.github.colorspace.connector;

/**
 * A render intent determines how a connector maps colors from one color space
 * to another. The choice of mapping is important when the source color space
 * has a larger color gamut than the destination color space.
 */
public enum RenderIntent {
    /**
     * Colors that are in the destination gamut are left unchanged.
     * Colors that fall outside the destination gamut are mapped to the closest possible
     * color within the gamut of the destination color space (they are clipped).
     */
    ABSOLUTE,

    /**
     * Compresses the source gamut into the destination gamut.
     * This render intent affects all colors, inside and outside of destination gamut.
     * The goal of this render intent is to preserve the visual relationship between colors.
     */
    PERCEPTUAL,

    /**
     * Similar to the Absolute render intent, this render intent matches the
     * closest color in the destination gamut but makes adjustments for the
     * destination white point.
     */
    RELATIVE,

    /**
     * Attempts to maintain the relative saturation of colors from the source
     * gamut to the destination gamut, to keep highly saturated colors as
     * saturated as possible.
     */
    SATURATION
}
