package be.kdg.integration2.mvpglobal.view.base;

import be.kdg.integration2.mvpglobal.view.UISettings;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public abstract class BaseView extends BorderPane {
    protected UISettings uiSettings;

    public BaseView() {
        uiSettings = UISettings.getInstance();

        this.getStylesheets().add(uiSettings.getStyleSheetPath().toString());

        try {
            initialiseNodes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        layoutNodes();
    }


    protected abstract void initialiseNodes() throws SQLException;
    protected abstract void layoutNodes();
}

