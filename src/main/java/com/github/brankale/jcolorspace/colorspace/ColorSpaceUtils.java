package com.github.brankale.jcolorspace.colorspace;

import com.github.brankale.jcolorspace.colorspace.connector.Connector;
import com.github.brankale.jcolorspace.colorspaces.CieLab;
import com.github.brankale.jcolorspace.colorspaces.ColorSpaces;
import com.github.brankale.jcolorspace.utils.FloatArray;

public class ColorSpaceUtils {

    private ColorSpaceUtils() {
        // avoid instantiation
    }

    /**
     * @param cs     Colorspace of the two colors.
     * @param color1 first color.
     * @param color2 second color.
     * @return the deltaE 2000 between the two colors.
     */
    public static float deltaE(ColorSpace cs, FloatArray color1, FloatArray color2) {
        if (cs instanceof CieLab) {
            return deltaE2000(color1, color2);
        } else {
            Connector connector = cs.connect(ColorSpaces.CIE_LAB);
            FloatArray lab1 = connector.transform(color1);
            FloatArray lab2 = connector.transform(color2);
            return deltaE2000(lab1, lab2);
        }
    }

    /**
     * @param cs1    the colorspace of the first color.
     * @param color1 the first color.
     * @param cs2    the colorspace of the second color.
     * @param color2 the second color.
     * @return the deltaE 2000 between the two colors.
     */
    public static float deltaE(ColorSpace cs1, FloatArray color1, ColorSpace cs2, FloatArray color2) {
        FloatArray lab1;
        FloatArray lab2;

        // TODO: remove instanceof
        if (cs1 instanceof CieLab) {
            lab1 = new FloatArray(color1);
        } else {
            Connector connector1 = cs1.connect(ColorSpaces.CIE_LAB);
            lab1 = connector1.transform(color1);
        }

        if (cs2 instanceof CieLab) {
            lab2 = new FloatArray(color2);
        } else {
            Connector connector2 = cs2.connect(ColorSpaces.CIE_LAB);
            lab2 = connector2.transform(color2);
        }

        return deltaE2000(lab1, lab2);
    }

    private static float deltaE2000(FloatArray lab1, FloatArray lab2) {
        final double _L = (lab1.get(0) + lab2.get(0)) / 2.0;

        final double C1 = Math.sqrt(square(lab1.get(1)) + square(lab1.get(2)));
        final double C2 = Math.sqrt(square(lab2.get(1)) + square(lab2.get(2)));
        final double C = (C1 + C2) / 2.0;

        final double G = (1 - Math.sqrt(Math.pow(C, 7) / (Math.pow(C, 7) + Math.pow(25, 7)))) / 2.0;

        final double _a1 = lab1.get(1) * (1 + G);
        final double _a2 = lab2.get(1) * (1 + G);

        final double _C1 = Math.sqrt(square(_a1) + square(lab1.get(2)));
        final double _C2 = Math.sqrt(square(_a2) + square(lab2.get(2)));

        final double _C = (_C1 + _C2) / 2.0;

        double _h1 = Math.atan2(lab1.get(2), _a1);
        if (_h1 < 0)
            _h1 += Math.toRadians(360);

        double _h2 = Math.atan2(lab2.get(2), _a2);
        if (_h2 < 0)
            _h2 += Math.toRadians(360);

        double _H;
        if (Math.abs(_h1 - _h2) > Math.toRadians(180))
            _H = (_h1 + _h2 + Math.toRadians(360)) / 2.0;
        else
            _H = (_h1 + _h2) / 2.0;

        final double T = 1 - 0.17 * Math.cos(_H - Math.toRadians(30)) + 0.24 * Math.cos(2 * _H)
                + 0.32 * Math.cos(3 * _H + Math.toRadians(6)) - 0.2 * Math.cos(4 * _H - Math.toRadians(63));

        double _deltah;
        if (Math.abs(_h2 - _h1) <= Math.toRadians(180))
            _deltah = _h2 - _h1;
        else if (Math.abs(_h2 - _h1) > Math.toRadians(180) && _h2 <= _h1)
            _deltah = _h2 - _h1 + Math.toRadians(360);
        else
            _deltah = _h2 - _h1 - Math.toRadians(360);

        final double _deltaL = lab2.get(0) - lab1.get(0);
        final double _deltaC = _C2 - _C1;
        final double _deltaH = 2 * Math.sqrt(_C1 * _C2) * Math.sin(_deltah / 2.0);

        final double Sl = 1 + (0.015 * square(_L - 50)) / (Math.sqrt(20 + square(_L - 50)));
        final double Sc = 1 + 0.045 * _C;
        final double Sh = 1 + 0.015 * _C * T;

        final double deltaTheta = 30 * Math.exp(-square((_H - Math.toRadians(-275)) / 25));

        final double Rc = 2 * Math.sqrt(Math.pow(_C, 7) / (Math.pow(_C, 7) + Math.pow(25, 7)));

        final double Rt = -Rc * Math.sin(2 * deltaTheta);

        final double Kl = 1.0;
        final double Kc = 1.0;
        final double Kh = 1.0;

        final double tmp1 = square(_deltaL / (Kl * Sl));
        final double tmp2 = square(_deltaC / (Kc * Sc));
        final double tmp3 = square(_deltaH / (Kh * Sh));
        final double tmp4 = Rt * (_deltaC / (Kc * Sc)) * (_deltaH / (Kh * Sh));

        final double deltaE = Math.sqrt(tmp1 + tmp2 + tmp3 + tmp4);
        return (float) deltaE;
    }

    private static double square(double value) {
        return value * value;
    }

}
