package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Monolith extends Human{
    public Monolith(){
        texture = new Texture("sprites/characters/monolith.png");
        x = 140f;
        y = 160f;
        name = "Monolith";

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

    //Этот метод выводит на экран фразы
    public StringBuilder inputPhrase() {
        voiceID++;
        try {
            if (!phraseInArrayWithdrawn[phraseId]) {
                phraseInArrayWithdrawn[phraseId] = withdrawnPhrase(phraseArray.get(phraseId));

                //В этом месте происходит анимация говорения Монолитовца
                if (character % 2 == 0) {
                    y += 1;
                } else {
                    y -= 1;
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
