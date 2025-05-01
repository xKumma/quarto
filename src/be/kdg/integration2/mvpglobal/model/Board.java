package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.pieces.Piece;

public class Board  {

    private Piece[][] pieces = new Piece[4][4];

    public Board(String[][] board) {
        // parse board gotten from the DB
    }

    public Board() {
    }

    public void movePiece(Move move) {
        pieces[move.getPosition().x()][move.getPosition().y()] = move.getPiece();
    }

    public Piece[][] getPieces() {
        return pieces;
    }


    // region AI
    public boolean endMoveAIPossible() {

        return false;
    }
    public boolean endMovePlayerPossible() {


        return false;
    }
    public boolean otherFactPossible() {

        return false;
    }
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
    // endregion
}