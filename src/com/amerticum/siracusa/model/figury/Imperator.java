package com.amerticum.siracusa.model.figury;

import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.model.Hod;
import com.amerticum.siracusa.model.Pole;
import com.amerticum.siracusa.model.Doska;
import com.badlogic.gdx.utils.Array;
import com.amerticum.siracusa.model.Figura;

public class Imperator extends Figura {
	public Imperator(int x, int y, boolean belaya) {
		super(x, y, belaya, belaya ? "bel-imperator" : "cher-imperator");

		vozmojnyeHody.add(new Hod(0, 1, false));
		vozmojnyeHody.add(new Hod(1, 1, false));
		vozmojnyeHody.add(new Hod(1, 0, false));
		vozmojnyeHody.add(new Hod(1, -1, false));
		vozmojnyeHody.add(new Hod(0, -1, false));
		vozmojnyeHody.add(new Hod(-1, -1, false));
		vozmojnyeHody.add(new Hod(-1, 0, false));
		vozmojnyeHody.add(new Hod(-1, 1, false));
	}

	@Override
	public int cena(){
		return Constants.IMPERATOR_STOIT;
	}
}
