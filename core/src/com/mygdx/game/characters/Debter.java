package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Debter extends Human{
    public Debter(){
        texture = new Texture("sprites/characters/debter.png");
        x = 300f;
        y = 140f;
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/fa.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/sol.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/lya.wav")));
        pace = 2;
    }
}
