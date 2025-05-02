package be.kdg.integration2.mvpglobal.model.rulebasedsystem.rules;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactsHandler;

import java.util.ArrayList;
import java.util.List;

public class RulesHandler {

    private List<Rule> rules = new ArrayList<>();

    /**
     * Order of the rules:<br>
     * PLACING:<br>
     * 1. if the AI can win
     *    -> the AI makes this move<br>
     * 2. if the AI can place a piece in the center
     *    -> the AI makes this move<br>
     * 3. if none of the above
     *    -> the AI makes a random move<br>
     * <br>
     * PICKING:<br>
     * 4. if the player would win with the selected piece
     *   -> the AI tries to pick a different piece<br>
     * 5. if the game is in the early stages
     *   -> the AI tries to pick a risky piece with a high score<br>
     * 6. if none of the above
     *  -> the AI picks a safe piece<br>
     */
    public RulesHandler (){
        rules.add (0, new RuleWinningPositionAI());
        rules.add (1, new RuleCenterPlacementAI());
        rules.add (2, new RulePlacementAI());
        rules.add (3, new RulePreventPlayerWin());
        rules.add (4, new RuleEarlyEndMoveAI());
        rules.add (5, new RuleEndMoveAI());
    }
    public boolean checkConditionRule (int index, FactsHandler facts) {
        return rules.get(index).conditionRule(facts);
    }
    public boolean fireActionRule (int index, FactsHandler facts, Board board, Move move) {
        return rules.get(index).actionRule(facts, board, move);
    }
    public int numberOfRules () {
        return rules.size();
    }
}

