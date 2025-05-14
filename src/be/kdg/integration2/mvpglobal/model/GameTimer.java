package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.TimeUpdateData;
import javafx.animation.AnimationTimer;

import java.util.function.Consumer;

public class GameTimer {
    private AnimationTimer timer;
    private long startNano;
    private long totalElapsedMillis;

    private long cachedTurnMillis;
    private boolean isPaused = false;

    private final Consumer<TimeUpdateData> updateCallback;

    /**
     * Instantiates a new Game timer.
     *
     * @param updateCallback the update callback
     */
    public GameTimer(Consumer<TimeUpdateData> updateCallback) {
        this.updateCallback = updateCallback;
    }

    /**
     * Starts the timer.
     */
    public void start() {
        startNano = System.nanoTime();
        cachedTurnMillis = 0;
        isPaused = false;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long turnTime = isPaused ? cachedTurnMillis : cachedTurnMillis + (now - startNano) / 1_000_000;
                long total = totalElapsedMillis + turnTime;
                updateCallback.accept(new TimeUpdateData(total, turnTime));
            }
        };
        timer.start();
    }

    /**
     * Pauses the timer.
     */
    public void pause() {
        if (timer != null && !isPaused) {
            long now = System.nanoTime();
            cachedTurnMillis += (now - startNano) / 1_000_000;
            totalElapsedMillis += cachedTurnMillis;
            isPaused = true;
            timer.stop();
        }
    }

    /**
     * Resume the timer.
     */
    public void resume() {
        if (timer != null && isPaused) {
            startNano = System.nanoTime();
            isPaused = false;
            timer.start();
        }
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        if (timer != null) timer.stop();
    }

    /**
     * Gets cached total elapsed millis.
     *
     * @return the cached total elapsed millis
     */
    public long getCachedTotalElapsedMillis() {
        return totalElapsedMillis;
    }
}
