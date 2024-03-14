package com.whut.tankgame06;

import java.io.*;
import java.util.Vector;

/**
 * @version 1.0
 * @auther 李栋龙
 */
public class Recorder {
    //记录击毁坦克数
    private static int allEnemyTankNum=0;
    private static BufferedWriter bw =null;
    private static BufferedReader br=null;
    private static String recorderFile="src\\myRecord.txt";
    private static Vector<EnemyTank> enemyTanks=null;


    //定义一个Node的Verctor，用于保存敌人的信息
    private static Vector<Node> nodes=new Vector<>();

    public static String getRecorderFile() {
        return recorderFile;
    }

    //读取文件，恢复相关信息
    public static Vector<Node> getNodesAndEnemyRec(){

        try {
            br=new BufferedReader(new FileReader(recorderFile));
            allEnemyTankNum=Integer.parseInt(br.readLine());
            //读取生成node
            String line="";
            while((line=br.readLine())!=null){
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return nodes;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }



    //当游戏退出，保存allEnemyTankNum
    public static void keepRecord(){
        try {
            bw=new BufferedWriter(new FileWriter(recorderFile));
            bw.write(allEnemyTankNum+"");
            bw.newLine();
            //记录坦克信息
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive){
                    String record=enemyTank.getX()+" "+enemyTank.getY()+" "+enemyTank.getDirection();
                    bw.write(record);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }
}
