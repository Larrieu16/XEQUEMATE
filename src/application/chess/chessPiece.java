package chess;
import boardgame.board;
import boardgame.piece;
import boardgame.position;

public abstract class chessPiece extends piece {
    private color color;
    private int moveCount;

    public chessPiece(board board, color color) {
        super(board);
        this.color = color;
    }
    public chess.color getColor() {
        return color;
    }
    public int getMoveCount(){
        return moveCount;
    }

    public void increaseMoveCount(){
        moveCount++;
    }

    public void decreaseMoveCount(){
        moveCount--;
    }

    public chessPosition getChessPosition(){
        return chessPosition.fromPosition(position);
    }

    protected boolean isThereOpponentPiece(position position) {
        chessPiece p = (chessPiece)getBoard().piece(position);
        return p != null && p.getColor() != color;
    }
}
