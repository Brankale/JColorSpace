package com.github.brankale.jcolorspace.colorspace.connector;

import com.github.brankale.jcolorspace.colorspace.rgb.WhitePoint;
import com.github.brankale.jcolorspace.utils.FloatArray;
import com.github.brankale.jcolorspace.utils.MatrixUtils;
import org.ejml.simple.SimpleMatrix;

public enum Adaptation {

    XYZ_SCALING(new SimpleMatrix(3, 3, true, new double[] {
            1.0, 0.0, 0.0,
            0.0, 1.0, 0.0,
            0.0, 0.0, 1.0
    })),

    VON_KRIES(new SimpleMatrix(3, 3, true, new double[] {
             0.40024,  0.70760, -0.08081,
            -0.22630,  1.16532,  0.04570,
             0.00000,  0.00000,  0.91822
    })),

    BRADFORD(new SimpleMatrix(3, 3, true, new double[] {
             0.8951,  0.2664, -0.1614,
            -0.7502,  1.7135,  0.0367,
             0.0389, -0.0685,  1.0296
    }));

    private final SimpleMatrix mtx;

    Adaptation(SimpleMatrix mtx) {
        this.mtx = mtx;
    }

    public FloatArray getChromaticAdaptationMtx(WhitePoint src, WhitePoint dst) {
        SimpleMatrix srcW = new SimpleMatrix(3, 1, true, new double[] {
                src.x, src.y, src.z
        });

        SimpleMatrix dstW = new SimpleMatrix(3, 1, true, new double[] {
                dst.x, dst.y, dst.z
        });

        SimpleMatrix s = mtx.mult(srcW);
        SimpleMatrix d = mtx.mult(dstW);

        SimpleMatrix tmp = new SimpleMatrix(3, 3, true, new double[] {
                d.get(0, 0) / s.get(0, 0), 0, 0,
                0,  d.get(1, 0) / s.get(1, 0), 0,
                0, 0, d.get(2, 0) / s.get(2, 0)
        });

        SimpleMatrix chromaticAdaptationMtx = mtx.invert().mult(tmp).mult(mtx);
        return MatrixUtils.toFloatArray(chromaticAdaptationMtx);
    }

}
