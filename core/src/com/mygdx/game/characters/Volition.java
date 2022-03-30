package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Volition extends Human{
    public Volition(){
        texture = new Texture("sprites/characters/volition.png");
        x = -1000f;
        y = -1000f;
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/volition/sol.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/volition/lya.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/volition/ci.wav")));
        pace = 2;
    }
}
