package com.amerticum.siracusa.model.figury;

import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.model.Figura;
import com.amerticum.siracusa.model.Hod;

public class Geroi extends Figura {
	public Geroi(int x, int y, boolean belyaya) {
		super(x, y, belyaya, belyaya ? "bel-geroi" : "cher-geroi");

		vozmojnyeHody.add(new Hod(0, 1, true));
		vozmojnyeHody.add(new Hod(1, 1, true));
		vozmojnyeHody.add(new Hod(1, 0, true));
		vozmojnyeHody.add(new Hod(1, -1, true));
		vozmojnyeHody.add(new Hod(0, -1, true));
		vozmojnyeHody.add(new Hod(-1, -1, true));
		vozmojnyeHody.add(new Hod(-1, 0, true));
		vozmojnyeHody.add(new Hod(-1, 1, true));
	}

	@Override
	public int cena(){
		return Constants.GEROI_STOIT;
	}
}
