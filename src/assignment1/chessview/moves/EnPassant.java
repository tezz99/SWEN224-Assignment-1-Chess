package assignment1.chessview.moves;

import assignment1.chessview.*;
import assignment1.chessview.pieces.*;

/**
 * This represents an "en passant move" --- http://en.wikipedia.org/wiki/En_passant.
 * 
 * @author djp
 * 
 */
public class EnPassant extends SinglePieceTake {
	public EnPassant(SinglePieceMove move) {
		super(move.piece(),new Pawn(!move.piece().isWhite()),move.oldPosition(),move.newPosition());
	}
	
	@Override
	public boolean isValid(Board board) {				
		// First, check new and old positions are valid
		if(!checkValidPositions()) { return false; }		
		// Second, check valid piece is being taken		
		if(!checkValidPieceTaken(board)) { return false; }				
		// Finally, check piece being taken did the double step		
		Pawn takenPawn = (Pawn) board.pieceAt(getTakenPosition());		
		return takenPawn.wasDoubleStep();				
	}
	
	@Override
	public void apply(Board board) {						
		board.move(oldPosition, newPosition);
		board.setPieceAt(getTakenPosition(), null);
	}
	
	public String toString() {
		return super.toString() + "ep";
	}
	
	/**
	 * Check that a valid piece is taken (i.e. it's a pawn, it's the right
	 * colour, etc)
	 * 
	 * @param board
	 * @return
	 */
	private boolean checkValidPieceTaken(Board board) {
		if (board.pieceAt(newPosition) != null) {
			// Cannot have piece at position we're moving to
			return false;
		} else {
			Position takePosition = getTakenPosition();
			Piece takenPiece = board.pieceAt(takePosition);
			if (takenPiece == null || takenPiece.isWhite() == piece.isWhite() || !(takenPiece instanceof Pawn)) {
				// we're not taking a valid piece
				return false;
			}
		}
		return true;
	}

	/**
	 * Check new and old positions are valid
	 * 
	 * @return
	 */
	private boolean checkValidPositions() {
		int dir = piece.isWhite() ? 1 : -1;
		int finalRow = piece.isWhite() ? 6 : 3;
		if(newPosition.row() != finalRow) {
			// Invalid final position
			return false;
		} else if (newPosition.row() != (oldPosition.row() + dir)) {
			// Invalid new position row			
			return false;
		} else if (newPosition.column() != (oldPosition.column() + 1)
				&& newPosition.column() != (oldPosition.column() - 1)) {
			// Invalid new position column						
			return false;
		}
		//
		return true;
	}

	/**
	 * Calculate position of piece being taken
	 */
	private Position getTakenPosition() {
		int dir = piece.isWhite() ? -1 : 1;
		return new Position(newPosition.row()+dir,newPosition.column());
	}
}
