package be.kdg.integration2.mvpglobal.view.base;

import be.kdg.integration2.mvpglobal.view.UISettings;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

/**
 * Base class for all views in the application.
 * <p>
 * This class provides a common structure for views, including a reference to the UI settings,
 * and methods for initializing and laying out nodes.
 */
public abstract class BaseView extends BorderPane {
    protected UISettings uiSettings;

    /**
     * Constructor for the BaseView class.
     * <p>
     * This constructor initializes the UI settings and sets the stylesheet for the view.
     * It also calls the methods to initialize and layout nodes.
     */
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

    /**
     * Abstract method to initialize nodes in the view.
     * <p>
     * This method should be implemented by subclasses to provide specific initialization logic.
     *
     * @throws SQLException if an SQL error occurs during initialization.
     */
    protected abstract void initialiseNodes() throws SQLException;
    /**
     * Abstract method to layout nodes in the view.
     * <p>
     * This method should be implemented by subclasses to provide specific layout logic.
     */
    protected abstract void layoutNodes();
}

