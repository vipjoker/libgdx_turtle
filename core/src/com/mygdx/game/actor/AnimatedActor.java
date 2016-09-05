package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 * Created by omakhobei on 8/4/2016.
 */
public class AnimatedActor extends BaseActor {
    private float elapsedTime;
    private Animation activeAnimation;
    private String activeName;
    private HashMap<String,Animation> animationStorage;

    public AnimatedActor(){
        super();
        elapsedTime = 0;
        activeAnimation = null;
        activeName = null;
        animationStorage = new HashMap<>();
    }

    public void storeAnimation(String name,Animation animation){
        animationStorage.put(name,animation);
        if (activeName == null) {
            setActiveAnimation(name);
        }
    }

    public void setActiveAnimation(String name) {
        if (!animationStorage.containsKey(name)) {
            System.out.println("No animation: " + name);
            return;
        }

        activeName = name;
        activeAnimation =animationStorage.get(name);
        elapsedTime = 0;
        Texture texture = activeAnimation.getKeyFrame(0).getTexture();
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
    }


    public void storeActiveAnimation(String name, Texture texture){
        TextureRegion region = new TextureRegion(texture);
        TextureRegion[] frames = {region};
        Animation animation = new Animation(1.0f,frames);
        storeAnimation(name,animation);

    }

    public String getAnimationName(){
        return activeName;
    }

    public void act(float dt){
        super.act(dt);
        elapsedTime += dt;
    }

    public void draw(Batch batch, float parentAlpha){
        region.setRegion(activeAnimation.getKeyFrame(elapsedTime));
        super.draw(batch,parentAlpha);
    }



}
