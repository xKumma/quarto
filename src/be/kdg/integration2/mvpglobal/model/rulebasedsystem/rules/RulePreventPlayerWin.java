package be.kdg.integration2.mvpglobal.model.rulebasedsystem.rules;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactValues;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactsHandler;

/**
 * This rule triggers if the AI initially picked a piece that would allow the player to win.<br>
 * In this case, it will try to pick another safe piece that won't allow the player to win.<br>
 * If no such piece is found, it just selects the safest piece, hoping the player won't notice they can win.
 */
public class RulePreventPlayerWin extends Rule {
    @Override
    public boolean conditionRule(FactsHandler facts) {
        return facts.factAvailable(FactValues.PLAYERCANWIN);
    }

    @Override
    public boolean actionRule(FactsHandler facts, Board board, Move move) {
        board.determinePreventiveEndMove(move);
        return true;
    }
}
