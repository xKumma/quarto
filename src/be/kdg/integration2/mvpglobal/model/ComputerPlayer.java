package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.InferenceEngine;

import java.util.Random;

public class ComputerPlayer extends Player{

    private BotDifficulty difficulty;

    public ComputerPlayer() {
        super();
        //...
    }


    public Move getMove (GameSession gameSession){ // method to be completed
        InferenceEngine engine = new InferenceEngine();
        Move move = new Move();
        Board board = gameSession.getBoard();
        engine.determineFacts(board);
        engine.applyRules(board, move);

        return move;
    }

    public PositionData getPosition(GameSession gameSession) {
        PositionData positionData = null;

        switch (difficulty) {
            case MEDIUM -> {
                // medium complexity
            }

            case HARD -> {
                // high complexity
            }

            default -> {
                // simple
                Random prng = new Random();
                positionData = new PositionData(
                        prng.nextInt(gameSession.getBoard().getPieces().length),
                        prng.nextInt(gameSession.getBoard().getPieces()[0].length)
                );
            }
        }
        return positionData;
    }

    public String getPiece(GameSession gameSession) {
        Piece piece = null;

        switch (difficulty) {
            case MEDIUM -> {
                // medium complexity
            }

            case HARD -> {
                // high complexity
            }

            default -> {
                // simple
                Random prng = new Random();
                if (gameSession.getUnusedPieces().isEmpty()) return null;
                piece = gameSession.getUnusedPieces().get(prng.nextInt(gameSession.getUnusedPieces().size()));
            }
        }
        return piece != null ? piece.toString() : null;
    }

    public BotDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(BotDifficulty difficulty) {
        this.difficulty = difficulty;
    }
}
