package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;

public class GameSetup implements BaseModel {
    private int startingPlayer = 0; // 0 = R, 1 = P, 2 = B; can be made into an enum
    private BotDifficulty difficulty = BotDifficulty.EASY;


    public GameSessionData getSessionData() {
        return new GameSessionData(startingPlayer, difficulty);
    }

    public void setDifficulty(BotDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
