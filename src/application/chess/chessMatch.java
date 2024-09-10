package chess;
import boardgame.board;
import boardgame.piece;
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

    public boolean[][] possibleMoves(chessPosition sourcePosition) {
        position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }


    public chessPiece performChessMove(chessPosition sourcePosition, chessPosition targetPosition){
        position source = sourcePosition.toPosition();
        position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source,target);
        piece capturedPiecce = makeMove(source,target);
        return (chessPiece)capturedPiecce;
    }

    private piece makeMove(position source, position target){
        piece p = board.removePiece(source);
        piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        return capturedPiece;
    }

    private void validateSourcePosition(position position){
        if(!board.thereIsAPiece(position)){
            throw new chessException("Não existe uma peça nessa nessa posição.");
        }
        if(!board.piece(position).isThereAnyPossibleMove()){
            throw new chessException("Não existe movimento possíveis para a peça escolhida.");
        }
    }
    private void validateTargetPosition(position source, position target){
        if(!board.piece(source).possibleMove(target)){
            throw new chessException("A peça escolhida não pode se mover para o destino escolhido.");
        }
    }


    private void placeNewPiece(char column, int row, chessPiece piece){
        board.placePiece(piece, new chessPosition(column, row).toPosition());
    }

    private void initialSetup(){
        placeNewPiece('c', 1, new rook(board, color.WHITE));
        placeNewPiece('c', 2, new rook(board, color.WHITE));
        placeNewPiece('d', 2, new rook(board, color.WHITE));
        placeNewPiece('e', 2, new rook(board, color.WHITE));
        placeNewPiece('e', 1, new rook(board, color.WHITE));
        placeNewPiece('d', 1, new king(board, color.WHITE));

        placeNewPiece('c', 7, new rook(board, color.BLACK));
        placeNewPiece('c', 8, new rook(board, color.BLACK));
        placeNewPiece('d', 7, new rook(board, color.BLACK));
        placeNewPiece('e', 7, new rook(board, color.BLACK));
        placeNewPiece('e', 8, new rook(board, color.BLACK));
        placeNewPiece('d', 8, new king(board, color.BLACK));
    }


}
