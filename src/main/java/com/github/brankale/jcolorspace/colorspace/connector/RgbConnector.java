package com.github.brankale.jcolorspace.colorspace.connector;

import com.github.brankale.jcolorspace.colorspace.rgb.Rgb;
import com.github.brankale.jcolorspace.utils.FloatArray;
import com.github.brankale.jcolorspace.utils.MatrixUtils;
import org.ejml.simple.SimpleMatrix;

public class RgbConnector extends Connector {
    private final SimpleMatrix conversionMatrix;
    private SimpleMatrix chromaticAdaptationMtx;

    public RgbConnector(Rgb source, Rgb destination, RenderIntent renderIntent) {
        super(source, destination, renderIntent);
        setAdaptation(Adaptation.BRADFORD);
        conversionMatrix = createConversionMatrix(source, destination, renderIntent);
    }

    public void setAdaptation(Adaptation adaptation) {
        chromaticAdaptationMtx = MatrixUtils.toSimpleMatrix(adaptation.getChromaticAdaptationMtx(
                ((Rgb) source).getWhitePoint(),
                ((Rgb) destination).getWhitePoint()
        ), 3, 3);
    }

    private SimpleMatrix createConversionMatrix(Rgb source, Rgb destination, RenderIntent renderIntent) {
        FloatArray srcTransformFA = source.getTransform();
        FloatArray dstInvTransformFA = destination.getInverseTransform();

        SimpleMatrix srcTransformMtx = MatrixUtils.toSimpleMatrix(srcTransformFA, 3, 3);
        SimpleMatrix dstTransformMtx = MatrixUtils.toSimpleMatrix(dstInvTransformFA, 3, 3);

        return switch (renderIntent) {
            case ABSOLUTE -> dstTransformMtx.mult(srcTransformMtx);
            case RELATIVE -> dstTransformMtx.mult(chromaticAdaptationMtx).mult(srcTransformMtx);
            // TODO: RenderIntent.PERCEPTUAL and RenderIntent.SATURATION behave like RenderIntent.RELATIVE
            //       because I don't know how to implement them.
            default -> dstTransformMtx.mult(chromaticAdaptationMtx).mult(srcTransformMtx);
        };
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
