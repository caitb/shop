package com.masiis.shop.common.util.image;

import com.masiis.shop.common.util.OSSObjectUtils;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
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

    public static BufferedImage drawImage(int width, int height, List<Element> elements, String savePath) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();


        g.setColor(new Color(225,225,225));
        g.fillRect(0,0,width, height);
        g.setColor(Color.BLACK);

        for(Element element : elements){
            if(element.getContent() instanceof String){
                g.setFont(element.getFont());
                g.setColor(element.getColor());

                if(element.getLineStyle()==0){
                    g.drawString(element.getContent().toString(), element.getX(), element.getY());
                }else if(element.getLineStyle()==1){
                    for(int i=0; i<element.getContent().toString().length(); i++){
                        int l = i%11;
                        int t = i/11;
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
            } else if(element.getContent() == null) {
                g.setColor(element.getColor());
                g.drawRect(element.getX(), element.getY(), element.getW(), element.getH());
                g.fillRoundRect(element.getX(), element.getY(), element.getW(), element.getH(), 5, 5);
                g.setColor(Color.BLACK);
            }
        }

//        if(StringUtils.isNotBlank(savePath)) {
//            ByteArrayOutputStream bs = null;
//            ImageOutputStream imOut = null;
//            InputStream is = null;
//            try {
//                bs = new ByteArrayOutputStream();
//                imOut = ImageIO.createImageOutputStream(bs);
//                ImageIO.write(bufferedImage, "png", imOut);
//                is = new ByteArrayInputStream(bs.toByteArray());
//
//                OSSObjectUtils.deleteBucketFile(savePath);
//                OSSObjectUtils.uploadFile(savePath, is);
//
//            } catch (Exception e) {
//                bs.close();
//                imOut.close();
//                is.close();
//
//                e.printStackTrace();
//            }
//        }

        return bufferedImage;
    }

    public static BufferedImage makeCircle(BufferedImage image) {

        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, w, h));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }
}
