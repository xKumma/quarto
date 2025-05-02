package be.kdg.integration2.mvpglobal.model.rulebasedsystem;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.GameSession;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.TurnPhase;
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
    public void determineFacts (GameSession session) {
        Board board = session.getBoard();
        currentFacts.resetFacts();
        currentFacts.setFactsEvolved(false);
        // Determine which FactValues are currently present on the given board
        if (session.getTurnPhase() == TurnPhase.PLACING ) {
            currentFacts.addFact(FactValues.AIPLACING);
            if (board.endWinningPositionPossible(session.getSelectedPiece())) {
                currentFacts.addFact(FactValues.WINNINGPOSITIONAI);
            }
            if (board.centerPositionPossible()) {
                currentFacts.addFact(FactValues.CENTERAVAILABLE);
            }
        }
        else {
            currentFacts.addFact(FactValues.AIPICKING);
            board.setPieceScores(session.getUnusedPieces());
        }

        if (session.getMoves().size() < 4) {
            currentFacts.addFact(FactValues.EARLYGAME);
        }
    }

    /**
     *   rules are ordered - stops when a rule has been fired and starts re-evaluating the rules when the facts have been changed.
     */
    public void applyRules (GameSession session, Move move) {
        if (currentFacts.factsObserved()) {
            ruleFired = false;
            int i;
            do {
                currentFacts.setFactsEvolved(false);
                i = 0;
                while (i < currentRules.numberOfRules() && !ruleFired && !currentFacts.factsChanged()) {
                    if (currentRules.checkConditionRule(i, currentFacts)) {
                        ruleFired = currentRules.fireActionRule(i, currentFacts, session.getBoard(), move);
                    }
                    i++;
                }
            } while (i < currentRules.numberOfRules() && !ruleFired);
            if (!ruleFired) {
                if (currentFacts.factsChanged()) applyRules(session, move);
            }
        } else {
            session.getBoard().determineRandomMove(move);
        }
    }

}