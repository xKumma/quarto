package be.kdg.integration2.mvpglobal.model.rulebasedsystem.rules;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactValues;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactsHandler;

/**
 * This rule triggers if the AI is currently picking a position to place a piece
 * and there are free spots in the center of the board.<br>
 * The AI will choose one of these central spots as they provide more future possibilities.
 */
public class RuleCenterPlacementAI extends Rule {
    @Override
    public boolean conditionRule(FactsHandler facts) {
        return facts.factAvailable(FactValues.CENTERAVAILABLE) && facts.factAvailable(FactValues.AIPLACING);
    }

    @Override
    public boolean actionRule(FactsHandler facts, Board board, Move move) {
        board.determineCentralPositionMove(move);
        return true;
    }
}
