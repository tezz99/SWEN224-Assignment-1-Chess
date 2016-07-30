package assignment1.chessview.moves;

import org.eclipse.jdt.annotation.NonNull;


/**
 * A MultiPieceMove represents a simple move operation involving one or more
 * pieces.
 * 
 * @author djp
 * 
 */
public interface MultiPieceMove extends Move {	
    public @NonNull String toString();
}
