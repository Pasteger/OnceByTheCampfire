package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.mygdx.game.PrologueSpace.bandit;
import static com.mygdx.game.PrologueSpace.debter;
import static com.mygdx.game.PrologueSpace.military;
import static com.mygdx.game.PrologueSpace.volition;
import static com.mygdx.game.PrologueSpace.author;
import static com.mygdx.game.PrologueSpace.protagonist;
import static com.mygdx.game.PrologueSpace.currentCharacter;


public class SpeakingClass extends Thread{
    String inputFileName;
    SpeakingClass (String inputFileName){
        this.inputFileName = inputFileName;
    }
    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while (!(line = reader.readLine()).equals("КОНЕЦ")) {
                System.out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void banditNewPhrase(Array<String> phraseArrayNew) {
        bandit.setPhraseId(0);
        bandit.phraseArray.clear();
        for (String line : phraseArrayNew) { bandit.phraseArray.add(line); }
        bandit.phraseInArrayWithdrawn = new boolean[bandit.phraseArray.size];
        currentCharacter = "Bandit";
    }

    public void debterNewPhrase(Array<String> phraseArrayNew) {
        debter.setPhraseId(0);
        debter.phraseArray.clear();
        for (String line : phraseArrayNew) { debter.phraseArray.add(line); }
        debter.phraseInArrayWithdrawn = new boolean[debter.phraseArray.size];
        currentCharacter = "Debter";
    }

    public void volitionNewPhrase(Array<String> phraseArrayNew) {
        volition.setPhraseId(0);
        volition.phraseArray.clear();
        for (String line : phraseArrayNew) { volition.phraseArray.add(line); }
        volition.phraseInArrayWithdrawn = new boolean[volition.phraseArray.size];
        currentCharacter = "Volition";
    }

    public void militaryNewPhrase(Array<String> phraseArrayNew) {
        military.setPhraseId(0);
        military.phraseArray.clear();
        for (String line : phraseArrayNew) { military.phraseArray.add(line); }
        military.phraseInArrayWithdrawn = new boolean[military.phraseArray.size];
        currentCharacter = "Military";
    }

    public void authorNewPhrase(Array<String> phraseArrayNew) {
        author.setPhraseId(0);
        author.phraseArray.clear();
        for (String line : phraseArrayNew) { author.phraseArray.add(line); }
        author.phraseInArrayWithdrawn = new boolean[author.phraseArray.size];
        currentCharacter = "Author";
    }

    public void protagonistNewPhrase(Array<String> phraseArrayNew) {
        protagonist.setPhraseId(0);
        protagonist.phraseArray.clear();
        for (String line : phraseArrayNew) { protagonist.phraseArray.add(line); }
        protagonist.phraseInArrayWithdrawn = new boolean[protagonist.phraseArray.size];
        currentCharacter = "Protagonist";
    }
}