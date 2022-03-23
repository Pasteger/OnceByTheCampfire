package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.characters.*;

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
    }

    @Override
    public void render(float delta) {
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

        //Этот флаг нужен, чтобы персонаж начинал говорить не с запуска окна, а с нажатия пробела
        if(!startSpeak && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            startSpeak = true;
            speakingClass.start();
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

    public void changeTeller(String name){
        System.out.println("Сменился рассказчик на " + name);
    }

    public void doEffect(String name){
        if(name.equals("АБОБА21")){
            System.out.println("АБОБА21");
        }
    }

    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() { }
}
