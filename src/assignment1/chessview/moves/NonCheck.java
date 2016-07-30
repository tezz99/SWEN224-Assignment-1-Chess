package assignment1.chessview.moves;

import assignment1.chessview.*;
import assignment1.chessview.pieces.*;

public class NonCheck implements Move {
	private MultiPieceMove move;	
	
	public NonCheck(MultiPieceMove move) {
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
			
			// Second, check opposition is not in check.
			return board != null && !board.isInCheck(!isWhite());			
		}
		return false;
	}
	
	public void apply(Board board) {
		move.apply(board);
	}
	
	public String toString() {
		return move.toString();
	}
}
