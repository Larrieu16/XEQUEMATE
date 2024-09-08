package chesspieces;

import boardgame.board;
import chess.chessPiece;
import chess.color;

public class rook extends chessPiece {


    public rook(board board, color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return " R";
    }
}
