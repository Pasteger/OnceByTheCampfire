package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;

public class Singleton extends Human{

    //В разработке...

    public Singleton(){
        texture = new Texture("sprites/characters/singleton.png");
        x = 10f;
        y = 155f;
        name = "Singleton";
    }
}
