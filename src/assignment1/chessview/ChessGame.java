package assignment1.chessview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import assignment1.chessview.moves.Castling;
import assignment1.chessview.moves.Check;
import assignment1.chessview.moves.EnPassant;
import assignment1.chessview.moves.Move;
import assignment1.chessview.moves.MultiPieceMove;
import assignment1.chessview.moves.NonCheck;
import assignment1.chessview.moves.PawnPromotion;
import assignment1.chessview.moves.SinglePieceMove;
import assignment1.chessview.moves.SinglePieceTake;
import assignment1.chessview.pieces.Bishop;
import assignment1.chessview.pieces.King;
import assignment1.chessview.pieces.Knight;
import assignment1.chessview.pieces.Pawn;
import assignment1.chessview.pieces.Piece;
import assignment1.chessview.pieces.Queen;
import assignment1.chessview.pieces.Rook;

/**
 * This class represents a game of chess, which is essentially just a list moves
 * that make up the game.
 * 
 * @author djp
 * 
 */
public class ChessGame {
    private @NonNull ArrayList<Round> rounds;

    public ChessGame(@NonNull String sheet) throws IOException {
	this(new StringReader(sheet));
    }

    /**
     * Construct a ChessGame object from a given game sheet, where each round
     * occurs on a new line.
     * 
     * @param gameSheet
     */
    public ChessGame(@NonNull Reader input) throws IOException {
	rounds = new ArrayList<>();

	BufferedReader reader = new BufferedReader(input);

	// First, read in the commands
	String line;		
	while((line = reader.readLine()) != null) {
	    if(line.equals("")) { continue; } // skip blank lines
	    int pos = line.indexOf(' ');
	    if(pos == -1) { pos = line.length(); }


	    String whiteMoveString = line.substring(0,pos);
	    assert whiteMoveString != null; //Ensure that the white move string is not equal to null.
	    Move white = moveFromString(whiteMoveString,true);

	    Move black = null;
	    if(pos != line.length()) {
		String blackMoveString = line.substring(pos+1);
		assert blackMoveString != null; //Ensure that black move string is not equal to null.
		black = moveFromString(blackMoveString, false);
	    }
	    rounds.add(new Round(white,black));
	}
    }

    public @NonNull List<Round> rounds() {
	return rounds;
    }

    /**
     * This method computes the list of boards which make up the game. If an
     * invalid move, or board is encountered then a RuntimeException is thrown.
     * 
     * @return
     */
    public @NonNull List<Board> boards() {
	ArrayList<Board> boards = new ArrayList<>();
	Board b = new Board();
	boards.add(b);
	boolean lastTime = false;
	for(Round r : rounds) {		
	    if (lastTime) { return boards; }
	    b = new Board(b);	

	    Move whiteMove = r.white();
	    assert whiteMove != null; //Ensure that white move is not equal to null.

	    if(!b.apply(whiteMove)) { return boards; }
	    boards.add(b);


	    if(r.black() != null) {
		Move blackMove = r.black();
		assert blackMove != null; //Ensure that black move is not equal to null.

		b = new Board(b);
		if(!b.apply(blackMove)) { return boards; }
		boards.add(b);				
	    } else {
		lastTime = true;
	    }
	}		
	return boards;
    }

    /**
     * Construct a move object from a given string.
     * 
     * @param str
     * @return
     */
    private static @NonNull Move moveFromString(@NonNull String str, boolean isWhite) {
	Piece piece;		
	int index = 0;
	char lookahead = str.charAt(index);

	switch(lookahead) {
	case 'N':
	    piece = new Knight(isWhite);
	    index++;
	    break;
	case 'B':
	    piece = new Bishop(isWhite);
	    index++;
	    break;
	case 'R':
	    piece = new Rook(isWhite);
	    index++;
	    break;
	case 'K':
	    piece = new King(isWhite);
	    index++;
	    break;
	case 'Q':
	    piece = new Queen(isWhite);
	    index++;
	    break;
	case 'O':
	    if(str.equals("O-O")) {
		return new Castling(isWhite,true);
	    } else if(str.equals("O-O-O")) {
		return new Castling(isWhite,false);
	    } else if(str.equals("O-O+")) {
		return new Check(new Castling(isWhite,true));
	    } else if(str.equals("O-O-O+")) {
		return new Check(new Castling(isWhite,false));
	    } else {
		throw new IllegalArgumentException("invalid sheet");
	    }
	default:
	    piece = new Pawn(isWhite);
	}

	//Ensures null is not passed to positionFromString method.
	String startPosString = str.substring(index,index+2);
	assert startPosString != null; 

	Position start = positionFromString(startPosString);
	char moveType = str.charAt(index+2);
	Piece target = null;
	index = index + 3;		

	if(moveType == 'x') {
	    lookahead = str.charAt(index);
	    switch(lookahead) {
	    case 'N':
		target = new Knight(!isWhite);
		index++;
		break;
	    case 'B':
		target = new Bishop(!isWhite);
		index++;
		break;
	    case 'R':
		target = new Rook(!isWhite);
		index++;
		break;
	    case 'K':
		target = new King(!isWhite);
		index++;
		break;
	    case 'Q':
		target = new Queen(!isWhite);
		index++;
		break;
	    default:
		target = new Pawn(!isWhite); 
	    }
	} else if(moveType != '-') {
	    throw new IllegalArgumentException("invalid sheet");
	}

	//Ensures null is not passed to positionFromString method.
	String endPosString = str.substring(index,index+2);
	assert endPosString != null; 

	Position end = positionFromString(endPosString);
	index = index + 2;

	Move move;

	if(target != null) {
	    move = new SinglePieceTake(piece,target,start,end);
	} else {
	    move = new SinglePieceMove(piece,start,end);
	}

	if((index+1) < str.length() && str.charAt(index) == 'e' && str.charAt(index+1) == 'p') {
	    move = new EnPassant((SinglePieceMove) move);
	    index+=2;
	} else if((index+1) < str.length() && str.charAt(index)=='=') {
	    lookahead = str.charAt(index+1);
	    Piece promotion;
	    switch(lookahead) {
	    case 'N':
		promotion = new Knight(isWhite);					
		break;
	    case 'B':
		promotion = new Bishop(isWhite);					
		break;
	    case 'R':
		promotion = new Rook(isWhite);					
		break;
	    case 'K':
		promotion = new King(isWhite);					
		break;
	    case 'Q':
		promotion = new Queen(isWhite);					
		break;
	    default:
		throw new IllegalArgumentException("invalid sheet");
	    }
	    move = new PawnPromotion((SinglePieceMove) move,promotion);
	    index+=2;
	}

	if(index < str.length() && str.charAt(index) == '+') {
	    move = new Check((MultiPieceMove) move);
	    index++;
	} else {
	    move = new NonCheck((MultiPieceMove) move);
	}

	if(index != str.length()) {
	    throw new IllegalArgumentException("invalid sheet");
	}					

	return move;
    }

    private static @NonNull Position positionFromString(@NonNull String pos) {
	if(pos.length() != 2) {
	    throw new IllegalArgumentException("invalid position: " + pos);
	}
	int col = (pos.charAt(0) - 'a') + 1;
	int row = Integer.parseInt(pos.substring(1,2));		
	return new Position(row,col);
    }
}
