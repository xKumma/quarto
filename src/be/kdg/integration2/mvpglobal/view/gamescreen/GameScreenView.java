package be.kdg.integration2.mvpglobal.view.gamescreen;

import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.PieceButton;
import be.kdg.integration2.mvpglobal.view.gamescreen.records.BoardUpdateData;
import be.kdg.integration2.mvpglobal.view.gamescreen.records.RoundUpdateData;
import be.kdg.integration2.mvpglobal.view.gamescreen.records.TimeUpdateData;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameScreenView extends BaseView {

    GridPane board;
    GridPane unusedPieces;
    Label roundInfoLbl;
    Label timeLbl;
    Label infoLbl;
    Button menuBtn;

    public GameScreenView(UISettings uiSettings) {
        super(uiSettings);
    }

    @Override
    protected void initialiseNodes() {
        board = new GridPane();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                PieceButton boardBtn = new PieceButton(null, null);
                boardBtn.getStyleClass().add("board-button");
                board.add(boardBtn, x, y);
            }
        }


        unusedPieces = new GridPane();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 2; y++) {
                PieceButton unusedBtn = new PieceButton( null, null);
                unusedBtn.getStyleClass().add("unused-button");
                unusedPieces.add(unusedBtn, x, y);
            }
        }

        roundInfoLbl = new Label(
                    """
                    Round: X
                    Player: <name>
                    """
        );
        roundInfoLbl.getStyleClass().add("game-info");

        timeLbl = new Label("Time: %02d:%02d (Turn: %02d:%03d)");

        infoLbl = new Label("Do XYZ");
        infoLbl.getStyleClass().add("info-label");

        menuBtn = new Button("Menu");
        menuBtn.getStyleClass().add("button");
        System.out.println("------------------------------------");
    }

    @Override
    protected void layoutNodes() {
        Label qLabel = new Label("Q");
        StackPane qContainer = new StackPane(qLabel);
        qContainer.setAlignment(Pos.CENTER);
        qContainer.getStyleClass().add("header-q");

        Label restLabel = new Label("arto");

        HBox header = new HBox(qContainer, restLabel);
        header.setAlignment(Pos.CENTER);
        header.getStyleClass().add("header");
        setTop(header);

        VBox centerLeft = new VBox(roundInfoLbl, timeLbl, unusedPieces);
        centerLeft.setSpacing(32);

        HBox centerUpper = new HBox(board, centerLeft);
        centerUpper.setSpacing(128);
        centerUpper.setAlignment(Pos.CENTER);

        VBox centerContainer = new VBox(centerUpper, infoLbl);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.getStyleClass().add("center");
        centerContainer.setSpacing(32);
        setCenter(centerContainer);

        HBox footer = new HBox(menuBtn);
        footer.setAlignment(Pos.CENTER);
        setBottom(footer);
    }

    public void update(RoundUpdateData updateData) {
        roundInfoLbl.setText(
                String.format(
                        """
                        Round: %d
                        Player: %s
                        """,
                        updateData.round(), updateData.playerName()
                )
        );

        infoLbl.setText(updateData.infoText());
    }

    public void update(TimeUpdateData updateData) {
        long totalTime = updateData.totalTime();
        long turnTime = updateData.turnTime();

        timeLbl.setText(
                String.format(
                        "Time: %02d:%02d.%02d (Turn: %02d:%03d)",
                        (totalTime / 60000), // Minutes
                        (totalTime / 1000) % 60, // Seconds
                        (totalTime / 10) % 100, // Hundredths of a second

                        (turnTime / 1000) % 60, turnTime % 1000
                )
        );
    }

    public void update(BoardUpdateData updateData) {
        int x = updateData.x();
        int y = updateData.y();

        if (x >=0 && y >=0) updateBoardPiece(updateData.imagePath(),updateData.color(),x,y);
        else updateUnusedPiece(updateData.imagePath(),updateData.color(),x,y);
    }

    private void updateBoardPiece(String pieceImagePath, String color, int x, int y) {
        for (Node node : board.getChildren()) {
            Integer column = GridPane.getColumnIndex(node);
            Integer row = GridPane.getRowIndex(node);

            if (column != x || row != y) continue;

            System.out.println("Updating BOARD at " + column + " " + row + " with " + pieceImagePath);
            ((PieceButton) node).setPieceImage(pieceImagePath, color);
            return;
        }
    }

    private void updateUnusedPiece(String pieceImagePath, String color, int x, int y) {
        for (Node node : unusedPieces.getChildren()) {
            Integer column = GridPane.getColumnIndex(node);
            Integer row = GridPane.getRowIndex(node);

            if (column != -(x + 1) || row != -(y + 1)) continue;

            System.out.println("Updating UNUSED at " + column + " " + row + " with " + pieceImagePath);
            ((PieceButton) node).setPieceImage(pieceImagePath, color);
            return;
        }
    }

    //region Getters
    public GridPane getBoard() {
        return board;
    }

    public Label getRoundInfoLbl() {
        return roundInfoLbl;
    }

    public Button getMenuBtn() {
        return menuBtn;
    }

    public Label getInfoLbl() {
        return infoLbl;
    }
    //endregion
}
