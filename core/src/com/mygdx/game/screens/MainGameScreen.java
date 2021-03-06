package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilities.ChoiceHandler;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utilities.SpeakingClass;
import com.mygdx.game.characters.*;
import java.util.ArrayList;
import java.util.Random;
import static com.mygdx.game.screens.MainMenuScreen.*;

public class MainGameScreen implements Screen {
    private final MyGdxGame game;

    // Текстуры
    public static Texture currentBackground;
    public static Texture backgroundPolyana;
    private final Texture textField;
    private final Texture nameField;
    private static Texture[] QTELetters;
    static int currentLetter = 0;

    //Эти переменные отвечают за вывод фраз на экран
    private static String speakerName;
    private static StringBuilder getPhrase;
    private int paceOfSpeak;
    private boolean startSpeak;
    public static String currentCharacter = "";

    // QTE
    public static String difficulty;
    public static boolean FuncStarted = false;
    public static boolean tunedUp = false;
    static int aimCountTemp = 0;
    static int mistakes = 3;
    static long time = 8;
    static int aimCount = 5;
    static String[] keyMas = new String[]{"A", "D"};
    static String currentKey = "";
    static long timeStart = 0;

    //Это персонажи нашей игры
    public static Bandit bandit;
    public static Debter debter;
    public static Military military;
    public static Volition volition;
    public static Monolith monolith;
    public static Author author;
    public static Protagonist protagonist;

    //кнопки выборов
    private static TextButton choiceFirstButton;
    private static TextButton choiceSecondButton;
    private static TextButton choiceThirdButton;
    public static boolean choiceActive;

    //Класс для прочитки текста
    private final SpeakingClass speakingClass;

    //Создаем choiceHandler
    public static int choiceNow = 0;

    //Переменные для эффектов
    private final Sprite portal;
    private final Texture blueGlowTexture;
    private final Texture bloodMoreTexture;
    private final Texture bloodTexture;
    private static boolean screenShakingMoment;
    private static boolean screenUp;
    private static boolean screenDown;
    public static boolean portalMoment;
    private static boolean blueGlowMoment;
    private static boolean bloodMoment;
    private static boolean bloodMoreMoment;
    private static Sound contusionSound;
    private static Sound healSound;
    private static Sound footstepsSound;
    private static Sound boneCrackSound;
    private static Sound anomaly;
    private static int flashTime;
    private int backgroundX = -10;
    private int backgroundY = -10;

    //костёр
    private static boolean campfireIsLit;
    private final Array<Texture> campfire;
    private int campfirePhase;
    private int campfireThreePhase;

    private static boolean end;

