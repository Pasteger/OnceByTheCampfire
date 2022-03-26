package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Military extends Human{
    public Military(){
        texture = new Texture("sprites/characters/military.png");
        x = 440f;
        y = 150f;
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/sol.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/lya.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/ci.wav")));
        pace = 3;
    }
}
