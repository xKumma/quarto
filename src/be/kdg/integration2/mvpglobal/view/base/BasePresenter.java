package be.kdg.integration2.mvpglobal.view.base;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.view.UISettings;

/**
 * Base class for all presenters in the application.
 * <p>
 * This class provides a common structure for presenters, including a reference to the view and model,
 * as well as methods for initialization and event handling.
 *
 * @param <V> The type of the view associated with this presenter.
 * @param <M> The type of the model associated with this presenter.
 */
public class BasePresenter<V, M> {
    protected V view;

    protected M model;

    protected UISettings uiSettings;

    /**
     * Constructor for the BasePresenter class.
     *
     * @param view  The view associated with this presenter.
     * @param model The model associated with this presenter.
     */
    public BasePresenter(V view, M model) {
        this.view = view;
        this.model = model;

        this.uiSettings = UISettings.getInstance();
    }

    /**
     * Initializes the presenter by adding event handlers.
     * This method can be overridden by subclasses to provide additional initialization logic.
     */
    public void init() {
        addEventHandlers();
    }

    /**
     * Initializes the presenter with additional data.
     * This method can be overridden by subclasses to provide additional initialization logic.
     *
     * @param data The data to initialize the presenter with.
     */
    public void init(Object data) {
        if (model instanceof BaseModel) {
            ((BaseModel) model).init(data);
        }
        init();
    }

    /**
     * Updates the view with the current state of the model.
     */
    protected void updateView(){    }

    /**
     * Adds event handlers to the view.
     */
    protected void addEventHandlers(){}

    /**
     * Gets the view associated with this presenter.
     * @return The view associated with this presenter.
     */
    public BaseView getView() {
        return (BaseView) view;
    }

    /**
     * Gets the model associated with this presenter.
     * @return The model associated with this presenter.
     */
    public BaseModel getModel() {
        return (BaseModel) model;
    }
}
