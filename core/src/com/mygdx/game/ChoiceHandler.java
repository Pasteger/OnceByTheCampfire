package com.mygdx.game;

import jdk.internal.icu.text.UnicodeSet;

import java.util.ArrayList;

public class ChoiceHandler {
    public static ArrayList<String> choiceArray = new ArrayList<>();

    public static ArrayList<String> getChoiceArray() {
        return choiceArray;
    }

    public ChoiceHandler() {
    }

    public static void addInArray(String strChoice){
        choiceArray.add(strChoice);
    }
}
