package be.kdg.integration2.mvpglobal.model.rulebasedsystem.rules;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.rulebasedsystem.facts.FactsHandler;

public abstract class Rule {
    public abstract boolean conditionRule(FactsHandler facts);
    public abstract boolean  actionRule(FactsHandler facts, Board board, Move move);  // returns true if the new move was determined, returns false if only the facts have been modified
}

