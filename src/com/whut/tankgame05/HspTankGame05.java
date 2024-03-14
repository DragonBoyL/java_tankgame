package com.whut.tankgame05;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @version 1.0
 * @auther 李栋龙
 */
public class HspTankGame05 extends JFrame {
    //定义MyPanel
    MyPanel mp = null;
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        new HspTankGame05();
    }

    public HspTankGame05() {
        System.out.println("请输入：1.新游戏 2.继续游戏");
        String Key = sc.next();
        mp = new MyPanel(Key);
        new Thread(mp).start();
        this.add(mp);//（游戏的绘图区）
        this.setSize(1300, 950);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //在JFrame中添加窗口关闭处理时间
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
