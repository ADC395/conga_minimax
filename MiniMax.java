import java.util.ArrayList;

class MiniMax {
	int nodesExplored = 0;
	int nodesPruned = 0;
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
	private Node miniMax(Node parentNode, Player currentPlayer, Player nextPlayer, int depth, double alpha, double beta, boolean isMaximizingPlayer) {
		if (depth == 0) {
			return parentNode;
		}
		ArrayList<CongaBoard> childStates =  Helper.getNextStates(parentNode.getCongaBoard(), currentPlayer);
		// Make a new node for every Conga board state
		for (CongaBoard childState : childStates) {
			parentNode.addChild(new Node(childState, parentNode));
		}
		if (isMaximizingPlayer) {
			double maxEval = Integer.MIN_VALUE;
			for (Node childNode: parentNode.getChildNodes()) {
				nodesExplored++;
				Node nodeTobeEvaluated = miniMax(childNode, currentPlayer, nextPlayer, depth - 1, alpha, beta, false);
				if (nodeTobeEvaluated != null) {
					nodesExplored++;
					double eval = Helper.evaluateBoard(nodeTobeEvaluated.getCongaBoard(), currentPlayer, nextPlayer);
					if (eval > maxEval) {
						parentNode.setBestChildState(childNode);
					}
					maxEval = Math.max(maxEval, eval);
					alpha = Math.max(alpha, eval);
					if (beta <= alpha) {
						this.nodesPruned++;
						this.nodesExplored--;
						break;
					}
				}
			}
			return parentNode.getBestChildState();
		} else {
			double minEval = Integer.MAX_VALUE;
			for (Node childNode: parentNode.getChildNodes()) {
				nodesExplored++;
				Node nodeTobeEvaluated = miniMax(childNode, currentPlayer, nextPlayer, depth - 1, alpha, beta, true);
				if (nodeTobeEvaluated != null) {
					double eval = Helper.evaluateBoard(nodeTobeEvaluated.getCongaBoard(), currentPlayer, nextPlayer);
					if (eval < minEval) {
						parentNode.setBestChildState(childNode);
					}
					minEval = Math.min(minEval, eval);
					beta = Math.min(beta, eval);
					if (beta <= alpha) {
						nodesPruned++;
						nodesExplored--;
						break;
					}
				}
			}
			return parentNode.getBestChildState();
		}
	}

	/*
	 * get the next optimal move
	 *
	 * @param	congaBoard: current state of Conga board
	 * @param	currentPlayer: Current player making move
	 * @param	nextPlayer: Next player to make move
	 *
	 * @return	next best state obtained from our evaluation
	 */
	public CongaBoard getNextMoveState(CongaBoard congaBoard, Player currentPlayer, Player nextPlayer) {
		Node rootNode = new Node(congaBoard, null);
		return this.miniMax(rootNode, currentPlayer, nextPlayer, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true).getCongaBoard();
	}
	public void setNodesExplored(int numberExplored){
		this.nodesExplored = numberExplored;
	}
	public void setNodesPruned(int numberPruned){
		this.nodesPruned = numberPruned;
	}
}


