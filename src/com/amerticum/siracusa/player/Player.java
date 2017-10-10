package com.amerticum.siracusa.player;

import com.amerticum.siracusa.model.Doska;
import com.amerticum.siracusa.model.Hod;

public abstract class Player {

	protected boolean cvet;
	/**
	 * Default constructor
	 * 
	 * @param cvet
	 *            the player's color
	 */
	public Player(boolean cvet) {
		this.cvet = cvet;
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
		return null;
	}

	public boolean poluchitCvet() {
		return cvet;
	}

	public void zadaiCvet(boolean cvet) {
		this.cvet = cvet;
	}
}
