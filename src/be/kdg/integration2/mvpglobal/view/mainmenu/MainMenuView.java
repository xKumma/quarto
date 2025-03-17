package be.kdg.integration2.mvpglobal.view.mainmenu;

import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.Header;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainMenuView extends BaseView {

    private Button startGameButton;
    private Button rulesButton;
    private Button leaderboardButton;
    private Button quitButton;

    public MainMenuView() {
        super();
    }

    protected void initialiseNodes() {
        startGameButton = new Button("Start Game");
        rulesButton = new Button("Rules");
        leaderboardButton = new Button("Leaderboard");
        quitButton = new Button("Quit");

    }

    protected void layoutNodes() {
        setTop(new Header());
        VBox buttons = new VBox(new Label("Menu"),startGameButton, rulesButton, leaderboardButton, quitButton);
        buttons.setSpacing(3);
        buttons.setAlignment(Pos.CENTER);
        setCenter(buttons);

    }

    Button getQuitButton() {
        return quitButton;
    }

    Button getLeaderboardButton() {
        return leaderboardButton;
    }

    Button getRulesButton() {
        return rulesButton;
    }

    Button getStartGameButton() {
        return startGameButton;
    }
}
