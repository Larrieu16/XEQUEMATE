package chesspieces;
import boardgame.board;
import chess.chessPiece;
import chess.color;

public class king extends chessPiece{
    public king(board board,color color){
        super(board,color);
    }

    @Override
    public String toString(){
        return " K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean [getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }
}
