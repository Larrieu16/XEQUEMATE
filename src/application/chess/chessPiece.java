package chess;
import boardgame.board;
import boardgame.piece;

public abstract class chessPiece extends piece {
    private color color;

    public chessPiece(board board, color color) {
        super(board);
        this.color = color;
    }
    public chess.color getColor() {
        return color;
    }

}
