package com.github;

import com.github.colormodels.ColorModels;
import com.github.utils.FloatArray;

import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

public abstract class Rgb extends ColorSpace {
    private final FloatArray primaries;
    private final WhitePoint whitePoint;
    private final List<DoubleUnaryOperator> oetf;
    private final List<DoubleUnaryOperator> eotf;

    public Rgb(
            String name,
            FloatArray primaries,
            WhitePoint whitePoint,
            double gamma
    ) {
        this(name, primaries, whitePoint, d -> Math.pow(d, 1 / gamma), d -> Math.pow(d, gamma));
    }

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
                Stream.of(
                        (DoubleUnaryOperator) d -> Math.pow(d, 1.0 / gammaRed),
                        d -> Math.pow(d, 1.0 / gammaGreen),
                        d -> Math.pow(d, 1.0 / gammaBlue)
                ).toList(),
                Stream.of(
                        (DoubleUnaryOperator) d -> Math.pow(d, gammaRed),
                        d -> Math.pow(d, gammaGreen),
                        d -> Math.pow(d, gammaBlue)
                ).toList()
        );
    }

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
        this.oetf = Stream.of(oetf, oetf, oetf).toList();
        this.eotf = Stream.of(eotf, eotf, eotf).toList();
    }

    /**
     *
     * @param name
     * @param primaries
     * @param whitePoint
     * @param oetf 3 elements list with different oetf for every RGB channel
     * @param eotf 3 elements list with different eotf for every RGB channel
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

    public FloatArray getPrimaries() {
        return primaries;
    }

    public WhitePoint getWhitePoint() {
        return whitePoint;
    }
}
