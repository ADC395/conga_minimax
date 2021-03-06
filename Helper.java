import java.util.ArrayList;

public class Helper {
    static double evaluateBoard(CongaBoard congaBoard, Player currentPlayer, Player nextPlayer) {
        int[][] relativeNeighborIndex = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
        Tile[][] board = congaBoard.getBoard();
        double blackScore, whiteScore;
        double blackMoves, whiteMoves;
        blackMoves = 0;
        whiteMoves = 0;
        blackScore = 0;
        whiteScore = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                for (int[] index : relativeNeighborIndex) {
                    try {
                        // Check number of moves for each player
                        if(congaBoard.checkMove(board[row][col], board[row + index[0]][col + index[1]], null) != Move.INVALID){
                            if(board[row][col].getPlayer() != null && board[row][col].getPlayer().getColor() == Color.BLACK){
                                blackMoves++;
                            }
                            else if(board[row][col].getPlayer() != null && board[row][col].getPlayer().getColor() == Color.WHITE){
                                whiteMoves++;
                            }
                        }
                        // Check tile for Black
                        if (board[row][col].getPlayer() != null && board[row][col].getPlayer().getColor() == Color.BLACK) {
                            if(board[row + index[0]][col + index[1]].getPlayer() != null && board[row + index[0]][col + index[1]].getPlayer().getColor() == Color.WHITE) {
                                whiteScore += board[row][col].getCount() - board[row + index[0]][col + index[1]].getCount();
                            }
                            if(board[row + index[0]][col + index[1]].getPlayer() == null){
                                blackScore ++;
                            }
                        }if (board[row][col].getPlayer() != null && board[row][col].getPlayer().getColor() == Color.WHITE) {
                            if (board[row][col].getCount() > 1) {
                                whiteScore -= (board[row][col].getCount() - 1);
                            }
                        }
//
                    }  catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }

            }
        }

        // White is our maximizer, black is our minimizer
        if (blackMoves == 0) {
            return Integer.MAX_VALUE;
        } else if (whiteMoves == 0) {
            return Integer.MIN_VALUE;
        } else {
            // Difference of white score and black score, plus difference of number of tiles owned
            return  (whiteScore - blackScore) + (currentPlayer.getOccupiedTiles().size() - nextPlayer.getOccupiedTiles().size());
        }
    }


    /*
     * Get the list of children states from current Conga board state
     *
     * @param   congaBoard: current congaBoard State
     * @param   currentPlayer: player who has the current turn
     *
     * @return  list of children states
     */
    static ArrayList<CongaBoard> getNextStates(CongaBoard congaBoard, Player currentPlayer) {
        ArrayList<CongaBoard> nextStates = new ArrayList<>();
        Tile[][] board = congaBoard.getBoard();
        MovesInfo movesInfo;
        Tile currentTile;

        // Get all the possible moves that current player can make in current board state
        for (int row = 0; row < congaBoard.getRows(); row++) {
            for (int col = 0; col < congaBoard.getColumns(); col++) {
                currentTile = board[row][col];
                if (currentTile.getPlayer() != null && currentTile.getPlayer().equals(currentPlayer)) {
                    movesInfo = getPossibleMoves(congaBoard, currentTile);
                    // For every possible move, create a clone of current board state and make move on the cloned board.
                    for (int i = 0; i < movesInfo.moveType.size(); i++) {
                        CongaBoard newCongaBoard = congaBoard.cloneBoard();
                        Tile [][] newBoard = newCongaBoard.getBoard();

                        currentTile = newBoard[movesInfo.startIndices.get(i)[0]][movesInfo.startIndices.get(i)[1]];
                        Tile goalTile = newBoard[movesInfo.goalIndices.get(i)[0]][movesInfo.goalIndices.get(i)[1]];

                        Move move = movesInfo.moveType.get(i);
                        newCongaBoard.move(currentTile, goalTile, move);

                        newCongaBoard.setBoardValue(currentPlayer);
                        nextStates.add(newCongaBoard);
                    }
                }
            }
        }
        return nextStates;
    }

    /*
     * Get all the possible moves and move info from a tile in the board
     *
     * @param   congaBoard: current Conga board
     * @param   currentTile: tile you are currently on
     *
     * @return  information about the possible moves
     */
    private static MovesInfo getPossibleMoves(CongaBoard congaBoard, Tile currentTile) {
        MovesInfo movesInfo = new MovesInfo();
        Tile[][] board = congaBoard.getBoard();
        int[] currentIndex = currentTile.getId();
        int rowSize = congaBoard.getRows();
        int colSize = congaBoard.getColumns();
        int diagSize = Math.min(rowSize, colSize);
        Tile goalTile;

        // Moves along the row, change column
        for (int col = 1; col < colSize; col++) {
            // Moves along the row towards right (+column)
            // Moves along the row towards left (-column)
            int[] newColumns = new int[] {currentIndex[1] + col, currentIndex[1] - col};
            for (int newCol: newColumns) {
                // check if the new index is valid
                if (isValidIndex(congaBoard, currentIndex[0], newCol)) {
                    goalTile = board[currentIndex[0]][newCol];
                    // check if the move to goal tile is valid
                    if (congaBoard.checkMove(currentTile, goalTile, Move.ROW) != Move.INVALID) {
                        movesInfo.add(currentIndex, goalTile.getId(), Move.ROW);
                    }
                }
            }
        }
        // Moves along the column, change row
        for (int row = 1; row < rowSize; row++) {
            // Moves along the column towards bottom (+row)
            // Moves along the column towards top (-row)
            int[] newRows = new int[] {currentIndex[0] + row, currentIndex[0] - row};
            for (int newRow: newRows) {
                // check if the new index is valid
                if (isValidIndex(congaBoard, newRow, currentIndex[1])) {
                    goalTile = board[newRow][currentIndex[1]];
                    // check if the move to goal tile is valid
                    if (congaBoard.checkMove(currentTile, goalTile, Move.COLUMN) != Move.INVALID) {
                        movesInfo.add(currentIndex, goalTile.getId(), Move.COLUMN);
                    }
                }
            }
        }
        // Moves along the diagonal, change both row and column
        for (int diag = 1; diag < diagSize; diag++) {
            // Moves along the column towards bottom, along the row towards right (+row, +column)
            // Moves along the column towards bottom, along the row towards left (+row, -column)
            // Moves along the column towards top, along the row towards right (-row, +column)
            // Moves along the column towards top, along the row towards left (-row, -column)
            int[][] newDiagonals = new int[][] {
                    {currentIndex[0] + diag, currentIndex[1] + diag},
                    {currentIndex[0] + diag, currentIndex[1] - diag},
                    {currentIndex[0] - diag, currentIndex[1] + diag},
                    {currentIndex[0] - diag, currentIndex[1] - diag},
            };
            for (int[] newDiag: newDiagonals) {
                // check if the new index is valid
                if (isValidIndex(congaBoard,newDiag[0], newDiag[1])) {
                    goalTile = board[newDiag[0]][newDiag[1]];
                    // check if the move to goal tile is valid
                    if (congaBoard.checkMove(currentTile, goalTile, Move.DIAGONAL) != Move.INVALID) {
                        movesInfo.add(currentIndex, goalTile.getId(), Move.DIAGONAL);
                    }
                }
            }
        }
        return movesInfo;
    }

    /*
     * check if the index is valid on the Conga board
     *
     * @param   congBoard: Conga board
     * @param   rowIndex: row index value
     * @param   colIndex: column index value
     *
     * @return  true, if indices are valid. false, otherwise
     */
    private static boolean isValidIndex(CongaBoard congaBoard, int rowIndex, int colIndex) {
        return rowIndex < congaBoard.getRows() && rowIndex >= 0 && colIndex < congaBoard.getColumns() && colIndex >= 0;
    }

}

/*
 * MovesInfo holds all the information about the moves that can be made at a given state
 */
class MovesInfo {
    ArrayList<int[]> startIndices;
    ArrayList<int[]> goalIndices;
    ArrayList<Move> moveType;

    MovesInfo() {
        this.startIndices = new ArrayList<>();
        this.goalIndices = new ArrayList<>();
        this.moveType = new ArrayList<>();
    }

    /*
     * add the move info
     *
     * @param   startIndex: index of tile you are moving from
     * @param   goalIndex: index of tile you are moving to
     * @param   move: type of move you are making when moving from startIndex to goalIndex
     */
    public void add(int[] startIndex, int[] goalIndex, Move move) {
        this.startIndices.add(startIndex);
        this.goalIndices.add(goalIndex);
        this.moveType.add(move);
    }
}
