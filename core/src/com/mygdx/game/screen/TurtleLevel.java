package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
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
    private float audioVolume;
    private Sound waterDrop;
    private Music instrumental;
    private Music oceanSurf;
    public TurtleLevel(Game game){
        super(game);
    }

    @Override
    public void create() {
        ocean = new BaseActor();
        ocean.setTexture(new Texture("water.jpg"));
        ocean.setPosition(0,0);
        mainStage.addActor(ocean);


        waterDrop = Gdx.audio.newSound(Gdx.files.internal("sounds/Water_Drop.ogg"));
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("sounds/Master_of_the_Feast.ogg"));
        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("sounds/Ocean_Waves.ogg"));
        audioVolume = 0.8f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        instrumental.play();

        oceanSurf.setLooping(true);
        oceanSurf.setVolume(audioVolume);
        oceanSurf.play();


        BaseActor overlay = ocean.clone();
        overlay.setPosition(-50,-50);
        overlay.setColor(1,1,1,0.25f);
        uiStage.addActor(overlay);

        BaseActor rock = new BaseActor();
        rock.setTexture(new Texture("rock.png"));
        rock.setEllipseBoundary();

        rockList = new ArrayList<>();
        int[] rockCoords = {200,0,
                            200,100,
                            250,200,
                            360,200,
                            470,200};

        for(int i = 0; i < 5; i++){
            BaseActor r  = rock.clone();
            r.setPosition(rockCoords[2*i],rockCoords[2*i + 1]);
            mainStage.addActor(r);
            rockList.add(r);
        }

        BaseActor starFish = new BaseActor();
        starFish.setTexture(new Texture("starfish.png"));
        starFish.setEllipseBoundary();

        starfishList = new ArrayList<>();
        int[] starFishCoords = {400,100,
                                100,400,
                                650,400};

        for(int i = 0; i < 3; i++){
            BaseActor s = starFish.clone();
            s.setPosition(starFishCoords[2*i],starFishCoords[2*i + 1]);
            mainStage.addActor(s);
            starfishList.add(s);
        }

        turtle =  new PhysicsActor();
        TextureRegion[] frames = new TextureRegion[6];
        for(int i =1 ; i <= 6; i++){
            String fileName = "turtle-" + i + ".png";
            Texture texture = new Texture(fileName);

            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            frames[i-1]  = new TextureRegion(texture);
        }

        Array<TextureRegion> framesArray = new Array<>(frames);
        Animation animation = new Animation(0.1f,framesArray,Animation.PlayMode.LOOP);
        turtle.storeAnimation("swim",animation);

        Texture frame1 = new Texture("turtle-1.png");
        turtle.storeActiveAnimation("rest",frame1);

        turtle.setOrigin(turtle.getWidth()/2,turtle.getHeight()/2);
        turtle.setPosition(20,20);
        turtle.setRotation(90);
        turtle.setEllipseBoundary();
        turtle.setMaxSpeed(100);
        turtle.setDeceleration(200);
        mainStage.addActor(turtle);





    }

    @Override
    public void update(float dt) {

        turtle.setAccelerationAS(0,0);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            turtle.rotateBy(90*dt);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            turtle.rotateBy(-90 * dt);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            turtle.accelerateForward(100);
        }

        if(turtle.getSpeed() > 1 && turtle.getAnimationName().equals("rest"))
            turtle.setActiveAnimation("swim");
        if(turtle.getSpeed() < 1 && turtle.getAnimationName().equals("swim"))
            turtle.setActiveAnimation("rest");


        turtle.setX(MathUtils.clamp(turtle.getX(),0,mapWith - turtle.getWidth()));
        turtle.setY(MathUtils.clamp(turtle.getY(),0,mapHeight - turtle.getHeight()));

        for(BaseActor r :rockList)
            turtle.overlaps(r,true);

        ArrayList<BaseActor> removeList = new ArrayList<>();
        for (BaseActor s :starfishList){
            if(turtle.overlaps(s,false)) {
                waterDrop.play(audioVolume);
                removeList.add(s);
            }
        }


        for(BaseActor b :removeList){
            b.remove();
            starfishList.remove(b);
        }

    }

    @Override
    public void dispose() {
        waterDrop.dispose();
        instrumental.dispose();
        oceanSurf.dispose();
    }
}
