package com.github.colorspace.connector;

import com.github.colorspace.rgb.Rgb;
import com.github.utils.FloatArray;
import com.github.utils.MatrixUtils;
import org.ejml.simple.SimpleMatrix;

public class RgbConnector extends Connector {
    private final SimpleMatrix conversionMatrix;

    // TODO: move into a separate class
    private static final SimpleMatrix BRADFORD = new SimpleMatrix(3, 3, true, new double[] {
             0.8951,  0.2664, -0.1614,
            -0.7502,  1.7135,  0.0367,
             0.0389, -0.0685,  1.0296
    });

    public RgbConnector(Rgb source, Rgb destination, RenderIntent renderIntent) {
        super(source, destination, renderIntent);
        conversionMatrix = createConversionMatrix(source, destination, renderIntent);
    }

    private static SimpleMatrix createConversionMatrix(Rgb source, Rgb destination, RenderIntent renderIntent) {
        FloatArray srcTransformFA = source.getTransform();
        FloatArray dstInvTransformFA = destination.getInverseTransform();

        SimpleMatrix srcTransformMtx = MatrixUtils.toSimpleMatrix(srcTransformFA, 3, 3);
        SimpleMatrix dstTransformMtx = MatrixUtils.toSimpleMatrix(dstInvTransformFA, 3, 3);

        if (renderIntent == RenderIntent.ABSOLUTE) {
            return dstTransformMtx.mult(srcTransformMtx);
        } else {
            // TODO: RenderIntent.PERCEPTUAL and RenderIntent.SATURATION behave like RenderIntent.RELATIVE
            //       because I don't know how to implement them.
            SimpleMatrix chromaticAdaptationMtx = getChromaticAdaptationMtx(source, destination);
            return dstTransformMtx.mult(chromaticAdaptationMtx).mult(srcTransformMtx);
        }
    }

    // TODO: move into a separate class
    private static SimpleMatrix getChromaticAdaptationMtx(Rgb src, Rgb dst) {
        SimpleMatrix srcW = new SimpleMatrix(3, 1, true, new double[] {
                src.getWhitePoint().x, src.getWhitePoint().y, src.getWhitePoint().z
        });

        SimpleMatrix dstW = new SimpleMatrix(3, 1, true, new double[] {
                dst.getWhitePoint().x, dst.getWhitePoint().y, dst.getWhitePoint().z
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

    @Override
    public FloatArray transform(float r, float g, float b) {
        return transform(new FloatArray(r, g, b));
    }

    @Override
    public FloatArray transform(FloatArray v) {
        FloatArray linearSrcRgb = ((Rgb) source).toLinear(v);
        SimpleMatrix linearSrcRgbMtx = MatrixUtils.toSimpleMatrix(linearSrcRgb, 3, 1);
        SimpleMatrix linearDstRgbMtx = conversionMatrix.mult(linearSrcRgbMtx);
        FloatArray linearDstRgb = MatrixUtils.toFloatArray(linearDstRgbMtx);
        return ((Rgb) destination).fromLinear(linearDstRgb);
    }
}
