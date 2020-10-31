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

		// Check same diagonal - not correct
		/*
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
		}*/
		
		// -Debug- Default return false
		return false;
	}


	/* Moves the stones from the Player's current tile to the goal
	 * tile in a legal fashion
	 *
	 * @param	Player 			
	 * @param	current_tile	The tile the Player wishes to move from
	 * @param	goal_tile	The tile the Player wishes to move to
	 * @return	none		Updates board, no return
	 */
	public Move(Tile current_tile, Tile goal_tile){
		int count, rowDistance, colDistance;

		rowDistance = abs(current_tile.row - goal_tile.row);
		colDistance = abs(current_tile.column - goal_tile.column);
		
		// Set count
		count = current_tile.getCount();

		// Diagonal Move - stuck			
		if(rowDistance == colDistance){
			// 
		}

		// Same Row Move
		else if(rowDistance == 0){
			// Current < Goal
			if(current_tile.column < goal_tile.column){
				for(i = 0; i <= colDistance; i++){
					// At current_tile: count = 0
					if(i == 0){
						this.board[current_tile.row][current_tile.column + i].setCount(0);
						continue;
					}
					// At goal_tile: count = remaining pcs
					else if(i == colDistance){
						this.board[current_tile.row][current_tile.column + i].setCount(count);
					}
					// At a tile in between: count = i, update
					else{
						this.board[current_tile.row][current_tile.column + i].setCount(i)
						count = count - i;
					}
				}

			}
			// Current > Goal
			else{
				for(i = 0; i <= colDistance; i++){
					// At current_tile: count = 0
					if(i == 0){
						this.board[current_tile.row][curret_tile.column - i].setCount(0);
						continue;
					}
					// At goal_tile: count = reamining pcs
					else if(i == colDistance){
						this.board[current_tile.row][current_tile.column - i].setCount(count);
					}
					// At atile in between: count = i, update
					else{
						this.board[current_tile.row][current_tile.column - i].setCount(i);
						count = count - i;
					}
				}
			}
		}

		// Same Column Move
		else if(colDistance == 0){
			// Current < Goal
			if(current_tile.row < goal_tile.row){
				for(i = 0; i <= rowDistance; i++){
					// At current_tile: count = 0
					if(i == 0){
						this.board[current_tile.row + i][current_tile.column].setCount(0);
						continue;
					}
					// At goal_tile: count = remaining pcs
					else if(i == rowDistance){
						this.board[current_tile.row + i][current_tile.column].setCount(count);
					}
					// At a tile in between: count = i, update
					else{
						this.board[current_tile.row + i][current_tile.column].setCount(i)
						count = count - i;
					}
				}

			}
			// Current > Goal
			else{
				for(i = 0; i <= rowDistance; i++){
					// At current_tile: count = 0
					if(i == 0){
						this.board[current_tile.row - i][curret_tile.column].setCount(0);
						continue;
					}
					// At goal_tile: count = reamining pcs
					else if(i == rowDistance){
						this.board[current_tile.row - i][current_tile.column].setCount(count);
					}
					// At atile in between: count = i, update
					else{
						this.board[current_tile.row - i][current_tile.column].setCount(i);
						count = count - i;
					}
				}
			}
		}
	}








