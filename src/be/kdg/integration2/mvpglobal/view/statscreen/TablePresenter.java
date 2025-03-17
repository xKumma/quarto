package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

public class TablePresenter extends BasePresenter<TabletView, BaseModel> {

    public TablePresenter(BaseModel model, TabletView tableView) {
        super(tableView, model);
    }
}
