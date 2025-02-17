package be.kdg.integration2.mvpglobal.model.rulebasedsystem;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactValues;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactsHandler;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.rules.RulesHandler;

public class InferenceEngine {
    private FactsHandler currentFacts;
    private RulesHandler currentRules;
    private boolean ruleFired;
    public InferenceEngine() {
        currentFacts = new FactsHandler();
        currentRules = new RulesHandler();
        ruleFired = false;
    }
    public void determineFacts (Board board) {
        currentFacts.resetFacts();
        currentFacts.setFactsEvolved(false);
        // Determine which FactValues are currently present on the given board
        if (board.endMoveAIPossible()) {
            currentFacts.addFact(FactValues.ENDMOVEAI);
        }
        if (board.endMovePlayerPossible()) {
            currentFacts.addFact(FactValues.ENDMOVEPLAYER);
        }
        if (board.otherFactPossible()) {
            currentFacts.addFact(FactValues.OTHERFACT);
        }
        if (board.endWinningPositionAIPossible()) {
            currentFacts.addFact(FactValues.WINNINGPOSITIONAI);
        }
        if (board.endWinningPositionPlayerPossible()) {
            currentFacts.addFact(FactValues.WINNINGPOSITIONPLAYER);
        }
        // Test code - examples - to be removed :
        currentFacts.addFact(FactValues.WINNINGPOSITIONAI);
        currentFacts.addFact(FactValues.OTHERFACT);
    }

    /**
     *   rules are ordered - stops when a rule has been fired and starts re-evaluating the rules when the facts have been changed.
     */
    public void applyRules (Board board, Move move) {
        if (currentFacts.factsObserved()) {
            ruleFired = false;
            int i;
            do {
                currentFacts.setFactsEvolved(false);
                i = 0;
                while (i < currentRules.numberOfRules() && !ruleFired && !currentFacts.factsChanged()) {
                    if (currentRules.checkConditionRule(i, currentFacts)) {
                        ruleFired = currentRules.fireActionRule(i, currentFacts, board, move);
                    }
                    i++;
                }
            } while (i < currentRules.numberOfRules() && !ruleFired);
            if (!ruleFired) {
                System.out.println("No rules were fired!!"); // Test code - to be removed!
                board.determineRandomMove(move);
            }
        } else {
            board.determineRandomMove(move);
        }
    }

}