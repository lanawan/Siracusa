/*
 * Copyright 2013 Stepan Melnichuk (baris.sencan@me.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.amerticum.siracusa.view;

import com.amerticum.siracusa.Assets;
import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.SiracusaGame;
import com.amerticum.siracusa.model.Doska;
import com.amerticum.siracusa.model.Hod;
import com.amerticum.siracusa.player.AlphaBetaPlayer;
import com.amerticum.siracusa.player.DeterministicPlayer;
import com.amerticum.siracusa.player.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * A {@link SplashScreen}
 * 
 * @author Stepan Melnichuk
 */
public class MainMenu implements Screen {
	private Stage stage;
	private BitmapFont font;
	private TextButton netPlayButton, computerPlayButton, soloPlayButton, playedGamesReview, gameHistory;
	private Doska doska;
	private Table table;
	private SpriteBatch batch;
	private Sprite sprite;
	private static Player humanPlayer;
	private static Player computerPlayer;


	public MainMenu(Doska doska) {
		this.doska = doska;
		initUI();


	}

	private void initUI() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setWidth(stage.getWidth());
		table.align(Align.center|Align.top);
		table.setPosition(0,Gdx.graphics.getHeight());

		computerPlayButton = new TextButton(" Play computer ", Assets.skin, "menu");
		netPlayButton = new TextButton(" Play network ", Assets.skin, "menu");
		soloPlayButton = new TextButton(" Play solo ", Assets.skin, "menu");
		playedGamesReview = new TextButton(" Played games review ",Assets.skin, "menu");
		gameHistory = new TextButton(" About this game ",Assets.skin, "menu");

		table.padTop((Gdx.graphics.getHeight() - 750 - 4*Constants.MARGE)/2);
		table.add(computerPlayButton).size(Constants.MENU_BUTTON_WIDTH,Constants.MENU_BUTTON_HEIGHT).padBottom(Constants.MARGE);
		table.row();
		table.add(netPlayButton).size(Constants.MENU_BUTTON_WIDTH,Constants.MENU_BUTTON_HEIGHT).padBottom(Constants.MARGE);;
		table.row();
		table.add(soloPlayButton).size(Constants.MENU_BUTTON_WIDTH,Constants.MENU_BUTTON_HEIGHT).padBottom(Constants.MARGE);;
		table.row();
		table.add(playedGamesReview).size(Constants.MENU_BUTTON_WIDTH,Constants.MENU_BUTTON_HEIGHT).padBottom(Constants.MARGE);;
		table.row();
		table.add(gameHistory).size(Constants.MENU_BUTTON_WIDTH,Constants.MENU_BUTTON_HEIGHT);

		batch = new SpriteBatch();
		sprite = new Sprite(Assets.mainMenuTexture);
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		computerPlayer = new AlphaBetaPlayer(false,2);
		humanPlayer = new DeterministicPlayer(true);

		computerPlayButton.addListener(new ClickListener() {
										   @Override
										   public void clicked(InputEvent event, float x, float y) {
											   doska.player1 = humanPlayer;
											   doska.player2 = computerPlayer;
											   SiracusaGame.game.setScreen(new GameScreen(doska));
										   }
									   });

		soloPlayButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SiracusaGame.game.setScreen(new GameScreen(doska));
			}
		});

		netPlayButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});

		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		sprite.draw(batch);
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
		Gdx.graphics.requestRendering();
	}
	@Override
	public void show() {

	}

	@Override
	public void dispose() {
		stage.dispose();
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


}
