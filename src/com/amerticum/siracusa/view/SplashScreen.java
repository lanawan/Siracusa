package com.amerticum.siracusa.view;

import com.amerticum.siracusa.SiracusaGame;
import com.amerticum.siracusa.Assets;
import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.model.Doska;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Гризли on 25.11.2016.
 */

public class SplashScreen implements Screen {
    private String source = "Chess of Siracusa\nDuring several centuries Siracusa was the most powerful Greek city anywhere in the Mediterranean.\nThe people of this city played chess. As a heritage of multiple battles and of many different crossroads of cultures the chess they were playing could be different from well-known. Why not like for example this game...";
    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private float textPosX = 0f;
    private Label titleLabel, sourceLabel;
    private Doska echiquier;

    public SplashScreen() {
        Assets.loadGame();
        echiquier = new Doska();
        echiquier.rasstavFigury();
        initUI();
    }

    private void initUI() {
        batch = new SpriteBatch();


        viewport =  new FitViewport(Constants.SCREEN_HEIGHT/2, Constants.SCREEN_WIDTH/2);
        stage = new Stage(viewport);
        textPosX = Constants.SCREEN_HEIGHT;

        titleLabel = new Label("Siege of Siracuse", Assets.skin, "title");
        titleLabel.setWrap(true);
        titleLabel.setPosition(stage.getWidth()/2 - titleLabel.getWidth()/2 , stage.getHeight() - titleLabel.getHeight());

        sourceLabel = new Label(source, Assets.skin);
        sourceLabel.setWrap(true);
        sourceLabel.setPosition(stage.getWidth()/2 - sourceLabel.getWidth()/2, Constants.MARGE);

        stage.addActor(titleLabel);
        stage.addActor(sourceLabel);

        Assets.splashMusic.setLooping(true);
        Assets.splashMusic.play();
    }


    private void positionUI() {
        titleLabel.setPosition(stage.getWidth()/2 - titleLabel.getWidth()/2 , stage.getHeight() - titleLabel.getHeight());
        sourceLabel.setPosition(stage.getWidth()/2 - sourceLabel.getWidth()/2, Constants.MARGE);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            SiracusaGame.game.setScreen(new MainMenu(echiquier));
        }
        textPosX -= 2.5f;
        Gdx.gl.glClearColor(40/255f, 20/255f, 10/255f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        //batch.begin();

        //batch.end();
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        positionUI();
        Gdx.graphics.requestRendering();
    }

    @Override
    public void show() {
        Assets.splashMusic.setLooping(true);
        Assets.splashMusic.play();
    }

    @Override
    public void hide() {
		Assets.splashMusic.stop();
    }

    @Override
    public void pause() {
        Assets.splashMusic.pause();
    }

    @Override
    public void resume() {
        Assets.splashMusic.play();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
