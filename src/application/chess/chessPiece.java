package chess;
import boardgame.board;
import boardgame.piece;
import boardgame.position;

public abstract class chessPiece extends piece {
    private color color;

    public chessPiece(board board, color color) {
        super(board);
        this.color = color;
    }
    public chess.color getColor() {
        return color;
    }

    protected boolean isThereOpponentPiece(position position) {
        chessPiece p = (chessPiece)getBoard().piece(position);
        return p != null && p.getColor() != color;
    }
}
