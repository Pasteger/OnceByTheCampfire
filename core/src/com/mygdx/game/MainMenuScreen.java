package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenuScreen implements Screen {
    final MyGdxGame game;
    OrthographicCamera camera;
    Texture background;
    Stage stage;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    Button startButton;
    Button settingsButton;
    Button exitButton;
    boolean isPressed;
    MyTextInputListener listener;
    boolean listenerExist;


    MainMenuScreen(final MyGdxGame gam){
        this.game = gam;
        listenerExist = false;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        game.music = Gdx.audio.newMusic(Gdx.files.internal("music/bossmark.mp3"));
        game.music.setVolume(game.volume);
        game.music.setLooping(false);

        font = new BitmapFont();
        skin = new Skin();
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.font.getData().setScale(2);

        startButton = new TextButton("Start", textButtonStyle);
        stage.addActor(startButton);
        startButton.setPosition(20, 150);

        settingsButton = new TextButton("Settings", textButtonStyle);
        stage.addActor(settingsButton);
        settingsButton.setPosition(20, 100);

        exitButton = new TextButton("Exit", textButtonStyle);
        stage.addActor(exitButton);
        exitButton.setPosition(20, 50);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 650);
        background = new Texture("sprites/main_menu_background.jpg");

        listener = new MyTextInputListener(game);
    }

    @Override
    public void render(float delta) {
        isPressed = false;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!game.music.isPlaying()){ game.music.play(); }

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.end();

        stage.draw();



        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!isPressed) {
                    isPressed = true;
                    game.setScreen(new LoadScreen(game));
                    dispose();
                }
            }
        });


        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!isPressed) {
                    isPressed = true;
                    game.setScreen(new SettingsScreen(game));
                    dispose();
                }
            }
        });


        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(0 / 0); } });
    }
    @Override public void show(){}
    @Override public void resize(int width, int height){}
    @Override public void pause(){}
    @Override public void resume(){}
    @Override public void hide(){}
    @Override public void dispose(){}
}
