package mcdli5.nihilfit.util;

import java.awt.*;

public final class ColorUtils {
    public static Color average(Color colorLeft, Color colorRight, float leftWeight) {
        float rightWeight = 1.0F - leftWeight;
        int red = (int) Math.sqrt(colorLeft.getRed() * colorLeft.getRed() * leftWeight + colorRight.getRed() * colorRight.getRed() * rightWeight);
        int green = (int) Math.sqrt(colorLeft.getGreen() * colorLeft.getGreen() * leftWeight + colorRight.getGreen() * colorRight.getGreen() * rightWeight);
        int blue = (int) Math.sqrt(colorLeft.getBlue() * colorLeft.getBlue() * leftWeight + colorRight.getBlue() * colorRight.getBlue() * rightWeight);
        return new Color(red, green, blue);
    }
}
