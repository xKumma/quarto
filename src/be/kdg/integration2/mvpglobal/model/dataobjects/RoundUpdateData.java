package be.kdg.integration2.mvpglobal.model.dataobjects;

/**
 * A data object representing an update for a round in the game.
 *
 * @param playerName the name of the player
 * @param round the round number
 * @param infoText additional information about the round
 */
public record RoundUpdateData(String playerName, int round, String infoText) {
}
