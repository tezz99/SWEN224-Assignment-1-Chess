package assignment1.chessview.moves;

import assignment1.chessview.Board;
import assignment1.chessview.Position;
import assignment1.chessview.pieces.King;
import assignment1.chessview.pieces.Piece;
import assignment1.chessview.pieces.Rook;

public class Castling implements MultiPieceMove {
    private boolean isWhite;
    private boolean kingSide;

    public Castling(boolean isWhite, boolean kingSide) {
	this.isWhite = isWhite;
	this.kingSide = kingSide;
    }

    public boolean isWhite() {
	return isWhite;
    }

    public void apply(Board board) {
	int row = isWhite ? 1 : 8;
	int rcol = kingSide ? 8 : 1;
	int rncol = kingSide ? 6 : 4;
	int kncol = kingSide ? 7 : 3;

	Position kpos = new Position(row,5);
	Position knpos = new Position(row,kncol);
	Position rpos = new Position(row,rcol);
	Position rnpos = new Position(row,rncol);

	board.move(kpos, knpos);
	board.move(rpos, rnpos);
    }

    public boolean isValid(Board board) {
	int row = isWhite ? 1 : 8;
	int col = kingSide ? 8 : 1;
	Position kpos = new Position(row,5);
	Position rpos = new Position(row,col);
	Piece king = board.pieceAt(kpos);
	Piece rook = board.pieceAt(rpos);

	return king != null && rook != null && king.equals(new King(isWhite))
		&& rook.equals(new Rook(isWhite))
		&& board.clearRowExcept(kpos, rpos, king, rook)
		&& !board.kingMoved(isWhite)
		&& !board.rookMoved(isWhite,kingSide);
    }		

    public String toString() {
	if(kingSide) {
	    return "O-O";
	} else {
	    return "O-O-O";
	}
    }
}
