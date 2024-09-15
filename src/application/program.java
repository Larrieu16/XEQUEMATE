import boardgame.board;
import boardgame.position;
import chess.chessMatch;
import chess.chessPiece;
import chess.chessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import chess.chessException;

public class program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        chessMatch chessMatch = new chessMatch();
        List<chessPiece> captured = new ArrayList<>();



        while (true) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, captured);
                System.out.println();
                System.out.print("Fonte: ");
                chessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);
                System.out.println();
                System.out.print("Alvo: ");
                chessPosition target = UI.readChessPosition(sc);

                chessPiece capturedPiece = chessMatch.performChessMove(source, target);
                if(capturedPiece != null){
                    captured.add(capturedPiece);
                }

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