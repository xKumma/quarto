package be.kdg.integration2.mvpglobal.view.gamescreen;

import be.kdg.integration2.mvpglobal.model.dataobjects.BoardUpdateData;
import be.kdg.integration2.mvpglobal.model.dataobjects.RoundUpdateData;
import be.kdg.integration2.mvpglobal.model.dataobjects.TimeUpdateData;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.Header;
import be.kdg.integration2.mvpglobal.view.components.PieceButton;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameScreenView extends BaseView {

    private GridPane board;
    private GridPane unusedPieces;
    private Label roundInfoLbl;
    private Label timeLbl;
    private Label infoLbl;
    private Button menuBtn;
    private PieceButton chosenPieceButton;
    private Label chosenPieceLabel;

    public GameScreenView() {
        super();
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

        chosenPieceLabel = new Label("Chosen piece: ");
        chosenPieceButton = new PieceButton(null, null);

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
        setTop(new Header());

        //new boxes with chosenpiece included
        GridPane chosenPieceGrid = new GridPane();
        chosenPieceGrid.add(chosenPieceLabel, 0, 0);
        chosenPieceGrid.add(chosenPieceButton, 1, 0);
        VBox labels = new VBox(roundInfoLbl, timeLbl);
        labels.setSpacing(32);
        HBox allLabels = new HBox(labels, chosenPieceGrid);
        //allLabels.setAlignment(Pos.TOP_LEFT);
        allLabels.setSpacing(193);


        VBox centerLeft = new VBox(allLabels, unusedPieces);
        centerLeft.setSpacing(32);

        HBox centerUpper = new HBox(board, centerLeft);
        centerUpper.setSpacing(128);
        centerUpper.setAlignment(Pos.CENTER);

        chosenPieceGrid.setAlignment(Pos.TOP_LEFT);


        VBox centerContainer = new VBox(centerUpper, infoLbl);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setSpacing(32);
        setCenter(centerContainer);

        HBox footer = new HBox(menuBtn);
        footer.setAlignment(Pos.CENTER);
        footer.getStyleClass().add("container");
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

        if (x >=0 && y >=0) setBoardPiece(updateData.imagePath(),updateData.color(),x,y);
        else setUnusedPiece(updateData.imagePath(),updateData.color(),x,y);
    }

    private void setBoardPiece(String pieceImagePath, String color, int x, int y) {
        for (Node node : board.getChildren()) {
            Integer column = GridPane.getColumnIndex(node);
            Integer row = GridPane.getRowIndex(node);

            if (column != x || row != y) continue;

            System.out.println("Updating BOARD at " + column + " " + row + " with " + pieceImagePath);
            ((PieceButton) node).setPieceImage(pieceImagePath, color);

            removeChosenPiece();
            removeUnused(pieceImagePath+"#"+color);
            return;
        }
    }

    void setChosenPiece(String slug) {
        String[] sluglist = slug.split("#");
        chosenPieceButton.setPieceImage(sluglist[0], sluglist[1]);
        removeUnused(slug);
    }

    private void removeChosenPiece() {
        chosenPieceButton.setPieceImage(null, null);
    }

    private void removeUnused(String slug) {
        for (Node node : unusedPieces.getChildren()) {
            if (!node.toString().equals(slug)) continue;
            ((PieceButton)node).setPieceImage(null, null);
        }
    }

    private void setUnusedPiece(String pieceImagePath, String color, int x, int y) {
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
    GridPane getBoard() {
        return board;
    }

    Label getChosenPieceLabel() {
        return chosenPieceLabel;
    }

    PieceButton getChosenPieceGrid() {
        return chosenPieceButton;
    }

    Label getRoundInfoLbl() {
        return roundInfoLbl;
    }

    Button getMenuBtn() {
        return menuBtn;
    }

    Label getInfoLbl() {
        return infoLbl;
    }

    GridPane getUnusedPieces() { return unusedPieces; }
    //endregion
}
