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
    protected int voiceID;
    protected int phraseId;
    protected StringBuilder getPhrase;
    protected int character;
    protected Array<Sound> voice;
    protected Random random;
    protected int pace = 0;
    public boolean needToClear = false;

    public Human(){
        phraseArray = new Array<>();
        getPhrase = new StringBuilder();
        voice = new Array<>();
        random = new Random();
    }

    public Float getX() {return x;}
    public Float getY() {return y;}
    public void setX(Float x) { this.x = x; }
    public void setY(Float y) { this.y = y; }

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
                //В этом месте происходит анимация говорения
                if(getPhrase.charAt(getPhrase.length()-1) != ' ' && getPhrase.charAt(getPhrase.length()-1) != '.') {
                    if (character % 2 == 0) {
                        y += 1;
                    } else {
                        y -= 1;
                    }
                    if(voiceID > 2) {
                        voice.get(random.nextInt(voice.size-1)).play(0.1f);
                        voiceID = 0;
                    }
                }
                return getPhrase;
            } else {
                phraseId++;
                needToClear = true;
            }
        } catch (Exception ignored) {
        }
        return getPhrase;
    }

    //Этот метод тоже выводит на экран фразы
    protected boolean withdrawnPhrase(String phrase){
        boolean withdrawn = false;
        try {
            getPhrase.append(phrase.charAt(character));
            character++;
        }
        catch (Exception e){
            withdrawn = true;
        }
        return withdrawn;
    }

    public void clearPhrase(){
        phraseId = 0;
        getPhrase = new StringBuilder();
        character = 0;
        needToClear = false;
    }
}
