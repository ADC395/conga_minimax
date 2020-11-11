/*
 * Main class
 */
public class Main {
    public static void main(String[] args) {
        CongaBoard congaBoard;
        Player player1;
        Player player2;
        Player currentPlayer;

        player1 = new Player(Color.WHITE);
        player2 = new Player(Color.BLACK);
        // randomize starting player
        currentPlayer = (int)(Math.random() * 10) % 2 == 0 ? player1 : player2;
        // Initialize the Conga board
        congaBoard = new CongaBoard(4, 4, 10, new Player[] {player1, player2});
        congaBoard.setBoardValue(currentPlayer);
        // TODO: Stop game if it is not finished after certain time
        int temp = 0;
        while (true) {
            System.out.println("Current turn: " + currentPlayer.getColor());
            System.out.println("Current board state: ");
            CongaBoard.printBoard(congaBoard);
            Helper.getNextStates(congaBoard, currentPlayer);
            if (temp == 1) {
                break;
            }
            temp++;
            // change player
            currentPlayer = currentPlayer == player1 ? player2 : player1;

            if (Helper.evaluateBoard(congaBoard) == Integer.MAX_VALUE) {
                System.out.println("Player WHITE won!");
                break;
            } else if (Helper.evaluateBoard(congaBoard) == Integer.MIN_VALUE) {
                System.out.println("Player BlACK won!");
                break;
            }
            System.out.println("----------------------------------------------------------------");
        }
    }
}