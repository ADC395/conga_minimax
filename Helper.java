public class Helper {
    static int evaluateBoard(CongaBoard congaBoard) {
        Tile[][] board = congaBoard.board;
        int[][] evalIndex = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
        int whiteMove = 0;
        int blackMove = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                for (int[] eval: evalIndex) {
                    try {
                        if (congaBoard.checkMove(board[r][c], board[r+eval[0]][c+eval[1]]) != CongaBoard.Move.INVALID) {
                            if (board[r][c].getPlayer().getColor() == Color.BLACK) {
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
            return whiteMove - blackMove;
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
