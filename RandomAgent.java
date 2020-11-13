import java.util.ArrayList;
import java.util.Random;

/* Random Agent class */
public class RandomAgent {
    /*
     * make a random move
     *
     * @param   congaBoard: current CongaBoard
     * @param   randomAgent: Player who is acting as random agent
     */
    public static CongaBoard randomMove(CongaBoard congaBoard, Player randomAgent) {
        // pick a random tiles of randomAgent player
        // make a valid random move from there
        Random rand = new Random(System.currentTimeMillis());
        ArrayList<CongaBoard> nextStates = Helper.getNextStates(congaBoard, randomAgent);
        int randNum = rand.nextInt(nextStates.size());
        System.out.println("random number: " +  randNum);
        return nextStates.get(randNum);
    }
}

//        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//                System.out.println("random number: " +  randNum);
//                for (CongaBoard c: nextStates) {
//                c.printBoard();
//                }
//                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");