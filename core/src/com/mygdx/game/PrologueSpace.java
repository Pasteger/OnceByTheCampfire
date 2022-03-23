package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.characters.*;

public class PrologueSpace implements Screen {
    private final MyGdxGame game;

    private final  Texture background;
    private final  Texture textField;

    //Эти переменные отвечают за вывод фраз на экран
    private StringBuilder getPhrase;
    private int paceOfSpeak;
    private boolean startSpeak;
    private int queueSpeak = 0;
    private boolean phraseSet;

    //Это персонажи нашей игры
    private final Bandit bandit;
    private final Debter debter;
    private final Military military;
    private final Volition volition;


    PrologueSpace(final MyGdxGame game){
        this.game = game;

        getPhrase = new StringBuilder();

        //Это персонажи нашей игры
        bandit = new Bandit();
        debter = new Debter();
        military = new Military();
        volition = new Volition();

        background = new Texture("sprites/backgrounds/orange_forest.png");
        textField = new Texture("sprites/text_field.png");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getCamera().update();
        game.batch.setProjectionMatrix(game.getCamera().combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0);

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
        }

        //Этот метод вызывается каждый цикл рендера и на текстовом поле мечатается фраза
        if(startSpeak) {
            speak();
        }

        paceOfSpeak++;
    }

    private void speak(){
        //Все говорят по очереди
        //Каждый if на этом уровне определяет кто говорит
        //if(queueSpeak == 0 && paceOfSpeak >= bandit.getPace()){...}
        //if(queueSpeak == 1 && paceOfSpeak >= monolith.getPace()){...}
        //if(queueSpeak == 2 && paceOfSpeak >= bandit.getPace()){...}
        //if(queueSpeak == 3 && paceOfSpeak >= bandit.getPace()){...}
        //Сначала говорит Бандит, потом Монолитовец, потом снова Бандит
        if(queueSpeak == 0 && paceOfSpeak >= bandit.getPace()) {
            //Здесь устанавливается фраза, которую скажет персонаж
            //Флаг phraseSet нужен, чтобы фраза устанавливалась только 1 раз
            if(!phraseSet) {
                //phraseId и phraseArray нужно обязательно очистить от предыдущего значения
                bandit.setPhraseId(0);
                bandit.phraseArray.clear();

                //И вот таким образо устанавливается фраза
                //Да, нужно писать ручками все \n и строчки
                //Я не смог автоматизировать это. Если ты сможешь, все скажут тебе "спасибо"
                //Как надо писать:
                //1) В строчке должно быть примерно 72 символа
                //2) В одном add() должно быть не больше 4 строчек
                //3) Если в add() не влезает текст смело добавляй ещё один add() и пиши туда остальное
                //Всё это можно увидеть чуть ниже
                bandit.phraseArray.add("Fuck, and                                       what would it be to gore, the scribe wants to eat. Damn, and there's a naked");
                bandit.phraseArray.add("crooked-nosed ones to poke around, otherwise it's boring. Oh, the paritsa has already");
                //Также после заполнения массива фраз нужно обновить массив выводов
                bandit.phraseInArrayWithdrawn = new boolean[bandit.phraseArray.size];
                phraseSet = true;
            }
            //Этот метод выводит текст на экран и включает звуки
            getPhrase = bandit.inputPhrase();

            paceOfSpeak = 0;
            //Когда персонаж закончит говорить, он передаст очередь следующему
            if(bandit.getPhraseId() == bandit.phraseArray.size){
                queueSpeak = 1;
                phraseSet = false;
            }
        }
    }

    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() { }
}
