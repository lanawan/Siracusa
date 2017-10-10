package com.amerticum.siracusa.model.figury;

import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.model.Hod;
import com.amerticum.siracusa.model.Figura;

public class Smertnik extends Figura {
	public Smertnik(int x, int y, boolean belaya) {
		super(x, y, belaya, belaya ? "bel-smertnik" : "cher-smertnik");
		mojetVzyatPriHode = false;
		vozmojnyeHody.add(new Hod(0, 1, true));
		vozmojnyeHody.add(new Hod(1, 1, true));
		vozmojnyeHody.add(new Hod(1, 0, true));
		vozmojnyeHody.add(new Hod(1, -1, true));
		vozmojnyeHody.add(new Hod(0, -1, true));
		vozmojnyeHody.add(new Hod(-1, -1, true));
		vozmojnyeHody.add(new Hod(-1, 0, true));
		vozmojnyeHody.add(new Hod(-1, 1, true));

		vozmojnyeHody.add(new Hod(0, 2, false));
		vozmojnyeHody.add(new Hod(2, 2, false));
		vozmojnyeHody.add(new Hod(2, 0, false));
		vozmojnyeHody.add(new Hod(2, -2, false));
		vozmojnyeHody.add(new Hod(0, -2, false));
		vozmojnyeHody.add(new Hod(-2, -2, false));
		vozmojnyeHody.add(new Hod(-2, 0, false));
		vozmojnyeHody.add(new Hod(-2, 2, false));

		zahvatHody.add(new Hod(0, 1, false));
		zahvatHody.add(new Hod(1, 1, false));
		zahvatHody.add(new Hod(1, 0, false));
		zahvatHody.add(new Hod(1, -1, false));
		zahvatHody.add(new Hod(0, -1, false));
		zahvatHody.add(new Hod(-1, -1, false));
		zahvatHody.add(new Hod(-1, 0, false));
		zahvatHody.add(new Hod(-1, 1, false));

	}
	@Override
	public int cena(){
		return Constants.SMERTNIK_STOIT;
	}
}
