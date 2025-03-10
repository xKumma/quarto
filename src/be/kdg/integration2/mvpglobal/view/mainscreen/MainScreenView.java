package be.kdg.integration2.mvpglobal.view.mainscreen;

import be.kdg.integration2.mvpglobal.view.UISettings;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class MainScreenView extends BorderPane  {

    private MenuItem exitMI;
    private MenuItem saveMI;
    private MenuItem loadMI;
    private MenuItem statistic;
    private MenuItem table;
    private MenuItem settingsMI;
    private MenuItem aboutMI;
    private MenuItem infoMI;
    private Button testButton;


    private UISettings uiSettings;

    public MainScreenView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        this.exitMI = new MenuItem("Exit");
        this.saveMI = new MenuItem("Save");
        this.loadMI = new MenuItem("Load");
        this.settingsMI = new MenuItem("Settings");
        this.aboutMI = new MenuItem("About");
        this.infoMI = new MenuItem("Info");
        this.testButton = new Button ("Test of Rule Based System");
        this.statistic = new MenuItem("Statistics");
        this.table = new MenuItem("Table");
    }

    private void layoutNodes() {
        Menu menuFile = new Menu("File",null,loadMI, saveMI, new SeparatorMenuItem(), settingsMI, new SeparatorMenuItem(),exitMI , new SeparatorMenuItem() , statistic , new SeparatorMenuItem() , table);
        Menu menuHelp = new Menu("Help",null, aboutMI, infoMI);
        MenuBar menuBar = new MenuBar(menuFile,menuHelp);
        setTop(menuBar);
        setBottom(testButton);
    }

    MenuItem getExitItem() {return exitMI;}

    MenuItem getSaveItem() {return saveMI;}

    MenuItem getLoadItem() {return loadMI;}

    MenuItem getSettingsItem() {return settingsMI;}

    MenuItem getAboutItem() {return aboutMI;}

    MenuItem getInfoItem() {return infoMI;}
    Button getTestButton () {return testButton;}

    MenuItem getStatisticsItem() {return statistic;}

    MenuItem getTableItem() {return table;}

}
