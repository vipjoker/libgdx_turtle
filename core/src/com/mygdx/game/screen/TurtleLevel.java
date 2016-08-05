package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.BaseActor;
import com.mygdx.game.actor.PhysicsActor;

import java.util.ArrayList;

/**
 * Created by omakhobei on 8/5/2016.
 */
public class TurtleLevel extends BaseScreen {

    private BaseActor ocean;
    private ArrayList<BaseActor> rockList;
    private ArrayList<BaseActor> starfishList;
    private PhysicsActor turtle;
    private int mapWith =800;
    private int mapHeight = 600;

    public TurtleLevel(Game game){
        super(game);
    }

    @Override
    public void create() {
        ocean = new BaseActor();
        ocean.setTexture(new Texture("water.jpg"));
        ocean.setPosition(0,0);
        mainStage.addActor(ocean);
    }

    @Override
    public void update(float dt) {

    }
}
