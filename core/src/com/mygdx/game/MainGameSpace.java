package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class MainGameSpace implements Screen {
    final MyGdxGame game;

    MainGameSpace(final MyGdxGame game){ this.game = game; }

    @Override
    public void render(float delta) {

    }

    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() { }
}
