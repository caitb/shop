package com.masiis.shop.common.util.image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawUtil {

    public static void drawString(Graphics2D g, String content, Font font, Color color, int x, int y) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(content, x, y);
    }

}
