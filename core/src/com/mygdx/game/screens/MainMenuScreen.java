package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utilities.MyTextInputListener;

public class MainMenuScreen implements Screen {
    private final MyGdxGame game;
    private final Texture background;
    private final Button newGameButton;
    private final Button loadGameButton;
    private final Button settingsButton;
    private final Button exitButton;
    private final Music menuMusic;
    MyTextInputListener listener;
    boolean listenerExist;
    public static boolean doReading = true;
    public static boolean QTESuccess = false;
    public static boolean QTEActive = false;


    public MainMenuScreen(final MyGdxGame gam){
        this.game = gam;
        listenerExist = false;

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/main_menu.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.2f);

        newGameButton = new TextButton("New Game", game.getTextButtonStyle());
        game.stage.addActor(newGameButton);
        newGameButton.setPosition(20, 200);

        loadGameButton = new TextButton("Load Game", game.getTextButtonStyle());
        game.stage.addActor(loadGameButton);
        loadGameButton.setPosition(20, 150);

        settingsButton = new TextButton("Settings", game.getTextButtonStyle());
        game.stage.addActor(settingsButton);
        settingsButton.setPosition(20, 100);

        exitButton = new TextButton("Exit", game.getTextButtonStyle());
        game.stage.addActor(exitButton);
        exitButton.setPosition(20, 50);

        background = new Texture("sprites/backgrounds/main_polyana.png");

        listener = new MyTextInputListener(game);
    }

    @Override
    public void render(float delta) {
        game.setButtonIsPressed(false);
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getCamera().update();
        game.batch.setProjectionMatrix(game.getCamera().combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.end();

        game.stage.draw();

        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.isButtonIsPressed()) {
                    game.setButtonIsPressed(true);
                    menuMusic.stop();
                    game.setScreen(new MainGameScreen(game));
                    dispose();
                }
            }
        });


        loadGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.isButtonIsPressed()) {
                    game.setButtonIsPressed(true);
                    game.setScreen(new LoadScreen(game));
                    dispose();
                }
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.isButtonIsPressed()) {
                    game.setButtonIsPressed(true);
                    game.setScreen(new SettingsScreen(game));
                    dispose();
                }
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.isButtonIsPressed()) {
                    game.setButtonIsPressed(true);
                    System.out.println(0 / 0);
                }
            }
        });

    }
    @Override public void show(){menuMusic.play();}
    @Override public void resize(int width, int height){}
    @Override public void pause(){}
    @Override public void resume(){}
    @Override public void hide(){}
    @Override public void dispose(){}
}
