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
public class PawnPromotion implements MultiPieceMove {
	private Piece promotion;
	private SinglePieceMove move;
	
	public PawnPromotion(SinglePieceMove move, Piece promotion) {		
		this.promotion = promotion;
		this.move = move;
	}
	
	public boolean isWhite() {
		return move.isWhite();
	}
	
	public boolean isValid(Board board) {				
		int row = isWhite() ? 8 : 1;		
		return move.isValid(board) && move.piece() instanceof Pawn
				&& move.newPosition.row() == row;
	}
	
	public void apply(Board board) {
		move.apply(board);
		board.setPieceAt(move.newPosition(), promotion);
	}
	
	public String toString() {
		return super.toString() + "=" + SinglePieceMove.pieceChar(promotion);
	}
}
