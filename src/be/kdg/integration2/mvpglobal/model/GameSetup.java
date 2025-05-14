package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;


public class GameSetup implements BaseModel {
    private int startingPlayer = 0; // 0 = R, 1 = P, 2 = B;

    /**
     * Gets session data.
     *
     * @return the session data
     */
    public GameSessionData getSessionData() {
        return new GameSessionData(startingPlayer, BotDifficulty.EASY);
    }

    /**
     * Sets starting player.
     *
     * @param startingPlayer the starting player
     */
    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
