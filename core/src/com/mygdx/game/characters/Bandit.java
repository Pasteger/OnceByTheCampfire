package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bandit extends Human {
    public Bandit(){
        texture = new Texture("sprites/characters/bandit.png");
        x = -1000f;
        y = -1000f;

        //Все ноты голоса Бандита
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/do.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/re.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/mi.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/fa.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/sol.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/lya.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/ci.wav")));

        //Темп речи Бандита (быстрый)
        pace = 2;
    }
}
