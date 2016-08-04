package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class MyGdxGame extends Game{
	SpriteBatch batch;
	Texture img;
	Stage mStage;
	
	@Override
	public void create () {
        this.setScreen(new SplashScreen());
		mStage = new Stage();
		Button button = new Button();
		mStage.addActor(button);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mStage.act();
		mStage.draw();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
