package com.masiis.shop.web.platform.utils;

import org.springframework.beans.factory.config.YamlProcessor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
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
            System.out.println(e.getMessage());
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
        DrawPicUtil drawPicUtil = new DrawPicUtil();
        BufferedImage bufferedImage = drawPicUtil.loadImageLocal("D:\\level1.jpg");
        List<DrawPicParam> drawPicParams = new ArrayList<>();
        DrawPicParam drawPicParam1 = drawPicUtil.getDrawPicParam();
        drawPicParam1.setY(200);
        drawPicParam1.setContent("授权编号：MASIIS200461600268");//
        drawPicParams.add(drawPicParam1);
        DrawPicParam drawPicParam5 = drawPicUtil.getDrawPicParam();
        drawPicParam5.setY(630);
        drawPicParam5.setContent("BOSS");
        drawPicParam5.setFont(new Font("楷体", Font.BOLD, 60));
        drawPicParams.add(drawPicParam5);
        DrawPicParam drawPicParam3 = drawPicUtil.getDrawPicParam();
        drawPicParam3.setY(840);
        drawPicParam3.setContent("赵  亮");
        drawPicParam3.setFont(new Font("楷体", Font.BOLD, 60));
        drawPicParams.add(drawPicParam3);
        DrawPicParam drawPicParam4 = drawPicUtil.getDrawPicParam();
        drawPicParam4.setY(900);
        drawPicParam4.setContent("有权于网络和实体渠道销售麦士生物科技产品");
        drawPicParams.add(drawPicParam4);
        DrawPicParam drawPicParam9 = drawPicUtil.getDrawPicParam();
        drawPicParam9.setY(950);
        drawPicParam9.setContent("抗引力光感精华液");
        drawPicParams.add(drawPicParam9);
        int x = 280;
        int y = 80;
        DrawPicParam drawPicParam6 = drawPicUtil.getDrawPicParam();
        drawPicParam6.setX(x);
        drawPicParam6.setY(drawPicParam9.getY() + y);
        drawPicParam6.setContent("证件号：210381111111223455");
        drawPicParams.add(drawPicParam6);
        DrawPicParam drawPicParam7 = drawPicUtil.getDrawPicParam();
        drawPicParam7.setX(x);
        drawPicParam7.setY(drawPicParam6.getY() + y);
        drawPicParam7.setContent("手  机：18612111522");
        drawPicParams.add(drawPicParam7);
        DrawPicParam drawPicParam8 = drawPicUtil.getDrawPicParam();
        drawPicParam8.setX(x);
        drawPicParam8.setY(drawPicParam7.getY() + y);
        drawPicParam8.setContent("微  信：nevermore1126");
        drawPicParams.add(drawPicParam8);
        DrawPicParam drawPicParam2 = drawPicUtil.getDrawPicParam();
        drawPicParam2.setX(x);
        drawPicParam2.setY(drawPicParam8.getY() + y);
        drawPicParam2.setContent("授权期限：2016年05月09日至2018年05月09日");
        drawPicParams.add(drawPicParam2);
        DrawPicParam drawPicParam10 = drawPicUtil.getDrawPicParam();
        drawPicParam10.setX(x);
        drawPicParam10.setY(drawPicParam2.getY() + 50);
        drawPicParam10.setContent("最终解释权归北京麦士生物科技有限责任公司");
        drawPicParam10.setFont(new Font("楷体", Font.BOLD, 25));
        drawPicParams.add(drawPicParam10);

//        drawPicUtil.writeImageLocal("D:\\level1_result.jpg", drawPicUtil.modifyImage(bufferedImage, "AAA", 200, 200));
        drawPicUtil.writeImageLocal("D:\\level1_result.jpg", drawPicUtil.modifyImage(bufferedImage, drawPicParams));
        System.out.print("success");
    }

}
