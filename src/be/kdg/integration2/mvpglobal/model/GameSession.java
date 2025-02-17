package be.kdg.integration2.mvpglobal.model;

public class GameSession {

    ComputerPlayer computer;
    HumanPlayer player;
    Board board;

    public GameSession () {
        this.board = new Board();
        this.player = new HumanPlayer();
        this.computer = new ComputerPlayer();
        //...
    }
    public void play(){
        //...

        // determine move AI:
        Move move = computer.getMove(this);
        // ...
    }

    public Board getBoard () {return board;}
}