package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;
import be.kdg.integration2.mvpglobal.model.pieces.PieceAttribute;

import java.util.*;

public class Board  {
    private Piece[][] pieces = new Piece[4][4];
    private Map<Piece, Integer> pieceScores;

    public Board() {}

    /**
     * Plays a move on the board
     *
     * @param move move to play
     */
    public void movePiece(Move move) {
        pieces[move.getPosition().x()][move.getPosition().y()] = move.getPiece();
    }


    /**
     * Scores a piece based on how many attributes match with the pieces already on the board.<br>
     * The more attributes match, the higher the score.
     *
     * @param piece The piece to score.
     * @return The score of the piece.
     */
    public int scorePiece(Piece piece) {
        Map<PieceAttribute, Object> pieceAttributes = piece.getAttributes();
        int score = 0;
        for (Piece[] value : pieces) {
            for (int y = 0; y < pieces[0].length; y++) {
                Piece placed = value[y];
                if (placed == null) continue;

                Map<PieceAttribute, Object> placedAttrs = placed.getAttributes();

                // Count how many attributes match
                for (PieceAttribute key : pieceAttributes.keySet()) {
                    if (pieceAttributes.get(key).equals(placedAttrs.get(key))) {
                        score++;
                    }
                }
            }
        }
        return score;
    }

    // region AI

