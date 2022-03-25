package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.mygdx.game.MainMenuScreen.doReading;
import static com.mygdx.game.MainMenuScreen.QTESuccess;
import static com.mygdx.game.MainMenuScreen.QTEActive;
import static com.mygdx.game.PrologueSpace.*;


public class SpeakingClass extends Thread{
    String inputFileName;
    SpeakingClass (String inputFileName){ this.inputFileName = inputFileName; }
    @Override
    public void run() {
        try (FileInputStream fis =  new FileInputStream(inputFileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            Array<String> phrase = new Array<>();
            String[] command;
            String line;
            boolean interruption = false;
            boolean skipLines = false;
            String currentSpeaker = "NONE";
            int StringCounter = 0;
            boolean startWriting = false;
        while (true){
                sleep(700);
                if (doReading){
                    StringCounter = 0;
                    phrase.clear();
                    while ((line = reader.readLine()) != null) {
                        command = line.split(" ");
                        // QTE
                        if (line.contains("QTE")){
                            switch (command[1]){
                                case("EASY"):
                                case("NORMAL"):
                                case("HARD"):
                                    QTEActive = true;
                                    doReading = false;
                                    com.mygdx.game.PrologueSpace.makeQTE(command[1]);
                                    break;
                                case("SUCCESS"):
                                    QTEActive = false;
                                    if(!QTESuccess){
                                        skipLines = true;
                                    }
                                    if (QTESuccess && skipLines){
                                        skipLines = false;
                                    }
                                    break;
                                case("FAIL"):
                                    QTEActive = false;
                                    if(QTESuccess){
                                        skipLines = true;
                                    }
                                    if (!QTESuccess && skipLines){
                                        skipLines = false;
                                    }
                                    break;
                                case("END"):
                                    QTESuccess = false;
                                    skipLines = false;
                                    QTEActive = false;
                            }
                        }
                        if (QTEActive){ break; }
                        if (!skipLines){
                            if (line.split(" ")[0].contains("END")){ doReading=false; break; }
                            // Появление персонажа
                            if (line.contains("APPEAR")){
                                if (command[1].equals("DEBT")){
                                    changePlaceDebter(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                                    continue;}
                                if (command[1].equals("VOLITION")){
                                    changePlaceVolition(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                                    continue;}
                                if (command[1].equals("MILITARY")){
                                    changePlaceMilitary(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                                    continue;}
                                if (command[1].equals("BANDIT")){
                                    changePlaceBandit(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                                    continue;}
                            }
                            // Эффект
                            if (line.contains("EFFECT")){
                                com.mygdx.game.PrologueSpace.doEffect(command[1]);
                                startWriting = false;
                                continue;
                            }
                            // Смена рассказчика
                            if (command.length == 1) {
                                    switch (line) {
                                        case ("DEBT"):
                                            changeTeller("dept1");
                                            currentSpeaker = "DEBT";
                                            startWriting = true;
                                            interruption = true;
                                            continue;
                                        case ("VOLITION"):
                                            changeTeller("vol2");
                                            currentSpeaker = "VOLITION";
                                            startWriting = true;
                                            interruption = true;
                                            continue;
                                        case ("MILITARY"):
                                            changeTeller("mil3");
                                            currentSpeaker = "MILITARY";
                                            startWriting = true;
                                            interruption = true;
                                            continue;
                                        case ("BANDIT"):
                                            changeTeller("ban4");
                                            currentSpeaker = "BANDIT";
                                            startWriting = true;
                                            interruption = true;
                                            continue;
                                        case ("AUTHOR"):
                                            changeTeller("auth5");
                                            currentSpeaker = "AUTHOR";
                                            startWriting = true;
                                            interruption = true;
                                            continue;
                                        case ("PROTAGONIST"):
                                            changeTeller("mc6");
                                            currentSpeaker = "PROTAGONIST";
                                            startWriting = true;
                                            interruption = true;
                                            continue;
                                    }
                            }
                            // читаемся
                            if (startWriting) {
                                phrase.add(line);
                                StringCounter++;
                                if (StringCounter == 4){
                                    startWriting = false;
                                    doReading = false;
                                    setPhrase(currentSpeaker, phrase);
                                    break;
                                }
                                if (line.equals("DEBT")){
                                    startWriting = false;
                                    doReading = false;
                                    setPhrase(currentSpeaker, phrase);
                                    break;
                                }
                                if (line.equals("VOLITION")){
                                    startWriting = false;
                                    doReading = false;
                                    setPhrase(currentSpeaker, phrase);
                                    break;
                                }
                                if (line.equals("BANDIT")){
                                    startWriting = false;
                                    doReading = false;
                                    setPhrase(currentSpeaker, phrase);
                                    break;
                                }
                                if (line.equals("PROTAGONIST")){
                                    startWriting = false;
                                    doReading = false;
                                    setPhrase(currentSpeaker, phrase);
                                    break;

                                }
                                if (line.equals("MILITARY")){
                                    startWriting = false;
                                    doReading = false;
                                    setPhrase(currentSpeaker, phrase);
                                    break;
                                }
                                if (line.equals("AUTHOR")){
                                    startWriting = false;
                                    doReading = false;
                                    setPhrase(currentSpeaker, phrase);
                                    break;
                                }
                            }
                            if (!startWriting && phrase.size != 0){
                                setPhrase(currentSpeaker, phrase);
                                phrase.clear();
                            }
                        }
                    }
                }
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void banditNewPhrase(Array<String> phraseArrayNew) {
        String tempLine = "";
        bandit.setPhraseId(0);
        bandit.phraseArray.clear();
        for (String line : phraseArrayNew) {
            tempLine = tempLine + line + "\n";
        }
        bandit.phraseArray.add(tempLine);
        bandit.phraseInArrayWithdrawn = new boolean[bandit.phraseArray.size];
        currentCharacter = "Bandit";
    }

    public void debterNewPhrase(Array<String> phraseArrayNew) {
        String tempLine = "";
        debter.setPhraseId(0);
        debter.phraseArray.clear();
        for (String line : phraseArrayNew) {
            tempLine = tempLine + line + "\n";
        }
        debter.phraseArray.add(tempLine);
        debter.phraseInArrayWithdrawn = new boolean[debter.phraseArray.size];
        currentCharacter = "Debter";
    }

    public void volitionNewPhrase(Array<String> phraseArrayNew) {
        String tempLine = "";
        volition.setPhraseId(0);
        volition.phraseArray.clear();
        for (String line : phraseArrayNew) {
            tempLine = tempLine + line + "\n";
        }
        volition.phraseArray.add(tempLine);
        volition.phraseInArrayWithdrawn = new boolean[volition.phraseArray.size];
        currentCharacter = "Volition";
    }

    public void militaryNewPhrase(Array<String> phraseArrayNew) {
        String tempLine = "";
        military.setPhraseId(0);
        military.phraseArray.clear();
        for (String line : phraseArrayNew) {
            tempLine = tempLine + line + "\n";
        }
        military.phraseArray.add(tempLine);
        military.phraseInArrayWithdrawn = new boolean[military.phraseArray.size];
        currentCharacter = "Military";
    }

    public void authorNewPhrase(Array<String> phraseArrayNew) {
        String tempLine = "";
        author.setPhraseId(0);
        author.phraseArray.clear();
        for (String line : phraseArrayNew) {
            tempLine = tempLine + line + "\n";
        }
        author.phraseArray.add(tempLine);
        author.phraseInArrayWithdrawn = new boolean[author.phraseArray.size];
        currentCharacter = "Author";
    }

    public void protagonistNewPhrase(Array<String> phraseArrayNew) {
        String tempLine = "";
        protagonist.setPhraseId(0);
        protagonist.phraseArray.clear();
        for (String line : phraseArrayNew) {
            tempLine = tempLine + line + "\n";
        }
        protagonist.phraseArray.add(tempLine);
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

    public void setPhrase(String teller, Array<String> phrase){
        switch (teller){
            case("DEBT"):
                debterNewPhrase(phrase);
                break;
            case("VOLITION"):
                volitionNewPhrase(phrase);
                break;
            case("MILITARY"):
                militaryNewPhrase(phrase);
                break;
            case("BANDIT"):
                banditNewPhrase(phrase);
                break;
            case("AUTHOR"):
                authorNewPhrase(phrase);
                break;
            case("PROTAGONIST"):
                protagonistNewPhrase(phrase);
        }
    }
}
