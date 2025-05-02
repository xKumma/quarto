package be.kdg.integration2.mvpglobal.model.rulebasedsystem.rules;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactValues;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactsHandler;

/**
 * This rule triggers if the AI is currently picking a piece for the player to place
 * and the game is still in its early stages. <br>
 * The AI will choose a riskier piece in this case to hopefully have
 * the player set it up for a better chance of winning.<br>
 * If the risky piece causes the player to win, the Rule will set this as a fact and not fire.
 */
public class RuleEarlyEndMoveAI extends Rule {
    @Override
    public boolean conditionRule(FactsHandler facts) {
        return facts.factAvailable(FactValues.EARLYGAME) && facts.factAvailable(FactValues.AIPICKING);
    }

    @Override
    public boolean actionRule(FactsHandler facts, Board board, Move move) {
        board.determineRiskyEndMove(move);

        if (board.endWinningPositionPossible(move.getPiece())) {
            facts.addFact(FactValues.PLAYERCANWIN);
            return false;
        }

        return true;     // returns true if the new move was determined, returns false if only the facts have been modified
    }
}
