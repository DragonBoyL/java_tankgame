package com.whut.tankgame03;

import com.sun.org.apache.xml.internal.security.signature.reference.ReferenceNodeSetData;

import java.util.TreeMap;

/**
 * @version 1.0
 * @auther 李栋龙
 */
public class Shot implements Runnable {
    int x;
    int y;
    int direction=0;
    int speed=2;
    boolean isLive=true;

    public Shot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (direction){
                case 0:
                    y-=speed;break;
                case 1:
                    x+=speed;break;
                case  2:
                    y+=speed;break;
                case 3:
                    x-=speed;break;
            }

            System.out.println("子弹的坐标为"+x+","+y);
            if(!(x>=0&&x<=1000&&y>=0&&y<=750)){
                isLive=false;
                break;
            }
        }
    }
}
