
public class Main {
    public static void main(String[] args) {
        CongaBoard congaBoard = new CongaBoard();
        Tile testTile = new Tile(new int[]{0, 0});
        testTile.setPlayer(new Player(Color.BLACK, testTile.getId()));
        System.out.println(congaBoard.board[0][0].equals(testTile));
    }
}
