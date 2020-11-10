import java.util.Random;

/*
 * Main class
 */
public class Main {
    public static void main(String[] args) {
        CongaBoard congaBoard;
        Player player1;
        Player player2;
        Player currentPlayer;

        congaBoard = new CongaBoard();
        // Initialize players and put them on the first and last tile on the board
        player1 = new Player(Color.WHITE, congaBoard.board[0][0].getId());
        congaBoard.board[0][0].setCount(10);
        congaBoard.board[0][0].setPlayer(player1);
        player2 = new Player(Color.BLACK, congaBoard.board[congaBoard.rows-1][congaBoard.columns-1].getId());
        congaBoard.board[congaBoard.rows-1][congaBoard.columns-1].setCount(10);
        congaBoard.board[congaBoard.rows-1][congaBoard.columns-1].setPlayer(player2);

        Random rand = new Random();
        currentPlayer = rand.nextBoolean() ? player1 : player2;
        int tempCount = 0;
        while (true) {
            System.out.println(currentPlayer.getColor());
            Helper.getNextStates(congaBoard, currentPlayer);
            // change player
            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }

            if (tempCount == 1) {
                return;
            }
            if (Helper.evaluateBoard(congaBoard) == Integer.MAX_VALUE) {
                System.out.println("Player WHITE won!");
                break;
            } else if (Helper.evaluateBoard(congaBoard) == Integer.MIN_VALUE) {
                System.out.println("Player BlACK won!");
                break;
            }
            System.out.println("--------------------------------");
            tempCount++;
        }
    }
}