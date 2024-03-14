package com.whut.tankgame03;

import javax.swing.*;

/**
 * @version 1.0
 * @auther 李栋龙
 */
public class HspTankGame03 extends JFrame {
    //定义MyPanel
    MyPanel mp=null;
    public static void main(String[] args) {
        new HspTankGame03();
    }

    public HspTankGame03() {
        mp=new MyPanel();
        new Thread(mp).start();
        this.add(mp);//（游戏的绘图区）
        this.setSize(1000,750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
