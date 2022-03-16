package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenuScreen implements Screen {
    private final MyGdxGame game;
    private final Texture background;
    private final Button startButton;
    private final Button settingsButton;
    private final Button exitButton;
    MyTextInputListener listener;
    boolean listenerExist;


    MainMenuScreen(final MyGdxGame gam){
        this.game = gam;
        listenerExist = false;


        game.music = Gdx.audio.newMusic(Gdx.files.internal("music/bossmark.mp3"));
        game.music.setVolume(game.volume);
        game.music.setVolume(0);
        game.music.setLooping(false);


        startButton = new TextButton("Start", game.getTextButtonStyle());
        game.stage.addActor(startButton);
        startButton.setPosition(20, 150);

        settingsButton = new TextButton("Settings", game.getTextButtonStyle());
        game.stage.addActor(settingsButton);
        settingsButton.setPosition(20, 100);

        exitButton = new TextButton("Exit", game.getTextButtonStyle());
        game.stage.addActor(exitButton);
        exitButton.setPosition(20, 50);

        background = new Texture("sprites/main_menu_background.jpg");

        listener = new MyTextInputListener(game);
    }

    @Override
    public void render(float delta) {
        game.setButtonIsPressed(false);
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!game.music.isPlaying()){ game.music.play(); }

        game.getCamera().update();
        game.batch.setProjectionMatrix(game.getCamera().combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.end();

        game.stage.draw();



        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.isButtonIsPressed()) {
                    game.setButtonIsPressed(true);
                    game.setScreen(new MainGameSpace(game));
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
    @Override public void show(){}
    @Override public void resize(int width, int height){}
    @Override public void pause(){}
    @Override public void resume(){}
    @Override public void hide(){}
    @Override public void dispose(){}
}
