package poc;

public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(Move move) {
      super(String.format("Invalid move by %s!!! (Pos: %d:%d )",
              move.getPlayer().name , move.getPosition().x(), move.getPosition().y()));
    }
}
