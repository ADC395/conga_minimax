/*
 * Main class
 */
public class Main {
    public static void main(String[] args) {
        CongaBoard congaBoard;
        Player player1;
        Player player2;
        Player currentPlayer;
        Player nextPlayer;

        // player 1 will be our agent using Minimax with Alpha Beta pruning
        player1 = new Player(Color.WHITE);
        player2 = new Player(Color.BLACK);
        // randomize starting player
        currentPlayer = (int)(Math.random() * 10) % 2 == 0 ? player1 : player2;
        nextPlayer = currentPlayer == player1 ? player2 : player1;
        // Initialize the Conga board
        congaBoard = new CongaBoard(4, 4, 10, new Player[] {player1, player2});
        congaBoard.setBoardValue(currentPlayer);
        // TODO: Stop game if it is not finished after certain time
        while (true) {
            // Minimax Agent playing
            if (currentPlayer == player1) {
                System.out.println("Current turn: MINIMAX Agent (WHITE)");
                System.out.println("Current board state: ");
                congaBoard.printBoard();
                congaBoard = MiniMax.getNextMoveState(congaBoard, player1, player2);
            } else {
                //random agent playing
                System.out.println("Current turn: RANDOM Agent (BLACK)");
                System.out.println("Current board state: ");
                congaBoard.printBoard();
                congaBoard = RandomAgent.randomMove(congaBoard, player2);
            }
            // TODO: Show move info
            System.out.println("Move made. New board state: ");
            congaBoard.printBoard();

            int eval = Helper.evaluateBoard(congaBoard, player1, player2);
            if ( eval == Integer.MAX_VALUE) {
                System.out.println("MINIMAX AGENT(WHITE) won!");
                break;
            } else if ( eval == Integer.MIN_VALUE) {
                System.out.println("RANDOM AGENT(BLACK) won!");
                break;
            }
            // change player
            currentPlayer = currentPlayer == player1 ? player2 : player1;
            System.out.println("----------------------------------------------------------------");
        }
    }
}