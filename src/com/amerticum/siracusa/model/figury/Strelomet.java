package com.amerticum.siracusa.model.figury;
import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.model.Hod;
import com.amerticum.siracusa.model.Figura;

public class Strelomet extends Figura {
    public Strelomet(int x, int y, boolean belaya) {
        super(x, y, belaya, belaya ? "bel-strelomet" : "cher-strelomet");

        mojetVzyatPriHode = false;

        vozmojnyeHody.add(new Hod(0, 1, false));
        vozmojnyeHody.add(new Hod(1, 1, false));
        vozmojnyeHody.add(new Hod(1, 0, false));
        vozmojnyeHody.add(new Hod(1, -1, false));
        vozmojnyeHody.add(new Hod(0, -1, false));
        vozmojnyeHody.add(new Hod(-1, -1, false));
        vozmojnyeHody.add(new Hod(-1, 0, false));
        vozmojnyeHody.add(new Hod(-1, 1, false));

        zahvatHody.add(new Hod(0, 1, true));

    }

    @Override
    public int cena(){
        return Constants.STRELOMET_STOIT;
    }
}
