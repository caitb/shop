package com.masiis.shop.web.platform.utils.image;

import com.masiis.shop.common.util.OSSObjectUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by cai_tb on 16/5/20.
 */
public class DrawImageUtil {

    public static void drawImage(int width, int height, List<Element> elements, String savePath) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();

        for(Element element : elements){
            if(element.getContent() instanceof String){
                g.setFont(element.getFont());
                g.setColor(element.getColor());

                if(element.getLineStyle()==0){
                    g.drawString(element.getContent().toString(), element.getX(), element.getY());
                }else if(element.getLineStyle()==1){
                    for(int i=0; i<element.getContent().toString().length(); i++){
                        int l = i%16;
                        int t = i/16;
                        g.drawString(
                                ((String)element.getContent()).substring(i, i+1),
                                element.getX()+element.getFont().getSize()*l,
                                element.getY()+(element.getFont().getSize()+8)*(t+1)
                        );
                    }
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

        ByteArrayOutputStream bs = null;
        ImageOutputStream imOut = null;
        InputStream is = null;
        try {
            bs = new ByteArrayOutputStream();
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bufferedImage, "png", imOut);
            is = new ByteArrayInputStream(bs.toByteArray());

            OSSObjectUtils.deleteBucketFile(savePath);
            OSSObjectUtils.uploadFile(savePath, is);
        } catch (Exception e) {
            bs.close();
            imOut.close();
            is.close();

            e.printStackTrace();
        }
    }
}
