package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Bandit extends Human {
    public Bandit(){
        texture = new Texture("sprites/characters/bandit.png");
        x = 440f;
        y = 140f;
        name = "Bandit";

        //Все ноты голоса Бандита
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/do.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/re.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/mi.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/fa.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/sol.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/lya.wav")));
        //voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/bandit/ci.wav")));

        //Темп речи Бандита (быстрый)
        pace = 1;
    }

    //Этот метод выводит на экран фразы
    public StringBuilder inputPhrase() {
        voiceID++;
        try {
            if (!phraseInArrayWithdrawn[phraseId]) {
                phraseInArrayWithdrawn[phraseId] = withdrawnPhrase(phraseArray.get(phraseId));

                //В этом месте происходит анимация говорения Бандита
                if (character % 3 == 0) {
                    if (character % 2 == 0) {
                        y += 1;
                    } else {
                        y -= 1;
                    }
                }
                return getPhrase;
            } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                phraseId++;
                phrasePhase = 0;
                getPhrase = new StringBuilder();
                character = 0;
            }
        } catch (Exception ignored) {
        }
        return getPhrase;
    }
}
