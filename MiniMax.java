import java.util.ArrayList;

class MiniMax {
	/*
	 * get the next optimal move
	 *
	 * @param	congaBoard: current state of Conga board
	 * @param	currentPlayer: Current player making move
	 * @param	nextPlayer: Next player to make move
	 *
	 * @return	next best state obtained from our evaluation
	 */
	public static CongaBoard getNextMoveState(CongaBoard congaBoard, Player currentPlayer, Player nextPlayer) {
		// TODO: return most optimal state
		Node rootNode = new Node(congaBoard, null);
		return MiniMax.miniMax(rootNode, currentPlayer, nextPlayer, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true).getCongaBoard();
	}

	/*
	 * returns the most optimal next state of the game
	 *
	 * @param	congaBoard:	current state of Conga board
	 * @param	currentPlayer: Current player making move
	 * @param	nextPlayer: Next player to make move
	 * @param	depth: Cutoff depth for the game tree
	 * @param	alpha: alpha value for alpha beta pruning
	 * @param	beta: beta value for alpha beta pruning
	 * @param	isMaximizingPlayer: true, if it's MAXIMIZER; false, if it's MINIMIZER
	 *
	 * @return	best evaluation value
	 */
	private static Node miniMax(Node parentNode, Player currentPlayer, Player nextPlayer, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
		// TODO: Game winning move before the depth, i.e max player has no more move place || min player has no more move
		// TODO: Implement visited state
		if (depth == 0) {
			return parentNode;
		}
		ArrayList<CongaBoard> childStates =  Helper.getNextStates(parentNode.getCongaBoard(), currentPlayer);
		// Make a new node for every Conga board state
		for (CongaBoard childState : childStates) {
			parentNode.addChild(new Node(childState, parentNode));
		}
		if (isMaximizingPlayer) {
			int maxEval = Integer.MIN_VALUE;
			for (Node childNode: parentNode.getChildNodes()) {
				Node nodeTobeEvaluated = miniMax(childNode, currentPlayer, nextPlayer, depth - 1, alpha, beta, false);
				if (nodeTobeEvaluated != null) {
					int eval = Helper.evaluateBoard(nodeTobeEvaluated.getCongaBoard(), currentPlayer, nextPlayer);
					if (eval > maxEval) {
						parentNode.setBestChildState(childNode);
					}
					maxEval = Math.max(maxEval, eval);
					alpha = Math.max(alpha, eval);
					if (beta <= alpha) {
						break;
					}
				}
			}
			return parentNode.getBestChildState();
		} else {
			int minEval = Integer.MAX_VALUE;
			for (Node childNode: parentNode.getChildNodes()) {
				Node nodeTobeEvaluated = miniMax(childNode, currentPlayer, nextPlayer, depth - 1, alpha, beta, true);
				if (nodeTobeEvaluated != null) {
					int eval = Helper.evaluateBoard(nodeTobeEvaluated.getCongaBoard(), currentPlayer, nextPlayer);
					if (eval < minEval) {
						parentNode.setBestChildState(childNode);
					}
					minEval = Math.min(minEval, eval);
					beta = Math.min(beta, eval);
					if (beta <= alpha) {
						break;
					}
				}
			}
			return parentNode.getBestChildState();
		}
	}
}