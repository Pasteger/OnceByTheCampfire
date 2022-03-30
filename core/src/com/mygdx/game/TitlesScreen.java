package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import static com.mygdx.game.MainMenuScreen.QTEActive;
import static com.mygdx.game.MainMenuScreen.doReading;

public class TitlesScreen implements Screen {
    private final MyGdxGame game;
    private final Texture background;
    private final Texture developers;
    private final Texture thanks;
    private final Texture license;
    private final Texture sponsor;
    private final Music titlesMusic;
    private final String titles;
    private float titlesY;
    private float developersY;
    private float thanksY;
    private float licenseY;
    private float sponsorY;
    private final float speed;

    TitlesScreen(final MyGdxGame gam){
        this.game = gam;

        titlesMusic = Gdx.audio.newMusic(Gdx.files.internal("music/titles.wav"));
        titlesMusic.setLooping(false);
        titlesMusic.setVolume(0.2f);

        background = new Texture("sprites/backgrounds/titles_screen_background.jpg");
        developers = new Texture("sprites/titles/developers.png");
        thanks = new Texture("sprites/titles/thanks.png");
        license = new Texture("sprites/titles/license.png");
        sponsor = new Texture("sprites/titles/sponsor.png");

        titles = "Once by the campfire" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Спасибо за то, что играли в нашу игру!\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Для вас трудились:\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Разработчики:\n" +
                 "Дмитрий Рубин - Pasteger\n" +
                 "Дмитрий Придуха - DemasMemas\n" +
                 "Илья Соусов - Ne1ran\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Тестировщики:\n" +
                 "Дмитрий Рубин - Pasteger\n" +
                 "Дмитрий Придуха - DemasMemas\n" +
                 "Илья Соусов - Ne1ran\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Сценаристы:\n" +
                 "Дмитрий Придуха - DemasMemas\n" +
                 "Илья Соусов - Ne1ran\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Авторы идеи:\n" +
                 "Дмитрий Придуха - DemasMemas\n" +
                 "Дмитрий Рубин - Pasteger\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Звукооператор:\n" +
                 "Валентин Жуков - Zzzhukov\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Под спонсорством:\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Валентин Жуков - Zzzhukov\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Лицензия" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Creative Commons Attribution-ShareAlike 3.0 Unported License\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Спасибо:\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Неизвестному с Хабра\n" +
                 "Лисовой Анастасии\n" +
                 "Рубиной Любе\n" +
                 "Катковой Лизе\n" +
                 "\n" +
                 "\n" +
                 "А также тебе :)\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "Ищи нас здесь: \n" +
                 "https://github.com/Pasteger/OnceByTheCampfire \n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "\n" +
                 "До новых встреч!";

        titlesY = -15;
        developersY = -645;
        sponsorY = -2020;
        licenseY = -2445;
        thanksY = -2970;

        speed = 0.85f;
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
        game.batch.draw(developers, 20, developersY);
        game.batch.draw(sponsor, 20, sponsorY);
        game.batch.draw(license, 20, licenseY);
        game.batch.draw(thanks, 20, thanksY);
        game.font.draw(game.batch, titles, 20, titlesY);
        game.batch.end();

        titlesY += speed;
        developersY += speed;
        sponsorY += speed;
        licenseY += speed;
        thanksY += speed;

        if(!titlesMusic.isPlaying() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            game.setScreen(new MainMenuScreen(game));
        }
    }
    @Override public void show(){
        titlesMusic.play();}
    @Override public void resize(int width, int height){}
    @Override public void pause(){}
    @Override public void resume(){}
    @Override public void hide(){}
    @Override public void dispose(){}
}
