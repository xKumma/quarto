package be.kdg.integration2.mvpglobal.view.base;

import be.kdg.integration2.mvpglobal.view.UISettings;
import javafx.scene.layout.BorderPane;

public abstract class BaseView extends BorderPane {
    protected UISettings uiSettings;

    public BaseView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        initialiseNodes();
        layoutNodes();
    }


    protected abstract void initialiseNodes();
    protected abstract void layoutNodes();
}

