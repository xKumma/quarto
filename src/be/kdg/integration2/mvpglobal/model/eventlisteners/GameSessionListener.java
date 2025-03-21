package be.kdg.integration2.mvpglobal.model.eventlisteners;

import be.kdg.integration2.mvpglobal.model.Move;

public interface GameSessionListener {
    void onMoveSuccessful(Move move);

}
