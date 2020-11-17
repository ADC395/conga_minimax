import java.io.IOException;
import java.util.ArrayList;

/*
 * Main class
 */
public class Main {
    public static void main(String[] args) throws IOException {
        CongaBoard congaBoard;
        Player player1;
        Player player2;
        Player currentPlayer;
        int moveCount = 0;
        int games = 1;

        // player 1 will be our agent using Minimax with Alpha Beta pruning
        player1 = new Player(Color.WHITE);
        player2 = new Player(Color.BLACK);
        // randomize starting player
//        currentPlayer = (int) (Math.random() * 10) % 2 == 0 ? player1 : player2;
//        // Initialize the Conga board
//        congaBoard = new CongaBoard(4, 4, 10, new Player[]{player1, player2});
//        congaBoard.setBoardValue(currentPlayer);
//        System.out.println("Current board state: ");
//        congaBoard.printBoard();

        while (games <= 1){
            currentPlayer = (int) (Math.random() * 10) % 2 == 0 ? player1 : player2;
            // Initialize the Conga board
            congaBoard = new CongaBoard(4, 4, 10, new Player[]{player1, player2});
            congaBoard.setBoardValue(currentPlayer);
            System.out.println("Current board state: ");
            congaBoard.printBoard();
            MiniMax miniMax = new MiniMax();

            while (true) {
                // Minimax Agent playing
                if (currentPlayer == player1) {
                    System.out.println("Current turn: MINIMAX Agent (WHITE)");
                    congaBoard = miniMax.getNextMoveState(congaBoard, player1, player2);

                    System.out.println("Number of nodes explored " + miniMax.nodesExplored);
                    System.out.println("Number of nodes pruned " + miniMax.nodesPruned);

                    // Reset pruned - explored
                    miniMax.setNodesExplored(0);
                    miniMax.setNodesPruned(0);

                } else {
                    //random agent playing
                    System.out.println("Current turn: RANDOM Agent (BLACK)");
                    congaBoard = RandomAgent.randomMove(congaBoard, player2);
                }

                System.out.println("Move made. New board state: ");
                congaBoard.printBoard();

                double eval = Helper.evaluateBoard(congaBoard, player1, player2);
                if (eval == Integer.MAX_VALUE) {
                    System.out.println("MINIMAX AGENT(WHITE) won in " + moveCount + " steps.");
                    break;
                } else if (eval == Integer.MIN_VALUE) {
                    System.out.println("RANDOM AGENT(BLACK) won!" + moveCount + " steps.");
                    break;
                }
                // change player
                currentPlayer = currentPlayer == player1 ? player2 : player1;
                moveCount++;
                System.out.println(" Move Count: " + moveCount);
                System.out.println("----------------------------------------------------------------");
                if(moveCount >= 150){
                    System.out.println("Move timeout - Agent did not win in under 150 Moves");
                    break;
                }
            }
            moveCount = 0;
            games++;
        }
    }
}