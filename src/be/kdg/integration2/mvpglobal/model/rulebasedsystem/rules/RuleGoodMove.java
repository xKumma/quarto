package be.kdg.integration2.mvpglobal.model.rulebasedsystem.rules;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactsHandler;

public class RuleGoodMove extends Rule{
    public RuleGoodMove() {
    }

    @Override
    public boolean conditionRule(FactsHandler facts) {
        System.out.println("Condition RuleGoodMove executed"); // Test code - to be removed!
        return false;
    }

    @Override
    public boolean actionRule(FactsHandler facts, Board board, Move move) {
        System.out.println("Action RuleGoodMove executed"); // Test code - to be removed!
        // Code to be added is this rule could initiate a new fact

        board.determineGoodMove(move);
        return true;     // returns true if the new move was determined, returns false if only the facts have been modified
    }
}
