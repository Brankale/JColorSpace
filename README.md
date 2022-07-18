# 🌈 JColorSpace 🌈

This library lets you create new ColorSpaces and perform conversions between them.

## Special thanks

I would like to really thank:
- the [Android documentation](https://developer.android.com/reference/android/graphics/ColorSpace) from which the architecture of the library is highly inspired.
- Bruce Justin Lindbloom for his awesome [website](http://www.brucelindbloom.com/) where you can find all kinds of formulas.

Without them this project wouldn't have been possible.

## How to use

### ⚠ Important Notes ⚠

In their current state APIs are not quite stable, so It can be possible that the following code snippets are wrong.

### Creating a new Rgb ColorSpace

```java
// extend Rgb class
class Srgb extends Rgb {
    public Srgb() {
        super(
            "sRGB IEC61966-2.1",    // name of the colorspace
            Primaries.BT709,        // primaries coordinates (red x, red y, green x, green y, blue x, blue y)
            Illuminant.D65,         // white point
            // opto-electrical transfer function (oetf)
            d -> {
                if (d < 0.0031308)
                    return 12.92 * d;
                else
                    return 1.055 * Math.pow(d, 1 / 2.4) - 0.055;
            },
            // electro-optical transfer function (eotf)
            d -> {
                if (d < 0.04045)
                    return d / 12.92;
                else
                    return Math.pow(((d + 0.055) / 1.055), 2.4);
            }
        );
    }
}
```

There are lots of constructors to simplify colorspace creation. For example:
- you can define a gamma value used for all the three channels
- you can define a specific gamma value for each individual channel.
- you can define a OETF/EOTF function used for all the three channels
- you can define a OETF/EOTF function for each individual channel.

### Creating a new ColorSpace

To create a non-RGB colorspace you must extend ColorSpace class.

### Perform conversion between colorspaces

```java
// creates a connector between two color spaces
Connector connector = ColorSpaces.CIE_LAB.connect(ColorSpaces.SRGB);

// define a color in CIELab colorspace
FloatArray lab = new FloatArray(0, 0, 0);

// perform the conversion from CIELab to sRGB
FloatArray rgb = connector.transform(lab);
```

## How does it differ from the Android colorspace lib

TODO
 