    public MainGameScreen(final MyGdxGame game) {
        this.game = game;

        speakingClass = new SpeakingClass("chapters/chapter1.txt");

        getPhrase = new StringBuilder();
        speakerName = "";

        campfire = new Array<>();
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

        //Это персонажи нашей игры
        bandit = new Bandit();
        debter = new Debter();
        military = new Military();
        volition = new Volition();
        author = new Author();
        protagonist = new Protagonist();

        currentBackground = new Texture("sprites/backgrounds/orange_forest.png");
        backgroundPolyana = new Texture("sprites/backgrounds/main_polyana.png");
        textField = new Texture("sprites/interface/text_field.png");
        nameField = new Texture("sprites/interface/name_field.png");
        QTELetters = new Texture[]{new Texture("sprites/QTELetters/letter_A.png"),
                new Texture("sprites/QTELetters/letter_D.png"),
                new Texture("sprites/QTELetters/letter_S.png"),
                new Texture("sprites/QTELetters/letter_W.png")};


        //Текстуры эффектов
        blueGlowTexture = new Texture("sprites/effects/blue_glow.png");
        portal = new Sprite(new Texture("sprites/effects/portal.png"), 400, 400);
        portal.setX(300);
        portal.setY(250);
        bloodMoreTexture = new Texture("sprites/effects/blood4.png");
        bloodTexture = new Texture("sprites/effects/blood3.png");

        //Звуки эффектов
        contusionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/contusion.wav"));
        healSound = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/heal.wav"));
        footstepsSound = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/footsteps.wav"));
        boneCrackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/boneCrack.wav"));
        anomaly = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/anomaly.wav"));

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
        game.batch.draw(currentBackground, backgroundX, backgroundY);

        if (portalMoment) {
            portal.draw(game.batch);
            portal.rotate(-1);
        }

        //Здесь рисуются персонажи
        game.batch.draw(debter.getTexture(), debter.getX(), debter.getY());
        game.batch.draw(bandit.getTexture(), bandit.getX(), bandit.getY());
        game.batch.draw(military.getTexture(), military.getX(), military.getY());
        game.batch.draw(volition.getTexture(), volition.getX(), volition.getY());

        //Здесь поспрайтово рисуется костёр
        if (campfireIsLit) game.batch.draw(campfire.get(campfirePhase), 550, 150);
        // Отрисовка буковки для QTE
        if (QTEActive) game.batch.draw(QTELetters[currentLetter], 50, 400);

        game.batch.draw(textField, 0, 0);
        game.batch.draw(nameField, 0, 162);
        //Здесь рисуется текст
        game.font.draw(game.batch, getPhrase.toString(), 10, 150);
        game.font.draw(game.batch, speakerName, 10, 200);

        if (bloodMoment) game.batch.draw(bloodTexture, 0, 0);
        if (bloodMoreMoment) game.batch.draw(bloodMoreTexture, 0, 0);
        if (blueGlowMoment) game.batch.draw(blueGlowTexture, 0, 0);
        if (flashTime > 1) {
            flashTime--;
            if (flashTime < 100) {
                Gdx.gl.glClearColor(1, 1, 1, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            }
        }

        game.batch.end();

        game.stage.draw();

        choiceButtons();

        if (!startSpeak) {
            startSpeak = true;
            speakingClass.start();
        }
        // Временное значение для промотки текста
        if (!doReading && Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !QTEActive && !choiceActive) skipSpeakingPhrase();
        // Вызов метода QTE
        if (QTEActive && FuncStarted) activeQTE();
        // Этот метод вызывается каждый цикл рендера и на текстовом поле печатается фраза
        if (startSpeak) speak();
        // Тряска экрана
        if (screenShakingMoment) screenShaking();

        paceOfSpeak++;

        //Здесь происходит переключение спрайтов огня
        //Один спрайт держится на экране 3 цикла рендера
        if (campfireIsLit) {
            campfireThreePhase++;
            if (campfireThreePhase > 3) {
                campfireThreePhase = 0;
                campfirePhase++;
                if (campfirePhase > 11) {
                    campfirePhase = 0;
                }
            }
        }

        if (end) {
            speakingClass.stop();
            startSpeak = false;
            game.setScreen(new TitlesScreen(game));
            dispose();
        }
    }

    private void skipSpeakingPhrase(){
        doReading = true;
        if (!currentCharacter.equals("")) {
            doReading = false;
            if (currentCharacter.equals("Bandit")) {
                while (bandit.getPhraseId() != bandit.phraseArray.size) getPhrase = bandit.inputPhrase();
                currentCharacter = "";
                bandit.clearPhrase();
            }
            if (currentCharacter.equals("Debter")) {
                while (debter.getPhraseId() != debter.phraseArray.size) getPhrase = debter.inputPhrase();
                currentCharacter = "";
                debter.clearPhrase();
            }
            if (currentCharacter.equals("Volition")) {
                while (volition.getPhraseId() != volition.phraseArray.size) getPhrase = volition.inputPhrase();
                currentCharacter = "";
                volition.clearPhrase();
            }
            if (currentCharacter.equals("Military")) {
                while (military.getPhraseId() != military.phraseArray.size) getPhrase = military.inputPhrase();
                currentCharacter = "";
                military.clearPhrase();
            }
            if (currentCharacter.equals("Monolith")) {
                while (monolith.getPhraseId() != monolith.phraseArray.size) getPhrase = monolith.inputPhrase();
                currentCharacter = "";
                monolith.clearPhrase();
            }
            if (currentCharacter.equals("Author")) {
                while (author.getPhraseId() != author.phraseArray.size) getPhrase = author.inputPhrase();
                currentCharacter = "";
                author.clearPhrase();
            }
            if (currentCharacter.equals("Protagonist")) {
                while (protagonist.getPhraseId() != protagonist.phraseArray.size)
                    getPhrase = protagonist.inputPhrase();
                currentCharacter = "";
                protagonist.clearPhrase();
            }
        }
    }

    private void choiceButtons(){
        choiceFirstButton.addListener(new ChangeListener() {
            boolean listener;

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.isButtonIsPressed()) {
                    listener = true;
                    game.setButtonIsPressed(true);
                    choiceFirstButton.setVisible(false);
                    choiceSecondButton.setVisible(false);
                    choiceThirdButton.setVisible(false);
                    setChoice(choiceNow);
                    System.out.println("Choice 1 was taken");
                }
            }

            public void setChoice(int choiceID) {
                doReading = true;
                choiceActive = false;
                ChoiceHandler.addInArray("CHOICE " + choiceID + " " + 1);
            }
        });

        choiceSecondButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.isButtonIsPressed()) {
                    game.setButtonIsPressed(true);
                    choiceFirstButton.setVisible(false);
                    choiceSecondButton.setVisible(false);
                    choiceThirdButton.setVisible(false);
                    setChoice(choiceNow);
                    System.out.println("Choice 2 was taken");
                }
            }

            public void setChoice(int choiceID) {
                doReading = true;
                choiceActive = false;
                ChoiceHandler.addInArray("CHOICE " + choiceID + " " + 2);
            }
        });

        choiceThirdButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.isButtonIsPressed()) {
                    game.setButtonIsPressed(true);
                    choiceFirstButton.setVisible(false);
                    choiceSecondButton.setVisible(false);
                    choiceThirdButton.setVisible(false);
                    setChoice(choiceNow);
                    System.out.println("Choice 3 was taken");
                }
            }

            public void setChoice(int choiceID) {
                doReading = true;
                choiceActive = false;
                ChoiceHandler.addInArray("CHOICE " + choiceID + " " + 3);
            }
        });
    }

    private void activeQTE(){
        if (!tunedUp) {
            aimCountTemp = 0;
            mistakes = 3;
            time = 8000 + System.currentTimeMillis();
            aimCount = 5;
            keyMas = new String[]{"A", "D"};
            if (difficulty.equals("NORMAL")) {
                mistakes = 2;
                time = 5000 + System.currentTimeMillis();
                aimCount = 8;
                keyMas = new String[]{"A", "D", "S"};
            }
            if (difficulty.equals("HARD")) {
                mistakes = 1;
                time = 3000 + System.currentTimeMillis();
                aimCount = 10;
                keyMas = new String[]{"A", "D", "S", "W"};
            }
            Random random = new Random();
            currentKey = keyMas[currentLetter = random.nextInt(keyMas.length)];
            tunedUp = true;
        }
        Random random = new Random();

        // Кнопки для QTE
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            if (currentKey.equals("A")) {
                aimCountTemp += 1;
                currentKey = keyMas[currentLetter = random.nextInt(keyMas.length)];

            } else {
                mistakes -= 1;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            if (currentKey.equals("D")) {
                aimCountTemp += 1;
                currentKey = keyMas[currentLetter = random.nextInt(keyMas.length)];

            } else {
                mistakes -= 1;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            if (currentKey.equals("S")) {
                aimCountTemp += 1;
                currentKey = keyMas[currentLetter = random.nextInt(keyMas.length)];

            } else {
                mistakes -= 1;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            if (currentKey.equals("W")) {
                aimCountTemp += 1;
                currentKey = keyMas[currentLetter = random.nextInt(keyMas.length)];

            } else {
                mistakes -= 1;
            }
        }
        if (aimCountTemp >= aimCount || System.currentTimeMillis() - timeStart > time) {
            long timeFinish = System.currentTimeMillis();
            if (timeFinish - timeStart > time) {
                mistakes = -1;
            }
            QTESuccess = mistakes >= 0;
            doReading = true;
            FuncStarted = false;
        }
    }

    private void speak() {
        // Говорит бандит
        if (currentCharacter.equals("Bandit") && paceOfSpeak >= bandit.getPace()) {
            speakerName = "Бандит";
            getPhrase = bandit.inputPhrase();
            paceOfSpeak = 0;
            if (bandit.getPhraseId() == bandit.phraseArray.size) {
                currentCharacter = "";
                bandit.clearPhrase();
            }
        }

        // Говорит долгаш
        if (currentCharacter.equals("Debter") && paceOfSpeak >= debter.getPace()) {
            speakerName = "Долговец";
            getPhrase = debter.inputPhrase();
            paceOfSpeak = 0;
            if (debter.getPhraseId() == debter.phraseArray.size) {
                currentCharacter = "";
                debter.clearPhrase();
            }
        }

        // Говорит свободовец
        if (currentCharacter.equals("Volition") && paceOfSpeak >= volition.getPace()) {
            speakerName = "Свободовец";
            getPhrase = volition.inputPhrase();
            paceOfSpeak = 0;
            if (volition.getPhraseId() == volition.phraseArray.size) {
                currentCharacter = "";
                volition.clearPhrase();
            }
        }

        // Говорит военный
        if (currentCharacter.equals("Military") && paceOfSpeak >= military.getPace()) {
            speakerName = "Вояка";
            getPhrase = military.inputPhrase();
            paceOfSpeak = 0;
            if (military.getPhraseId() == military.phraseArray.size) {
                currentCharacter = "";
                military.clearPhrase();
            }
        }

        // Говорит автор
        if (currentCharacter.equals("Author") && paceOfSpeak >= author.getPace()) {
            speakerName = "Автор";
            getPhrase = author.inputPhrase();
            paceOfSpeak = 0;
            if (author.getPhraseId() == author.phraseArray.size) {
                currentCharacter = "";
                author.clearPhrase();
            }
        }

        // Говорит герой
        if (currentCharacter.equals("Protagonist") && paceOfSpeak >= protagonist.getPace()) {
            speakerName = "Вы";
            getPhrase = protagonist.inputPhrase();
            paceOfSpeak = 0;
            if (protagonist.getPhraseId() == protagonist.phraseArray.size) {
                currentCharacter = "";
                protagonist.clearPhrase();
            }
        }
    }

    public static void startChoice(int countChoice, int choiceID, ArrayList<String> choiceNames) {
        choiceActive = true;
        choiceNow = choiceID;
        currentCharacter = "";
        speakerName = "Выбор";
        protagonist.clearPhrase();
        getPhrase = new StringBuilder();
        ArrayList<String> tempArray = new ArrayList<>();
        System.out.println("CounyChoice = " + countChoice);
        for (String i : choiceNames) {
            if (i.contains("/")) {
                tempArray.add(i.substring(0, i.length() - 1));
            } else tempArray.add(i);
        }
        if (countChoice >= 0) {
            choiceFirstButton.setText(tempArray.get(0));
            choiceFirstButton.setPosition(640, 110);
            choiceFirstButton.setVisible(true);
        }
        if (countChoice >= 1) {
            choiceSecondButton.setText(tempArray.get(1));
            choiceSecondButton.setPosition(640, 60);
            choiceSecondButton.setVisible(true);
        }
        if (countChoice >= 2) {
            choiceThirdButton.setText(tempArray.get(2));
            choiceThirdButton.setPosition(640, 10);
            choiceThirdButton.setVisible(true);
        }
    }

    public static void doEffect(String name, int var) {
        //Добавляющие эффекты
        if (name.equals("PORTAL")) {
            portalMoment = true;
        }
        if (name.equals("ABOBA21")) {
            screenShakingMoment = true;
        }
        if (name.equals("BLOOD")) {
            bloodMoment = true;
        }
        if (name.equals("BLOODKAPETZ")) {
            bloodMoreMoment = true;
        }
        if (name.equals("AMOGUS36")) {
            blueGlowMoment = true;
        }
        if (name.equals("CHLORKA")) {
            flashTime = var;
            doReading = true;
        }
        if (name.equals("ANOMALY")) {
            anomaly.play(0.005f);
            anomaly.loop();
        }
        if (name.equals("LIT_CAMPFIRE")){
            campfireIsLit = true;
        }
        //Убирающие эффекты
        if (name.equals("CLEAR")) {
            anomaly.stop();
            screenShakingMoment = false;
            portalMoment = false;
        }
        if (name.equals("HEADCRAB11")) {
            healSound.play(0.1f);
            bloodMoment = false;
            bloodMoreMoment = false;
            blueGlowMoment = false;
        }
        //Скоротечные эффекты
        if (name.equals("PUPSITCH98")) {
            contusionSound.play(0.1f);
        }
        if (name.equals("BEBRA4")) {
            footstepsSound.play(0.1f);
        }
        if (name.equals("NYAMNYAMNYAM6")) {
            boneCrackSound.play(0.1f);
        }
        if (name.equals("PUT_OUT_CAMPFIRE")){
            campfireIsLit = false;
        }
        //конец
        if (name.equals("END")){
            end = true;
        }
    }

    public static void makeQTE(String difficultyNew) {
        FuncStarted = true;
        difficulty = difficultyNew;
        tunedUp = false;
    }

    public static void changeBG(String fileName) {
        if(fileName.equals("main_polyana.png")) currentBackground = backgroundPolyana;
    }

    //Методы эффектов
    private void screenShaking() {
        if (!screenUp && !screenDown) {
            backgroundY += 5;
            backgroundX += 5;
            screenUp = true;
            screenDown = false;
        } else if (screenUp && !screenDown) {
            backgroundY += 5;
            backgroundX -= 5;
            screenUp = false;
            screenDown = true;
        } else if (!screenUp) {
            backgroundY -= 5;
            backgroundX += 5;
            screenDown = true;
            screenUp = true;
        } else {
            backgroundY -= 5;
            backgroundX -= 5;
            screenUp = false;
            screenDown = false;
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
