import boardgame.board;
import boardgame.position;
import chess.chessMatch;

public class program {
    public static void main(String[] args) {
        chessMatch chessMatch = new chessMatch();
        UI.printBoard(chessMatch.getPieces());
    }
}