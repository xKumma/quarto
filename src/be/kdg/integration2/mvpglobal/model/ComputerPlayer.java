package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.rulebasedsystem.InferenceEngine;

public class ComputerPlayer extends Player{

    private BotDifficulty difficulty;

    public ComputerPlayer() {
        super();
        //...
    }

    public Move getMove (GameSession gameSession){
        InferenceEngine engine = new InferenceEngine();
        Move move = new Move("bot", gameSession.getSelectedPiece());
        engine.determineFacts(gameSession);
        engine.applyRules(gameSession, move);

        return move;
    }

    public BotDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(BotDifficulty difficulty) {
        this.difficulty = difficulty;
    }
}
