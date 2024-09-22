package chesspieces;
import boardgame.board;
import chess.chessPiece;
import chess.color;
import boardgame.position;
import chess.chessMatch;

public class king extends chessPiece{
   private chessMatch chessMatch;
   public king(board board, color color, chessMatch chessMatch) {
       super(board, color);
       this.chessMatch = chessMatch;
   }

    @Override
    public String toString(){
        return "R";
    }

    private boolean canMove(position position){
        chessPiece p = (chessPiece)getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    private boolean testRookCastling(position position) {
        chessPiece p = (chessPiece)getBoard().piece(position);
        return p != null && p instanceof rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        position p = new position(0,0);

        //acima
        p.setValues(position.getRow()-1, position.getColumn());
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        //abaixo
        p.setValues(position.getRow()+1, position.getColumn());
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        //esquerda
        p.setValues(position.getRow(), position.getColumn()-1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        //direita
        p.setValues(position.getRow(), position.getColumn()+1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        //noroeste
        p.setValues(position.getRow() -1, position.getColumn()-1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        //nordeste
        p.setValues(position.getRow()-1, position.getColumn()+1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        //sudoeste
        p.setValues(position.getRow()+1, position.getColumn()-1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        //sudeste
        p.setValues(position.getRow()+1, position.getColumn()+1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        // #MOVIMENTO ESPECIAL CASTLING
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            // #specialmove castling kingside rook
            position posT1 = new position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posT1)) {
                position p1 = new position(position.getRow(), position.getColumn() + 1);
                position p2 = new position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    mat[position.getRow()][position.getColumn() + 2] = true;
                }
            }
            // #MOVIMENTO ESPECIAL CASTLING QUEENSIDE ROOK
            position posT2 = new position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(posT2)) {
                position p1 = new position(position.getRow(), position.getColumn() - 1);
                position p2 = new position(position.getRow(), position.getColumn() - 2);
                position p3 = new position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
                    mat[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return mat;
    }
}
