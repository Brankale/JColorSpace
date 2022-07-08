package com.github;

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

    // TODO: In the Android documentation it accepts a ColorSpace instead of a Rgb object.
    //       Since not all colorspaces have a transform matrix it's easier to support only Rgb colorspaces.
    public Connector(Rgb source, Rgb destination, RenderIntent renderIntent) {
        this.source = source;
        this.destination = destination;
        this.renderIntent = renderIntent;

        SimpleMatrix src = MatrixUtils.toSimpleMatrix(source.getTransform(), 3, 3);
        SimpleMatrix dst = MatrixUtils.toSimpleMatrix(destination.getInverseTransform(), 3, 3);

        // TODO: adaptation matrix?
        conversionMatrix = dst.mult(src);
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
        SimpleMatrix rgbSrc = MatrixUtils.toSimpleMatrix(v, 3, 1);
        SimpleMatrix rgbDst = conversionMatrix.mult(rgbSrc);
        return MatrixUtils.toFloatArray(rgbDst);
    }

}
