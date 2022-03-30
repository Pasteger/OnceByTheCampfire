package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Military extends Human{
    public Military(){
        texture = new Texture("sprites/characters/military.png");
        x = -1000f;
        y = -1000f;
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/military/sol.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/military/lya.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/military/ci.wav")));
        pace = 3;
    }
}
