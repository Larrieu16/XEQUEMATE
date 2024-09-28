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



        while (!chessMatch.getCheckMate()) {
            try {
                System.out.println("Bem vindo ao jogo de xadrez! ");
                System.out.println("Coloque as posições em caixa baixa(Low Case).");
                System.out.println("Peças: (P= Peão; T= Torre; R= Rei; RA= Rainha; B= Bispo; C= Cavalo;) ");
                System.out.println();

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

                if (chessMatch.getPromoted() != null) {
                    System.out.print("Coloque a peça para promoção: (B/C/T/RA): ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("C") && !type.equals("T") & !type.equals("RA")) {
                        System.out.print("Invalid value! Enter piece for promotion (B/C/T/RA): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    chessMatch.replacePromotedPiece(type);
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
        UI.clearScreen();
        UI.printMatch(chessMatch,captured);
    }
}