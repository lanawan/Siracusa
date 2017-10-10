package com.amerticum.siracusa.model;

public class IstoriaHodov {
    private Figura figura1;
    private Figura figura2;
    private Hod hod;
    private Pole pole1, pole2;

    public IstoriaHodov(Figura figura1, Figura figura2, Hod hod) {
        this.figura1 = figura1;
        this.figura2 = figura2;
        this.hod = hod;
    }

    public Figura getFigura1() {
        return figura1;
    }

    public void setFigura1(Figura figura1) {
        this.figura1 = figura1;
    }

    public Figura getFigura2() {
        return figura2;
    }

    public void setFigura2(Figura figura2) {
        this.figura2 = figura2;
    }

    public Hod getHod() {
        return hod;
    }

    public void setHod(Hod hod) {
        this.hod = hod;
    }
}
