package com.amerticum.siracusa.model.figury;

import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.model.Figura;
import com.amerticum.siracusa.model.Hod;

public class Luchnik extends Figura {
	public Luchnik(int x, int y, boolean belaya) {
		super(x, y, belaya, belaya ? "bel-luchnik" : "cher-luchnik");

		mojetVzyatPriHode = false;

		vozmojnyeHody.add(new Hod(0, 1, false));
		vozmojnyeHody.add(new Hod(1, 1, false));
		vozmojnyeHody.add(new Hod(1, 0, false));
		vozmojnyeHody.add(new Hod(1, -1, false));
		vozmojnyeHody.add(new Hod(0, -1, false));
		vozmojnyeHody.add(new Hod(-1, -1, false));
		vozmojnyeHody.add(new Hod(-1, 0, false));
		vozmojnyeHody.add(new Hod(-1, 1, false));

		zahvatHody.add(new Hod(0, 3, false));
		zahvatHody.add(new Hod(-1, 2, false));
		zahvatHody.add(new Hod(1, 2, false));
	}

	@Override
	public int cena(){
		return Constants.LUCHNIK_STOIT;
	}
}
