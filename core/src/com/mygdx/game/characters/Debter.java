package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Debter extends Human{
    public Debter(){
        texture = new Texture("sprites/characters/debter.png");
        x = -1000f;
        y = -1000f;
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/debter/fa.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/debter/sol.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/debter/lya.wav")));
        pace = 3;
    }
}
