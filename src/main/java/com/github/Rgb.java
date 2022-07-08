package com.github;

import com.github.colormodels.ColorModels;
import com.github.utils.FloatArray;

import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

public abstract class Rgb extends ColorSpace {
    private final FloatArray primaries;
    private final WhitePoint whitePoint;

    /**
     * The opto-electronic transfer function (OETF or OECF) encodes tristimulus values in a scene
     * to a non-linear electronic signal value. An OETF is often expressed as a power function
     * with an exponent between 0.38 and 0.55 (the reciprocal of 1.8 to 2.6).
     * 
     * The list contains three OETFs, one for each channel RED, GREEN and BLUE.
     */
    private final List<DoubleUnaryOperator> oetf;

    /**
     * The electro-optical transfer function (EOTF or EOCF) decodes a non-linear electronic signal
     * value to a tristimulus value at the display. An EOTF is often expressed as a power function
     * with an exponent between 1.8 and 2.6.
     *
     * The list contains three EOTFs, one for each channel RED, GREEN and BLUE.
     */
    private final List<DoubleUnaryOperator> eotf;

    /**
     * Constructs an RGB colorspace.
     *
     * @param name       the name of the colorspace.
     * @param primaries  the coordinates of the three primaries.
     * @param whitePoint the white point.
     * @param gamma      the gamma function applied to all the three channels (i.e. R, G, B).
     */
    public Rgb(
            String name,
            FloatArray primaries,
            WhitePoint whitePoint,
            double gamma
    ) {
        this(name, primaries, whitePoint, d -> Math.pow(d, 1 / gamma), d -> Math.pow(d, gamma));
    }

    /**
     * Constructs an RGB colorspace.
     *
     * @param name       the name of the colorspace.
     * @param primaries  the coordinates of the three primaries.
     * @param whitePoint the white point.
     * @param gammaRed   the gamma function applied to the red channel.
     * @param gammaGreen the gamma function applied to the green channel.
     * @param gammaBlue  the gamma function applied to the blue channel.
     */
    public Rgb(
            String name,
            FloatArray primaries,
            WhitePoint whitePoint,
            double gammaRed,
            double gammaGreen,
            double gammaBlue
    ) {
        this(
                name,
                primaries,
                whitePoint,
                List.of(
                        d -> Math.pow(d, 1.0 / gammaRed),
                        d -> Math.pow(d, 1.0 / gammaGreen),
                        d -> Math.pow(d, 1.0 / gammaBlue)
                ),
                List.of(
                        d -> Math.pow(d, gammaRed),
                        d -> Math.pow(d, gammaGreen),
                        d -> Math.pow(d, gammaBlue)
                )
        );
    }

    /**
     * Constructs an RGB colorspace.
     *
     * @param name       the name of the colorspace.
     * @param primaries  the coordinates of the three primaries.
     * @param whitePoint the white point.
     * @param oetf       the OETF function applied to all the three channels (i.e. R, G, B).
     * @param eotf       the EOTF function applied to all the three channels (i.e. R, G, B).
     */
    public Rgb(
            String name,
            FloatArray primaries,
            WhitePoint whitePoint,
            DoubleUnaryOperator oetf,
            DoubleUnaryOperator eotf
    ) {
        super(name, ColorModels.RGB);
        this.primaries = primaries;
        this.whitePoint = whitePoint;
        this.oetf = List.of(oetf, oetf, oetf);
        this.eotf = List.of(eotf, eotf, eotf);
    }

    /**
     * Constructs an RGB colorspace.
     *
     * @param name       the name of the colorspace.
     * @param primaries  the coordinates of the three primaries.
     * @param whitePoint the white point.
     * @param oetf       a list with the OETFs functions applied to the three channels. The first OETF will be applied
     *                   to the RED channel, the second the GREEN channel and the last one to the BLUE channel.
     * @param eotf       a list with the EOTFs functions applied to the three channels. The first EOTF will be applied
     *                   to the RED channel, the second the GREEN channel and the last one to the BLUE channel.
     */
    public Rgb(
            String name,
            FloatArray primaries,
            WhitePoint whitePoint,
            List<DoubleUnaryOperator> oetf,
            List<DoubleUnaryOperator> eotf
    ) {
        super(name, ColorModels.RGB);
        this.primaries = primaries;
        this.whitePoint = whitePoint;
        this.oetf = oetf;
        this.eotf = eotf;
    }

    public FloatArray getPrimaries() {
        return primaries;
    }

    public WhitePoint getWhitePoint() {
        return whitePoint;
    }

    public FloatArray fromLinear(float r, float g, float b) {
        return new FloatArray(
                (float) oetf.get(0).applyAsDouble(r),
                (float) oetf.get(1).applyAsDouble(g),
                (float) oetf.get(2).applyAsDouble(b)
        );
    }

    public FloatArray fromLinear(FloatArray v) {
        return fromLinear(v.get(0), v.get(1), v.get(2));
    }

    public FloatArray toLinear(float r, float g, float b) {
        return new FloatArray(
                (float) eotf.get(0).applyAsDouble(r),
                (float) eotf.get(1).applyAsDouble(g),
                (float) eotf.get(2).applyAsDouble(b)
        );
    }

    public FloatArray toLinear(FloatArray v) {
        return toLinear(v.get(0), v.get(1), v.get(2));
    }

    // TODO: implement this method
    public abstract FloatArray getTransform();

    // TODO: implement this method
    public abstract FloatArray getInverseTransform();
}
