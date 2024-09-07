package chess;
import boardgame.board;
public class chessMatch {
    private board board;
    public chessMatch(){
        board = new board(8,8);
    }
    public chessPiece[][] getPieces(){
        chessPiece[][] mat = new chessPiece[board.getRows()][board.getColumns()];
        for(int i=0;i<board.getRows();i++){
            for(int j=0;j<board.getColumns();j++){
                mat[i][j] = (chessPiece) board.piece(i,j);
            }
        }
        return mat;
    }
}
