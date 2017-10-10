package com.amerticum.siracusa.model;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.amerticum.siracusa.Assets;

public class Pole extends Actor {
	public boolean aktivnoe = false;
	public boolean podZahvatom = false;
	private TextureRegion texturaPoleNorma;
	private TextureRegion texturaPoleAktivno;
	private TextureRegion texturaPolePodZahvatom;
	private boolean zanyato;
	private Figura figura;

	public Pole(int x, int y, boolean chernoe) {
		setBounds(x, y, 1, 1);
		if(chernoe){
			texturaPoleNorma = Assets.gameAtlas.findRegion("pole-cher-norma");
		}else{
			texturaPoleNorma = Assets.gameAtlas.findRegion("pole-bel-norma");
		}
		texturaPoleAktivno = Assets.gameAtlas.findRegion("pole-aktivno");
		texturaPolePodZahvatom = Assets.gameAtlas.findRegion("pole-podzahvatom");
	}
	public Pole() {
		zanyato = false;
	}

	public Pole(Pole pole) {
		this.zanyato = pole.poleZanyato();
		this.figura = pole.poleZanyato() ? pole.poluchitFiguru().clone() : null;
	}

	public Pole(Figura figura) {
		this.zanyato = true;
		this.figura = figura;
	}

	public String toString() {
		if(zanyato) {
			return figura.toString();
		}else {
			return ".";
		}
	}

	public void zadatFiguru(Figura figura){
		this.zanyato = true;
		this.figura = figura;
	}
	public void osvoboditPole(){
		this.zanyato = false;
		this.figura = null;
	}
	public Figura poluchitFiguru() {
		return figura;
	}

	public boolean poleZanyato() {
		return zanyato;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (aktivnoe) {
			batch.draw(texturaPoleAktivno, getX(), getY(), 1, 1);
		} else if(podZahvatom) {
			batch.draw(texturaPolePodZahvatom, getX(), getY(), 1, 1);
		}else{
			batch.draw(texturaPoleNorma, getX(), getY(), 1, 1);
		}
	}
}
