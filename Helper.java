import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Helper {
    /*
     * Static evaluator of the CongaBoard
     * @param   (CongaBoard) congaBoard
     * @return  (int) evaluation value of the board
     */
    static int evaluateBoard(CongaBoard congaBoard) {
        Tile[][] board = congaBoard.board;
        // evalIndex contains all the possible index of neighbors relative to current tile
        int[][] relativeNeighborIndex = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
        int whiteMove = 0;
        int blackMove = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                for (int[] index: relativeNeighborIndex) {
                    try {
                        if (congaBoard.checkMove(board[row][col], board[row+index[0]][col+index[1]]) != Move.INVALID) {
                            if (board[row][col].getPlayer().getColor() == Color.BLACK) {
                                blackMove++;
                            } else {
                                whiteMove++;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            }
        }
        // White is our maximizer, black is our minimizer
        if (blackMove == 0) {
            return Integer.MAX_VALUE;
        } else if (whiteMove == 0) {
            return Integer.MIN_VALUE;
        } else {
            // Current score of board = total degree of freedom of white - black
            return whiteMove - blackMove;
        }
    }

    static void getNextStates(CongaBoard congaBoard, Player currentPlayer) {
        // check every tile
        // if tile has the player color, generate states from that tile
        // do that for every tile
        Tile[][][] nextStates;
        ArrayList<int[]> moveIndices = new ArrayList<>();
        int[] currentIndex;
        for (int row = 0; row < congaBoard.rows; row++) {
            for (int col = 0; col < congaBoard.columns; col++) {
                currentIndex = new int[] {row, col};
                if (congaBoard.board[row][col].getPlayer() != null
                    && congaBoard.board[row][col].getPlayer().equals(currentPlayer)) {
                    // passing rows as size so that it works in boards not set to default size of 4
                    moveIndices.addAll(getPossibleIndices(congaBoard, currentIndex, congaBoard.rows));
                    // make the new moves
                    // add the states
                    // return the states
                }
            }
        }
        System.out.println("-----------------------------------------");
        for (int[] i: moveIndices) {
            System.out.println(Arrays.toString(i));
        }
    }

    private static ArrayList<int[]> getPossibleIndices(CongaBoard congaBoard, int[] currentIndex, int size) {
        // valid move types: row, diagonal, column
        int addRow, addCol, subRow, subCol;
        ArrayList<int[]> indices = new ArrayList<>();
        int[] goalIndex;

        for (int col = 1; col < size; col++) {
            addCol = currentIndex[1] + col;
            subCol = currentIndex[1] - col;
            // row move (+)
            goalIndex = new int[] {currentIndex[0], addCol};
            if (checkTiles(congaBoard, currentIndex, goalIndex, size, Move.ROW)) {
                indices.add(goalIndex);
            }
            // row move (-)
            goalIndex = new int[] {currentIndex[0], subCol};
            if (checkTiles(congaBoard, currentIndex, goalIndex, size, Move.ROW)) {
                indices.add(goalIndex);
            }
        }

        for (int row = 1; row < size; row++) {
            addRow = currentIndex[0] + row;
            subRow = currentIndex[0] - row;

            // column move (+)
            goalIndex = new int[] {addRow, currentIndex[1]};
            if (checkTiles(congaBoard, currentIndex, goalIndex, size, Move.COLUMN)) {
                indices.add(goalIndex);
            }
            // column move (-)
            goalIndex = new int[] {subRow, currentIndex[1]};
            if (checkTiles(congaBoard, currentIndex, goalIndex, size, Move.COLUMN)) {
                indices.add(goalIndex);
            }
        }

        for (int diag = 1; diag < size; diag++) {
            int addDiagRow = currentIndex[0] + diag;
            int addDiagCol = currentIndex[1] + diag;
            int subDiagRow = currentIndex[0] - diag;
            int subDiagCol = currentIndex[1] - diag;

            // diagonal move (+) (+)
            goalIndex = new int[] {addDiagRow, addDiagCol};
            if (checkTiles(congaBoard, currentIndex, goalIndex, size, Move.DIAGONAL)) {
                indices.add(goalIndex);
            }
            // diagonal move (-) (-)
            goalIndex = new int[] {subDiagRow, subDiagCol};
            if (checkTiles(congaBoard, currentIndex, goalIndex, size, Move.DIAGONAL)) {
                indices.add(goalIndex);
            }
            // diagonal move (+) (-)
            goalIndex = new int[] {addDiagRow, subDiagCol};
            if (checkTiles(congaBoard, currentIndex, goalIndex, size, Move.DIAGONAL)) {
                indices.add(goalIndex);
            }
            // diagonal move (-) (+)
            goalIndex = new int[] {subDiagRow, addDiagCol};
            if (checkTiles(congaBoard, currentIndex, goalIndex, size, Move.DIAGONAL)) {
                indices.add(goalIndex);
            }
        }
        return indices;
    }

    // Check if the index is valid
    private static boolean checkTiles(CongaBoard congaBoard, int[] currentIndex, int[] goalIndex, int size, Move move) {
        // check if index is valid
        if (currentIndex[0] >= size || currentIndex[0] < 0 || goalIndex[0] >= size || goalIndex[0] < 0
            || currentIndex[1] >= size || currentIndex[1] < 0 || goalIndex[1] >= size || goalIndex[1] < 0) {
            return false;
        }
        Tile currentTile = congaBoard.board[currentIndex[0]][currentIndex[1]];
        Tile goalTile = congaBoard.board[goalIndex[0]][goalIndex[1]];
        Tile tempTile = currentTile;

        int distance = switch (move) {
            case ROW, DIAGONAL -> Math.abs(currentIndex[1] - goalIndex[1]);
            case COLUMN -> Math.abs(currentIndex[0] - goalIndex[0]);
            default -> 0;
        };
        // check if you have enough pieces to make move
        if (currentTile.getCount() < getMinimumPieces(distance)) {
            return  false;
        }
        // check if there is any other between goal and current tile
        while (tempTile != goalTile) {
            tempTile = congaBoard.getNextTile(tempTile, goalTile, move);
            if (tempTile.getPlayer() != null && tempTile.getPlayer().getColor() != currentTile.getPlayer().getColor()) {
                return false;
            }
        }
        return true;
    }

    /*
     * Get minimum amount of pieces required to make move
     *
     * @param   distance: Distance need to move
     *
     * @return  number of pieces required to make move
     */
    private static int getMinimumPieces(int distance) {
        if (distance == -1) {
            return Integer.MAX_VALUE;
        }
        /* Algorithm to calculate minimum number of pieces required to move
         * Sum of n numbers = n(n+1) / 2
         * Sum of n-1 numbers = n(n-1) / 2
         * Minimum required number of pieces to make move = (n(n-1) / 2) + 1
         */
        return ((distance * (distance - 1) / 2) + 1);
    }

}

// TODO: use player occupied tile to make efficient