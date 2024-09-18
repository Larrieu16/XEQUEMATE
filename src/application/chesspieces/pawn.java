package chesspieces;
import boardgame.board;
import boardgame.position;
import chess.chessPiece;
import chess.color;

public class pawn extends chessPiece{
    public pawn(boardgame.board board, chess.color color){
        super(board,color);
    }

    public boolean[][] possibleMoves(){
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        boardgame.position p = new position(0, 0);

        if(getColor() == color.BRANCO){
            p.setValues(position.getRow() -1, position.getColumn());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() -2, position.getColumn());
            position p2 = new position(position.getRow()-1, position.getColumn());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount()==0){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() -1, position.getColumn()-1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() -1, position.getColumn()+1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
        }
        else{
            p.setValues(position.getRow() +1, position.getColumn());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() +2, position.getColumn());
            position p2 = new position(position.getRow()+1, position.getColumn());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount()==0){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() +1, position.getColumn()-1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() +1, position.getColumn()+1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }

        }

        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }



}
