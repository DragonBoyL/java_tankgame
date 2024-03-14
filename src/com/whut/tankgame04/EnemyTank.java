package com.whut.tankgame04;

import java.util.Map;
import java.util.Vector;

/**
 * @version 1.0
 * @auther 李栋龙
 */
public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> shots = new Vector<Shot>();
    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true) {

            //如果没有子弹了，就创建一个
            if (isLive && shots.size() < 1) {
                Shot shot = null;
                switch (getDirection()) {
                    case 0:
                        shot = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        shot = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        shot = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        shot = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(shot);
                new Thread(shot).start();
            }

            //根据坦克的方向来继续移动
            switch (getDirection()) {
                case 0:
                    //让坦克保持一个方向走三十步
                    for (int i = 0; i < 30; i++) {
                        if (getY() >= 0) {
                            moveUp();
                        }
                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000) {
                            moveRight();
                        }

                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750) {
                            moveDown();
                        }

                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0) {
                            moveLeft();
                        }

                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }


            //随机改变
            setDirection((int) (Math.random() * 4));

            //写线程，要记得什么时候退出
            if (!isLive) {
                break;
            }
        }
    }
}
