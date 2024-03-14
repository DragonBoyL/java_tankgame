package com.whut.tankgame05;

/**
 * @version 1.0
 * @auther 李栋龙
 */
public class Bomb {
    int x, y;
    int life = 9;//炸弹的声明周期
    boolean isLive = true;//是否还存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
