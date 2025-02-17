package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.rulebasedsystem.InferenceEngine;

public class ComputerPlayer extends Player{

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
}
