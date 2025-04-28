package be.kdg.integration2.mvpglobal.model.dataobjects;
/**
 * A data object representing time update data.
 *
 * @param totalTime The total time in milliseconds.
 * @param turnTime The time for the current turn in milliseconds.
 */
public record TimeUpdateData(long totalTime, long turnTime) {
}
