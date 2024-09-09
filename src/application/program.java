import boardgame.board;
import boardgame.position;
import chess.chessMatch;
import chess.chessPiece;
import chess.chessPosition;
import java.util.Scanner;

public class program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        chessMatch chessMatch = new chessMatch();

        while (true) {
            UI.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.print("Fonte: ");
            chessPosition source = UI.readChessPosition(sc);

            System.out.println();
            System.out.print("Alvo: ");
            chessPosition target = UI.readChessPosition(sc);

            chessPiece capturedPiece = chessMatch.performChessMove(source, target);
         }
    }
}