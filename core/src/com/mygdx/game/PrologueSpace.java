package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.characters.*;

import java.util.ArrayList;

import static com.mygdx.game.MainMenuScreen.doReading;

public class PrologueSpace implements Screen {
    private final MyGdxGame game;

    // Текстуры
    private final Texture background;
    private final Texture textField;
    private final Texture portal;
    public boolean portalMoment = false;

    //Эти переменные отвечают за вывод фраз на экран
    private StringBuilder getPhrase;
    private int paceOfSpeak;
    private boolean startSpeak;
    public static String currentCharacter = "";

    //Это персонажи нашей игры
    public static Bandit bandit;
    public static Debter debter;
    public static Military military;
    public static Volition volition;
    public static Author author;
    public static Protagonist protagonist;

    //кнопки
    private static TextButton choiceFirstButton;
    private static TextButton choiceSecondButton;
    private static TextButton choiceThirdButton;

    //Класс для прочитки текста
    SpeakingClass speakingClass = new SpeakingClass("chapters/chapter1.txt");


    PrologueSpace(final MyGdxGame game){
        this.game = game;

        getPhrase = new StringBuilder();

        //Это персонажи нашей игры
        bandit = new Bandit();
        debter = new Debter();
        military = new Military();
        volition = new Volition();
        author = new Author();
        protagonist = new Protagonist();

        background = new Texture("sprites/backgrounds/orange_forest.png");
        textField = new Texture("sprites/text_field.png");
        portal = new Texture("sprites/effects/portal.png");

        game.stage = new Stage();
        Gdx.input.setInputProcessor(game.stage);
        game.batch = new SpriteBatch();

        choiceFirstButton = new TextButton("", game.getTextButtonStyle());
        game.stage.addActor(choiceFirstButton);
        choiceFirstButton.setVisible(false);

        choiceSecondButton = new TextButton("", game.getTextButtonStyle());
        game.stage.addActor(choiceSecondButton);
        choiceSecondButton.setVisible(false);

        choiceThirdButton = new TextButton("", game.getTextButtonStyle());
        game.stage.addActor(choiceThirdButton);
        choiceThirdButton.setVisible(false);
    }

    @Override
    public void render(float delta) {
        game.setButtonIsPressed(false);
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getCamera().update();
        game.batch.setProjectionMatrix(game.getCamera().combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0);

        // Здесь рисуется портал, если он нужен
        if (portalMoment){ game.batch.draw(portal, 300, 250); }

        //Здесь рисуются персонажи
        game.batch.draw(debter.getTexture(), debter.getX(), debter.getY());
        game.batch.draw(bandit.getTexture(), bandit.getX(), bandit.getY());
        game.batch.draw(military.getTexture(), military.getX(), military.getY());
        game.batch.draw(volition.getTexture(), volition.getX(), volition.getY());


        game.batch.draw(textField, 0, 0);
        //Здесь рисуется текст
        game.font.draw(game.batch, getPhrase.toString(), 10, 150);

        game.batch.end();

        game.stage.draw();

        choiceFirstButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.isButtonIsPressed()) {
                    game.setButtonIsPressed(true);
                    choiceFirstButton.setVisible(false);
                    choiceSecondButton.setVisible(false);
                    choiceThirdButton.setVisible(false);

                    System.out.println("Произошёл выбор 1");

                    dispose(); } }});

        choiceSecondButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.isButtonIsPressed()) {
                    game.setButtonIsPressed(true);
                    choiceFirstButton.setVisible(false);
                    choiceSecondButton.setVisible(false);
                    choiceThirdButton.setVisible(false);

                    System.out.println("Произошёл выбор 2");

                    dispose(); } }});

        choiceThirdButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.isButtonIsPressed()) {
                    game.setButtonIsPressed(true);
                    choiceFirstButton.setVisible(false);
                    choiceSecondButton.setVisible(false);
                    choiceThirdButton.setVisible(false);

                    System.out.println("Произошёл выбор 3");

                    dispose(); } }});

        //Этот флаг нужен, чтобы персонаж начинал говорить не с запуска окна, а с нажатия пробела
        if(!startSpeak && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            startSpeak = true;
            speakingClass.start();
        }
        if(!doReading && Gdx.input.isKeyPressed(Input.Keys.G)){
            doReading = true;
        }
        //Этот метод вызывается каждый цикл рендера и на текстовом поле мечатается фраза
        if(startSpeak) { speak(); }

        paceOfSpeak++;
    }

    private void speak(){
        // Говорит бандит
        if(currentCharacter.equals("Bandit") && paceOfSpeak >= bandit.getPace()) {
            getPhrase = bandit.inputPhrase();
            paceOfSpeak = 0;
            if(bandit.getPhraseId() == bandit.phraseArray.size){ currentCharacter = ""; } }

        // Говорит долгаш
        if(currentCharacter.equals("Debter") && paceOfSpeak >= debter.getPace()) {
            getPhrase = debter.inputPhrase();
            paceOfSpeak = 0;
            if(bandit.getPhraseId() == debter.phraseArray.size){ currentCharacter = ""; } }

        // Говорит свободовец
        if(currentCharacter.equals("Volition") && paceOfSpeak >= volition.getPace()) {
            getPhrase = volition.inputPhrase();
            paceOfSpeak = 0;
            if(volition.getPhraseId() == volition.phraseArray.size){ currentCharacter = ""; } }

        // Говорит военный
        if(currentCharacter.equals("Military") && paceOfSpeak >= military.getPace()) {
            getPhrase = military.inputPhrase();
            paceOfSpeak = 0;
            if(military.getPhraseId() == military.phraseArray.size){ currentCharacter = ""; } }

        // Говорит автор
        if(currentCharacter.equals("Author") && paceOfSpeak >= bandit.getPace()) {
            getPhrase = bandit.inputPhrase();
            paceOfSpeak = 0;
            if(bandit.getPhraseId() == bandit.phraseArray.size){ currentCharacter = ""; } }

        // Говорит герой
        if(currentCharacter.equals("Protagonist") && paceOfSpeak >= bandit.getPace()) {
            getPhrase = bandit.inputPhrase();
            paceOfSpeak = 0;
            if(bandit.getPhraseId() == bandit.phraseArray.size){ currentCharacter = ""; } }
    }

    public static void startChoice(int countChoice, ArrayList<String> choiceNames) {
        if (countChoice >= 1) {
            choiceFirstButton.setText(choiceNames.get(0));
            choiceFirstButton.setPosition(640, 110);
            choiceFirstButton.setVisible(true); }
        if (countChoice >= 2) {
            choiceSecondButton.setText(choiceNames.get(1));
            choiceSecondButton.setPosition(640, 60);
            choiceSecondButton.setVisible(true); }
        if (countChoice >= 3) {
            choiceThirdButton.setText(choiceNames.get(2));
            choiceThirdButton.setPosition(640, 10);
            choiceThirdButton.setVisible(true); }
    }

    public static void changeTeller(String name){
        System.out.println("Сменился рассказчик на " + name);
    }

    public static void doEffect(String name){
        if(name.equals("ABOBA21")){
            System.out.println("АБОБА21");
        }
    }

    public static void makeQTE(String difficulty){

    }

    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() { }
}