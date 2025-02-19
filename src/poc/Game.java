package poc;

public class Game {
    private GameSession session;

    public void makeMove(Move move) {
        session.makeMove(move);
    }

    public void endGame() {}
}
