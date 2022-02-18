package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LoadScreen implements Screen {
    final MyGdxGame game;
    Stage stage;
    Button loadButtonFirst;
    Button loadButtonSecond;
    Button loadButtonThird;
    Button deleteButtonFirst;
    Button deleteButtonSecond;
    Button deleteButtonThird;
    Button saveDescriptionFirst;
    Button saveDescriptionSecond;
    Button saveDescriptionThird;
    Button backButton;
    Texture background;
    Texture saveSquare;
    boolean isPressed;

    LoadScreen(final MyGdxGame game){
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        loadButtonFirst = new TextButton("Load Save", game.textButtonStyle);
        stage.addActor(loadButtonFirst);
        loadButtonFirst.setPosition(20, 100);

        deleteButtonFirst = new TextButton("Delete Save", game.textButtonStyle);
        stage.addActor(deleteButtonFirst);
        deleteButtonFirst.setPosition(20, 50);

        loadButtonSecond = new TextButton("Load Save 2", game.textButtonStyle);
        stage.addActor(loadButtonSecond);
        loadButtonSecond.setPosition(220, 100);

        deleteButtonSecond = new TextButton("Delete Save 2", game.textButtonStyle);
        stage.addActor(deleteButtonSecond);
        deleteButtonSecond.setPosition(220, 50);

        loadButtonThird = new TextButton("Load Save 3", game.textButtonStyle);
        stage.addActor(loadButtonThird);
        loadButtonThird.setPosition(420, 100);

        deleteButtonThird = new TextButton("Delete Save 3", game.textButtonStyle);
        stage.addActor(deleteButtonThird);
        deleteButtonThird.setPosition(420, 50);

        saveDescriptionFirst = new TextButton("Save 1", game.textButtonStyle);
        stage.addActor(saveDescriptionFirst);
        saveDescriptionFirst.setPosition(70, 300);

        deleteButtonThird = new TextButton("Save 2", game.textButtonStyle);
        stage.addActor(deleteButtonThird);
        deleteButtonThird.setPosition(270, 300);

        saveDescriptionThird = new TextButton("Save 3", game.textButtonStyle);
        stage.addActor(saveDescriptionThird);
        saveDescriptionThird.setPosition(470, 300);

        backButton = new TextButton("Back", game.textButtonStyle);
        stage.addActor(backButton);
        backButton.setPosition(20, 20);

        background = new Texture("sprites/main_menu_background.jpg");
        saveSquare = new Texture("sprites/marble_square.png");
    }

    @Override
    public void render(float delta) {
        isPressed = false;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.draw(saveSquare, 20, 200);
        game.batch.draw(saveSquare, 220, 200);
        game.batch.draw(saveSquare, 420, 200);
        game.batch.end();

        stage.draw();
    }

    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() { }
}
