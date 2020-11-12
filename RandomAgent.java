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
        ArrayList<CongaBoard> boardStates = Helper.getNextStates(congaBoard, randomAgent);
        Random rand = new Random();
        return boardStates.get(rand.nextInt(boardStates.size()));
    }
}
