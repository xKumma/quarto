package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.rulebasedsystem.InferenceEngine;

/**
 * The type Computer player.
 */
public class ComputerPlayer extends Player{

    private BotDifficulty difficulty;

    /**
     * Instantiates a new Computer player.
     */
    public ComputerPlayer() {
        super();
        //...
    }

    /**
     * Gets a move for the bot to play
     *
     * @param gameSession the current session
     * @return a move decided by the Inference Engine based on the current session state
     */
    public Move getMove (GameSession gameSession){
        InferenceEngine engine = new InferenceEngine();
        Move move = new Move("bot", gameSession.getSelectedPiece());
        engine.determineFacts(gameSession);
        engine.applyRules(gameSession, move);

        return move;
    }


    /**
     * Gets difficulty.
     *
     * @return the difficulty
     */
    public BotDifficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Sets difficulty.
     *
     * @param difficulty the difficulty
     */
    public void setDifficulty(BotDifficulty difficulty) {
        this.difficulty = difficulty;
    }
}
