package com.amerticum.siracusa;


import com.amerticum.siracusa.view.SplashScreen;
import com.badlogic.gdx.Game;

public class SiracusaGame extends Game {
	public static Game game;

	public SiracusaGame(){
		game = this;
	}
	@Override
	public void create(){
		//Gdx.graphics.setContinuousRendering(false);
		setScreen(new SplashScreen());
	}
}
