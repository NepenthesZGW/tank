package com.zgw.tankFrame;

import com.zgw.Facede.GameModel;
import com.zgw.MemenTo.Snapshot;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MainFrame extends Frame {

    private GameModel gameModel=GameModel.getInstance();

    private static final int WIDTH=800;
    private static final int HEIGHT=600;
    public static Rectangle  rectangle=new Rectangle(0,0,WIDTH,HEIGHT);

    public MainFrame() {
        this.setSize(WIDTH,HEIGHT);
        this.setResizable(false);
        this.setTitle("Tank War");
        this.setVisible(true);

        this.addKeyListener(new TankKeyListener());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    @Override
    public void paint(Graphics g){
        gameModel.paint(g);
    }

    private Image offSreenImage=null;
    @Override
    public void update(Graphics g){

        if(null==offSreenImage) {
            //创建画布，image
            offSreenImage=this.createImage(WIDTH, HEIGHT);
        }
        //获取该画布offSreenImage的画笔gOffSreen画笔
        Graphics gOffSreen=offSreenImage.getGraphics();
        //保存原来颜色
        Color c=gOffSreen.getColor();
        //设置画笔颜色
        gOffSreen.setColor(Color.BLACK);
        //用画笔颜色填充画布
        gOffSreen.fillRect(0, 0, WIDTH, HEIGHT);
        //把画笔设置会原来的颜色
        gOffSreen.setColor(c);
        //调用画图 画到画布上
        paint(gOffSreen);
        //把画布画（铺）到窗体
        g.drawImage(offSreenImage, 0, 0,null);

    }



    private class TankKeyListener extends KeyAdapter {

        LinkedList<Direct> directs=new LinkedList<Direct>();

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()){
                case 87:
                    if (!directs.contains(Direct.UP))
                        directs.add(Direct.UP);
                    break;
                case 83:
                    if (!directs.contains(Direct.DOWN))
                        directs.add(Direct.DOWN);
                    break;
                case 65:
                    if (!directs.contains(Direct.LEFT))
                        directs.add(Direct.LEFT);
                    break;
                case 68:
                    if (!directs.contains(Direct.RIGHT))
                        directs.add(Direct.RIGHT);
                    break;
                case 32:
                    gameModel.getMyTank().fire(gameModel.fireStrategy);
                    break;
                case 80:
                    gameModel.createEnemy();
                    break;
                case 76:
                    Snapshot.load();
                    break;
                case 79:
                    Snapshot.save();
                    break;
                 default:break;
            }
            move();
        }
        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()){
                case 87:
                    directs.remove(Direct.UP);
                    break;
                case 83:
                    directs.remove(Direct.DOWN);
                    break;
                case 65:
                    directs.remove(Direct.LEFT);
                    break;
                case 68:
                    directs.remove(Direct.RIGHT);
                    break;
                default:break;
            }
            move();
        }
        private  void move(){
            if(!directs.isEmpty())
                gameModel.getMyTank().setDirect(directs.getLast());
            else
                gameModel.getMyTank().stop();
        }
    }

}
