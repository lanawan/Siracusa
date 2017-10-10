package com.amerticum.siracusa.player;

import com.amerticum.siracusa.model.Doska;
import com.amerticum.siracusa.model.Hod;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends Player {
	Random rand;
	
	/**
	 * @param cvet
	 */
	public RandomPlayer(boolean cvet) {
		super(cvet);
		rand = new Random();
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
		int n = hody.size();
		
		if(n == 0) {
			return null;
		}
		int k = rand.nextInt(n);
		//k = 1; // TODO remove
		return hody.get(k);
	}

}
