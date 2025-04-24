package be.kdg.integration2.mvpglobal.utility;

import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;
import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveManager {
    public static final Path SAVE_PATH = Paths.get("saves/").toAbsolutePath();

    public static void saveToFile(GameSessionData gameSessionData) {
        try (PrintWriter out = new PrintWriter(new FileWriter(new File(SAVE_PATH.toFile(),
                gameSessionData.getPlayerName() + System.currentTimeMillis() + ".json")))) {
            out.println(gameSessionData.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameSessionData loadFromFile(File file) {
        try {
            String json = Files.readString(file.toPath());
            System.out.println("Loaded JSON: " + json);
            String playerName = extractString(json, "playerName");
            String botDifficulty = extractString(json, "botDifficulty");
            String lastSelectedPiece = extractString(json, "lastSelectedPiece");

            List<Move> moveHistory = new ArrayList<>();
            String movesRaw = extractArray(json, "moveHistory");
            System.out.println("Moves raw: " + movesRaw);

            Pattern movePattern = Pattern.compile(
                    "\\{\\s*\"player\":(?:\"([^\"]+)\"|null),\\s*\"x\":(\\d+),\\s*\"y\":(\\d+),\\s*\"pieceSlug\":([a-z_]+#[a-z]+),\\s*\"startTime\":(\\d+),\\s*\"endTime\":(\\d+)\\s*}"
            );


            Matcher matcher = movePattern.matcher(movesRaw);

            while (matcher.find()) {
                System.out.println("Found move: " + matcher.group(0));
                String player = matcher.group(1);
                int x = Integer.parseInt(matcher.group(2));
                int y = Integer.parseInt(matcher.group(3));
                String slug = matcher.group(4);
                long startTime = Long.parseLong(matcher.group(5));
                long endTime = Long.parseLong(matcher.group(6));

                Move move = new Move(player, new Piece(slug), new PositionData(x, y), startTime, endTime);
                moveHistory.add(move);
                System.out.println("Loaded move: " + move.toJson());
            }


            return new GameSessionData(playerName, botDifficulty, moveHistory, lastSelectedPiece);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String extractString(String json, String key) {
        String pattern = "\"" + key + "\":\\s*\"([^\"]+)\"";
        Matcher matcher = Pattern.compile(pattern).matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static String extractArray(String json, String key) {
        String pattern = "\"" + key + "\":\\s*\\[(.*?)]";
        Matcher matcher = Pattern.compile(pattern, Pattern.DOTALL).matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }
}

