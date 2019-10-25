package com.zgw.Facede;

import com.zgw.chain.Collider;
import com.zgw.chain.ColliderChain;
import com.zgw.factory.*;
import com.zgw.property.PropertyMgr;
import com.zgw.strategy.FireStrategy;
import com.zgw.tankFrame.GoodTank;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class GameModel  {


    public GoodTank myTank;
    public Map<UUID,GameObject> gameMap=null;
    public Map<UUID,GameObject> tankMap=null;
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
        gameMap=new HashMap<UUID, GameObject>();
        tankMap=new HashMap<UUID, GameObject>();
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
        Random random = new Random();
        myTank=new GoodTank(random.nextInt(700),random.nextInt(500),40,40);
        /*int enemyCount=Integer.parseInt((String)PropertyMgr.get("initTankCount"));
        for (int i = 0; i <enemyCount ; i++) {
            gameFactory.createTank(random.nextInt(800),
                    random.nextInt(300),50,50);
        }*/
        /*int wallCount=Integer.parseInt((String)PropertyMgr.get("wallCount"));
        for (int i = 0; i <wallCount ; i++) {
            gameFactory.createWall(random.nextInt(800),
                    random.nextInt(600),50,150);
        }*/
    }

    /*public void createEnemy(){
        gameFactory.createTank(new Random().nextInt(800),
                new Random().nextInt(300),50,50);
    }*/


    public void addGameObject(GameObject gameObject){
       if ((gameObject instanceof BaseTank) && !tankMap.containsKey(gameObject.getUuid()))
           tankMap.put(gameObject.getUuid(), gameObject);
       this.gameMap.put(gameObject.getUuid(),gameObject);
    }
    public void removeGameObject(GameObject gameObject){
       if((gameObject instanceof BaseTank)&&tankMap.containsKey(gameObject.getUuid()))
           tankMap.remove(gameObject.getUuid());
       this.gameMap.remove(gameObject.getUuid());
    }

    public GameObject removeGameObject(UUID uuid){
        GameObject remove = tankMap.remove(uuid);
        GameObject remove1 = gameMap.remove(uuid);
        return remove==remove1?remove:null;
    }
    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("总共的游戏物体数量"+gameMap.size(),10,40);
        g.setColor(color);


        Object[] objects = gameMap.values().toArray();
        for (int i = 0; i <objects.length ; i++) {
            ((GameObject)objects[i]).paint(g);
        }

        collision();
    }

    private void collision(){

        Object[] objects = gameMap.values().toArray();
        for (int i = 0; i <objects.length ; i++) {
            for (int j = i+1; j <objects.length ; j++) {
                colliderChain.collision((GameObject) objects[i],(GameObject)objects[j]);
            }
        }

    }

    public boolean findTank(UUID id){
        if(this.tankMap.containsKey(id)) return true;
        else return  false;
    }

    public BaseTank getMyTank() {
        return myTank;
    }
}
