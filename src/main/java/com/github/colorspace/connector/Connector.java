package com.github.colorspace.connector;

import com.github.colorspace.ColorSpace;
import com.github.utils.FloatArray;

public abstract class Connector {
    protected final ColorSpace source;
    protected final ColorSpace destination;
    protected final RenderIntent renderIntent;

    protected Connector (ColorSpace source, ColorSpace destination, RenderIntent renderIntent) {
        this.source = source;
        this.destination = destination;
        this.renderIntent = renderIntent;
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

    public abstract FloatArray transform(float r, float g, float b);

    public abstract FloatArray transform(FloatArray v);
}
