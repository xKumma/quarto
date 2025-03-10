package be.kdg.integration2.mvpglobal.view.gamescreen;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.GameSession;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import be.kdg.integration2.mvpglobal.model.dataobjects.BoardUpdateData;
import be.kdg.integration2.mvpglobal.model.dataobjects.TimeUpdateData;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class GameScreenPresenter extends BasePresenter<GameScreenView, GameSession> {

    private AnimationTimer timer;

    public GameScreenPresenter(BaseView view, BaseModel model, UISettings uiSettings) {
        super((GameScreenView)  view,(GameSession)  model, uiSettings);

        setUpBoard(((GameSession) model).getBoard1(),((GameSession) model).getUnusedPieces());

        TestMove();
    }

    @Override
    public void updateView() {
        super.updateView();
    }

    @Override
    protected void addEventHandlers() {
        super.addEventHandlers();
    }

    private void setUpBoard(Piece[][] board, List<Piece> unusedPieces) {
        List<BoardUpdateData> updates = new ArrayList<>();

        for (int i = 0; i < unusedPieces.size(); i++) {
            Piece piece = unusedPieces.get(i);

            int x = -(i/2)-1;
            int y = -(i%2)-1;

            updates.add(new BoardUpdateData(piece.toString(), x, y));
        }

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                Piece piece = board[x][y];
                
                if (piece == null) {continue;}
                
                updates.add(new BoardUpdateData(piece.toString(), x, y));
            }
        }

        for (BoardUpdateData updateData : updates) {
            view.update(updateData);
        }
    }

    public void updateBoard(Move move) {
        List<BoardUpdateData> updates = new ArrayList<>();

        updates.add(new BoardUpdateData(
                move.getPiece().toString(),
                move.getPosition().x(),
                move.getPosition().y()
                )
        );

        for (Node node : view.unusedPieces.getChildren()) {
            if (node.toString().equals(move.getPiece().toString())) {
                System.out.println(node + " " + (move.getPiece().toString()));

                int x = -(GridPane.getColumnIndex(node)+1);
                int y = -(GridPane.getRowIndex(node)+1);

                updates.add(new BoardUpdateData(null, x, y)
                );
            }
        }

        for (BoardUpdateData updateData : updates) {
            view.update(updateData);
        }
    }

    public void updateTurn(){

    }

    public void startTurn() {
        model.startNewTurn();
        startTimer();
    }

    public void endTurn() {
        model.endTurn();
        stopTimer();
        updateView();
        System.out.println("End turn");
    }

    /*
    * TODO: Optimize, a lot
    *  Move timer operations solely to View or a separate thread
    */

    private void startTimer() {
        long startTime = System.nanoTime();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedNano = now - startTime;
                long turnTime = elapsedNano / 1_000_000;
                long totalTime = model.getTotalElapsedTime() + turnTime;
                view.update(new TimeUpdateData(totalTime, turnTime));
            }
        };
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void TestMove(){
        System.out.println("\n\nMaking a move");
        updateBoard(model.moves.get(1));
        System.out.println("\n\nMaking a move");
        updateBoard(model.moves.get(2));
        startTurn();
    }
}
