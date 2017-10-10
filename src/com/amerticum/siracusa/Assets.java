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

package com.amerticum.siracusa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

	public static Skin skin;
	public static Music splashMusic;
	public static TextureAtlas gameAtlas;
	public static Texture mainMenuTexture;


	public static void loadGame() {
		splashMusic = Gdx.audio.newMusic(Gdx.files.internal("music/fortress.ogg"));
		gameAtlas = new TextureAtlas(Gdx.files.internal("atlases/siracusa.pack"));
		skin = new Skin(Gdx.files.internal("skin.json"),gameAtlas);
		mainMenuTexture = new Texture(Gdx.files.internal("backgrounds/siegeofsiracuse.jpg"));
	}

	public static void disposeGame() {
		skin.dispose();
		splashMusic.dispose();
		gameAtlas.dispose();
	}

}
