package com.amerticum.siracusa.model.figury;
import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.model.Hod;
import com.amerticum.siracusa.model.Figura;

public class Vsadnik extends Figura {
    public Vsadnik(int x, int y, boolean belaya) {
        super(x, y, belaya, belaya ? "bel-vsadnik" : "cher-vsadnik");

        vozmojnyeHody.add(new Hod(0, 2, false));
        vozmojnyeHody.add(new Hod(1, 1, false));
        vozmojnyeHody.add(new Hod(2, 0, false));
        vozmojnyeHody.add(new Hod(1, -1, false));
        vozmojnyeHody.add(new Hod(0, -2, false));
        vozmojnyeHody.add(new Hod(-1, -1, false));
        vozmojnyeHody.add(new Hod(-2, 0, false));
        vozmojnyeHody.add(new Hod(-1, 1, false));
    }

    @Override
    public int cena(){
        return Constants.VSADNIK_STOIT;
    }
}
