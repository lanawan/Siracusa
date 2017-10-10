package com.amerticum.siracusa.model.figury;

import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.model.Doska;
import com.amerticum.siracusa.model.Figura;

public class Kopeishik extends Figura {
	public Kopeishik(int x, int y, boolean belaya) {
		super(x, y, belaya, belaya ? "bel-kopeishik" : "cher-kopeishik");
		mojetVzyatPriHode = false;

		vozmojnyeHody.add(new com.amerticum.siracusa.model.Hod(0, 1, false));
		vozmojnyeHody.add(new com.amerticum.siracusa.model.Hod(1, 1, false));
		vozmojnyeHody.add(new com.amerticum.siracusa.model.Hod(-1, 1, false));

		zahvatHody.add(new com.amerticum.siracusa.model.Hod(0, 1, false));
	}


	@Override
	public void hodila() {
		Doska doska = (Doska) getParent();
		int x = (int) getX();
		int y = (int) getY();

		if ((belaya && (y == 7)) || (!belaya && (y == 0))) {
			doska.sotriFiguruNa(x, y);
			doska.dobavFiguru(new Geroi(x, y, belaya));
		}
	}

	@Override
	public int cena(){
		return Constants.KOPEISHIK_STOIT;
	}
}
