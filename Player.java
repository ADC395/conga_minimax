import java.util.ArrayList;
import java.lang.*;

/* A player is an agent who can perform a move on the board
 * Each player is designated a color and has a list of occupied tiles
 *
 * @author	Adam Carrol
 * @version	1.0
 */
private class Player
{	
	
	private ArrayList<Tile> occupiedTiles;
	private Color color;


	//Constructor
	Player(Color color, Tile tile){
		this.color = color;
		this.occupiedTiles.Add(tile);
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public ArrayList<Tile> getOccupiedTiles(){
		return this.occupiedTiles;
	}


	/* Returns True if move is determined Valid, False if Illegal
	 * If this method returns True, the subsequent Move method is called
	 *
	 * @param	player		The Player wishing to move
	 * @param	current_tile	The tile the Player wishes to move from
	 * @param	goal_tile	The tile the Player wishes to move to
	 * @return	boolean		Valid = true, invalid = false
	 */
	public boolean CheckMove(Player player, Tile current_tile, Tile goal_tile)
	{
		int distance, required;
	
		// Check if goal tile is empty or of same color
		if((goal_tile.getColor() == null) or (goal_tile.getColor() == player.getColor())){
		
		}else{
			return false;
		}

		// Check same row
		if((current_tile.row == goal_tile.row){
			// Same row & column (cannot move to same tile)
			if(current_tile.column == goal_tile.column){
				return false;
			}

			// Columns: Current < Goal
			else if(current_tile.column < goal_tile.column){
				for(i = current_tile.column; i <= goal_tile.column; i++){
					if((this.board[current_tile.row][i].getColor() == player.getColor()) or (this.board[current_tile.row][i].getColor() = null)){
						continue;
					}else{
						return false;
					}
				}
				// Check count
				distance = abs(current_tile.column - goal_tile.column) - 1;
				required = ((distance(distance + 1))/2) + 1;
				
				if(current_tile.count < required){
					return false;
				}

				return true;
			}
			
			// Columns: Goal < Current
			else{
				for(i = current_tile.column; i >= goal_tile.column; i--){
					if((this.board[current_tile.row][i].getColor() == player.getColor()) or (this.board[current_tile.row][i].getColor() = null)){
						continue;
					}else{
						return false;
					}
				}
				// Check count
				distance = abs(current_tile.column - goal_tile.column) - 1;
				required = ((distance(distance + 1))/2) + 1;

				if(current_tile.count < required){
					return false;
				}

				return true;
			}
		}

		// Check same column
		else if((current_tile.column == goal_tile.column){
			// Same column & row (cannot move to same tile)
			if(current_tile.row == goal_tile.row){
				return false;
			}

			// Rows: Current < Goal
			else if(current_tile.row < goal_tile.row){
				for(i = current_tile.row; i <= goal_tile.row; i++){
					if((this.board[i][current_tile.column].getColor() == player.getColor()) || (this.board[i][current_tile.column.getColor() == null)){
						continue;
					}else{
						return false;
					}
				}		
				// Check count
				distance = abs(current_tile.column - goal_tile.column) - 1;
				required = ((distance(distance + 1))/2) + 1;
				
				if(current_tile.count < required){
					return false;
				}

				return true;
			}
			
			// Rows: Goal < Current
			else{
				for(i = current_tile.row; i >= goal_tile.row; i--){
					if((this.board[i][current_tile.column].getColor() == player.getColor()) || (this.board[i][current_tile.column.getColor() == null)){
						continue;
					}else{
						return false;
					}
				}			
				// Check count
				distance = abs(current_tile.column - goal_tile.column) - 1;
				required = ((distance(distance + 1))/2) + 1;
				
				if(current_tile.count < required){
					return false;
				}

				return true;
		}

		// Check same diagonal
		else if((current_tile.row - goal_tile.row) == (current_tile.column - goal_tile.column)){
			// Same Row & Column (cannot move to same tile)
			if(current_tile.row == goal_tile.row){
				return false;
			}

			// Current < Goal
			else if(current_tile.row < goal_tile.row){
				for(i = current_tile.row; i <= goal_tile.row; i++){
					if((this.board[i][i].getColor() == player.getColor()) || (this.board[i][i].getColor()) == null){
						continue;
					}else{
						return false;
					}
				}
				// Check count
				distance = abs(current_tile.row - goal_tile.row) - 1;
				required = ((distance(distance + 1))/2) + 1;

				if(current_tile.count < required){
					return false;
				}
				
				return true;
			}

			// Goal < Current
			else{
				for(i = current_tile.row; i >= goal_tile.row; i--){
					if((this.board[i][i].getColor() == player.getColor()) || (this.board[i][i].getColor() == null){
						continue;
					}else{
						return false;
					}
				}
				// Check count
				distance = abs(current_tile.row - goal_tile.row) - 1;
				required = ((distance(distance + 1))/2) + 1;

				if(current_tile.count < required){
					return false;
				}

				return true;
			}
		}
		
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








