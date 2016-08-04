package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by omakhobei on 8/3/2016.
 */
public class BaseActor extends Actor {
    public TextureRegion region;

    public Polygon boundingPolygon;
    public BaseActor(){
        super();
        region = new TextureRegion();
        boundingPolygon = null;
    }

    public void setTexture(Texture texture){
        int w = texture.getWidth();
        int h = texture.getHeight();
        setWidth(w);
        setHeight(h);
        region.setRegion(texture);
    }
    @Override
    public void act(float dt){
        super.act(dt);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        Color color = getColor();

        batch.setColor(color.r,color.g,color.b,color.a);

        if(isVisible()){
            batch.draw(region,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),
                    getScaleY(),getRotation());
        }
    }

    public void setRectangleBoundary(){

        float width = getWidth();
        float height = getHeight();
        float[] points = {0,0,
                        width,0,
                        width,height,
                        0,height};
        boundingPolygon = new Polygon(points);
        boundingPolygon.setOrigin(getOriginX(),getOriginY());

    }

    public void setEllipseBoundary(){
        float width = getWidth();
        float height = getHeight();

        float twoPi = 6.28f;
        int corners = 8;
        float[] points = new float[corners *2];


        for(int i = 0 ; i < corners; i++){
            float angle = i==0 ? 0: twoPi/i;
            points[2*i] = width/2 + width/2 * MathUtils.cos(angle);
            points[2*i +1] = height/2 + height/2*MathUtils.sin(angle);
        }

        boundingPolygon = new Polygon(points);
        boundingPolygon.setOrigin(getOriginX(),getOriginY());
    }

    public Polygon getBoundingPolygon(){
        boundingPolygon.setPosition(getX(),getY());
        boundingPolygon.setRotation(getRotation());
        return boundingPolygon;
    }

    public boolean overlaps(BaseActor baseActor, boolean resolve){
        Polygon poly1 =  this.getBoundingPolygon();
        Polygon poly2 =  baseActor.getBoundingPolygon();
        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))return false;

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();

        boolean polyOverlap = Intersector.overlapConvexPolygons(poly1,poly2,mtv);

        if(polyOverlap && resolve){
            this.moveBy(mtv.normal.x * mtv.depth,mtv.normal.y * mtv.depth);
        }

        float significant = 0.5f;

        return(polyOverlap && (mtv.depth > significant));
        }

    public void copy(BaseActor original){
        this.region = new TextureRegion(original.region);
        if(original.boundingPolygon!= null){
            this.boundingPolygon = new Polygon(original.boundingPolygon.getVertices());
            this.boundingPolygon.setOrigin(original.getOriginX(),original.getOriginY());
        }
        this.setPosition(original.getX(),original.getY());
        this.setOriginX(original.getOriginX());
        this.setOriginY(original.getOriginY());
        this.setWidth(original.getWidth());
        this.setHeight(original.getHeight());
        this.setColor(original.getColor());
        this.setVisible(original.isVisible());
    }

    public BaseActor clone(){
        BaseActor newbie = new BaseActor();
        newbie.copy(this);
        return newbie;
    }



}
