package be.kdg.integration2.mvpglobal.model;

public class Board  {
    public boolean endMoveAIPossible() {
        return false;
    }
    public boolean endMovePlayerPossible() {
        return false;
    }
    public boolean otherFactPossible() { return false;}
    public boolean endWinningPositionAIPossible() {
        return false;
    }
    public boolean endWinningPositionPlayerPossible() {
        return false;
    }
    public void determineBlockEndMove (Move move) {
        // change attributes of move
    }
    public void determineBlockWinningPositionMove (Move move) {
        // change attributes of move
    }
    public void determineEndMove (Move move) {
        // change attributes of move
    }
    public void determineGoodMove (Move move) {
        // change attributes of move
    }
    public void determineRandomMove (Move move) {
        // change attributes of move
    }
    public void determineWinningPositionMove (Move move) {
        // change attributes of move
    }
}