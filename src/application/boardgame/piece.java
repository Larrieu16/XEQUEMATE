package boardgame;
import boardgame.board;
import boardgame.position;
public class piece {
    protected position position;
    private board board;
    public piece(board board)  {
        this.board = board;
        position = null;
    }
    protected board getBoard(){
        return board;
    }


}