    /**
     * Checks if there are any empty positions in the center of the board.<br>
     * The center square is defined as the square with coordinates [1][1] to [2][2].
     *
     * @return True if there are empty positions in the center square, false otherwise.
     */
    public boolean centerPositionPossible() {
        for (int x = 1; x <= 2; x++) {
            for (int y = 1; y <= 2; y++) {
                if (pieces[x][y] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there is a winning position for the given piece on the board.<br>
     * If there is, it returns true.
     *
     * @param selectedPiece The piece to check for a winning position.
     * @return True if there is a winning position, false otherwise.
     */
    public boolean endWinningPositionPossible(Piece selectedPiece) {
        return getWinningPosition(selectedPiece) != null;
    }

    /**
     * Determines a risky end move for the AI.<br>
     * A risky move is defined as placing the piece with the highest score,
     * which is the riskiest to place on the board.
     *
     * @param move The move to set the piece for.
     */
    public void determineRiskyEndMove (Move move) {
        move.setPiece(getRiskyPiece());
    }

    /**
     * Determines a safe end move for the AI.<br>
     * A safe move is defined as placing the piece with the lowest score,
     * which is the safest to place on the board.
     *
     * @param move The move to set the piece for.
     */
    public void determineSafeEndMove(Move move) {
        move.setPiece(getSafePiece());
    }

    /**
     * Determines a preventive end move for the AI.<br>
     * A preventive move is defined as placing the piece with the lowest score,
     * which is the safest to place on the board, but only if it does not lead to a winning position.
     *
     * @param move The move to set the piece for.
     */
    public void determinePreventiveEndMove(Move move) {
        List<Piece> sortedBySafety = pieceScores.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue)) // lowest score first
                .map(Map.Entry::getKey)
                .toList();

        for (Piece candidate : sortedBySafety) {
            if (!endWinningPositionPossible(candidate)) {
                move.setPiece(candidate);
                return;
            }
        }

        // Fallback: no safe piece found, return the safest anyway
        move.setPiece(sortedBySafety.getFirst());
    }

    /**
     * Determines a random move for the AI.<br>
     * If the position is not set, a random position is determined.<br>
     * If the piece is not set, a random piece is determined.
     *
     * @param move The move to set the position and piece for.
     */
    public void determineRandomMove (Move move) {
        if (move.getPosition() == null) {
            determinePositionMove(move);
        } else {
            move.setPiece(getRandomPiece());
        }
    }

    /**
     * Determines a random position on the board.<br>
     *
     * @param move The move to set the position for.
     */
    public void determinePositionMove (Move move) {
        var pos = getRandomPosition();
        move.setPosition(pos.x(), pos.y());
    }

    /**
     * Determines a move that will win the game if possible.<br>
     * If there is no winning position, a random move is made instead.
     *
     * @param move The move to set the position for.
     */
    public void determineWinningPositionMove (Move move) {
        var pos = getWinningPosition(move.getPiece());
        if (pos == null) {
            determineRandomMove(move); // fallback
            return;
        }
        move.setPosition(pos.x(), pos.y());
    }

    /**
     * Determines a move in the center of the board.<br>
     * The center square is defined as the square with coordinates [1][1] to [2][2].<br>
     * If there are no empty positions in the center square, a random move is made instead.
     *
     * @param move The move to set the position for.
     */
    public void determineCentralPositionMove(Move move) {
        Random rand = new Random();

        List<PositionData> centerCandidates = new ArrayList<>();
        for (int x = 1; x <= 2; x++) {
            for (int y = 1; y <= 2; y++) {
                if (pieces[x][y] == null) {
                    centerCandidates.add(new PositionData(x, y));
                }
            }
        }

        if (!centerCandidates.isEmpty()) {
            PositionData pos = centerCandidates.get(rand.nextInt(centerCandidates.size()));
            move.setPosition(pos.x(), pos.y());
        } else {
            determineRandomMove(move); // fallback
        }
    }

    /**
     * Returns a random position on the board that is empty.
     *
     * @return A random position on the board.
     */
    public PositionData getRandomPosition() {
        Random prng = new Random();
        PositionData positionData = null;

        boolean success = false;
        while (!success) {
            positionData = new PositionData(
                    prng.nextInt(pieces.length),
                    prng.nextInt(pieces[0].length)
            );

            success = pieces[positionData.x()][positionData.y()] == null;
        }
        return positionData;
    }

    /**
     * Checks if there is a winning position for the given piece on the board.<br>
     * If there is, it returns the position data of the winning position.
     *
     * @param piece The piece to check for a winning position.
     * @return The position data of the winning position, or null if there is no winning position.
     */
    public PositionData getWinningPosition(Piece piece) {
        for (int x = 0; x < pieces.length; x++) {
            for (int y = 0; y < pieces[0].length; y++) {
                if (pieces[x][y] != null) continue;

                // Simulate placing the piece
                pieces[x][y] = piece;
                boolean wins = RuleChecker.fourInARow(this);
                pieces[x][y] = null; // Undo

                if (wins) {
                    return new PositionData(x, y);
                }
            }
        }
        return null;
    }

    /**
     * Returns a random piece from the unused pieces list.
     *
     * @return A random piece from the unused pieces list.
     */
    public Piece getRandomPiece() {
        return pieceScores.keySet().stream().toList().get(new Random().nextInt(pieces.length));
    }

    /**
     * Returns a piece that has the lowest score, meaning it is the safest to place on the board.
     * If there are multiple pieces with the same score, one is chosen at random.
     *
     * @return The safest piece to place on the board.
     */
    public Piece getSafePiece() {
        int minScore = Collections.min(pieceScores.values());

        List<Piece> safestPieces = pieceScores.entrySet().stream()
                .filter(e -> e.getValue() == minScore)
                .map(Map.Entry::getKey)
                .toList();

        return safestPieces.get(new Random().nextInt(safestPieces.size()));
    }

    /**
     * Returns a piece that has the highest score, meaning it is the riskiest to place on the board.
     * If there are multiple pieces with the same score, one is chosen at random.
     *
     * @return The riskiest piece to place on the board.
     */
    public Piece getRiskyPiece() {
        int maxScore = Collections.max(pieceScores.values());

        List<Piece> riskyPieces = pieceScores.entrySet().stream()
                .filter(e -> e.getValue() == maxScore)
                .map(Map.Entry::getKey)
                .toList();

        return riskyPieces.get(new Random().nextInt(riskyPieces.size()));
    }

    // endregion

    // region Getters and Setters

    /**
     * Gets the board
     * @return board as a 2D array of Pieces
     */
    public Piece[][] getPieces() {
        return pieces;
    }


    /**
     * Sets the piece scores for the unused pieces.<br>
     * The board calculates the scores based on how many attributes match with the pieces already on the board.
     *
     * @param unusedPieces The list of unused pieces to score.
     */
    public void setPieceScores(List<Piece> unusedPieces) {
        pieceScores = new HashMap<>();

        for (Piece piece : unusedPieces) {
            pieceScores.put(piece, scorePiece(piece));
        }
    }

    // endregion
}