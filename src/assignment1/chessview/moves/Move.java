package assignment1.chessview.moves;

import org.eclipse.jdt.annotation.NonNull;

import assignment1.chessview.Board;

/**
 * A move is any move which is permitted by either the white or black player.
 * This includes simple moves (where pieces just take on new positions), take
 * moves (where a piece is taken as well), and check moves (where the opponent
 * is put into check)
 * 
 * @author djp
 * 
 */
public interface Move {
    /**
     * Check whether this move is valid or not.
     * 
     * @param board
     * @return
     */
    public boolean isValid(@NonNull Board board);

    /**
     * Update the board to reflect the board after the move is played.
     * 
     * @param board
     */
    public void apply(@NonNull Board board);

    /**
     * Is this move for white or black?
     * 
     * @return
     */
    public boolean isWhite();
}
