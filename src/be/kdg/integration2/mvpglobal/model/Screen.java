package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.view.aboutscreen.AboutScreenPresenter;
import be.kdg.integration2.mvpglobal.view.aboutscreen.AboutScreenView;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import be.kdg.integration2.mvpglobal.view.endscreen.EndScreenPresenter;
import be.kdg.integration2.mvpglobal.view.endscreen.EndScreenView;
import be.kdg.integration2.mvpglobal.view.gamescreen.GameScreenPresenter;
import be.kdg.integration2.mvpglobal.view.gamescreen.GameScreenView;
import be.kdg.integration2.mvpglobal.view.gamesetupscreen.GameSetupPresenter;
import be.kdg.integration2.mvpglobal.view.gamesetupscreen.GameSetupView;
import be.kdg.integration2.mvpglobal.view.leaderboardscreen.LeaderboardScreenPresenter;
import be.kdg.integration2.mvpglobal.view.leaderboardscreen.LeaderboardScreenView;
import be.kdg.integration2.mvpglobal.view.loginscreen.LoginScreenPresenter;
import be.kdg.integration2.mvpglobal.view.loginscreen.LoginScreenView;
import be.kdg.integration2.mvpglobal.view.mainmenu.MainMenuPresenter;
import be.kdg.integration2.mvpglobal.view.mainmenu.MainMenuView;
import be.kdg.integration2.mvpglobal.view.rules.RulesPresenter;
import be.kdg.integration2.mvpglobal.view.rules.RulesView;
import be.kdg.integration2.mvpglobal.view.settingsscreen.SettingsPresenter;
import be.kdg.integration2.mvpglobal.view.settingsscreen.SettingsView;
import be.kdg.integration2.mvpglobal.view.startscreen.StartScreenPresenter;
import be.kdg.integration2.mvpglobal.view.startscreen.StartScreenView;
import be.kdg.integration2.mvpglobal.view.statscreen.StatScreenPresenter;
import be.kdg.integration2.mvpglobal.view.statscreen.StatScreenView;
import be.kdg.integration2.mvpglobal.view.statscreen.TablePresenter;
import be.kdg.integration2.mvpglobal.view.statscreen.TabletView;

import java.util.function.Supplier;

/**
 * Enum representing different screens in the application.
 * Each screen is associated with a specific presenter, which is created using a supplier.
 */
public enum Screen {
    GAME(() -> new GameScreenPresenter(new GameScreenView(), new GameSession())),
    GAME_SETUP(() -> new GameSetupPresenter(new GameSetupView(), new GameSetup())),
    MAIN_MENU(() -> new MainMenuPresenter(new MainMenuView(), null)),
    LEADERBOARD(() -> new LeaderboardScreenPresenter(new LeaderboardScreenView(), null)),
    END_SCREEN(() -> new EndScreenPresenter(new EndScreenView(), null)),
    RULES(() -> new RulesPresenter(new RulesView(), null)),
    LOGIN(() -> new LoginScreenPresenter(null, new LoginScreenView())),
    TABLE(() ->  new TablePresenter( new TabletView() , new Tabledata())),
    GRAPH(() -> new StatScreenPresenter(new StatScreenView(), new Statistics())),
    ABOUT(() -> new AboutScreenPresenter(null, new AboutScreenView())),
    START(() ->new StartScreenPresenter(new StartScreenView() , null )),
    SETTINGS(() -> new SettingsPresenter(null, new SettingsView()));

    private final Supplier<BasePresenter> presenterSupplier;

    /**
     * Constructor for the Screen enum.
     *
     * @param presenterSupplier A supplier that provides the presenter for the screen.
     */
    Screen(Supplier<BasePresenter> presenterSupplier) {
        this.presenterSupplier = presenterSupplier;
    }

    /**
     * Creates and returns the presenter associated with the screen.
     *
     * @return The presenter for the screen.
     */
    public BasePresenter createPresenter() {
        return presenterSupplier.get();
    }
}
