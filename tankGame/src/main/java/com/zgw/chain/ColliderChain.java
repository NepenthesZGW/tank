package com.zgw.chain;

import com.zgw.Facede.GameObject;

import java.util.LinkedList;
import java.util.List;

public class ColliderChain implements Collider<GameObject>{

    private List<Collider> colliderList=null;
    public  ColliderChain(){
        colliderList=new LinkedList<Collider>();
    }

    public void add(Collider collider) {
        this.colliderList.add(collider);
    }

    public boolean collision(GameObject g1,GameObject g2){
        for (Collider collider:colliderList) {
           if( collider.collision(g1,g2)) return true;
        }
        return false;
    }
}
