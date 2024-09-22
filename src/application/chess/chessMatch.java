package chess;
import boardgame.board;
import boardgame.piece;
import boardgame.position;
import chesspieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class chessMatch {
    private int turn;
    private color currentPlayer;
    private List<piece> piecesOnTheBoard = new ArrayList<>();
    private List<piece> capturedPieces = new ArrayList<>();
    private board board;

    private boolean checkMate;
    private boolean check;
    private chessPiece enPassantVulnerable;

    public chessMatch(){
        turn = 1;
        currentPlayer = color.BRANCO;
        board = new board(8,8);
        piecesOnTheBoard = new ArrayList<>();
        initialSetup();

    }

    public int getTurn(){
        return turn;
    }

    public color getCurrentPlayer(){
        return currentPlayer;
    }

    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
    }

    public chessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
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

        if(testCheck(currentPlayer)){
            undoMove(source, target, capturedPiecce);
            throw new chessException("Você não pode se colocar em check.");
        }

        chessPiece movedPiece = (chessPiece)board.piece(target);

        check = (testCheck(opponent(currentPlayer))) ? true:false;


        if(testCheckMate(opponent(currentPlayer))){
            checkMate = true;
        }
        else{
            nextTurn();
        }

        // #MOVIMENTO ESPECIAL EN PASSANT
        if (movedPiece instanceof pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        }
        else {
            enPassantVulnerable = null;
        }

        return (chessPiece)capturedPiecce;
    }

    private piece makeMove(position source, position target){
        chessPiece p = (chessPiece)board.removePiece(source);
        p.increaseMoveCount();
        piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if(capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);

        }

        // #MOVIMENTO ESPECIAL CASTLING KINGSIDE ROOK
        if (p instanceof king && target.getColumn() == source.getColumn() + 2) {
            position sourceT = new position(source.getRow(), source.getColumn() + 3);
            position targetT = new position(source.getRow(), source.getColumn() + 1);
            chessPiece rook = (chessPiece)board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }
        // #MOVIMENTO ESPECIAL CASTLING QUEENSIDE ROOK
        if (p instanceof king && target.getColumn() == source.getColumn() - 2) {
            position sourceT = new position(source.getRow(), source.getColumn() - 4);
            position targetT = new position(source.getRow(), source.getColumn() - 1);
            chessPiece rook = (chessPiece)board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // #specialmove en passant
        if (p instanceof pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                position pawnPosition;
                if (p.getColor() == color.BRANCO) {
                    pawnPosition = new position(target.getRow() + 1, target.getColumn());
                }
                else {
                    pawnPosition = new position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    private void validateSourcePosition(position position){
        if(!board.thereIsAPiece(position)){
            throw new chessException("Não existe uma peça nessa nessa posição.");
        }
        if (currentPlayer != ((chessPiece)board.piece(position)).getColor()){
            throw new chessException("A peça escolhida não é sua.");
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

    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer ==  color.BRANCO) ? color.PRETO : color.BRANCO;
    }

    private void undoMove(position source, position target, piece capturedPiece){
        chessPiece p = (chessPiece)board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);
        if(capturedPiece != null){
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        // #MOVIMENTO ESPECIAL CASTLING KINGSIDE ROOK
        if (p instanceof king && target.getColumn() == source.getColumn() + 2) {
            position sourceT = new position(source.getRow(), source.getColumn() + 3);
            position targetT = new position(source.getRow(), source.getColumn() + 1);
            chessPiece rook = (chessPiece)board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }
        // #MOVIMENTO ESPECIAL CASTLING QUEENSIDE ROOK
        if (p instanceof king && target.getColumn() == source.getColumn() - 2) {
            position sourceT = new position(source.getRow(), source.getColumn() - 4);
            position targetT = new position(source.getRow(), source.getColumn() - 1);
            chessPiece rook = (chessPiece)board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }
        if (p instanceof pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                chessPiece pawn = (chessPiece)board.removePiece(target);
                position pawnPosition;
                if (p.getColor() == color.BRANCO) {
                    pawnPosition = new position(3, target.getColumn());
                }
                else {
                    pawnPosition = new position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
        }
    }


    private color opponent(color color){
        return(color == color.BRANCO) ? color.PRETO : color.BRANCO;
    }

    private chessPiece king(color color){
        List<piece> list = piecesOnTheBoard.stream().filter(x -> ((chessPiece)x).getColor() == color).collect(Collectors.toList());
        for (piece p : list){
            if(p instanceof king){
                return (chessPiece) p;
            }
        }
        throw new IllegalStateException("Não existe um rei "+color+" nesse tabuleiro. ERRO FATAL");
    }

    private boolean testCheck(color color){
        position kingPosition = king(color).getChessPosition().toPosition();
        List<piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((chessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (piece p: opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if(mat[kingPosition.getRow()][kingPosition.getColumn()]){
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(color color){
        if(!testCheck(color)){
            return false;
        }
        List<piece> list =piecesOnTheBoard.stream().filter(x -> ((chessPiece)x).getColor() == color).collect(Collectors.toList());
        for(piece p:list){
            boolean[][] mat = p.possibleMoves();
            for(int i=0; i<board.getRows(); i++){
                for(int j=0; j<board.getColumns(); j++){
                    if(mat[i][j]){
                        position source = ((chessPiece)p).getChessPosition().toPosition();
                        position target = new position(i,j);
                        piece capturedPiece = makeMove(source,target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if(! testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char column, int row, chessPiece piece){
        board.placePiece(piece, new chessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);

    }

    private void initialSetup(){
        placeNewPiece('a', 1, new rook(board, color.BRANCO));
        placeNewPiece('h', 1, new rook(board, color.BRANCO));
        placeNewPiece('b', 1, new knight(board, color.BRANCO));
        placeNewPiece('g', 1, new knight(board, color.BRANCO));
        placeNewPiece('c', 1, new bishop(board, color.BRANCO));
        placeNewPiece('f', 1, new bishop(board, color.BRANCO));
        placeNewPiece('d', 1, new queen(board, color.BRANCO));
        placeNewPiece('e', 1, new king(board, color.BRANCO, this));
        placeNewPiece('a', 2, new pawn(board, color.BRANCO,this));
        placeNewPiece('b', 2, new pawn(board, color.BRANCO,this));
        placeNewPiece('c', 2, new pawn(board, color.BRANCO,this));
        placeNewPiece('d', 2, new pawn(board, color.BRANCO,this));
        placeNewPiece('e', 2, new pawn(board, color.BRANCO,this));
        placeNewPiece('f', 2, new pawn(board, color.BRANCO,this));
        placeNewPiece('g', 2, new pawn(board, color.BRANCO,this));
        placeNewPiece('h', 2, new pawn(board, color.BRANCO,this));


        placeNewPiece('a', 8, new rook(board, color.PRETO));
        placeNewPiece('h', 8, new rook(board, color.PRETO));
        placeNewPiece('b', 8, new knight(board, color.PRETO));
        placeNewPiece('g', 8, new knight(board, color.PRETO));
        placeNewPiece('c', 8, new bishop(board, color.PRETO));
        placeNewPiece('f', 8, new bishop(board, color.PRETO));
        placeNewPiece('e', 8, new king(board, color.PRETO, this));
        placeNewPiece('a', 7, new pawn(board, color.PRETO,this));
        placeNewPiece('b', 7, new pawn(board, color.PRETO,this));
        placeNewPiece('c', 7, new pawn(board, color.PRETO,this));
        placeNewPiece('d', 7, new pawn(board, color.PRETO,this));
        placeNewPiece('d', 8, new queen(board, color.PRETO));
        placeNewPiece('e', 7, new pawn(board, color.PRETO,this));
        placeNewPiece('f', 7, new pawn(board, color.PRETO,this));
        placeNewPiece('g', 7, new pawn(board, color.PRETO,this));
        placeNewPiece('h', 7, new pawn(board, color.PRETO,this));
    }


}
