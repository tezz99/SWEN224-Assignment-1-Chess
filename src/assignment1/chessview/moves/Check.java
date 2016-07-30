package assignment1.chessview.moves;

import org.eclipse.jdt.annotation.NonNull;

import assignment1.chessview.Board;

/**
 * This represents a "check move". Note that, a check move can only be made up
 * from an underlying simple move; that is, we can't check a check move.
 * 
 * @author djp
 * 
 */
public class Check implements Move {
    private @NonNull MultiPieceMove move;	

    public Check(@NonNull MultiPieceMove move) {
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
	    // actually corresponds to putting the opposition's king into check.

	    // First, copy board and apply underlying move.
	    board = new Board(board);
	    board.apply(move);

	    // Second, check opposition is now in check.
	    return board.isInCheck(!isWhite());
	}
	return false;
    }

    public void apply(@NonNull Board board) {
	move.apply(board);
    }

    public @NonNull String toString() {
	return move.toString() + "+";
    }
}
