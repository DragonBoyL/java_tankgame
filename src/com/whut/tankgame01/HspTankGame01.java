package com.whut.tankgame01;

import javax.swing.*;

/**
 * @version 1.0
 * @auther 李栋龙
 */
public class HspTankGame01 extends JFrame {
    //定义MyPanel
    MyPanel mp=null;
    public static void main(String[] args) {
        new HspTankGame01();
    }

    public HspTankGame01() {
        mp=new MyPanel();
        this.add(mp);//（游戏的绘图区）
        this.setSize(1000,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
