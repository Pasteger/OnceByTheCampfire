package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            Array<Integer> choicesPicked = new Array<>();
            String[] command;
            ArrayList<String> choicesToPick = new ArrayList<>();
            String line;
            Array<String> choicesInPrologue = new Array<>();
            boolean skipLines = false;
            String currentSpeaker = "NONE";
            boolean waitForAnswer = false;
            boolean startWriting = false;
            boolean startChoiceReading = false;
            Map<Integer, Integer> choiceMap = new HashMap<>();
            choiceMap.put(1, 0);
            choiceMap.put(2, 0);
            choiceMap.put(3, 0);
            int choiceIndex = 1;
            int linesInChoice = 1;
        while (true){
                sleep(250);
                if (doReading){
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
                                    if (!phrase.isEmpty()){
                                        startWriting = false;
                                        setPhrase(currentSpeaker, phrase);
                                        phrase.clear();
                                    }
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
                        // Нахождение выбора
                        if (line.contains("CHOICE") && command.length == 2){
                            if (!phrase.isEmpty()){
                                startWriting = false;
                                setPhrase(currentSpeaker, phrase);
                                doReading = false;
                                phrase.clear();

                                while (!doReading) sleep(250);
                            }
                            currentSpeaker = "CHOICE" + command[1];
                            System.out.println("CHOICE " + command[1] +" STARTED");
                            startChoiceReading = true;
                            choiceIndex = Integer.parseInt(command[1]);
                            continue;
                        }
                        // Считывание строк выбора
                        if (startChoiceReading){
                            if(line.contains("CHOICE") && line.contains("END")){
                                if(Integer.parseInt(command[1]) == 1){
                                    System.out.println("Choice ended " + command[1]);
                                    skipLines = false;
                                }
                            }
                            if (waitForAnswer){
                                choicesInPrologue.add(ChoiceHandler.getChoiceFromArray(choiceIndex));
                                waitForAnswer = false;
                                startChoiceReading = false;
                            }
                            if(line.contains("CHOICE") && command.length == 3 && command[2].length()==1){
                                for (String i : ChoiceHandler.choiceArray){
                                    System.out.println(i);
                                }
                                if (line.contains(ChoiceHandler.getChoiceFromArray(choiceIndex))){
                                    System.out.println("a niche");
                                    skipLines = false;
                                    startChoiceReading = false;
                                } else {
                                    System.out.println("skipaem tupa");
                                    skipLines = true; }
                            }
                            choicesToPick.add(line);
                            if (line.contains("/")){
                                waitForAnswer = true;
                                startChoice(linesInChoice, choiceIndex, choicesToPick);
                                linesInChoice = 1;
                                choiceIndex = 1;
                                choicesToPick.clear();
                                doReading = false;
                                break;
                            } else linesInChoice++;
                            continue;}

                        if (line.split(" ")[0].contains("END")){ doReading=false; break; }

                        if (!skipLines){
                            // Репутация
                            if (line.contains("REPUTATION")){
                                if (line.contains("DEBT")){
                                    changeReputationDebter(command[1], Integer.parseInt(command[2]));
                                }
                                if (line.contains("VOLITION")){
                                    changeReputationVolition(command[1], Integer.parseInt(command[2]));
                                }
                                if (line.contains("MILITARY")){
                                    changeReputationMilitary(command[1], Integer.parseInt(command[2]));
                                }
                                if (line.contains("BANDIT")){
                                    changeReputationBandit(command[1], Integer.parseInt(command[2]));
                                }
                                continue;
                            }
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
                            // Смена фона
                            if (line.contains("CHANGE_BG")){
                                    com.mygdx.game.PrologueSpace.changeBG(command[1]);
                                    continue;
                            }
                            // Эффект
                            if (line.contains("EFFECT")){
                                if (!phrase.isEmpty()){
                                    startWriting = false;
                                    setPhrase(currentSpeaker, phrase);
                                    doReading = false;
                                }
                                try { com.mygdx.game.PrologueSpace.doEffect(command[1], Integer.parseInt(command[2]));
                                } catch (Exception ignored){ com.mygdx.game.PrologueSpace.doEffect(command[1], 0); }
                                if (line.contains("CHLORKA")){
                                    sleep(250);
                                    doReading = true;
                                }
                                continue;
                            }
                            // читаемся
                            if (startWriting) {
                                if (line.equals("DEBT") || line.equals("VOLITION") || line.equals("MILITARY") ||
                                        line.equals("PROTAGONIST") || line.equals("AUTHOR") || line.equals("BANDIT")
                                || line.contains("CHOICE")) {
                                    startWriting = false;
                                    doReading = false;
                                    setPhrase(currentSpeaker, phrase);
                                }
                                phrase.add(line);
                            }
                            // Смена рассказчика
                            if (command.length == 1) {
                                if (line.equals("DEBT") || (line.equals("VOLITION") || line.equals("MILITARY")
                                        || line.equals("PROTAGONIST") || line.equals("BANDIT") || line.equals("AUTHOR"))) {
                                    currentSpeaker = line;
                                    startWriting = true;
                                    break;
                                }
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

    /*  public void choiceNewPhrase(Array<String> phraseArrayNew) {
        String tempLine = "";
        choiceMaker.setPhraseId(0);
        choiceMaker.phraseArray.clear();
        for (String line : phraseArrayNew) {
            tempLine = tempLine + line + "\n";
        }
        choiceMaker.phraseArray.add(tempLine);
        choiceMaker.phraseInArrayWithdrawn = new boolean[choiceMaker.phraseArray.size];
        currentCharacter = "Choice";
    }*/

    public void startWriting(boolean startWriting){

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
            tempLine = tempLine + line + "\n"; }
        military.phraseArray.add(tempLine);
        military.phraseInArrayWithdrawn = new boolean[military.phraseArray.size];
        currentCharacter = "Military";
    }

    public void authorNewPhrase(Array<String> phraseArrayNew) {
        String tempLine = "";
        author.setPhraseId(0);
        author.phraseArray.clear();
        for (String line : phraseArrayNew) {
            tempLine = tempLine + line + "\n"; }
        author.phraseArray.add(tempLine);
        author.phraseInArrayWithdrawn = new boolean[author.phraseArray.size];
        currentCharacter = "Author";
    }

    public void protagonistNewPhrase(Array<String> phraseArrayNew) {
        String tempLine = "";
        protagonist.setPhraseId(0);
        protagonist.phraseArray.clear();
        for (String line : phraseArrayNew) {
            tempLine = tempLine + line + "\n"; }
        protagonist.phraseArray.add(tempLine);
        protagonist.phraseInArrayWithdrawn = new boolean[protagonist.phraseArray.size];
        currentCharacter = "Protagonist";
    }

    public void changePlaceBandit(float x, float y){ bandit.setX(x); bandit.setY(y); }
    public void changePlaceDebter(float x, float y){ debter.setX(x); debter.setY(y); }
    public void changePlaceVolition(float x, float y){ volition.setX(x); volition.setY(y); }
    public void changePlaceMilitary(float x, float y){ military.setX(x); military.setY(y); }

    public void changeReputationDebter(String sign, int amount) {
        if (sign.equals("+")){ System.out.println("Debt + " + amount); }
        else { System.out.println("Debt - " + amount); } }
    public void changeReputationBandit(String sign, int amount){
        if (sign.equals("+")){ System.out.println("Bandit + " + amount); }
        else { System.out.println("Bandit - " + amount); } }
    public void changeReputationVolition(String sign, int amount){
        if (sign.equals("+")){ System.out.println("Volition + " + amount); }
        else { System.out.println("Volition - " + amount); } }
    public void changeReputationMilitary(String sign, int amount){
        if (sign.equals("+")){ System.out.println("Military + " + amount); }
        else { System.out.println("Military - " + amount); } }

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
