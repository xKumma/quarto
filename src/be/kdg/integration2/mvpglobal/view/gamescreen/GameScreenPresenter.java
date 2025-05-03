package be.kdg.integration2.mvpglobal.view.gamescreen;

import be.kdg.integration2.mvpglobal.model.*;
import be.kdg.integration2.mvpglobal.model.dataobjects.BoardUpdateData;
import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.dataobjects.RoundUpdateData;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.utility.SaveManager;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.PieceButton;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameScreenPresenter extends BasePresenter<GameScreenView, GameSession> {

    private GameTimer gameTimer;

    public GameScreenPresenter(BaseView view, BaseModel model) {
        super((GameScreenView)  view,(GameSession)  model);
    }

    @Override
    public void init(Object data) {
        super.init(data);
        setUpBoard(model.getBoard().getPieces(), model.getUnusedPieces());

        Router.getInstance().getPrimaryStage().setOnCloseRequest(event -> {
            event.consume(); // cancel default close

            boolean confirmed = confirmSaveBeforeExit();
            if (confirmed) {
                Platform.exit();
            }
        });


        gameTimer = new GameTimer(timeData -> view.update(timeData));

        startTurn();
    }

    @Override
    public void updateView() {
        super.updateView();
    }

    /**
     * Updates the view with the details of the given move.
     *
     * @param move The move containing the piece and its new position.
     */
    public void updateView(Move move) {
        view.update(new BoardUpdateData(
                move.getPiece().toString(),
                move.getPosition().x(),
                move.getPosition().y()

        ));
        if(!model.isActive()){
            SaveManager.saveToDB(model.getSessionData());
            gowin();
        }
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

                    if (model.selectPiece(pieceBtn.toString())) {
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
                        updateView(model.getCurrentMove());
                    }
                });
            }
        }
        view.getChosenPieceGrid().setOnMouseClicked(e -> SaveManager.saveToDB(model.getSessionData()));
    }

    private void goToMenu() {
        boolean shouldContinue = confirmSaveBeforeExit();
        if (!shouldContinue) return;

        Router.getInstance().goTo(Screen.MAIN_MENU, null);
    }
    private void gowin() {
        Router.getInstance().goTo(Screen.END_SCREEN, null);
    }


    /**
     * Sets up the game board visuals by updating the view with the positions of all pieces.
     *
     * @param board        A 2D array representing the current state of the board, where each element is a `Piece`.
     * @param unusedPieces A list of pieces that are not currently placed on the board for the unused pieces grid visual.
     */
    private void setUpBoard(Piece[][] board, List<Piece> unusedPieces) {
        List<BoardUpdateData> updates = new ArrayList<>();

        for (int i = 0; i < unusedPieces.size(); i++) {
            Piece piece = unusedPieces.get(i);

            // Calculate the x and y coordinates for the unused pieces GridPane.
            // To use the same method as for the board, we make this calculation that is then reversed to get the actual position.
            int x = -(i / 2) - 1;
            int y = -(i % 2) - 1;

            updates.add(new BoardUpdateData(piece.toString(), x, y));
        }

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                Piece piece = board[x][y];

                if (piece == null) {
                    continue; // Skip empty positions on the board.
                }

                updates.add(new BoardUpdateData(piece.toString(), x, y));
            }
        }

        // Update the view with all the collected board/unusedPieces updates.
        for (BoardUpdateData updateData : updates) {
            view.update(updateData);
        }
    }


    public void updateTurn(){
        RoundUpdateData updateData =
                new RoundUpdateData(model.getCurrentMove().getPlayer(), model.getMoves().size()+1, "");
        view.update(updateData);
    }
/**
     * Starts a new turn in the game.<br>
     * - Initializes the turn timer.<br>
     * - Signals the model to start a new turn.<br>
     * - Updates the view with the currently chosen piece.<br>
     * - If it is not the player's turn, initiates the bot's move.
     */
    public void startTurn() {
        startTimer();
        model.startNewTurn();
        view.setChosenPiece(model.getCurrentMove().getPiece().toString());

        updateTurn();

        if (model.isPlayersTurn()) return;

        botPlay();
    }

    private void botPlay() {
        Move botMove;

        boolean success = false;

        while (!success) {
            botMove = model.getComputerPlayer().getMove(model);
            success = model.selectPosition(botMove.getPosition());
        }

        updateView(model.getCurrentMove());

        if (!model.isActive()) return;

        success = false;
        while (!success) {
            botMove = model.getComputerPlayer().getMove(model);
            success = model.selectPiece(botMove.getPiece().toString());
        }

        endTurn();
    }

    /**
     * Ends the current turn in the game.<br>
     * - Signals the model to end the turn.<br>
     * - Stops the turn timer.<br>
     * - Starts a new turn.
     */
    public void endTurn() {
        model.endTurn();
        stopTimer();
        startTurn();

    }

    private void startTimer() {
        gameTimer.start();
    }

    private void stopTimer() {
        gameTimer.pause();
    }

    private void resumeTimer() {
        gameTimer.resume();
    }


    private boolean confirmSaveBeforeExit() {
        stopTimer();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(Router.getInstance().getPrimaryStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Do you want to save the game before exiting?");
        alert.setContentText("Choose an option:");

        ButtonType saveAndExit = new ButtonType("Save and Exit");
        ButtonType exitWithoutSaving = new ButtonType("Exit Without Saving");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(saveAndExit, exitWithoutSaving, cancel);

        Optional<ButtonType> result = alert.showAndWait();


        if (result.isEmpty() || result.get() == cancel) {
            resumeTimer();
            return false; // stay on screen
        }

        if (result.get() == saveAndExit) {
            SaveManager.saveToFile(model.getSessionData());
        }

        return true;
    }
}
