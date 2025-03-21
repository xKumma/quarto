package be.kdg.integration2.mvpglobal.view.base;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.view.UISettings;

public class BasePresenter<V, M> {
    protected V view;

    protected M model;

    protected UISettings uiSettings;

    public BasePresenter(V view, M model) {
        this.view = view;
        this.model = model;

        this.uiSettings = UISettings.getInstance();
    }

    public void init() {
        addEventHandlers();
    }

    public void init(Object data) {
        if (model instanceof BaseModel) {
            ((BaseModel) model).init(data);
        }
        init();
    }

    protected void updateView(){

    }

    protected void addEventHandlers(){}

    public BaseView getView() {
        return (BaseView) view;
    }

    public BaseModel getModel() {
        return (BaseModel) model;
    }
}
