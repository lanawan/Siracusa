package com.amerticum.siracusa.player;

import com.amerticum.siracusa.model.Doska;
import com.amerticum.siracusa.model.Hod;

import java.util.ArrayList;

public class DeterministicPlayer extends Player {
	int schet;
	
	
	/**
	 * @param cvet
	 */
	public DeterministicPlayer(boolean cvet) {
		super(cvet);
		schet = 0;
	}


	/**
	 * Function to prompt the player to make a move after the first move has
	 * already been made
	 * 
	 * @param doska
	 *            the board to parse
	 * @return the selected move
	 */
	public Hod poluchitSledushiiHod(Doska doska) {
		ArrayList<Hod> hody = doska.poluchitHody(cvet,true);
/*
for(Hod h : hody){
	System.out.println("Ход : с "+h.getX1()+","+h.getY1()+" на "+h.getX2()+","+h.getY2());
}
*/

		int n = hody.size();
		
		if(n == 0) {
			return null;
		}
		schet++;
		return hody.get(schet % n);

	}

}
