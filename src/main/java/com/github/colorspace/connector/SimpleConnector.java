package com.github.colorspace.connector;

import com.github.colorspace.ColorSpace;
import com.github.utils.FloatArray;

public class SimpleConnector extends Connector {
    public SimpleConnector(ColorSpace source, ColorSpace destination, RenderIntent renderIntent) {
        super(source, destination, renderIntent);
    }

    @Override
    public FloatArray transform(float r, float g, float b) {
        return transform(new FloatArray(r, g, b));
    }

    @Override
    public FloatArray transform(FloatArray v) {
        FloatArray xyz = source.toXyz(v);
        // TODO: add support for chromatic adaptation
        return destination.fromXyz(xyz);
    }
}
