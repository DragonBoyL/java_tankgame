package com.whut.tankgame06;

/**
 * @version 1.0
 * @auther 李栋龙
 * 一个Node对象，表示一个对人坦克的信息
 */
public class Node {
    private int x;
    private int y;
    private int direction;

    public Node(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
