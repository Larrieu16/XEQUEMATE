import boardgame.board;
import boardgame.position;
import chess.chessMatch;
import chess.chessPiece;
import chess.chessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;
import chess.chessException;

public class program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        chessMatch chessMatch = new chessMatch();

        while (true) {
            try {
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces());
                System.out.println();
                System.out.print("Fonte: ");
                chessPosition source = UI.readChessPosition(sc);

                System.out.println();
                System.out.print("Alvo: ");
                chessPosition target = UI.readChessPosition(sc);

                chessPiece capturedPiece = chessMatch.performChessMove(source, target);
            }
            catch (chessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();

            }
            catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                    sc.nextLine();
                    
            }
        }
    }
}