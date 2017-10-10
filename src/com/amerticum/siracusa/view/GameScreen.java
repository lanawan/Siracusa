//https://chandruscm.wordpress.com/2015/12/30/how-to-setup-google-play-game-services-in-libgdx-using-android-studio/
package com.amerticum.siracusa.view;

import com.amerticum.siracusa.Assets;
import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.SiracusaGame;
import com.amerticum.siracusa.model.Doska;
import com.amerticum.siracusa.model.Hod;
import com.amerticum.siracusa.player.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * A {@link } for {@link GameScreen}.
 * 
 * @author Stepan Melnichuk
 */
public class GameScreen implements Screen {
	private final Stage stage = new Stage(new ScreenViewport());
	private Table hud;
	private TextButton backButton, resetButton;
	private Doska resetDoska, doska;

	public GameScreen(Doska doska) {
		this.doska = doska;
		this.resetDoska = doska;
		initUI();
	}
	private void initUI() {
		Gdx.input.setInputProcessor(stage);
		hud = new Table();
		hud.setWidth(stage.getWidth());
		hud.align(Align.right|Align.top);
		hud.setPosition(0,Gdx.graphics.getHeight());

		backButton = new TextButton(" Back ", Assets.skin, "menu");
		resetButton = new TextButton(" Reset ", Assets.skin, "menu");

		hud.padTop((Gdx.graphics.getHeight() - 750 - 4*Constants.MARGE)/2);
		hud.add(backButton).size(Constants.MENU_BUTTON_WIDTH/3,Constants.MENU_BUTTON_HEIGHT/2).padBottom(Constants.MARGE).padRight(Constants.MARGE*3);
		hud.row();
		hud.add(resetButton).size(Constants.MENU_BUTTON_WIDTH/3,Constants.MENU_BUTTON_HEIGHT/2).padBottom(Constants.MARGE).padRight(Constants.MARGE*3);

		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				doska.anuliruiHod(true);
			}
		});
		resetButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Doska nDoska = new Doska();
				nDoska.rasstavFigury();
				SiracusaGame.game.setScreen(new GameScreen(nDoska));
			}
		});
        stage.addActor(hud);
	}
	@Override
	public void show() {
		doska.align(Align.bottomLeft);
		doska.setPosition(Constants.MARGE/2, Constants.MARGE/2);
		stage.addActor(doska);

		initUI();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(247/255f, 223/255f, 161/255f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		Gdx.graphics.requestRendering();
	}

	@Override
	public void hide() {
	}
	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	@Override
	public void dispose() {
		stage.dispose();
	}

}
