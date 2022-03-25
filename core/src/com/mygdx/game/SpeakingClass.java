package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.mygdx.game.MainMenuScreen.doReading;
import static com.mygdx.game.PrologueSpace.*;


public class SpeakingClass extends Thread{
    String inputFileName;
    SpeakingClass (String inputFileName){ this.inputFileName = inputFileName; }
    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            Array<String> phrase = new Array<String>();
            String[] command;
            String line;
            String currentSpeaker = "NONE";
            int StringCounter = 0;
            boolean startWriting = false;
        while (true){
                sleep(500);
                if (doReading){
                    while ((line = reader.readLine()) != null) {
                        command = line.split(" ");
                        // Появление персонажа. Убрать в метод
                        if (line.contains("APPEAR")){
                            if (command[1].equals("DEBT")){
                                changePlaceDebter(Integer.parseInt(command[2]), Integer.parseInt(command[3])); }
                            if (command[1].equals("VOLITION")){
                                changePlaceVolition(Integer.parseInt(command[2]), Integer.parseInt(command[3])); }
                            if (command[1].equals("MILITARY")){
                                changePlaceMilitary(Integer.parseInt(command[2]), Integer.parseInt(command[3])); }
                            if (command[1].equals("BANDIT")){
                                changePlaceBandit(Integer.parseInt(command[2]), Integer.parseInt(command[3])); }
                        }
                        // Эффект
                        if (line.contains("EFFECT")){
                            com.mygdx.game.PrologueSpace.doEffect(command[1]);
                        }
                        // Смена рассказчика
                        if (command.length == 1){
                            switch (line){
                                case("DEBT"):
                                    changeTeller("Долг1");
                                    currentSpeaker = "DEBT";
                                case("VOLITION"):
                                    changeTeller("Свобода2");
                                    currentSpeaker = "VOLITION";
                                case("MILITARY"):
                                    changeTeller("Вояка3");
                                    currentSpeaker = "MILITARY";
                                case("BANDIT"):
                                    changeTeller("Бандос4");
                                    currentSpeaker = "BANDIT";
                                case("AUTHOR"):
                                    changeTeller("Автор5");
                                    currentSpeaker = "AUTHOR";
                                case("PROTAGONIST"):
                                    changeTeller("Герой6");
                                    currentSpeaker = "PROTAGONIST";
                            }
                        }
                        if (startWriting) {
                            StringCounter++;
                            phrase.add(line);
                        }
                        System.out.println(line);
                        if (line.split(" ")[0].contains("END")){ doReading=false; break; }
                    }
                }
            }
        }
        catch (IOException | InterruptedException e) {
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

    public void changePlaceBandit(float x, float y){ bandit.setX(x); bandit.setY(y); }
    public void changePlaceDebter(float x, float y){ debter.setX(x); debter.setY(y); }
    public void changePlaceVolition(float x, float y){ volition.setX(x); volition.setY(y); }
    public void changePlaceMilitary(float x, float y){ military.setX(x); military.setY(y); }

    public void changeReputationBandit(String sign, int amount){
        if (sign.equals("+")){ System.out.println("Бандит + " + amount); }
        else { System.out.println("Бандит - " + amount); } }
    public void changeReputationDebter(String sign, int amount){
        if (sign.equals("+")){ System.out.println("Долг + " + amount); }
        else { System.out.println("Долг - " + amount); } }
    public void changeReputationVolition(String sign, int amount){
        if (sign.equals("+")){ System.out.println("Свобода + " + amount); }
        else { System.out.println("Свобода - " + amount); } }
    public void changeReputationMilitary(String sign, int amount){
        if (sign.equals("+")){ System.out.println("Военный + " + amount); }
        else { System.out.println("Военный - " + amount); } }
}