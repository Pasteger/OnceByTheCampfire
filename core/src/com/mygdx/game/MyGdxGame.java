package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	BitmapFont font;
	String saveName;
	Music music;
	Skin skin;
	TextButton.TextButtonStyle textButtonStyle;
	Float volume = 0.2f;
	OrthographicCamera camera;


	@Override
	public void create () {
		font = new BitmapFont();
		skin = new Skin();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 650);
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.font.getData().setScale(2);
		batch = new SpriteBatch();


		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {super.render();}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
