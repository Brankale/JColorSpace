package com.github.brankale.jcolorspace.colorspace.connector

import com.github.brankale.jcolorspace.colorspace.rgb.WhitePoint
import com.github.brankale.jcolorspace.utils.FloatArray
import com.github.brankale.jcolorspace.utils.MatrixUtils
import org.ejml.simple.SimpleMatrix

enum class Adaptation(private val mtx: SimpleMatrix) {
    XYZ_SCALING(
        SimpleMatrix(
            3, 3, true, *doubleArrayOf(
                1.0, 0.0, 0.0,
                0.0, 1.0, 0.0,
                0.0, 0.0, 1.0
            )
        )
    ),

    VON_KRIES(
        SimpleMatrix(
            3, 3, true, *doubleArrayOf(
                0.40024, 0.70760, -0.08081,
                -0.22630, 1.16532, 0.04570,
                0.00000, 0.00000, 0.91822
            )
        )
    ),

    BRADFORD(
        SimpleMatrix(
            3, 3, true, *doubleArrayOf(
                0.8951, 0.2664, -0.1614,
                -0.7502, 1.7135, 0.0367,
                0.0389, -0.0685, 1.0296
            )
        )
    );

    fun getChromaticAdaptationMtx(src: WhitePoint, dst: WhitePoint): FloatArray {
        val whiteSource = SimpleMatrix(
            3, 1, true, *doubleArrayOf(
                src.x.toDouble(),
                src.y.toDouble(),
                src.z.toDouble()
            )
        )

        val whiteDestination = SimpleMatrix(
            3, 1, true, *doubleArrayOf(
                dst.x.toDouble(),
                dst.y.toDouble(),
                dst.z.toDouble()
            )
        )

        // source white in LMS space
        val s = mtx.mult(whiteSource)

        // destination white in LMS space
        val d = mtx.mult(whiteDestination)

        val tmp = SimpleMatrix(
            3, 3, true, *doubleArrayOf(
                d[0, 0] / s[0, 0], 0.0, 0.0,
                0.0, d[1, 0] / s[1, 0], 0.0,
                0.0, 0.0, d[2, 0] / s[2, 0]
            )
        )

        val chromaticAdaptationMtx = mtx.invert().mult(tmp).mult(mtx)
        return MatrixUtils.toFloatArray(chromaticAdaptationMtx)
    }
}
