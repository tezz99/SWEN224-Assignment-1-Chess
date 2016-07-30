package assignment1.chessview.pieces;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import assignment1.chessview.Board;
import assignment1.chessview.Position;

public class Queen extends PieceImpl implements Piece {

    public Queen(boolean isWhite) {
	super(isWhite);
    }

    public boolean isValidMove(@NonNull Position oldPosition, @NonNull Position newPosition, @Nullable Piece isTaken, @NonNull Board board) {
	Piece p = board.pieceAt(oldPosition);
	Piece t = board.pieceAt(newPosition);
	return this.equals(p)
		&& (t == isTaken || (isTaken != null && isTaken.equals(t)))
		&& (board.clearDiaganolExcept(oldPosition, newPosition, p, t)
			|| board.clearColumnExcept(oldPosition, newPosition, p,
				t) || board.clearRowExcept(oldPosition,
					newPosition, p, t));
    }

    public @NonNull String toString() {
	if(isWhite) {
	    return "Q";
	} else {
	    return "q";
	}
    }
}
