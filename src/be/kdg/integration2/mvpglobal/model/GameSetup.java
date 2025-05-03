package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;

public class GameSetup implements BaseModel {
    private int startingPlayer = 0; // 0 = R, 1 = P, 2 = B;

    public GameSessionData getSessionData() {
        return new GameSessionData(startingPlayer, BotDifficulty.EASY);
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
