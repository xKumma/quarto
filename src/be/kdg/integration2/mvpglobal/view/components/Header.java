package be.kdg.integration2.mvpglobal.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class Header extends HBox {

    public Header() {
        super();
        setupComponent();
    }

    private void setupComponent() {
        Label qLabel = new Label("Q"); // 🅀
        StackPane qContainer = new StackPane(qLabel);
        //qContainer.setAlignment(Pos.CENTER);
        qContainer.getStyleClass().add("header-q");

        Label restLabel = new Label("arto");

        this.getChildren().addAll(qContainer, restLabel);
        setAlignment(Pos.CENTER);
        getStyleClass().add("header");

    }
}
