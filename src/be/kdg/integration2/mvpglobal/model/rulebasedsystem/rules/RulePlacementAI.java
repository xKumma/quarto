package be.kdg.integration2.mvpglobal.model.rulebasedsystem.rules;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactValues;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactsHandler;

/**
 * This rule triggers if the AI is currently picking a position to place a piece
 * and there aren't possible positions that will win the game.<br>
 * The AI will choose a random position in this case.
 */
public class RulePlacementAI extends Rule {
    @Override
    public boolean conditionRule(FactsHandler facts) {
        return facts.factAvailable(FactValues.AIPLACING);
    }

    @Override
    public boolean actionRule(FactsHandler facts, Board board, Move move) {
        board.determinePositionMove(move);
        return true;     // returns true if the new move was determined, returns false if only the facts have been modified
    }
}
