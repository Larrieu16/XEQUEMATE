package boardgame;
import boardgame.piece;

public class board {
    private int rows;
    private int columns;
    private piece[][] pieces;

    public board(int rows, int columns) {
        if(rows<1 || columns < 1){
            throw new boardException("Erro criando a coluna: É necessário ao menos 1 linha e 1 coluna.");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new piece[rows][columns];

    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public piece piece(int row, int column) {
        if(!positionExists(row, column)){
            throw new boardException("Posição não está no tabuleiro.");
        }
        return pieces[row][column];
    }
    public piece piece(position position){
        if(!positionExists(position)){
            throw new boardException("Posição não está no tabuleiro.");
        }
        return pieces[position.getRow()][position.getColumn()];

    }
    public void placePiece(piece piece, position position){
        if (thereIsAPiece(position)){
            throw new boardException("Já existe uma peça nessa posição " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    private boolean positionExists(int row, int column){
       return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(position position){
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(position position){
        if(!positionExists(position)){
            throw new boardException("Posição não está no tabuleiro.");
        }
        return piece(position) != null;
    }
}
