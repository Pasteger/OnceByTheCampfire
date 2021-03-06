package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.screens.MainMenuScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public Stage stage;
	public BitmapFont font;
	private String saveName;
	private TextButton.TextButtonStyle textButtonStyle;
	private OrthographicCamera camera;
	private boolean buttonIsPressed;

	@Override
	public void create () {
		font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("regular/GOST_A.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        parameter.size = 20;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        generator.dispose();

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
