package chess;
import boardgame.board;
import boardgame.position;
import chesspieces.king;
import chesspieces.rook;

public class chessMatch {
    private board board;
    public chessMatch(){
        board = new board(8,8);
        initialSetup();
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

    private void placeNewPiece(char column, int row, chessPiece piece){
        board.placePiece(piece, new chessPosition(column, row).toPosition());
    }

    private void initialSetup(){
        placeNewPiece('b',6, new rook(board, color.WHITE));
        placeNewPiece('e',8, new king(board, color.BLACK));
        placeNewPiece('e',1, new king(board, color.WHITE));
    }
}
