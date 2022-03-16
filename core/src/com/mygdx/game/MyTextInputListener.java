package com.mygdx.game;

import com.badlogic.gdx.Input;

public class MyTextInputListener implements Input.TextInputListener {
    final MyGdxGame game;

    MyTextInputListener(final MyGdxGame game){
        this.game = game;
    }

    @Override
    public void input (String text) {
        if (text.matches("[a-zA-Z_]*")) {
            game.setSaveName(text);
            System.out.println(text);
        }
    }

    @Override
    public void canceled () {
    }
}
