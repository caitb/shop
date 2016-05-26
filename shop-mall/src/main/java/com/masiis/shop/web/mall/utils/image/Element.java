package com.masiis.shop.web.mall.utils.image;

import java.awt.*;

/**
 * Created by cai_tb on 16/5/20.
 */
public class Element<T> {

    /* 水平坐标 */
    private int x;
    /* 竖直坐标 */
    private int y;
    /* 宽 */
    private int w;
    /* 高 */
    private int h;
    /* 字体 */
    private Font font;
    /* 颜色 */
    private Color color;
    /* 内容 */
    private T content;
    /* 行风格 */
    private int lineStyle = 1;

    public Element() {
    }

    public Element(int x, int y, Font font, Color color, T content) {
        this.x = x;
        this.y = y;
        this.font = font;
        this.color = color;
        this.content = content;
    }

    public Element(int x, int y, int w, int h, T content) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
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

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public int getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(int lineStyle) {
        this.lineStyle = lineStyle;
    }
}
