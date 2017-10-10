package com.amerticum.siracusa.model;

import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.ai.MinimaxAlphaBeta;
import com.amerticum.siracusa.controller.ControlerDoski;
import com.amerticum.siracusa.model.figury.*;
import com.amerticum.siracusa.player.Player;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class Doska extends Table {
	private static final Logger logger = Logger.getLogger(MinimaxAlphaBeta.class.getSimpleName());
	public Figura vybrannayaFigura;
	public int chered;
	public Player player1;
	public Player player2;
	public Pole[][] polya = new Pole[8][8];
	public Figura[][] figury = new Figura[8][8];
	private Imperator imperatorBel;
	private Imperator imperatorCher;
	public ArrayList<IstoriaHodov> istoriaHodov = new ArrayList<IstoriaHodov>();
	public Pole chitaiPoleNa(int x, int y) {
		return polya[x][y];
	}
	public Figura chitaiFiguruNa(int x, int y) {
		return figury[x][y];
	}

	public Doska() {
		setBounds(0, 0, 8, 8);
		setClip(true);
		setScaleX(Constants.DOSKA_WIDTH);
		setScaleY(Constants.DOSKA_WIDTH);
		addListener(new ControlerDoski(this));

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				polya[i][j] = new Pole(i, j, ((i + j) % 2) == 0);
				addActor(polya[i][j]);
			}
		}
	}
	public Doska(Pole[][] polya, Figura[][] figury, ArrayList<IstoriaHodov> istoriaHodov){
		this.polya = polya;
		this.figury = figury;
		this.istoriaHodov = istoriaHodov;
	}

	public void rasstavFigury() {
		for (int i = 0; i < 8; i++) {
			dobavFiguru(new Kopeishik(i, 1, true));
			dobavFiguru(new Kopeishik(i, 6, false));
		}

		dobavFiguru(new Strelomet(0, 0, true));
		dobavFiguru(new Strelomet(7, 0, true));
		dobavFiguru(new Strelomet(0, 7, false));
		dobavFiguru(new Strelomet(7, 7, false));

		dobavFiguru(new Luchnik(1, 0, true));
		dobavFiguru(new Luchnik(6, 0, true));
		dobavFiguru(new Luchnik(1, 7, false));
		dobavFiguru(new Luchnik(6, 7, false));

		dobavFiguru(new Vsadnik(2, 0, true));
		dobavFiguru(new Vsadnik(5, 0, true));
		dobavFiguru(new Vsadnik(2, 7, false));
		dobavFiguru(new Vsadnik(5, 7, false));

		dobavFiguru(new Smertnik(3, 0, true));
		dobavFiguru(new Smertnik(3, 7, false));

		dobavFiguru(new Imperator(4, 0, true));
		dobavFiguru(new Imperator(4, 7, false));
	}


	public void dobavFiguru(Figura figura) {
		addActor(figura);
		figury[(int) figura.getX()][(int) figura.getY()] = figura;
		polya[(int) figura.getX()][(int) figura.getY()].zadatFiguru(figura);
	}
	public void obnoviVidFigury(){
		for (Figura[] ryad : figury) {
			for (Figura figuraVRyadu : ryad) {
				if(figuraVRyadu != null) {
					addActor(figuraVRyadu);
				}
			}
		}
	}
	public void peremestiFuguruNa(int xOld, int yOld, int x, int y) {
		Figura figura = figury[xOld][yOld];

		figury[x][y] = figura;
		figury[xOld][yOld] = null;
		figura.setX(x);
		figura.setY(y);

		polya[x][y].zadatFiguru(figura);
		polya[xOld][yOld].osvoboditPole();
	}
    public void peredvinytFiguru(Hod hod, boolean neSimulator) {
		Figura figura1 = chitaiFiguruNa(hod.getX1(), hod.getY1());
		Figura figura2 = chitaiFiguruNa(hod.getX2(), hod.getY2());

		istoriaHodov.add(new IstoriaHodov(figura1, figura2, hod));
        if (figura2 != null) {
            sotriFiguruNa(hod.getX2(), hod.getY2());
            if(figura1 instanceof Smertnik){
                sotriFiguruNa(hod.getX1(), hod.getY1());
            }else{
                if (!(figura1 instanceof Luchnik) && !(figura1 instanceof Strelomet)) {
                    peremestiFuguruNa(hod.getX1(), hod.getY1(), hod.getX2(), hod.getY2());
                }
            }
        }else{
			peremestiFuguruNa(hod.getX1(), hod.getY1(), hod.getX2(), hod.getY2());
        }

        if(neSimulator) {
			obnoviVidFigury();
			vybrannayaFigura.hodila();
			vybrannayaFigura = null;
			chered++;
		}
    }
	public void anuliruiHod(boolean neSimulator){
		if(istoriaHodov.size()==0){
			return;
		}

		IstoriaHodov predidushiiHod = istoriaHodov.get(istoriaHodov.size()-1);
		Figura figura1 = predidushiiHod.getFigura1();
		Figura figura2 = predidushiiHod.getFigura2();
		Hod hod = predidushiiHod.getHod();
		if (figura2 != null){
			if(figura1 instanceof Smertnik) {
				dobavFiguru(figura1);
			}else{
				if (!(figura1 instanceof Luchnik) && !(figura1 instanceof Strelomet)) {
					peremestiFuguruNa(hod.getX2(), hod.getY2(), hod.getX1(), hod.getY1());
				}
			}
			dobavFiguru(figura2);
		}else{
			peremestiFuguruNa(hod.getX2(), hod.getY2(), hod.getX1(), hod.getY1());
		}

		istoriaHodov.remove(istoriaHodov.size()-1);
		if(neSimulator) {
			chered--;
		}
	}

	public void sotriFiguruNa(int x, int y) {
		Figura figura = figury[x][y];

		if (figura != null) {
			figura.remove();
			figury[x][y] = null;
			polya[x][y].osvoboditPole();
		}
	}
	public ArrayList<Hod> poluchitHody(boolean belaya, boolean proverkaNaShah){
		ArrayList<Hod> hody = new ArrayList<Hod>();
		for (Figura[] ryad : figury) {
			for (Figura figuraVRyadu : ryad) {
				if(figuraVRyadu != null && figuraVRyadu.belaya == belaya){
					ArrayList<Hod> testuremyeHody = figuraVRyadu.kudaPoitiIMojetVzyat(this);
					testuremyeHody.addAll(figuraVRyadu.chtoMojnoVzyat(this));
					if(!proverkaNaShah) {
						hody.addAll(testuremyeHody);
					}else{
						boolean poleBezopasno = true;
						for(Hod h : testuremyeHody) {
							poleBezopasno = poleBezopasno(figuraVRyadu, h);
							if (!poleBezopasno) {
								break;
							}
						}
						if(poleBezopasno){
							hody.addAll(testuremyeHody);
						}
					}
				}
			}
		}

		return hody;
	}

	public ArrayList<Hod> poluchitHodyImperatora(boolean belaya, boolean proverkaNaShah){
		Figura imperator=null;
		ArrayList<Hod> hody = new ArrayList<Hod>();
		for (Figura[] ryad : figury) {
			for (Figura figuraVRyadu : ryad) {
				if(figuraVRyadu != null && figuraVRyadu.belaya == belaya && figuraVRyadu instanceof Imperator){
					imperator = figuraVRyadu;
					hody.addAll(figuraVRyadu.kudaPoitiIMojetVzyat(this));
					break;
				}
			}
			if(imperator!=null){
				break;
			}
		}
		if(imperator==null){
			hody.add(new Hod(-1, -1, -1, -1));
			logger.severe("imperator not found");
			return hody;
		}
		if(!proverkaNaShah){
			return hody;
		}
		ArrayList<Hod> proverennyeHody = new ArrayList<Hod>();
		for(Hod hod : hody){
			if(poleBezopasno(imperator, hod)){
				proverennyeHody.add(hod);
			}
		}
		return proverennyeHody;
	}

	public boolean poleBezopasno(Figura figura, Hod hod) {
		boolean svoiCvet = figura.belaya;
		boolean bezopasno = true;

		peredvinytFiguru(hod, false);

		Figura imperator=null;
		for (Figura[] ryad : figury) {
			for (Figura figuraVRyadu : ryad) {
				if (figuraVRyadu != null && figuraVRyadu.belaya == svoiCvet && figuraVRyadu instanceof Imperator) {
					imperator=figuraVRyadu;
					break;
				}
			}
			if(imperator!=null){
				break;
			}
		}
		if(imperator==null){
			logger.severe("Imperator not found");
			anuliruiHod(false);
			return false;
		}

		for (Figura[] ryad : figury) {
			for (Figura figuraVRyadu : ryad) {
				if ((figuraVRyadu != null) && (figuraVRyadu.belaya != svoiCvet)) {
					Set<Pole> obrabotannyeKletki = new HashSet<Pole>();

					if (figuraVRyadu.mojetVzyatPriHode) {
						obrabotannyeKletki.addAll(figuraVRyadu.kudaPoitiIMojetVzyat(this, false));
					} else {
						obrabotannyeKletki.addAll(figuraVRyadu.chtoMojnoVzyat(this, false));
					}
					for (Pole pole : obrabotannyeKletki) {
						if ((imperator.getX() == pole.getX()) && (imperator.getY() == pole.getY())) {
							bezopasno = false;
							break;
						}
					}
					if(!bezopasno){
						break;
					}
				}
			}
			if(!bezopasno){
				break;
			}
		}

		anuliruiHod(false);
		return bezopasno;
	}
}
