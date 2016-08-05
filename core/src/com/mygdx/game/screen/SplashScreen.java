package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.actor.BaseActor;

/**
 * Created by omakhobei on 8/3/2016.
 */
public class SplashScreen extends  BaseScreen{

    private BaseActor logo;
    private Texture texture;

    public SplashScreen(Game game){
        super(game);
    }

    @Override
    public void create() {
        logo = new BaseActor();
        String localStoragePath = Gdx.files.getLocalStoragePath();
        System.out.println(localStoragePath);
        texture = new Texture("badlogic.jpg");
        logo.setTexture(texture);

        logo.setPosition(Gdx.graphics.getWidth()/2 - texture.getWidth()/2,
                Gdx.graphics.getHeight()/2 );


        logo.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {

               game.setScreen(new TurtleLevel(game));
                dispose();
                return true;
            }
        });
        uiStage.addActor(logo);
    }

    @Override
    public void update(float dt) {

    }
}
