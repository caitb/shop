package com.masiis.shop.web.common.utils;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * DrawPicUtil
 *
 * @author ZhaoLiang
 * @date 2016/5/9
 */
public class DrawPicUtil {
    private static Logger log = Logger.getLogger(DrawPicUtil.class);

    private Font font = new Font("", Font.PLAIN, 20);// 添加字体的属性设置

    private Graphics2D g = null;

    private int fontsize = 0;

    private int x = 0;

    private int y = 0;

    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入网络图片到缓冲区
     */
    public BufferedImage loadImageUrl(String imgName) {
        try {
            URL url = new URL(imgName);
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 设定文字的字体等
     */
    public void setFont(String fontStyle, int fontSize) {
        this.fontsize = fontSize;
        this.font = new Font(fontStyle, Font.PLAIN, fontSize);
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     */
    public BufferedImage modifyImage(BufferedImage img, Object content, int x,
                                     int y) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.RED);
            if (this.font != null)
                g.setFont(this.font);
            // 验证输出位置的纵坐标和横坐标
            if (x >= h || y >= w) {
                this.x = h - this.fontsize + 2;
                this.y = w;
            } else {
                this.x = x;
                this.y = y;
            }
            if (content != null) {
                g.drawString(content.toString(), this.x, this.y);
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（输出多个文本段） xory：true表示将内容在一行中输出；false表示将内容多行输出
     */
    public BufferedImage modifyImage(BufferedImage img, Object[] contentArr,
                                     int x, int y, boolean xory) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.RED);
            if (this.font != null)
                g.setFont(this.font);
            // 验证输出位置的纵坐标和横坐标
            if (x >= h || y >= w) {
                this.x = h - this.fontsize + 2;
                this.y = w;
            } else {
                this.x = x;
                this.y = y;
            }
            if (contentArr != null) {
                int arrlen = contentArr.length;
                if (xory) {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.x += contentArr[i].toString().length()
                                * this.fontsize / 2 + 5;// 重新计算文本输出位置
                    }
                } else {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.y += this.fontsize + 2;// 重新计算文本输出位置
                    }
                }
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {
        try {
            int w = b.getWidth();
            int h = b.getHeight();
            g = d.createGraphics();
            g.drawImage(b, 100, 10, w, h, null);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return d;
    }

    /**
     * 自定义修改图片 往图片上打字
     *
     * @param img
     * @param drawPicParamList
     * @return
     */
    public BufferedImage modifyImage(BufferedImage img, List<DrawPicParam> drawPicParamList) {
        try {
            int width = img.getWidth();
            int height = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            for (DrawPicParam drawPicParam : drawPicParamList) {
                g.setFont(drawPicParam.getFont());
                g.setColor(drawPicParam.getColor());
                if (drawPicParam.getX() == 0) {
                    int strSize = getStringPointSize(drawPicParam.getContent(), drawPicParam.getFont());
                    g.drawString(drawPicParam.getContent(), (width - strSize) / 2, drawPicParam.getY());
                } else {
                    g.drawString(drawPicParam.getContent(), drawPicParam.getX(), drawPicParam.getY());
                }
            }
            g.dispose();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return img;
    }

    /**
     * 计算字符串的像素长度与高度
     *
     * @param str  字符串
     * @param font 字体
     */
    public int getStringPointSize(String str, Font font) {
        FontMetrics metrics = new FontMetrics(font) {
        };
        Rectangle2D bounds = metrics.getStringBounds(str, null);
        return (int) bounds.getWidth();
    }

    public DrawPicParam getDrawPicParam() {
        return new DrawPicParam();
    }

    public class DrawPicParam {
        private String content;
        private int x = 0;
        private int y = 0;
        private Color color = Color.decode("#231815");
        private Font font = new Font("楷体", Font.PLAIN, 35);

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Font getFont() {
            return font;
        }

        public void setFont(Font font) {
            this.font = font;
        }
    }


    public static void main(String[] args) {
//        String s = "1,96,146,149,159,175,191,207,213,";
//        s = s.substring(s.indexOf("175,"), s.length());
//        System.out.println(s);
        DrawPicUtil drawPicUtil = new DrawPicUtil();
        BufferedImage bufferedImage = drawPicUtil.loadImageLocal("D:\\level4.jpg");
        List<DrawPicParam> drawPicParams = new ArrayList<>();
        DrawPicParam drawPicParam1 = drawPicUtil.getDrawPicParam();
        drawPicParam1.setX(90);
        drawPicParam1.setY(200);
        drawPicParam1.setContent("MASIIS400021600335");//
        drawPicParam1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        drawPicParams.add(drawPicParam1);
        DrawPicParam drawPicParam5 = drawPicUtil.getDrawPicParam();
        drawPicParam5.setY(200);
        drawPicParam5.setContent("执行董事");
        drawPicParam5.setFont(new Font("微软雅黑", Font.PLAIN, 100));
        drawPicParam5.setColor(Color.WHITE);
        drawPicParams.add(drawPicParam5);
        DrawPicParam drawPicParam3 = drawPicUtil.getDrawPicParam();
        drawPicParam3.setX(900);
        drawPicParam3.setY(565);
        drawPicParam3.setContent("段永成");
        drawPicParam3.setFont(new Font("宋体", Font.PLAIN, 50));
        drawPicParams.add(drawPicParam3);
        DrawPicParam drawPicParam4 = drawPicUtil.getDrawPicParam();
        drawPicParam4.setY(690);
        drawPicParam4.setContent("Has the right to sell the product of Beijing Masiis Biotech Co., Ltd.(Total Lighting Velvet Cream)");
        drawPicParam4.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        drawPicParams.add(drawPicParam4);
        DrawPicParam drawPicParam11 = drawPicUtil.getDrawPicParam();
        drawPicParam11.setY(725);
        drawPicParam11.setContent("via both e-commerce channel and physical store. ");
        drawPicParam11.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        drawPicParams.add(drawPicParam11);
        DrawPicParam drawPicParam9 = drawPicUtil.getDrawPicParam();
        drawPicParam9.setY(770);
        drawPicParam9.setContent("麦士抗引力焕颜新漾丝绒面纱霜");
        drawPicParam9.setFont(new Font("宋体", Font.PLAIN, 30));
        drawPicParam9.setColor(Color.decode("#FF4B21"));
        drawPicParams.add(drawPicParam9);
        String idCard = "620402198509270019";
        String newIdCard = idCard.substring(0, 4) + "**********" + idCard.substring(idCard.length() - 4, idCard.length());
        DrawPicParam drawPicParam6 = drawPicUtil.getDrawPicParam();
        drawPicParam6.setY(850);
        drawPicParam6.setContent("证件号：" + newIdCard + "，手机：13893036663，微信：dyc286588441");
        drawPicParam6.setFont(new Font("宋体", Font.PLAIN, 45));
        drawPicParams.add(drawPicParam6);
        DrawPicParam drawPicParam2 = drawPicUtil.getDrawPicParam();
        drawPicParam2.setY(920);
        drawPicParam2.setContent("授权期限：2016年05月30日至2016年12月31日");
        drawPicParam2.setFont(new Font("宋体", Font.PLAIN, 45));
        drawPicParams.add(drawPicParam2);

//        drawPicUtil.writeImageLocal("D:\\level1_result.jpg", drawPicUtil.modifyImage(bufferedImage, "AAA", 200, 200));
        drawPicUtil.writeImageLocal("D:\\level4_result.jpg", drawPicUtil.modifyImage(bufferedImage, drawPicParams));
        System.out.print("success");
    }

}
