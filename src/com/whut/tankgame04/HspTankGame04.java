package com.whut.tankgame04;

import javax.swing.*;

/**
 * @version 1.0
 * @auther 李栋龙
 */
public class HspTankGame04 extends JFrame {
    //定义MyPanel
    MyPanel mp=null;
    public static void main(String[] args) {
        new HspTankGame04();
    }

    public HspTankGame04() {
        mp=new MyPanel();
        new Thread(mp).start();
        this.add(mp);//（游戏的绘图区）
        this.setSize(1200,950);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
