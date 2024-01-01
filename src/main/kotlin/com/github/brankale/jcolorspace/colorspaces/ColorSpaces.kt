package com.github.brankale.jcolorspace.colorspaces

import com.github.brankale.jcolorspace.colorspace.ColorSpace
import com.github.brankale.jcolorspace.colorspace.rgb.Rgb

object ColorSpaces {
    @JvmField val CIE_LAB: ColorSpace = CieLab()
    @JvmField val SRGB: Rgb = Srgb()
    @JvmField val DCI_P3: Rgb = DciP3()
}
