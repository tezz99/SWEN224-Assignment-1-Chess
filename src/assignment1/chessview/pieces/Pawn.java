package assignment1.chessview.pieces;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import assignment1.chessview.Board;
import assignment1.chessview.Position;

public class Pawn extends PieceImpl implements Piece {

    private boolean wasDoubleStep; // remember whether took double step or not.

    public Pawn(boolean isWhite) {
	super(isWhite);
    }

    public boolean isValidMove(@NonNull Position oldPosition, @NonNull Position newPosition, @Nullable Piece isTaken, @NonNull Board board) {
	int dir = isWhite ? 1 : -1;
	int oldRow = oldPosition.row();
	int oldCol = oldPosition.column();
	int newRow = newPosition.row();
	int newCol = newPosition.column();

	Piece p = board.pieceAt(oldPosition);
	Piece t = board.pieceAt(newPosition);

	// this logic is more complex than for other pieces, since there is a
	// difference between a take and non-take move for pawns.

	if (isTaken != null) {			
	    return this.equals(p) && isTaken.equals(t)
		    && (oldCol == (newCol + 1) || oldCol == (newCol - 1))
		    && (oldRow + dir) == newRow;
	} else if ((oldRow + dir) == newRow && oldCol == newCol) {
	    return this.equals(p) && t == null;
	} else if ((oldRow + dir + dir) == newRow && oldCol == newCol) {
	    return ((dir == 1 && oldRow == 2) || (dir == -1 && oldRow == 7))
		    && board.pieceAt(new Position(oldRow + dir, oldCol)) == null
		    && t == null && this.equals(p);
	}
	return false;
    }	

    /**
     * Return true if the last move made by this piece was a double step.
     * 
     * @return
     */
    public boolean wasDoubleStep() {
	return wasDoubleStep;
    }

    public void setDoubleStep(boolean flag) {
	wasDoubleStep = flag;
    }

    public @NonNull String toString() {
	if(isWhite) {
	    return "P";
	} else {
	    return "p";
	}
    }
}
