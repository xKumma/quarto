package be.kdg.integration2.mvpglobal.model.rulebasedsystem.rules;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactValues;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactsHandler;

/**
 * This rule triggers if the AI is currently picking a position to place a piece
 * and there are possible positions that will win the game.<br>
 * The AI will choose this position, ensuring it wins the game.
 */
public class RuleWinningPositionAI extends Rule{
    @Override
    public boolean conditionRule(FactsHandler facts) {
        System.out.println("Condition RuleWinningPositionAI executed"); // Test code - to be removed!
        return facts.factAvailable(FactValues.WINNINGPOSITIONAI);
    }

    @Override
    public boolean actionRule(FactsHandler facts, Board board, Move move) {
        System.out.println("Action RuleWinningPositionAI executed"); // Test code - to be removed!
        // Code to be added is this rule could initiate a new fact

        board.determineWinningPositionMove(move);
        return true;     // returns true if the new move was determined, returns false if only the facts have been modified
    }
}

