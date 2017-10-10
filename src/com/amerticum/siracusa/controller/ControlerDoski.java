package com.amerticum.siracusa.controller;

import com.amerticum.siracusa.model.Doska;
import com.amerticum.siracusa.model.Figura;
import com.amerticum.siracusa.model.Hod;
import com.amerticum.siracusa.model.Pole;
import com.amerticum.siracusa.player.AlphaBetaPlayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;


public class ControlerDoski extends ActorGestureListener {
	private static final Logger logger = Logger.getLogger(ControlerDoski.class.getSimpleName());
	private int result=777;
	private final Doska doska;
	private final Set<Pole> podsvechennyeVozmojnyeHody = new HashSet<Pole>();

	public ControlerDoski(Doska doska) {
		this.doska = doska;
	}

	@Override
	public void tap(InputEvent event, float x, float y, int count, int button) {
		Actor target = event.getTarget();
		int tx = (int) target.getX();
		int ty = (int) target.getY();

		if (target.getClass().getSuperclass().equals(Figura.class)) {
			Figura figura = (Figura) target;

			if ((((doska.chered % 2) == 0) && figura.belaya) || (((doska.chered % 2) == 1) && !figura.belaya)) {
				if(doska.vybrannayaFigura == figura){
					doska.vybrannayaFigura = null;
					snyatPodsvetku();
				}
				else{
					vybratFiguru(figura);
				}
			} else {
				peredvinytFiguru(doska.vybrannayaFigura, tx, ty);
			}

		} else{
			peredvinytFiguru(doska.vybrannayaFigura, tx, ty);
		}
	}

	private void peredvinytFiguru(Figura figura, int x, int y) {
		if (result!=777 || figura == null || (!doska.chitaiPoleNa(x, y).aktivnoe && !doska.chitaiPoleNa(x, y).podZahvatom)) {
			return;
		}
		snyatPodsvetku();

		int xOld = (int) figura.getX();
		int yOld = (int) figura.getY();

		Hod hod = new Hod(xOld,yOld,x,y);
		// Я делаю мой ход
		doska.peredvinytFiguru(hod, true);

		// Если играю с компьютером (ничью пока что не запрограммировал)
		if(doska.player2!=null && doska.chered%2==1){
			Hod hodPlayer2 = doska.player2.poluchitSledushiiHod(doska);
			doska.vybrannayaFigura = doska.chitaiFiguruNa(hodPlayer2.getX1(),hodPlayer2.getY1());
			// У комьютера нет возможных ходов
			if (hodPlayer2 == null) {
				// Почему ? Может зажал его императора ?
				ArrayList<Hod> hody = doska.poluchitHodyImperatora(doska.player2.poluchitCvet(),true);
				// Если император не найден, то проблема в коде
				if(hody.size()==1 && hody.get(0).getY2()==-1){
				}else if(hody.size()==0){
					// Если найден и нет возможных ходов, то да - зажал :) Победа !
					result=1;
				}
			}else if(doska.player2 instanceof AlphaBetaPlayer) {
				// Комьютер ходит
				doska.peredvinytFiguru(hodPlayer2, true);

				// Тест на мат мне
				Hod hodPlayer1 = doska.player1.poluchitSledushiiHod(doska);
				// У меня нет ходов (
				if (hodPlayer1 == null) {
					ArrayList<Hod> hody = doska.poluchitHodyImperatora(doska.player1.poluchitCvet(), true);
					// Если мой император не найден, то проблема в коде
					if (hody.size() == 1 && hody.get(0).getY2() == -1) {
					} else if (hody.size() == 0) {
						// Мне мат : нет ходов потому, что именно мой император не может двинуться
						result = -1;
					}
				}
			}
		}


		switch(result){
			// Игрок 1 проиграл
			case -1:
				System.out.println("\n\n----------\nYOU LOOSE\n------------\n\n");
				break;
			// Ничья
			case 0:
				System.out.println("\n\n----------\nDRAW\n------------\n\n");
				break;
			// Игрок 1 победил
			case 1:
				System.out.println("\n\n----------\nYOU WIN\n------------\n\n");
				break;
		}


	}

	private void vybratFiguru(Figura figura){
		snyatPodsvetku();
		doska.vybrannayaFigura = figura;
		Set<Pole> polya = figura.kudaPoitiIMojetVzyat(doska, true);
		polya.addAll(figura.chtoMojnoVzyat(doska, true));
		for(Pole pole : polya){
			Hod hod = new Hod((int)figura.getX(), (int)figura.getY(), (int) pole.getX(), (int) pole.getY());
			if (doska.poleBezopasno(figura, hod)) {
				podsvechennyeVozmojnyeHody.add(pole);
			}else{
				pole.aktivnoe = false;
				pole.podZahvatom = false;
			}
		}
	}

	private void snyatPodsvetku() {
		for (Pole p : podsvechennyeVozmojnyeHody) {
			p.aktivnoe = false;
			p.podZahvatom = false;
		}
		podsvechennyeVozmojnyeHody.clear();
	}

}
