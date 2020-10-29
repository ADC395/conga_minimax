import java.util.ArrayList;

/* A player is an agent who can perform a move on the board
 * Each player is designated a color and has a list of occupied tiles
 *
 * @author	Adam Carrol
 * @version	1.0
 */
private class Player
{	
	
	private ArrayList<Tile> occupiedTiles;
	// True = white, False = black
	private boolean color;

	//Constructor Goes Here
	Player(){}


	/* Returns True if move is determined Valid, False if Illegal
	 * If this method returns True, the subsequent Move method is called
	 *
	 * @param	current_tile	The tile the Player wishes to move from
	 * @param	goal_tile	The tile the Player wishes to move to
	 * @return	valid or invalid move
	 */
	public boolean CheckMove(Tile current_tile, Tile goal_tile)
	{	

		// Check if goal tile is empty or of same color
		if((goal_tile.color == Null) or (goal_tile.color == this.color)){
			return true;
		}else{
			return false;
		}

		// Straight Line Conditions
		if((current_tile.row == goal_tile.row) or (current_tile.col == goal_tile.col)){
			// Code for tile in same row or column
		}
		else if((current_tile.row - goal_tile.row) == (current_tile.col - goal_tile.col)){
			// Code for tile in same diagonal
		}

		// Count condition - Does Player have enough stones in the current tile
		// to move to the goal tile?
		if(current_tile.count < /*Some Value*/){
			return false;
		}
		
		// Need to check if all tiles between curr and goal are
		// empty or of this.color


		// -Debug- Default return false
		return false;
	}


	/* Moves the stones from the Player's current tile to the goal
	 * tile in a legal fashion
	 *
	 * @param	current_tile	The tile the Player wishes to move from
	 * @param	goal_tile	The tile the Player wishes to move to
	 * @return	none		Updates board, no return
	 */
	public Move(Tile current_tile, Tile goal_tile){


	}








