package com.github.colorspace.connector;

import com.github.colorspace.ColorSpace;
import com.github.colorspace.rgb.Rgb;
import com.github.utils.FloatArray;
import com.github.utils.MatrixUtils;
import org.ejml.simple.SimpleMatrix;

public class Connector {

    private final Rgb source;
    private final Rgb destination;
    // TODO: since I don't know how to support different types of renderIntents
    //       I leaved the attribute here but it's not used.
    private final RenderIntent renderIntent;
    private final SimpleMatrix conversionMatrix;

    private static final SimpleMatrix BRADFORD = new SimpleMatrix(3, 3, true, new double[] {
             0.8951,  0.2664, -0.1614,
            -0.7502,  1.7135,  0.0367,
             0.0389, -0.0685,  1.0296
    });

    // TODO: In the Android documentation it accepts a ColorSpace instead of a Rgb object.
    //       Since not all colorspaces have a transform matrix it's easier to support only Rgb colorspaces.
    public Connector(Rgb source, Rgb destination, RenderIntent renderIntent) {
        this.source = source;
        this.destination = destination;
        this.renderIntent = renderIntent;

        SimpleMatrix src = MatrixUtils.toSimpleMatrix(source.getTransform(), 3, 3);
        SimpleMatrix dst = MatrixUtils.toSimpleMatrix(destination.getInverseTransform(), 3, 3);

        if (renderIntent == RenderIntent.ABSOLUTE) {
            // skip chromatic adaptation
            conversionMatrix = dst.mult(src);
        } else {
            // also perform chromatic adaptation
            SimpleMatrix chromaticAdaptationMtx = getChromaticAdaptationMtx();
            conversionMatrix = dst.mult(chromaticAdaptationMtx).mult(src);
        }
    }

    private SimpleMatrix getChromaticAdaptationMtx() {
        SimpleMatrix srcW = new SimpleMatrix(3, 1, true, new double[] {
                source.getWhitePoint().x, source.getWhitePoint().y, source.getWhitePoint().z
        });

        SimpleMatrix dstW = new SimpleMatrix(3, 1, true, new double[] {
                destination.getWhitePoint().x, destination.getWhitePoint().y, destination.getWhitePoint().z
        });

        SimpleMatrix s = BRADFORD.mult(srcW);
        SimpleMatrix d = BRADFORD.mult(dstW);

        SimpleMatrix tmp = new SimpleMatrix(3, 3, true, new double[] {
                d.get(0, 0) / s.get(0, 0), 0, 0,
                0,  d.get(1, 0) / s.get(1, 0), 0,
                0, 0, d.get(2, 0) / s.get(2, 0)
        });

        return BRADFORD.invert().mult(tmp).mult(BRADFORD);
    }

    public ColorSpace getSourceColorSpace() {
        return source;
    }

    public ColorSpace getDestinationColorSpace() {
        return destination;
    }

    public RenderIntent getRenderIntent() {
        return renderIntent;
    }

    public FloatArray transform(float r, float g, float b) {
        return new FloatArray(r, g, b);
    }

    public FloatArray transform(FloatArray v) {
        FloatArray nonLinear = source.toLinear(v);

        SimpleMatrix rgbSrc = MatrixUtils.toSimpleMatrix(nonLinear, 3, 1);
        SimpleMatrix rgbDst = conversionMatrix.mult(rgbSrc);

        FloatArray nonLinearDst = MatrixUtils.toFloatArray(rgbDst);
        return destination.fromLinear(nonLinearDst);
    }

}
