class MiniMax {
   int test;
  
   /* This function computes the MiniMax value for a node of the Tree of states
    * and implements alpha-beta pruning
    *
    * @params	node	Root node of game Tree of possible states
    * @params	depth	Depth of root node in Tree (0)
    * @params	isMax	True if player is Max, false if Min
    * @params	alpha	Alpha value of node, pass MIN_VALUE
    * @params	beta	Beta value of node, pass MAX_VALUE
    * 
    * @return	bestScore	returns Int of score for the move
    */
   public int MiniMax(Node node, int depth, boolean isMax, int alpha, int beta) {
       // DFS
       // create tree, root = congaBoard
       // loop for depth = 6
       // iterative deepeining at level 3 -> watch video
       // if depth == 6, evaluate the leaf nodes
       // do the minimax with alpha beta pruning
       // return most newIndex, optimal board state
       int bestScore, score;

       if(node.next == null){
	       return score;
       }

       if (isMax){
		bestScore = Interger.MIN_VALUE;
		while(node.next != null || depth <= 6){
			score = this.MiniMax(node, depth + 1, false, alpha, beta);
			bestScore = Math.Max(bestScore, score);
			alpha = Math.Max(alpha, bestScore);
			if (alpha >= beta){
				break;
			}
		}
		return bestScore;
       }

       else{
	       bestScore = MAX_VALUE;
	       while(node.next != null || depth <= 6){
		       score = this.MiniMax(node, depth + 1, true, alpha, beta);
		       bestScore = Math.Min(bestScore, score);
		       alpha = Math.Min(alpha, beestScore);
		       if (alpha >= beta){
			       break;
		       }
	       }
	       return bestScore;
       }
    }
}


