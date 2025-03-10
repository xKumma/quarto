package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;

public class GameSetup implements BaseModel {

     public GameSetup () {
         System.out.println(111111);
     }

    public void startGame(GameSessionData gameSession) {
        Router.Instance.goTo(Screen.GAME, gameSession);
        //Router.Instance.goTo(Screen.GAME, null);
    }
}
