package com.amerticum.siracusa.model;

import com.amerticum.siracusa.Assets;
import com.amerticum.siracusa.model.figury.Kopeishik;
import com.amerticum.siracusa.model.figury.Strelomet;
import com.amerticum.siracusa.player.AlphaBetaPlayer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Figura extends Actor implements Cloneable{
	public boolean belaya;
	public boolean mojetVzyatPriHode = true;
	protected ArrayList<Hod> vozmojnyeHody = new ArrayList<Hod>();
	protected ArrayList<Hod> zahvatHody = new ArrayList<Hod>();
	private final TextureRegion textureRegion;


	public Figura(int x, int y, boolean belaya, String regionName) {
		setBounds(x, y, 1, 1);
		this.belaya = belaya;
		textureRegion = Assets.gameAtlas.findRegion(regionName);
	}
	@Override
	public Figura clone(){
		try{
			return (Figura)super.clone();
		}catch (CloneNotSupportedException ex){
		}
		return null;
	}
	public int poluchiCvet(){
		if(belaya){
			return 1;
		}
		return -1;
	}
	public ArrayList<Hod> kudaPoitiIMojetVzyat(Doska doska) {
		ArrayList<Hod> hody = new ArrayList<Hod>();
		// Позиция фигуры
		int x = (int) getX();
		int y = (int) getY();
		// Цикл пробега по всем обычным ходам передвижения фигуры, но не по специальным ходам захвата других фигур
		for (Hod hod : vozmojnyeHody) {
			boolean cikl = true;
			// Цикл пробега только по тем полям, на которых не стоит данная фигура
			for (int i = 1; cikl; i++) {
				int tx = x + (hod.xOffset * i);
				int ty = y + ((belaya ? hod.yOffset : -hod.yOffset) * i);
				// Тест полей пробега в пределах доски
				if (tx >= 0 && tx < 8 && ty >= 0 && ty < 8){
					Pole pole = doska.chitaiPoleNa(tx, ty);
					Figura drugayaFigura = doska.chitaiFiguruNa(tx, ty);

					// Если на очередном поле стоит фигура
					if (drugayaFigura != null) {
						// возможность взятия при ходе и фигура другого цвета
						if (drugayaFigura.belaya!=belaya && mojetVzyatPriHode) {
							// Значит можно её взять : добавить поле в возможности хода
							hody.add(new Hod(x,y,tx,ty));
						}
						// Наткнуться на любую фигуру на пути пробега означает завершить цикл ходов в этом направлении
						cikl = false;
					} else{
						// А если на очередном поле пусто, то нужно добавить поле в возможности хода
						hody.add(new Hod(x,y,tx,ty));
					}
				} else {
					// Выход за пределы лоски означает завершить цикл ходов в этом направлении
					cikl = false;
				}
				// Если фигура ходит не целыми линиями или диагоналями
				if (!hod.prodoljayuchiy) {
					// То, сделав один шаг, нужно завершить цикл ходов
					cikl = false;
				}
			}
		}
		return hody;
	}
	public ArrayList<Hod> chtoMojnoVzyat(Doska doska) {
		ArrayList<Hod> hody = new ArrayList<Hod>();
		// Позиция фигуры
		int x = (int) getX();
		int y = (int) getY();

		// Цикл пробега по всем ходам захвата других фигур, но не по обычным ходам
		for (Hod hod : zahvatHody) {
			boolean cikl = true;
			// Цикл пробега только по тем полям, на которых не стоит данная фигура
			for (int i = 1; cikl; i++) {
				int tx = x + (hod.xOffset * i);
				int ty = y + ((belaya ? hod.yOffset : -hod.yOffset) * i);
				// Тест полей пробега в пределах доски
				if (tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
					Pole pole = doska.chitaiPoleNa(tx, ty);
					Figura drugayaFigura = doska.chitaiFiguruNa(tx, ty);
					// Если на очередном поле стоит фигура
					if (drugayaFigura != null) {
						// возможность взятия при ходе и фигура другого цвета
						if(drugayaFigura.belaya!=belaya && !mojetVzyatPriHode) {
							hody.add(new Hod(x, y, tx, ty));
						}
						// Наткнуться на любую фигуру на пути пробега означает завершить цикл ходов в этом направлении
						cikl = false;
					}
				} else {
					// Выход за пределы лоски означает завершить цикл ходов в этом направлении
					cikl = false;
				}
				// Если фигура ходит не целыми линиями или диагоналями
				if (!hod.prodoljayuchiy) {
					// То, сделав один шаг, нужно завершить цикл ходов
					cikl = false;
				}
			}
		}
		return hody;
	}

	public Set<Pole> kudaPoitiIMojetVzyat(Doska doska, boolean testRealnyiHod) {
		Set<Pole> polya = new HashSet<Pole>();
		// Позиция фигуры
		int x = (int) getX();
		int y = (int) getY();

		// Цикл пробега по всем обычным ходам передвижения фигуры, но не по специальным ходам захвата других фигур
		for (Hod hod : vozmojnyeHody) {
			boolean cikl = true;
			// Цикл пробега только по тем полям, на которых не стоит данная фигура
			for (int i = 1; cikl; i++) {
				int tx = x + (hod.xOffset * i);
				int ty = y + ((belaya ? hod.yOffset : -hod.yOffset) * i);
				// Тест полей пробега в пределах доски
				if (tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
					Pole pole = doska.chitaiPoleNa(tx, ty);
					Figura drugayaFigura = doska.chitaiFiguruNa(tx, ty);
					// Если на очередном поле стоит фигура
					if (drugayaFigura != null) {
						// Если это тест на возможность взятия при ходе и фигура другого цвета
						if ((!testRealnyiHod || drugayaFigura.belaya != belaya) && mojetVzyatPriHode) {
							// Значит можно её взять : добавить поле в возможности хода
							if(testRealnyiHod) {
								pole.podZahvatom = true;
							}
							polya.add(pole);
						}
						// Наткнуться на любую фигуру на пути пробега означает завершить цикл ходов в этом направлении
						cikl = false;
					} else{
						// А если на очередном поле пусто, то нужно добавить поле в возможности хода
						if(testRealnyiHod) {
							pole.aktivnoe = true;
						}
						polya.add(pole);
					}
				} else {
					// Выход за пределы лоски означает завершить цикл ходов в этом направлении
					cikl = false;
				}
				// Если фигура ходит не целыми линиями или диагоналями
				if (!hod.prodoljayuchiy) {
					// То, сделав один шаг, нужно завершить цикл ходов
					cikl = false;
				}
			}
		}
		return polya;
	}
	public Set<Pole> chtoMojnoVzyat(Doska doska, boolean testRealnyiHod) {
		Set<Pole> polya = new HashSet<Pole>();
		// Позиция фигуры
		int x = (int) getX();
		int y = (int) getY();

		// Цикл пробега по всем ходам захвата других фигур, но не по обычным ходам
		for (Hod hod : zahvatHody) {
			boolean cikl = true;
			// Цикл пробега только по тем полям, на которых не стоит данная фигура
			for (int i = 1; cikl; i++) {
				int tx = x + (hod.xOffset * i);
				int ty = y + ((belaya ? hod.yOffset : -hod.yOffset) * i);
				// Тест полей пробега в пределах доски
				if (tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
					Pole pole = doska.chitaiPoleNa(tx, ty);
					Figura drugayaFigura = doska.chitaiFiguruNa(tx, ty);
					// Если на очередном поле стоит фигура
					if (drugayaFigura != null) {
						// Если это тест на возможность взятия при ходе и фигура другого цвета
						if (!testRealnyiHod || drugayaFigura.belaya != belaya) {
							// Значит можно её взять : добавить поле в возможности хода
							if(testRealnyiHod) {
								pole.podZahvatom = true;
							}
							polya.add(pole);
						}
						// Наткнуться на любую фигуру на пути пробега означает завершить цикл ходов в этом направлении
						cikl = false;
					}else if(!testRealnyiHod){
						polya.add(pole);
					}
				} else {
					// Выход за пределы лоски означает завершить цикл ходов в этом направлении
					cikl = false;
				}
				// Если фигура ходит не целыми линиями или диагоналями
				if (!hod.prodoljayuchiy) {
					// То, сделав один шаг, нужно завершить цикл ходов
					cikl = false;
				}
			}
		}
		return polya;
	}

	public void hodila() {
	}

	public int cena(){
		return 0;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		batch.draw(textureRegion, getX(), getY(), 1, 1);
	}

}
