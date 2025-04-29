package be.kdg.integration2.mvpglobal.utility.dbconnection;

import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;
import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;
import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveManager {
    public static final Path SAVE_PATH = Paths.get("saves/").toAbsolutePath();

    /**
     * Saves the provided GameSessionData object to a file in JSON format.
     * The file is saved in the directory specified by SAVE_PATH, with the filename
     * constructed using the player's name and the current timestamp.
     *
     * @param gameSessionData The GameSessionData object to be saved. This object
     *                        contains all the necessary information about the game session.
     */
    public static void saveToFile(GameSessionData gameSessionData) {
        try (PrintWriter out = new PrintWriter(new FileWriter(new File(SAVE_PATH.toFile(),
                gameSessionData.getPlayerName() + System.currentTimeMillis() + ".json")))) {
            out.println(gameSessionData.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    public static void saveToDB(GameSessionData gameSessionData) {
        try {
            DBManager.getInstance().insertNewSession(
                    gameSessionData.getPlayerName(), gameSessionData.getBotDifficulty().ordinal());

            for (Move move : gameSessionData.getMoveHistory()) {
                DBManager.getInstance().insertNewMove(
                        move.getPlayer(),
                        move.getPiece().toString(),
                        move.getPosition().x(),
                        move.getPosition().y(),
                        move.getStartTime(),
                        move.getEndTime()
                );
            }

            System.out.println("Game session saved to database successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads a GameSessionData object from a specified file.
     *
     * @param file The file from which the game session data will be loaded.
     *             The file is expected to contain JSON data representing a game session.
     * @return A GameSessionData object populated with the data from the file,
     *         or null if an error occurs during the loading process.
     */
    public static GameSessionData loadFromFile(File file) {
        try {
            String json = Files.readString(file.toPath());
            String playerName = extractString(json, "playerName");
            String botDifficulty = extractString(json, "botDifficulty");
            String lastSelectedPiece = extractString(json, "lastSelectedPiece");

            List<Move> moveHistory = new ArrayList<>();
            String movesRaw = extractArray(json, "moveHistory");

            Pattern movePattern = Pattern.compile(
                    "\\{\\s*\"player\":(?:\"([^\"]+)\"|null),\\s*\"x\":(\\d+),\\s*\"y\":(\\d+),\\s*\"pieceSlug\":([a-z_]+#[a-z]+),\\s*\"startTime\":(\\d+),\\s*\"endTime\":(\\d+)\\s*}"
            );


            Matcher matcher = movePattern.matcher(movesRaw);

            while (matcher.find()) {
                String player = matcher.group(1);
                int x = Integer.parseInt(matcher.group(2));
                int y = Integer.parseInt(matcher.group(3));
                String slug = matcher.group(4);
                long startTime = Long.parseLong(matcher.group(5));
                long endTime = Long.parseLong(matcher.group(6));

                Move move = new Move(player, new Piece(slug), new PositionData(x, y), startTime, endTime);
                moveHistory.add(move);
            }

            return new GameSessionData(playerName, botDifficulty, moveHistory, lastSelectedPiece);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Extracts the value of a specified key from a JSON string.
     *
     * @param json The JSON string to search within.
     * @param key The key whose value needs to be extracted.
     * @return The value associated with the specified key, or null if the key is not found.
     */
    private static String extractString(String json, String key) {
        String pattern = "\"" + key + "\":\\s*\"([^\"]+)\"";
        Matcher matcher = Pattern.compile(pattern).matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * Extracts the array content of a specified key from a JSON string.
     *
     * @param json The JSON string to search within.
     * @param key The key whose array content needs to be extracted.
     * @return The array content as a string, or null if the key is not found.
     */
    private static String extractArray(String json, String key) {
        String pattern = "\"" + key + "\":\\s*\\[(.*?)]";
        Matcher matcher = Pattern.compile(pattern, Pattern.DOTALL).matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }
}

