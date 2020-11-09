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

    static Tile[][][] getNextStates(CongaBoard congaBoard, Player player) {
        Tile[][][] nextStates;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                getPossibleIndex(currentIndex, Move.ROW);
                getPossibleIndex(currentIndex, Move.COLUMN);
                getPossibleIndex(currentIndex, Move.DIAGONAL);
            }
        }
        return nextStates;
    }

    private static int[][] getPossibleIndex(int[] currentIndex, Move moveType) {
        // valid move types: row, diagonal, column
        switch (moveType) {
            case ROW:
                // possible row move: +- 1, 2, 3
                for (int row = 0; row < 3; row++) {

                }
                break;
            case COLUMN:
                break;
            case DIAGONAL:
                break;
        }
    }
}
// Check for goal condition
// Brute force
//                congaBoard.checkMove(board[r][c], board[r+1][c]);
//                congaBoard.checkMove(board[r][c], board[r-1][c]);
//                congaBoard.checkMove(board[r][c], board[r][c+1]);
//                congaBoard.checkMove(board[r][c], board[r][c-1]);
//                congaBoard.checkMove(board[r][c], board[r+1][c+1]);
//                congaBoard.checkMove(board[r][c], board[r-1][c-1]);
//                congaBoard.checkMove(board[r][c], board[r+1][c-1]);
//                congaBoard.checkMove(board[r][c], board[r-1][c+1]);
