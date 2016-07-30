package assignment1.chessview.moves;

import org.eclipse.jdt.annotation.NonNull;

import assignment1.chessview.Board;

public class NonCheck implements Move {
    private @NonNull MultiPieceMove move;	

    public NonCheck(@NonNull MultiPieceMove move) {
	this.move = move;		
    }

    public @NonNull MultiPieceMove move() {
	return move;
    }

    public boolean isWhite() {
	return move.isWhite();
    }

    public boolean isValid(@NonNull Board board) {		
	if (move.isValid(board)) {
	    // Ok, underlying move is valid; now, we need to check that it
	    // actuallys corresponds to putting the opposition's king into check.

	    // First, copy board and apply underlying move.			
	    board = new Board(board);
	    board.apply(move);			

	    // Second, check opposition is not in check.
	    return board != null && !board.isInCheck(!isWhite());			
	}
	return false;
    }

    public void apply(@NonNull Board board) {
	move.apply(board);
    }

    public @NonNull String toString() {
	return move.toString();
    }
}
