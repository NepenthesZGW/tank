package com.zgw.tankFrame;

import com.zgw.Facede.GameModel;
import com.zgw.factory.BaseTank;
import com.zgw.observer.FireObserver;
import com.zgw.observer.Observer;
import com.zgw.observer.TankFireEvent;
import com.zgw.resource.ResourceMgr;
import com.zgw.strategy.FireStrategy;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BadTank extends BaseTank {
    private static final int speed=5;
    private Random random;
    private int oldX,oldY;
    private List<Observer> observerList=new LinkedList<Observer>();
    {addObserver(new FireObserver());}

    public BadTank(int x, int y, int width, int height) {
        super();
        random=new Random();
        oldX=x;
        oldY=y;
        rectangle=new Rectangle(x,y,width,height);
        this.uuid=UUID.randomUUID();
        GameModel.getInstance().addGameObject(this);
    }

    public void addObserver(Observer observer){
        observerList.add(observer);
    }

    public void paint(Graphics g){

        switch (direct){
            case UP:
                g.drawImage(ResourceMgr.tankU,rectangle.x,rectangle.y,rectangle.width,rectangle.height,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.tankD,rectangle.x,rectangle.y,rectangle.width,rectangle.height,null);
                break;
            case LEFT:
                g.drawImage(ResourceMgr.tankL,rectangle.x,rectangle.y,rectangle.width,rectangle.height,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.tankR,rectangle.x,rectangle.y,rectangle.width,rectangle.height,null);
                break;
            default:break;
        }
        move();
    }

    public void fire(){
        TankFireEvent tankFireEvent = new TankFireEvent(this);
        for (Observer o:observerList) {
            o.dealEvet(tankFireEvent);
        }
    }

    public void fire(FireStrategy fireStrategy){
        int i = random.nextInt(100);
        if(i>=95) {
            fireStrategy.fire(this);
        }
    }

    public void back() {
        rectangle.x=oldX;
        rectangle.y=oldY;
    }

    public void die(){
        GameModel.getInstance().removeGameObject(this);
        GameModel.getInstance().gameFactory.createExplode(rectangle.x-20,rectangle.y-20);
    }

    public void stop() {
        if (direct == Direct.UP) rectangle.y += speed;
        else if (direct == Direct.DOWN) rectangle.y -= speed;
        else if (direct == Direct.LEFT) rectangle.x += speed;
        else if (direct == Direct.RIGHT) rectangle.x -= speed;
    }
    public void move(){
        fire();
        int i = random.nextInt(10000);
        if( i>=0  && i<100) direct=Direct.UP;
        if(i>=2500&&i<2600) direct=Direct.DOWN;
        if(i>=5000&&i<5100) direct=Direct.LEFT;
        if(i>=7500&&i<7600) direct=Direct.RIGHT;
        oldX=rectangle.x;
        oldY=rectangle.y;
        if (direct == Direct.UP) rectangle.y -= speed;
        if (direct == Direct.DOWN) rectangle.y += speed;
        if (direct == Direct.LEFT) rectangle.x -= speed;
        if (direct == Direct.RIGHT) rectangle.x += speed;
        canMove();

    }

    public boolean canMove(){
        if(rectangle.x<0)   {
            rectangle.x= 0;
            return false;
        }
        if(rectangle.x>750) {
            rectangle.x=750;
            return false;
        }
        if(rectangle.y<25){
            rectangle.y=25;
            return false;
        }
        if (rectangle.y>550){
            rectangle.y=550;
            return false;
        }
        return true;
    }

}
