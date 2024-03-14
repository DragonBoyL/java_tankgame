package com.whut.tankgame01;

import javax.swing.*;
import java.awt.*;

/**
 * @version 1.0
 * @auther 李栋龙
 */
public class MyPanel extends JPanel {
    //定义我的坦克
    MyTank mytank = null;

    public MyPanel() {
        mytank = new MyTank(100, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色

        //画出坦克
        drawTank(mytank.getX(), mytank.getY(), g, 0, 0);
    }

    /**
     * @param x
     * @param y
     * @param g         画笔
     * @param direction 坦克的方向
     * @param type      坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {

        //根据坦克类型设置不同的颜色
        switch (type) {
            case 0://我们的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克的方向，绘制坦克
        switch (direction) {
            case 0://0表示向上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10,  20, 40, false);//画出坦克右边的盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出坦克右边的盖子
                g.drawLine(x+20,y+30,x+20,y);
                break;
            default:

        }
    }
}
