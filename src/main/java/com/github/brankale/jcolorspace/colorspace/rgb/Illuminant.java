package com.github.brankale.jcolorspace.colorspace.rgb;

public class Illuminant {
    private Illuminant() {
        // hide constructor
    }

    public static final WhitePoint A   = new WhitePoint(1.09850f, 1.00000f, 0.35585f);
    public static final WhitePoint B   = new WhitePoint(0.99072f, 1.00000f, 0.85223f);
    public static final WhitePoint C   = new WhitePoint(0.98074f, 1.00000f, 1.18232f);
    public static final WhitePoint D50 = new WhitePoint(0.96422f, 1.00000f, 0.82521f);
    public static final WhitePoint D55 = new WhitePoint(0.95682f, 1.00000f, 0.92149f);
    public static final WhitePoint D65 = new WhitePoint(0.95047f, 1.00000f, 1.08883f);
    public static final WhitePoint D75 = new WhitePoint(0.94972f, 1.00000f, 1.22638f);
    public static final WhitePoint E   = new WhitePoint(1.00000f, 1.00000f, 1.00000f);
    public static final WhitePoint F2  = new WhitePoint(0.99186f, 1.00000f, 0.67393f);
    public static final WhitePoint F7  = new WhitePoint(0.95041f, 1.00000f, 1.08747f);
    public static final WhitePoint F11 = new WhitePoint(1.00962f, 1.00000f, 0.64350f);
}
