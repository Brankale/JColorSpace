package com.github.colorspace.rgb;

import com.github.colorspace.ColorModels;
import com.github.colorspace.connector.Connector;
import com.github.colorspace.connector.RenderIntent;
import com.github.colorspace.ColorSpace;
import com.github.utils.FloatArray;
import com.github.utils.MatrixUtils;
import org.ejml.simple.SimpleMatrix;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

public abstract class Rgb extends ColorSpace {
    private final ChromaticyCoordinate[] primaries;
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
        this.primaries = new ChromaticyCoordinate[] {
                new ChromaticyCoordinate(primaries.get(0), primaries.get(1)),
                new ChromaticyCoordinate(primaries.get(2), primaries.get(3)),
                new ChromaticyCoordinate(primaries.get(4), primaries.get(5)),
        };
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
        this.primaries = new ChromaticyCoordinate[] {
                new ChromaticyCoordinate(primaries.get(0), primaries.get(1)),
                new ChromaticyCoordinate(primaries.get(2), primaries.get(3)),
                new ChromaticyCoordinate(primaries.get(4), primaries.get(5)),
        };
        this.whitePoint = whitePoint;
        this.oetf = oetf;
        this.eotf = eotf;
    }

    public FloatArray getPrimaries() {
        return new FloatArray(
                primaries[0].x(), primaries[0].y(),
                primaries[1].x(), primaries[1].y(),
                primaries[2].x(), primaries[2].y()
        );
    }

    private ChromaticyCoordinate getRedChromaticityCoord() {
        return primaries[0];
    }

    private ChromaticyCoordinate getGreenChromaticityCoord() {
        return primaries[1];
    }

    private ChromaticyCoordinate getBlueChromaticityCoord() {
        return primaries[2];
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

    public FloatArray getTransform() {
        ChromaticyCoordinate r = getRedChromaticityCoord();
        ChromaticyCoordinate g = getGreenChromaticityCoord();
        ChromaticyCoordinate b = getBlueChromaticityCoord();

        double Xr = r.x() / r.y();
        double Xg = g.x() / g.y();
        double Xb = b.x() / b.y();

        double Yr = 1.0f;
        double Yg = 1.0f;
        double Yb = 1.0f;

        double Zr = (1.0f - r.x() - r.y()) / r.y();
        double Zg = (1.0f - g.x() - g.y()) / g.y();
        double Zb = (1.0f - b.x() - b.y()) / b.y();

        SimpleMatrix tmp = new SimpleMatrix(3, 3, true, new double[] {
                Xr, Xg, Xb,
                Yr, Yg, Yb,
                Zr, Zg, Zb
        });

        SimpleMatrix whitePointMatrix = new SimpleMatrix(3, 1, true, new double[] {
                whitePoint.x,
                whitePoint.y,
                whitePoint.z
        });

        SimpleMatrix s = tmp.invert().mult(whitePointMatrix);
        double sr = s.get(0);
        double sg = s.get(1);
        double sb = s.get(2);

        SimpleMatrix transformMatrix = new SimpleMatrix(3, 3, true, new double[] {
                sr * Xr, sg * Xg, sb * Xb,
                sr * Yr, sg * Yg, sb * Yb,
                sr * Zr, sg * Zg, sb * Zb
        });

        return MatrixUtils.toFloatArray(transformMatrix);
    }

    public FloatArray getInverseTransform() {
        SimpleMatrix matrix = MatrixUtils.toSimpleMatrix(getTransform(), 3, 3);
        return MatrixUtils.toFloatArray(matrix.invert());
    }

    @Override
    public FloatArray toXyz(FloatArray v) {
        v = toLinear(v);
        SimpleMatrix matrix = MatrixUtils.toSimpleMatrix(v, 3, 1);
        SimpleMatrix xyz = MatrixUtils.toSimpleMatrix(getTransform(), 3, 3).mult(matrix);
        return MatrixUtils.toFloatArray(xyz);
    }

    @Override
    public FloatArray fromXyz(FloatArray v) {
        v = fromLinear(v);
        SimpleMatrix matrix = MatrixUtils.toSimpleMatrix(v, 3, 1);
        SimpleMatrix rgb = MatrixUtils.toSimpleMatrix(getInverseTransform(), 3, 3).mult(matrix);
        return MatrixUtils.toFloatArray(rgb);
    }

    @Override
    public float getMinValue(int component) {
        return 0;
    }

    @Override
    public float getMaxValue(int component) {
        return 1.0f;
    }

    public Connector connect(Rgb destinationColorSpace) {
        return connect(destinationColorSpace, RenderIntent.ABSOLUTE);
    }

    public Connector connect(Rgb destinationColorSpace, RenderIntent renderIntent) {
        return new Connector(this, destinationColorSpace, renderIntent);
    }

}