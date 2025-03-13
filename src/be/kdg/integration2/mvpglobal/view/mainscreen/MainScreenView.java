package be.kdg.integration2.mvpglobal.view.mainscreen;

import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.scene.control.*;

public class MainScreenView extends BaseView {

    private MenuItem exitMI;
    private MenuItem saveMI;
    private MenuItem loadMI;
    private MenuItem statistic;
    private MenuItem table;
    private MenuItem settingsMI;
    private MenuItem aboutMI;
    private MenuItem infoMI;
    private Button testButton;
    private MenuItem leaderboardMI;
    private MenuItem gameSetupMI;
    private MenuItem menuMI;


    public MainScreenView() {
        super();
    }

    protected void initialiseNodes() {
        this.exitMI = new MenuItem("Exit");
        this.saveMI = new MenuItem("Save");
        this.loadMI = new MenuItem("Load");
        this.settingsMI = new MenuItem("Settings");
        this.aboutMI = new MenuItem("About");
        this.infoMI = new MenuItem("Info");
        this.testButton = new Button ("Test of Rule Based System");
        this.statistic = new MenuItem("Statistics");
        this.table = new MenuItem("Table");
        this.leaderboardMI = new MenuItem("Leaderboard");
        this.gameSetupMI = new MenuItem("Game Setup");
        this.menuMI = new MenuItem("Menu");
    }

    protected void layoutNodes() {
        Menu menuFile = new Menu(
                "File",null,
                loadMI,
                saveMI,
                gameSetupMI,
                menuMI,
                new SeparatorMenuItem(),
                settingsMI,
                new SeparatorMenuItem(),
                exitMI ,
                new SeparatorMenuItem() ,
                statistic ,
                new SeparatorMenuItem() ,
                table);
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

    MenuItem getGameSetupMI() {return gameSetupMI;}
    
    MenuItem getMenuMI() {return menuMI;}
}
