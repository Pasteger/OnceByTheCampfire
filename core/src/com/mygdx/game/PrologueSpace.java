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
import java.util.Random;

import static com.mygdx.game.MainMenuScreen.*;

public class PrologueSpace implements Screen {
    private final MyGdxGame game;

    // Текстуры
    private final Texture background;
    private final Texture textField;
    private final Texture nameField;
    private final Texture portal;
    private static Texture[] QTELetters;
    static int currentLetter = 0;
    public boolean portalMoment = false;

    //Эти переменные отвечают за вывод фраз на экран
    private String speakerName;
    private StringBuilder getPhrase;
    private int paceOfSpeak;
    private boolean startSpeak;
    public static String currentCharacter = "";

    // QTE
    public static String difficulty;
    public static boolean FuncStarted = false;
    public static boolean tunedUp = false;
    static int aimCountTemp = 0;
    static int mistakes = 3; static int time = 8; static int aimCount = 5; static String[] keyMas = new String[]{"A", "D"};
    static String currentKey = "";
    static long timeStart = 0;
    static boolean keyPressed;

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
        speakerName = "";

        //Это персонажи нашей игры
        bandit = new Bandit();
        debter = new Debter();
        military = new Military();
        volition = new Volition();
        author = new Author();
        protagonist = new Protagonist();

        background = new Texture("sprites/backgrounds/orange_forest.png");
        textField = new Texture("sprites/text_field.png");
        nameField = new Texture("sprites/name_field.png");
        portal = new Texture("sprites/effects/portal.png");
        QTELetters = new Texture[]{new Texture("sprites/QTELetters/letter_A.png"),
                new Texture("sprites/QTELetters/letter_D.png"),
                new Texture("sprites/QTELetters/letter_S.png"),
                new Texture("sprites/QTELetters/letter_W.png")};

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
        keyPressed = false;
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

        // Отрисовка буковки для QTE
        if (QTEActive){ game.batch.draw(QTELetters[currentLetter], 50, 400); }

        game.batch.draw(textField, 0, 0);
        game.batch.draw(nameField, 0, 162);
        //Здесь рисуется текст
        game.font.draw(game.batch, getPhrase.toString(), 10, 150);
        game.font.draw(game.batch, speakerName, 10, 200);

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
        if(!startSpeak && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            startSpeak = true;
            speakingClass.start();
        }
        // Временное значение для промотки текста
        if(!doReading && Gdx.input.isKeyJustPressed(Input.Keys.G) && !QTEActive){
            doReading = true;
        }

        // Вызов метода QTE
        if (QTEActive && FuncStarted){
            if(!tunedUp){
                aimCountTemp = 0;
                mistakes = 3; time = 8; aimCount = 5; keyMas = new String[]{"A", "D"};
                if (difficulty.equals("NORMAL")){ mistakes = 2; time = 5; aimCount = 8; keyMas = new String[]{"A", "D", "S"};}
                if (difficulty.equals("HARD")){ mistakes = 1; time = 3; aimCount = 10; keyMas = new String[]{"A", "D", "S", "W"};}
                Random random = new Random();
                currentKey = keyMas[currentLetter = random.nextInt(keyMas.length)];
                tunedUp = true;
            }
            if(tunedUp){
                Random random = new Random();
                // Кнопки для QTE
                if (!keyPressed){
                    while (true){
                        if (Gdx.input.isKeyJustPressed(Input.Keys.A)){
                            if(currentKey.equals("A")){ aimCountTemp += 1;
                                keyPressed = true;
                                currentKey = keyMas[currentLetter = random.nextInt(keyMas.length)];
                            break;}
                            else { mistakes -= 1; } }
                        if (Gdx.input.isKeyJustPressed(Input.Keys.D)){
                            if(currentKey.equals("D")){ aimCountTemp += 1;
                                keyPressed = true;
                                currentKey = keyMas[currentLetter = random.nextInt(keyMas.length)];
                            break;}
                            else { mistakes -= 1; } }
                        if (Gdx.input.isKeyJustPressed(Input.Keys.S)){
                            if(currentKey.equals("S")){ aimCountTemp += 1;
                                keyPressed = true;
                                currentKey = keyMas[currentLetter = random.nextInt(keyMas.length)];
                            break;}
                            else { mistakes -= 1; } }
                        if (Gdx.input.isKeyJustPressed(Input.Keys.W)){
                            if(currentKey.equals("W")){ aimCountTemp += 1;
                                keyPressed = true;
                                currentKey = keyMas[currentLetter = random.nextInt(keyMas.length)];
                            break;}
                            else { mistakes -= 1; } }
                        break;
                    }
                }
                if (aimCountTemp >= aimCount){
                    long timeFinish = System.currentTimeMillis();
                    if (timeFinish - timeStart>time * 1000L){ mistakes = -1; }
                    QTESuccess = mistakes >= 0;
                    doReading = true;
                    FuncStarted = false;
                }
            }
        }

        //Этот метод вызывается каждый цикл рендера и на текстовом поле мечатается фраза
        if(startSpeak) { speak(); }

        paceOfSpeak++;
    }

    private void speak(){
        // Говорит бандит
        if(currentCharacter.equals("Bandit") && paceOfSpeak >= bandit.getPace()) {
            speakerName = "Бандит";
            getPhrase = bandit.inputPhrase();
            paceOfSpeak = 0;
            if(bandit.getPhraseId() == bandit.phraseArray.size){ currentCharacter = ""; bandit.clearPhrase();} }

        // Говорит долгаш
        if(currentCharacter.equals("Debter") && paceOfSpeak >= debter.getPace()) {
            speakerName = "Долговец";
            getPhrase = debter.inputPhrase();
            paceOfSpeak = 0;
            if(debter.getPhraseId() == debter.phraseArray.size){ currentCharacter = ""; debter.clearPhrase();} }

        // Говорит свободовец
        if(currentCharacter.equals("Volition") && paceOfSpeak >= volition.getPace()) {
            speakerName = "Свободовец";
            getPhrase = volition.inputPhrase();
            paceOfSpeak = 0;
            if(volition.getPhraseId() == volition.phraseArray.size){ currentCharacter = ""; volition.clearPhrase();} }

        // Говорит военный
        if(currentCharacter.equals("Military") && paceOfSpeak >= military.getPace()) {
            speakerName = "Вояка";
            getPhrase = military.inputPhrase();
            paceOfSpeak = 0;
            if(military.getPhraseId() == military.phraseArray.size){ currentCharacter = ""; military.clearPhrase();} }

        // Говорит автор
        if(currentCharacter.equals("Author") && paceOfSpeak >= author.getPace()) {
            speakerName = "Автор";
            getPhrase = author.inputPhrase();
            paceOfSpeak = 0;
            if(author.getPhraseId() == author.phraseArray.size){ currentCharacter = ""; } author.clearPhrase();}

        // Говорит герой
        if(currentCharacter.equals("Protagonist") && paceOfSpeak >= protagonist.getPace()) {
            speakerName = "Вы";
            getPhrase = protagonist.inputPhrase();
            paceOfSpeak = 0;
            if(protagonist.getPhraseId() == protagonist.phraseArray.size){ currentCharacter = ""; protagonist.clearPhrase();} }
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
        System.out.println("Changed teller to " + name);
    }

    public static void doEffect(String name){
        if(name.equals("ABOBA21")){
            System.out.println("АБОБА21");
        }
    }

    public static void makeQTE(String difficultyNew){
        FuncStarted = true;
        difficulty = difficultyNew;
        tunedUp = false;
    }

    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() { game.font.dispose(); }
}