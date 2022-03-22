package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

//Этот класс обобщает персонажей общими чертами
public class Human {
    public Array<String> phraseArray;
    public boolean[] phraseInArrayWithdrawn;
    protected Texture texture;
    protected Float x;
    protected Float y;
    protected String name;
    protected int voiceID;
    protected int phraseId;
    protected int phrasePhase;
    protected StringBuilder getPhrase;
    protected int character;
    protected Array<Sound> voice;
    protected Random random;
    protected int pace;

    public Human(){
        phraseArray = new Array<>();
        getPhrase = new StringBuilder();
        voice = new Array<>();
        random = new Random();
        phrasePhase = 1;
    }

    public Float getX() {return x;}
    public Float getY() {return y;}
    public String getName() {return name;}
    public Texture getTexture() {return texture;}
    public int getPace() {return pace;}
    public int getPhraseId() {return phraseId;}
    public void setPhraseId(int phraseId) {this.phraseId = phraseId;}

    //Этот метод выводит на экран фразы
    public StringBuilder inputPhrase() {
        voiceID++;
        try {
            if (!phraseInArrayWithdrawn[phraseId]) {
                phraseInArrayWithdrawn[phraseId] = withdrawnPhrase(phraseArray.get(phraseId));

                //В этом месте происходит анимация говорения Бандита
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

    //Этот метод тоже выводит на экран фразы
    protected boolean withdrawnPhrase(String phrase){
        boolean withdrawn = false;
        phrasePhase = 0;
        try {
            getPhrase.append(phrase.charAt(character));
            if(voiceID > 2) {
                voice.get(random.nextInt(voice.size-1)).play(0.1F);
                voiceID = 0;
            }
            character++;
        }
        catch (Exception e){
            withdrawn = true;
        }
        return withdrawn;
    }
}
