package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Volition extends Human{
    public Volition(){
        texture = new Texture("sprites/characters/volition.png");
        x = 700f;
        y = 140f;
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/mi.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/fa.wav")));
        pace = 2;
    }
}
