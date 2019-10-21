package com.zgw.Facede;

import com.zgw.chain.Collider;
import com.zgw.chain.ColliderChain;
import com.zgw.factory.*;
import com.zgw.property.PropertyMgr;
import com.zgw.strategy.FireStrategy;
import com.zgw.tankFrame.GoodTank;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel  {


    public GoodTank myTank;
    public List<GameObject> gameObjectList=null;
    public transient GameFactory gameFactory=null;
    public transient  FireStrategy fireStrategy=null;
    public transient ColliderChain colliderChain=null;

    private static final GameModel instance=new GameModel();
    static {
        instance.init();
    }
    public static GameModel getInstance(){
        return  instance;
    }

    private GameModel(){
        gameObjectList=new ArrayList<GameObject>();
        colliderChain=new ColliderChain();
        try {
        //jdk 9这个方法被废
        //(FireStrategy)Class.forName(PropertyMgr.get("fireStrategy")).getDeclaredConstructor().newInstance;
        gameFactory=(GameFactory)Class.forName(PropertyMgr.get("gameFactory")).newInstance();
        fireStrategy = (FireStrategy)Class.forName(PropertyMgr.get("fireStrategy")).newInstance();
        String[] colliderPaths=PropertyMgr.get("collider").split(",");
        for (String str: colliderPaths) {
            System.out.println(str);
            Collider collider = (Collider)Class.forName(str).newInstance();
            colliderChain.add(collider);
        }

    } catch (Exception e) {
            System.out.println("配置文件加载失败");
    }
    }
    public void init(){
        myTank=new GoodTank(200,200,40,40);
        int enemyCount=Integer.parseInt((String)PropertyMgr.get("initTankCount"));
        Random random = new Random();
        for (int i = 0; i <enemyCount ; i++) {
            gameFactory.createTank(random.nextInt(800),
                    random.nextInt(300),50,50);
        }

        int wallCount=Integer.parseInt((String)PropertyMgr.get("wallCount"));
        for (int i = 0; i <wallCount ; i++) {
            gameFactory.createWall(random.nextInt(800),
                    random.nextInt(600),50,150);
        }
    }

    public void createEnemy(){
        gameFactory.createTank(new Random().nextInt(800),
                new Random().nextInt(300),50,50);
    }

    public void addGameObject(GameObject gameObject){
        this.gameObjectList.add(gameObject);
    }
    public void removeGameObject(GameObject gameObject){
        this.gameObjectList.remove(gameObject);
    }

    public void paint(Graphics g) {
        myTank.paint(g);
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("总共的游戏物体数量"+gameObjectList.size(),10,40);
        g.setColor(color);
        for (int i = 0; i <gameObjectList.size() ; i++) {
            gameObjectList.get(i).paint(g);
        }
        collision();
    }

    private void collision(){
        for (int i = 0; i <gameObjectList.size() ; i++) {
            for (int j = i+1; j <gameObjectList.size() ; j++) {
                colliderChain.collision(gameObjectList.get(i),gameObjectList.get(j));

            }
        }

    }

    public BaseTank getMyTank() {

        return myTank;
    }
}
