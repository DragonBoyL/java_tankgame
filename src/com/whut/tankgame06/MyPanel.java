package com.whut.tankgame06;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @version 1.0
 * @auther 李栋龙
 */
//为了让子弹重绘，需要将MyPanel变成线程
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    MyTank mytank = null;
    //定义敌人坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();

    //定义一个存放node对象
    Vector<Node> nodes=new Vector<>();
    //定义vector用于存放炸弹
    //当子弹击中坦克时，加入一个炸弹
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 3;

    //定义三张图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(String Key) {
        //先判断文件是否存在
        File file = new File(Recorder.getRecorderFile());
        if(file.exists()){
            nodes= Recorder.getNodesAndEnemyRec();
        }else{
            System.out.println("文件不存在，只能开启新游戏");
            Key="1";
        }

        Recorder.setEnemyTanks(enemyTanks);
        mytank = new MyTank(500, 100);
        switch (Key){
            case "1":
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
                    enemyTank.setEnemyTanks(enemyTanks);

                    enemyTank.setDirection(2);
                    //启动坦克线程
                    new Thread(enemyTank).start();
                    //给坦克加入子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2":
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY());
                    enemyTank.setEnemyTanks(enemyTanks);

                    enemyTank.setDirection(node.getDirection());
                    //启动坦克线程
                    new Thread(enemyTank).start();
                    //给坦克加入子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
            default:
                System.out.println("输入有误");
        }

        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/爆炸01.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/爆炸02.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/爆炸03.png"));

        //加载音乐
        //new AePlayWave("src\\com\\whut\\111.wav").start();
    }

    //编写方法记录成绩
    public void showInfo(Graphics g){
        //
        g.setColor(Color.black);

        g.setFont(new Font("宋体", Font.BOLD, 25));

        g.drawString("您累积击毁敌方坦克",1020,30);
        drawTank(1020,60,g,0,0);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnemyTankNum()+"",1080,100);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色

        showInfo(g);
        //画出坦克
        if (mytank != null && mytank.isLive) {
            drawTank(mytank.getX(), mytank.getY(), g, mytank.getDirection(), 1);
        }

//        //画出子弹
//        if (mytank.shot != null && mytank.shot.isLive == true) {
//            //g.fill3DRect(mytank.shot.x,mytank.shot.y,1,1,false);
//            g.draw3DRect(mytank.shot.x, mytank.shot.y, 2, 2, false);
//        }
        //将集合中的子弹都画出来
        for (int i = 0; i < mytank.shots.size(); i++) {
            Shot shot = mytank.shots.get(i);
            if (shot != null && shot.isLive == true) {
                g.draw3DRect(shot.x, shot.y, 2, 2, false);
            } else {
                mytank.shots.remove(shot);
            }

        }

        //如果集合中有炸弹，就画出
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //根据当前炸弹的life值画图
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDown();
            //如果炸弹的生命周期等于零，就从集合删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
        //画出敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 0);
                //画出子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 2, 2, false);
                    } else {//从vector移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
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
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克右边的盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出坦克右边的盖子
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1://1表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克左边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克右边的盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出坦克右边的盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2://2表示向下
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克右边的盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出坦克右边的盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3://3表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克左边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克右边的盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出坦克右边的盖子
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:

        }
    }

    //如果我们能发射多颗子弹，那么就需要把集合中所有的子弹都取出来判断
    public void hitEnemyTank() {
        for (int j = 0; j < mytank.shots.size(); j++) {
            Shot shot = mytank.shots.get(j);
            //判断是否击中了坦克
            if (shot != null && shot.isLive) {
                //遍历所有敌人坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    //判断敌人坦克是否击中我的坦克
    public void hitMyTank() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                //判断是否击中我们的坦克
                if (mytank.isLive && shot.isLive) {
                    hitTank(shot, mytank);
                }
            }
        }
    }

    //判断子弹是否击中敌人
    public void hitTank(Shot s, Tank enemyTank) {
        //判断子弹击中
        switch (enemyTank.getDirection()) {
            case 0:
            case 2:
                if (s.x >= enemyTank.getX() && s.x <= enemyTank.getX() + 40 && s.y >= enemyTank.getY() && s.y <= enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //将坦克从集合取出
                    enemyTanks.remove(enemyTank);

                    if(enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //把炸弹添加到集合
                    bombs.add(new Bomb(enemyTank.getX(), enemyTank.getY()));
                }
                break;
            case 3:
            case 1:
                if (s.x >= enemyTank.getX() && s.x <= enemyTank.getX() + 60 && s.y >= enemyTank.getY() && s.y <= enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //将坦克从集合取出
                    enemyTanks.remove(enemyTank);

                    if(enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //加入炸弹
                    bombs.add(new Bomb(enemyTank.getX(), enemyTank.getY()));
                }
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //上下左右
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            //改变坦克方向
            mytank.setDirection(0);
            if (mytank.getY() > 0) {
                mytank.moveUp();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            mytank.setDirection(1);
            if (mytank.getX() + 60 < 1000) {
                mytank.moveRight();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            mytank.setDirection(2);
            if (mytank.getY() + 60 < 750) {
                mytank.moveDown();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            mytank.setDirection(3);
            if (mytank.getX() > 0) {
                mytank.moveLeft();
            }

        }

        if (e.getKeyCode() == KeyEvent.VK_J) {
            //发射一颗子弹的情况
//            //判断子弹是否消亡
//            if(mytank.shot==null||mytank.shot.isLive==false){
//                mytank.shotEnemy();
//            }
            //发射多颗子弹
            mytank.shotEnemy();


        }
        //画板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //判断是否击中敌人坦克
            hitEnemyTank();
            //判断是否击中我
            hitMyTank();
            this.repaint();
        }
    }
}
