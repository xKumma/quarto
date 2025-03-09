package be.kdg.integration2.mvpglobal.view.base;

import be.kdg.integration2.mvpglobal.view.UISettings;

public class BasePresenter<V, M> {

    protected V view;
    protected M model;

    protected UISettings uiSettings;

    public BasePresenter(V view, M model, UISettings uiSettings) {
        this.view = view;
        this.model = model;
        this.uiSettings = uiSettings;

        addEventHandlers();
    }

    protected void updateView(){

    }

    protected void addEventHandlers(){

    }
}
