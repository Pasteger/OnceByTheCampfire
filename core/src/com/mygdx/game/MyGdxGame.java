package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public Stage stage;
	public BitmapFont font;
	private String saveName;
	public Music music;
	private TextButton.TextButtonStyle textButtonStyle;
	public Float volume = 0.2f;
	private OrthographicCamera camera;
	private boolean buttonIsPressed;


	@Override
	public void create () {
		font = new BitmapFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 650);

		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.font.getData().setScale(2);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
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

	public boolean isButtonIsPressed() {return !buttonIsPressed;}
	public void setButtonIsPressed(boolean buttonIsPressed) {this.buttonIsPressed = buttonIsPressed;}
	public TextButton.TextButtonStyle getTextButtonStyle() {return textButtonStyle;}
	public String getSaveName() {return saveName;}
	public void setSaveName(String saveName) {this.saveName = saveName;}
	public OrthographicCamera getCamera() {return camera;}
}
