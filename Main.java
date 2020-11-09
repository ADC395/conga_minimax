import java.util.Random;

public class Main {
    public static void main(String[] args) {
        CongaBoard congaBoard;
        Tile[][] board;
        Player player1;
        Player player2;
        Player turn;

        congaBoard = new CongaBoard();
        board = congaBoard.board;
        // Initialize and put players on the first and last tile on the board
        player1 = new Player(Color.WHITE, congaBoard.board[0][0].getId());
        congaBoard.board[0][0].setCount(10);
        congaBoard.board[0][0].setPlayer(player1);
        player2 = new Player(Color.BLACK, congaBoard.board[congaBoard.rows-1][congaBoard.columns-1].getId());
        congaBoard.board[congaBoard.rows-1][congaBoard.columns-1].setCount(10);
        congaBoard.board[congaBoard.rows-1][congaBoard.columns-1].setPlayer(player2);

        Random rand = new Random();
        turn = rand.nextBoolean() ? player1 : player2;
        while (true) {
            congaBoard.board = congaBoard.makeMove(turn);
            // change player
            if (turn == player1) {
                turn = player2;
            } else {
                turn = player1;
            }
            congaBoard.printBoard();

            if (Helper.evaluateBoard(congaBoard) == Integer.MAX_VALUE) {
                System.out.println("Player WHITE won!");
                break;
            } else if (Helper.evaluateBoard(congaBoard) == Integer.MIN_VALUE) {
                System.out.println("Player BlACK won!");
                break;
            }
        }
    }
}

// What to do in this situation
//        congaBoard.move(board[0][0], board[0][3]);
//                congaBoard.printBoard();
//                congaBoard.move(board[0][3], board[2][3]);
//                congaBoard.printBoard();
//                congaBoard.move(board[0][2], board[1][2]);
//                congaBoard.printBoard();
//                congaBoard.move(board[2][3], board[2][2]);
//                congaBoard.printBoard();
//                congaBoard.move(board[1][3], board[2][3]);
//                congaBoard.printBoard();
//                congaBoard.move(board[2][2], board[3][2]);
//                congaBoard.printBoard();
//                congaBoard.move(board[1][2], board[2][2]);
//                congaBoard.printBoard();