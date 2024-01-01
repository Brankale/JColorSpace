package com.github.brankale.jcolorspace.colorspace.connector;

import com.github.brankale.jcolorspace.colorspace.ColorSpace;
import com.github.brankale.jcolorspace.utils.FloatArray;

public abstract class Connector {
    protected final ColorSpace source;
    protected final ColorSpace destination;
    protected final RenderIntent renderIntent;
    protected boolean clipping;

    protected Connector (ColorSpace source, ColorSpace destination, RenderIntent renderIntent) {
        this.source = source;
        this.destination = destination;
        this.renderIntent = renderIntent;
        clipping = true;
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

    /**
     * Clips FloatArray values in the valid range defined by ColorSpace.getMinValue() / getMaxValue().
     * By default, clipping is set to true.
     *
     * @param clipping true to enable clipping, false to disable clipping.
     */
    public void setClipping(boolean clipping) {
        this.clipping = clipping;
    }

    protected FloatArray clip(FloatArray array) {
        FloatArray copy = new FloatArray(array);
        for (int component = 0; component < destination.getColorModel().componentCount(); ++component) {
            if (copy.get(component) < destination.getMinValue(component))
                copy.set(component, destination.getMinValue(component));
            else if (copy.get(component) > destination.getMaxValue(component))
                copy.set(component, destination.getMaxValue(component));
        }
        return copy;
    }

    public abstract FloatArray transform(float r, float g, float b);

    public abstract FloatArray transform(FloatArray v);
}
