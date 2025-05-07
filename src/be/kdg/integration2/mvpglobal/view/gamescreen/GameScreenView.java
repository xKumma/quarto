package be.kdg.integration2.mvpglobal.view.gamescreen;

import be.kdg.integration2.mvpglobal.model.dataobjects.BoardUpdateData;
import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.dataobjects.RoundUpdateData;
import be.kdg.integration2.mvpglobal.model.dataobjects.TimeUpdateData;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.Header;
import be.kdg.integration2.mvpglobal.view.components.PieceButton;
import javafx.geometry.Insets;
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
    }

    @Override
    protected void layoutNodes() {
        setTop(new Header());

        //new boxes with chosenpiece included
        GridPane chosenPieceGrid = new GridPane();
        chosenPieceGrid.add(chosenPieceLabel, 0, 0);

        chosenPieceGrid.add(chosenPieceButton, 1, 0);
        chosenPieceButton.getStyleClass().add("board-button");
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
        footer.setPadding(new Insets(15));
        footer.setAlignment(Pos.CENTER);
        footer.getStyleClass().add("container");
        setBottom(footer);
    }


    /**
     * Updates the round information and the info label with the provided data.
     *
     * @param updateData The data object containing the round number, player name, and info text.
     */
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


    /**
     * Updates the time label with the total and turn time information.<br>
     * The time is formatted as minutes, seconds, and hundredths of a second
     * for the total time, and seconds and milliseconds for the turn time.
     *
     * @param updateData The data object containing the total and turn time values.
     */
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

    /**
     * Updates the board or unused pieces based on the given `BoardUpdateData`.<br>
     * If the coordinates are non-negative, the piece is placed on the board.
     * Otherwise, the piece is added to the unused pieces grid.
     *
     * @param updateData The data object containing the piece's image path, color, and coordinates.
     */
    public void update(BoardUpdateData updateData) {
        int x = updateData.x();
        int y = updateData.y();

        if (x >= 0 && y >= 0) setBoardPiece(updateData.imageName(), updateData.color(), x, y);
        else setUnusedPiece(updateData.imageName(), updateData.color(), x, y);
    }

    public void highlightWinningMove(PositionData position, Runnable onFinished) {
        for (Node node : board.getChildren()) {
            Integer column = GridPane.getColumnIndex(node);
            Integer row = GridPane.getRowIndex(node);

            if (column != position.x() || row != position.y()) continue;

            ((PieceButton) node).playFlashAnimation(onFinished);
        }
    }

    /**
     * Updates the board grid with a piece at the specified position.<br>
     * If a node exists at the given coordinates, it sets the piece's image and color.
     * Additionally, it removes the chosen piece and the corresponding unused piece.
     *
     * @param pieceImageName The file name for the image of the piece.
     * @param color          The color of the piece.
     * @param x              The x-coordinate of the piece on the board.
     * @param y              The y-coordinate of the piece on the board.
     */
    private void setBoardPiece(String pieceImageName, String color, int x, int y) {
        for (Node node : board.getChildren()) {
            Integer column = GridPane.getColumnIndex(node);
            Integer row = GridPane.getRowIndex(node);

            if (column != x || row != y) continue;

            ((PieceButton) node).setPieceImage(pieceImageName, color);

            removeChosenPiece();
            removeUnused(pieceImageName + "#" + color);
            return;
        }
    }

    /**
     * Sets the chosen piece on the `chosenPieceButton` based on the given slug.
     * The slug is expected to be in the format "shape_size_type#color".<br>
     * The method splits the slug, sets the piece image and color,
     * and removes the piece from the unused pieces grid.
     *
     * @param slug The unique identifier of the piece, in the format "shape_size_type#color".
     */
    public void setChosenPiece(String slug) {
        String[] slugParts = slug.split("#");
        chosenPieceButton.setPieceImage(slugParts[0], slugParts[1]);
        removeUnused(slug);
    }

    /**
     * Sets an unused piece on the `unusedPieces` grid at the specified position.
     * If the position matches an existing node in the grid, the piece's image and color are updated.
     *
     * @param pieceImagePath The file path to the image of the piece.
     * @param color          The color of the piece.
     * @param x              The x-coordinate (negative index) of the piece in the grid.
     * @param y              The y-coordinate (negative index) of the piece in the grid.
     */
    private void setUnusedPiece(String pieceImagePath, String color, int x, int y) {
        for (Node node : unusedPieces.getChildren()) {
            Integer column = GridPane.getColumnIndex(node);
            Integer row = GridPane.getRowIndex(node);

            // Reverse the x and y coordinates to match the grid layout.
            if (column != -(x + 1) || row != -(y + 1)) continue;

            ((PieceButton) node).setPieceImage(pieceImagePath, color);
            return;
        }
    }

    private void removeChosenPiece() {
        chosenPieceButton.setPieceImage(null, null);
    }

    /**
     * Removes an unused piece from the `unusedPieces` grid based on the given slug.
     * The slug is compared to the string representation of each node in the grid.
     * If a match is found, the piece's image and color are cleared.
     *
     * @param slug The unique identifier of the piece to be removed, in the format "imageName#color".
     */
    private void removeUnused(String slug) {
        for (Node node : unusedPieces.getChildren()) {
            if (!node.toString().equals(slug)) continue;
            ((PieceButton)node).setPieceImage(null, null);
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
