package assignment1.chessview.moves;

import assignment1.chessview.*;
import assignment1.chessview.pieces.*;

/**
 * This represents a "check move". Note that, a check move can only be made up
 * from an underlying simple move; that is, we can't check a check move.
 * 
 * @author djp
 * 
 */
public class Check implements Move {
	private MultiPieceMove move;	
	
	public Check(MultiPieceMove move) {
		this.move = move;		
	}
	
	public MultiPieceMove move() {
		return move;
	}
	
	public boolean isWhite() {
		return move.isWhite();
	}
	
	public boolean isValid(Board board) {		
		if (move.isValid(board)) {
			// Ok, underlying move is valid; now, we need to check that it
			// actually corresponds to putting the opposition's king into check.

			// First, copy board and apply underlying move.
			board = new Board(board);
			board.apply(move);
						
			// Second, check opposition is now in check.
			return board.isInCheck(!isWhite());
		}
		return false;
	}
	
	public void apply(Board board) {
		move.apply(board);
	}
	
	public String toString() {
		return move.toString() + "+";
	}
}
