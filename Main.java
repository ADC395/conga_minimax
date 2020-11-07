public class Main {
    public static void main(String[] args) {
        CongaBoard congaBoard = new CongaBoard();
        Tile[][] board = congaBoard.board;
        // What to do in this situation
//        congaBoard.printBoard();
//        congaBoard.move(board[0][0], board[0][3]);
//        congaBoard.printBoard();
//        congaBoard.move(board[0][3], board[2][3]);
//        congaBoard.printBoard();
//        congaBoard.move(board[0][2], board[1][2]);
//        congaBoard.printBoard();
//        congaBoard.move(board[2][3], board[2][2]);
//        congaBoard.printBoard();
//        congaBoard.move(board[2][2], board[0][2]);
//        congaBoard.printBoard();
        congaBoard.printBoard();
        congaBoard.move(board[0][0], board[0][3]);
        congaBoard.printBoard();
        congaBoard.move(board[0][3], board[2][3]);
        congaBoard.printBoard();
        congaBoard.move(board[0][2], board[1][2]);
        congaBoard.printBoard();
        congaBoard.move(board[2][3], board[2][2]);
        congaBoard.printBoard();
        congaBoard.move(board[1][3], board[2][3]);
        congaBoard.printBoard();
        congaBoard.move(board[2][2], board[3][2]);
        congaBoard.printBoard();
        // goal state
        //TODO: Implement win condition
        congaBoard.move(board[1][2], board[2][3]);
        congaBoard.printBoard();
    }
}
