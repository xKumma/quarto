package be.kdg.integration2.mvpglobal.view.gamescreen;

import be.kdg.integration2.mvpglobal.model.*;
import be.kdg.integration2.mvpglobal.model.dataobjects.BoardUpdateData;
import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.dataobjects.TimeUpdateData;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.utility.SaveManager;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.PieceButton;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class GameScreenPresenter extends BasePresenter<GameScreenView, GameSession> {

    private AnimationTimer timer;

    public GameScreenPresenter(BaseView view, BaseModel model) {
        super((GameScreenView)  view,(GameSession)  model);
    }

    @Override
    public void init(Object data) {
        super.init(data);
        setUpBoard(model.getBoard().getPieces(), model.getUnusedPieces());

        startTurn();
    }

    @Override
    public void updateView() {
        super.updateView();
    }

    public void updateView(Move move) {
        view.update(new BoardUpdateData(
                move.getPiece().toString(),
                move.getPosition().x(),
                move.getPosition().y()
        ));
    }

    @Override
    protected void addEventHandlers() {
        super.addEventHandlers();
        view.getMenuBtn().setOnAction(e -> goToMenu());

        for (Node pieceBtn : view.getUnusedPieces().getChildren()) {
            if (pieceBtn instanceof PieceButton) {
                pieceBtn.setOnMouseClicked(e -> {
                    if (!model.isPlayersTurn() || !model.isActive()) return;
                    if (model.getTurnPhase() != TurnPhase.PICKING) return;

                    System.out.println("Selecting piece: " + pieceBtn.toString());
                    if (model.selectPiece(pieceBtn.toString())) {
                        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        endTurn();
                    }
                });
            }
        }

        for (Node pieceBtn : view.getBoard().getChildren()) {
            if (pieceBtn instanceof PieceButton) {
                pieceBtn.setOnMouseClicked(e -> {
                    if (!model.isPlayersTurn() || !model.isActive()) return;
                    if (model.getTurnPhase() != TurnPhase.PLACING) return;

                    PositionData positionData = new PositionData(
                            GridPane.getColumnIndex(pieceBtn),
                            GridPane.getRowIndex(pieceBtn)
                    );
                    if (model.selectPosition(positionData)) {
                        System.out.println("Selected position: " + positionData);
                        System.out.println("Updating view with: " + model.getCurrentMove());
                        updateView(model.getCurrentMove());
                    }
                });
            }
        }
        view.getChosenPieceGrid().setOnMouseClicked(e -> SaveManager.saveToFile(model.getSessionData()));
    }

    private void goToMenu() {
        Router.getInstance().goTo(Screen.MAIN_MENU, null);
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


    public void updateTurn(){

    }

    public void startTurn() {
        startTimer();
        model.startNewTurn();
        view.setChosenPiece(model.getCurrentMove().getPiece().toString());
        if (model.isPlayersTurn()) return;

        botPlay();
    }

    private void botPlay() {

        boolean success = false;

        while (!success) {
            PositionData positionData = model.getComputerPlayer().getPosition(model);
            System.out.println("Trying: " + positionData);
            success = model.selectPosition(positionData);
        }

        updateView(model.getCurrentMove());

        if (!model.isActive()) return;
        success = false;


        while (!success) {
            success = model.selectPiece(model.getComputerPlayer().getPiece(model));
        }

        endTurn();
    }

    public void endTurn() {
        model.endTurn();

        stopTimer();

        startTurn();
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
}
