package com.androidengine2d.engine;

import com.androidengine2d.UnityMath.Vector2;

import java.util.ArrayList;
import java.util.Collection;

//todo

/**Class for scene object which especially is a list of AbstractShape elements*/
public class ShapeObject extends EngineObject{
    public final ArrayList<AbstractShape> body;//set of Abstract shape
    public String name;//name
    /**ShapeObject constructor
     * ini all members default*/
    public ShapeObject(){
        this.id = 0;//ini id
        this.name = "Template";//ini name
        this.body = new ArrayList<>();//ini body
        this.angX = 0;//ini angle X axis
        this.angY = 0;//ini angle Y axis
        this.angZ = 0;//ini angle Z axis
        this.center = new Vector2(0,0);//ini center
        this.position = new Vector2(0,0);//ini position
    }
    /**ShapeObject constructor
     * ini name and id*/
    public ShapeObject(String name, int id){
        this();//invoke default constructor
        this.id = id;//ini id
        this.name = name;//ini name
    }
    /**ShapeObject constructor
     * ini id name adn position*/
    public ShapeObject(String name, int id, Vector2 pos){
        this(name, id);//ini name id constructor
        this.position = new Vector2(pos);//ini position
    }
    /**Method move*/
    public void move(Vector2 dir){
        this.position.add(dir);//move position on dir
        this.center.add(dir);//move center on dir TODO
        for(AbstractShape i: this.body){
            i.position.add(dir);//move all AbstractShapes
            i.repaint = true;
        }
    }
    @Deprecated
    public void setCenterVisible(boolean b){
        for(AbstractShape shape: this.body) {
            //g.drawString(objects.indexOf(it) + it.body.indexOf(shape) + "", (int)shape.position.x+200, (int)shape.position.y+200);
            shape.CENTER = b;
        }
    }
    /**Add new element on object*/
    public AbstractShape add(AbstractShape o){
        o.id = this.id;//ini new shape id
        o.angX += this.angX;//ini new shape angleX
        o.angY += this.angY;//ini new shape angleY
        o.angZ += this.angZ;//ini new shape angleZ
        this.body.add(o);//add new shape in body
        float sumX=0;
        float sumY=0;
        for(AbstractShape i: this.body){
            sumX+= i.center.x + i.position.x;//compute sum centers X
            sumY+= i.center.y + i.position.y;//compute sum centers Y
        }
        this.center = new Vector2(sumX/this.body.size(), sumY/this.body.size());//ini center
        this.position.add(o.position);//ini position
        for(AbstractShape i: this.body){
            i.parent = this;//ini shape parent
        }
        return o;
    }
    /**Add new element collection on object*/
    public void addAll(Collection<AbstractShape> o){
        for(AbstractShape i : o){
            add(i);//invoke add single add() for all new shapes in collections
        }
    }
}
