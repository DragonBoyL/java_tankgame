package com.whut.tankgame02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @version 1.0
 * @auther 李栋龙
 */
public class MyPanel extends JPanel implements KeyListener {
    //定义我的坦克
    MyTank mytank = null;
    //定义敌人坦克
    Vector<EnemyTank> enemyTanks =new Vector<>();
    int enemyTankSize=3;
    public MyPanel() {
        mytank = new MyTank(100, 100);
        for (int i = 0; i < enemyTankSize; i++) {
            EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
            enemyTank.setDirection(2);
            enemyTanks.add(enemyTank);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色

        //画出坦克
        drawTank(mytank.getX(), mytank.getY(), g, mytank.getDirection(), 1);
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirection(),0);
        }
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
            case 1://1表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克左边的轮子
                g.fill3DRect(x, y+30,  60, 10,false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20,  false);//画出坦克右边的盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出坦克右边的盖子
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2://2表示向下
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10,  20, 40, false);//画出坦克右边的盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出坦克右边的盖子
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3://3表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克左边的轮子
                g.fill3DRect(x, y+30,  60, 10,false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20,  false);//画出坦克右边的盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出坦克右边的盖子
                g.drawLine(x+30,y+20,x,y+20);
                break;
            default:

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //上下左右
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W){
            //改变坦克方向
            mytank.setDirection(0);
            mytank.moveUp();
        }else if (e.getKeyCode()==KeyEvent.VK_D){
            mytank.setDirection(1);
            mytank.moveRight();
        }else if (e.getKeyCode()==KeyEvent.VK_S){
            mytank.setDirection(2);
            mytank.moveDown();
        }else if (e.getKeyCode()==KeyEvent.VK_A){
            mytank.setDirection(3);
            mytank.moveLeft();
        }
        //画板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
