package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.characters.Bandit;
import com.mygdx.game.characters.ClearSkyer;
import com.mygdx.game.characters.Debter;
import com.mygdx.game.characters.Freedom;
import com.mygdx.game.characters.Monolith;
import com.mygdx.game.characters.Singleton;
import com.mygdx.game.characters.Volition;

import java.util.Random;

public class MainGameSpace implements Screen {
    final MyGdxGame game;
    boolean isPressed;
    Random random;

    Texture background;
    Texture textField;

    //В этом массиве хранятся спрайты костра
    Array<Texture> campfire;
    int campfirePhase;
    int campfireThreePhase;

    //Эти переменные отвечают за вывод фраз на экран
    StringBuilder getPhrase;
    int paceOfSpeak;
    boolean startSpeak;
    int queueSpeak;
    boolean phraseSet;

    //Это персонажи нашей игры
    Bandit bandit;
    Monolith monolith;
    ClearSkyer clearSkyer;
    Debter debter;
    Freedom freedom;
    Singleton singleton;
    Volition volition;


    MainGameSpace(final MyGdxGame game){
        this.game = game;
        random = new Random();
        isPressed = false;

        campfire = new Array<>();
        getPhrase = new StringBuilder();

        //Это персонажи нашей игры
        bandit = new Bandit();
        monolith = new Monolith();
        clearSkyer = new ClearSkyer();
        debter = new Debter();
        freedom = new Freedom();
        singleton = new Singleton();
        volition = new Volition();


        background = new Texture("sprites/main_menu_background.jpg");
        textField = new Texture("sprites/text_field.png");

        //Спрайты огня помещаются в массив
        campfire.add(new Texture("sprites/campfire/fire1.png"));
        campfire.add(new Texture("sprites/campfire/fire2.png"));
        campfire.add(new Texture("sprites/campfire/fire3.png"));
        campfire.add(new Texture("sprites/campfire/fire4.png"));
        campfire.add(new Texture("sprites/campfire/fire5.png"));
        campfire.add(new Texture("sprites/campfire/fire6.png"));
        campfire.add(new Texture("sprites/campfire/fire7.png"));
        campfire.add(new Texture("sprites/campfire/fire8.png"));
        campfire.add(new Texture("sprites/campfire/fire9.png"));
        campfire.add(new Texture("sprites/campfire/fire10.png"));
        campfire.add(new Texture("sprites/campfire/fire11.png"));
        campfire.add(new Texture("sprites/campfire/fire12.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0);

        //Здесь рисуются персонажи
        game.batch.draw(monolith.getTexture(), monolith.getX(), monolith.getY());
        game.batch.draw(clearSkyer.getTexture(), clearSkyer.getX(), clearSkyer.getY());
        game.batch.draw(debter.getTexture(), debter.getX(), debter.getY());
        game.batch.draw(bandit.getTexture(), bandit.getX(), bandit.getY());
        game.batch.draw(freedom.getTexture(), freedom.getX(), freedom.getY());
        game.batch.draw(singleton.getTexture(), singleton.getX(), singleton.getY());
        game.batch.draw(volition.getTexture(), volition.getX(), volition.getY());


        //Здесь поспрайтево рисуется костёр
        game.batch.draw(campfire.get(campfirePhase), 550, 150);


        game.batch.draw(textField, 0, 0);
        game.batch.draw(textField, 0, 486);
        //Здесь рисуется текст
        game.font.draw(game.batch, getPhrase.toString(), 10, 640);

        game.batch.end();

        //Этот флаг нужен, чтобы персонаж начинал говорить не с запуска окна, а с нажатия пробела
        if(!startSpeak && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            startSpeak = true;
        }

        //Этот метод вызывается каждый цикл рендера и на текстовом поле мечатается фраза
        if(startSpeak) {
            speak();
        }

        //Здесь происходит переключение спрайтов огня
        //Один спрайт держится на экране 3 цикла рендера
        campfireThreePhase++;
        if(campfireThreePhase > 3) {
            campfireThreePhase = 0;
            campfirePhase++;
            if (campfirePhase > 11) {
                campfirePhase = 0;
            }
        }

        paceOfSpeak++;
    }

    //Это очень длинный метод, но не бойся, тут нет ничего страшного
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
                //1) В строчке должно быть примерно 93 символа
                //2) В одном add() должно быть не больше 4 строчек
                //3) Если в add() не влезает текст смело добавляй ещё один add() и пиши туда остальное
                //Всё это можно увидеть чуть ниже
                bandit.phraseArray.add("Fuck, and what would it be to gore, the scribe wants to eat. Damn, and there's a naked man in" +
                        "\n" +
                        "his pocket, even if a deer got caught with swag, or better with money. They say, I would like" +
                        "\n" +
                        "to crush an ampoule for two right now, or to fall into some kind of shesha. Right now there" +
                        "\n" +
                        "would be vodyars.. Yes, I'll get a mental snack. At least some crooked-nosed ones to poke" +
                        "\n");
                bandit.phraseArray.add("around, otherwise it's boring. Oh, the paritsa has already shaken here, no sensible heating " +
                        "\n" +
                        "for you, no horseradish.");
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
        //Все говорят по очереди
        //Каждый if на этом уровне определяет кто говорит
        if(queueSpeak == 1 && paceOfSpeak >= monolith.getPace()) {
            //Здесь устанавливается фраза, которую скажет персонаж
            //Флаг phraseSet нужен, чтобы фраза устанавливалась только 1 раз
            if(!phraseSet) {
                //phraseId и phraseArray нужно обязательно очистить от предыдущего значения
                monolith.setPhraseId(0);
                monolith.phraseArray.clear();

                //И вот таким образо устанавливается фраза
                monolith.phraseArray.add("We thank You for revealing to Your servants the machinations of our enemies. Shine Your" +
                        "\n" +
                        "radiance on the souls of those who gave their lives in fulfillment of Your will. Into battle," +
                        "\n" +
                        "defenders of the Monolith! Into battle! Let's avenge our fallen brothers. May their eternal" +
                        "\n" +
                        "unity with the Monolith be blessed! Death... fierce death to those who reject His sacred power!");

                //Также после заполнения массива фраз нужно обновить массив выводов
                monolith.phraseInArrayWithdrawn = new boolean[monolith.phraseArray.size];
                phraseSet = true;
            }
            getPhrase = monolith.inputPhrase();
            paceOfSpeak = 0;
            //Когда персонаж закончит говорить, он передаст очередь следующему
            if(monolith.getPhraseId() == monolith.phraseArray.size){
                queueSpeak = 2;
                phraseSet = false;
            }
        }
        //Дальше коментариев не будет, надеюсь ты понял
        if(queueSpeak == 2 && paceOfSpeak >= bandit.getPace()) {
            if(!phraseSet) {
                bandit.setPhraseId(0);
                bandit.phraseArray.clear();
                bandit.phraseArray.add("Shut up!");
                bandit.phraseInArrayWithdrawn = new boolean[bandit.phraseArray.size];
                phraseSet = true;
            }
            getPhrase = bandit.inputPhrase();
            paceOfSpeak = 0;
            if(bandit.getPhraseId() == bandit.phraseArray.size){
                queueSpeak = 3;
                phraseSet = false;
            }
        }


        if(queueSpeak == 3 && paceOfSpeak >= monolith.getPace()) {
            if(!phraseSet) {
                monolith.setPhraseId(0);
                monolith.phraseArray.clear();
                monolith.phraseArray.add("Monolith we can't hear you. Why did you leave us, monolith? We are waiting for your" +
                        "\n" +
                        "orders, monolith.");
                monolith.phraseInArrayWithdrawn = new boolean[monolith.phraseArray.size];
                phraseSet = true;
            }
            getPhrase = monolith.inputPhrase();
            paceOfSpeak = 0;
            if(monolith.getPhraseId() == monolith.phraseArray.size){
                queueSpeak = 4;
                phraseSet = false;
            }
        }
        if(queueSpeak == 4 && paceOfSpeak >= bandit.getPace()) {
            if(!phraseSet) {
                bandit.setPhraseId(0);
                bandit.phraseArray.clear();
                bandit.phraseArray.add("Fuck off! I don't have time to chat. Fuck off, bro! What are you staring at?" +
                        "\n" +
                        "Haven't you been hit in the neck for a long time?");
                bandit.phraseInArrayWithdrawn = new boolean[bandit.phraseArray.size];
                phraseSet = true;
            }
            getPhrase = bandit.inputPhrase();
            paceOfSpeak = 0;
            if(bandit.getPhraseId() == bandit.phraseArray.size){
                queueSpeak = 5;
                phraseSet = false;
            }
        }
        if(queueSpeak == 5 && paceOfSpeak >= monolith.getPace()) {
            if(!phraseSet) {
                monolith.setPhraseId(0);
                monolith.phraseArray.clear();
                monolith.phraseArray.add("We know what they want, but we will fulfill our purpose, we will protect the monolith." +
                        "\n" +
                        "He will come back and rejoice, he will rejoice.. We will rejoice.");
                monolith.phraseInArrayWithdrawn = new boolean[monolith.phraseArray.size];
                phraseSet = true;
            }
            getPhrase = monolith.inputPhrase();
            paceOfSpeak = 0;
            if(monolith.getPhraseId() == monolith.phraseArray.size){
                queueSpeak = 6;
                phraseSet = false;
            }
        }


        if(queueSpeak == 6 && paceOfSpeak >= bandit.getPace()) {
            if(!phraseSet) {
                bandit.setPhraseId(0);
                bandit.phraseArray.clear();
                bandit.phraseArray.add("");
                bandit.phraseInArrayWithdrawn = new boolean[bandit.phraseArray.size];
                phraseSet = true;
            }
            getPhrase = bandit.inputPhrase();
            paceOfSpeak = 0;
            if(bandit.getPhraseId() == bandit.phraseArray.size){
                queueSpeak = 7;
                phraseSet = false;
            }
        }
        //Ах да, здесь можно, нет, нужно написать больше фраз и диальгов, ведь у нас игра об этом
    }

    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() { }
}
