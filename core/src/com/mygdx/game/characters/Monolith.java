package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Monolith extends Human{
    public Monolith(){
        texture = new Texture("sprites/characters/monolith.png");
        x = -1000f;
        y = -1000f;

        //Все ноты голоса Монолитовца
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/monolith/do.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/monolith/re.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/monolith/mi.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/monolith/fa.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/monolith/sol.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/monolith/lya.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/monolith/ci.wav")));

        //Темп речи Монолитовца (неторопливый)
        pace = 3;
    }
}
