package chess;

import boardgame.position;

public class chessPosition {
    private char column;
    private int row;

    public chessPosition(char column, int row) {
        if(column < 'a' || column > 'h' || row < 1 || row > 8){
            throw new chessException("Erro instanciando chessPosition. Valores válidos são de a1 para h8.");
        }
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }
    protected position toPosition(){
        return new position(8-row,column-'a');
    }
    protected static chessPosition fromPosition(position position){
        return new chessPosition((char)('a'- position.getColumn()), 8- position.getRow());
    }

    @Override
    public String toString(){
        return "" + column + row;
    }
}
