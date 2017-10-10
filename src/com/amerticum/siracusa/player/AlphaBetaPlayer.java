package com.amerticum.siracusa.player;

import com.amerticum.siracusa.ai.MinimaxAlphaBeta;
import com.amerticum.siracusa.model.Doska;
import com.amerticum.siracusa.model.Hod;

public class AlphaBetaPlayer extends Player {
	MinimaxAlphaBeta minimax;
	
	/**
	 * @param cvet
	 */
	public AlphaBetaPlayer(boolean cvet, int maxDepth) {
		super(cvet);
		minimax = new MinimaxAlphaBeta(cvet, maxDepth);
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
		Hod hod = minimax.decision(doska);
		return hod;
	}

}
