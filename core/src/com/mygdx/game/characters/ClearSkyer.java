package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ClearSkyer extends Human{
    public ClearSkyer(){
        texture = new Texture("sprites/characters/clear_skyer.png");
        x = 880f;
        y = 150f;
        name = "Clear skyer";

        //!!!!!!!!!!!
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/monolith/do.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/monolith/re.wav")));
        voice.add(Gdx.audio.newSound(Gdx.files.internal("sounds/voice/monolith/mi.wav")));

        pace = 3;
    }
}
