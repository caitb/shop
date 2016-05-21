package com.masiis.shop.api.utils.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by cai_tb on 16/5/20.
 */
public class DrawImageUtil {

    public static void drawImage(int width, int height, List<Element> elements, String savePath){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();

        for(Element element : elements){
            if(element.getContent() instanceof String){
                g.setFont(element.getFont());
                g.setColor(element.getColor());

                for(int i=0; i<element.getContent().toString().length(); i++){
                    int l = i%11;
                    int t = i/11;
                    g.drawString(
                            ((String)element.getContent()).substring(i, i+1),
                            element.getX()+element.getFont().getSize()*l,
                            element.getY()+element.getFont().getSize()*(t+1)
                    );
                }
            }else if(element.getContent() instanceof BufferedImage){

                g.drawImage(
                        (BufferedImage)element.getContent(),
                        element.getX(),
                        element.getY(),
                        element.getW(),
                        element.getH(),
                        null
                );
            }
        }

        try {
            ImageIO.write(bufferedImage, "png", new File(savePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
