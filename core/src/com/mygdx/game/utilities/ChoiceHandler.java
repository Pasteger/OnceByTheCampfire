package com.mygdx.game.utilities;

import com.badlogic.gdx.utils.Array;

public class ChoiceHandler {
    public static Array<String> choiceArray = new Array<>();

    public static Array<String> getChoiceArray() { return choiceArray; }

    public ChoiceHandler() { }

    public static void addInArray(String strChoice){ choiceArray.add(strChoice); }

    public static String getChoiceFromArray (int choiceIndex) { return choiceArray.get(choiceIndex - 1); }
}
