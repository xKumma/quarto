package be.kdg.integration2.mvpglobal.model.rulebasedsystem.rules;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactValues;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactsHandler;

public class RuleBlockWinningPositionPlayer extends Rule{
    public RuleBlockWinningPositionPlayer() {
    }

    @Override
    public boolean conditionRule(FactsHandler facts) {
        System.out.println("Condition RuleBlockWinningPositionPlayer executed"); // Test code - to be removed!
        return facts.factAvailable(FactValues.WINNINGPOSITIONPLAYER);
    }
    @Override
    public boolean actionRule(FactsHandler facts, Board board, Move move) {
        System.out.println("Action RuleBlockWinningPositionPlayer executed"); // Test code - to be removed!
        // Code to be added is this rule could initiate a new fact
        board.determineBlockWinningPositionMove(move);
        return true;     // returns true if the new move was determined, returns false if only the facts have been modified
    }
}

