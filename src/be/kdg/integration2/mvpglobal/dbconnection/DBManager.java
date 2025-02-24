package be.kdg.integration2.mvpglobal.dbconnection;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    Connection connection;
    Statement statement;
    public DBManager() throws SQLException {
        // database url: change to your own database (check jdbc workshop)
        String url = "jdbc:postgresql://10.134.178.12:5432/game";
        String user = "game";
        String password = "7sur7";
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    public void createPlayer (String username,String password) throws SQLException {
        String selectScore = "INSERT INTO human_players VALUES(?, ?)";
        PreparedStatement ps = connection.prepareStatement(selectScore);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.executeUpdate();
        ps.close();
    }

    public String getpassword (String username) throws SQLException {
        String querypassword = "SELECT password FROM human_players WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(querypassword);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    public ArrayList<Time> getAllSessionMoveTime(int sessionID) throws SQLException {
        String selectMovetime = "SELECT ( CAST( move_end_time AS TIMESTAMP)   - CAST( move_start_time AS TIMESTAMP) ) FROM moves WHERE sessionID = ? AND was_ai = FALSE";
        PreparedStatement ps = connection.prepareStatement(selectMovetime);
        ps.setInt(1, sessionID);
        ResultSet rs = ps.executeQuery();
        ArrayList<Time> moves = new ArrayList<>();
        while (rs.next()) {
            Time moveTime = rs.getTime(1);
            moves.add(moveTime);
        }
        ps.close();
        return moves;
    }

    public void insertNewMove(int sessionID, int moveID, boolean aiPlayer) throws SQLException {
        String insertNewMove = "INSERT INTO moves VALUES(?, ?, CURRENT_TIMESTAMP, NULL, ?)";
        PreparedStatement ps = connection.prepareStatement(insertNewMove);
        ps.setInt(1, moveID);
        ps.setInt(2, sessionID);
        ps.setBoolean(3, aiPlayer);
        ps.executeUpdate();
        ps.close();
    }

    public void updateMoveEndTime(int sessionID, int moveID) throws SQLException {
        String updateMoveEndTime = "UPDATE moves SET move_end_time = CURRENT_TIMESTAMP WHERE sessionID = ? AND moveID = ?";
        PreparedStatement ps = connection.prepareStatement(updateMoveEndTime);
        ps.setInt(1, sessionID);
        ps.setInt(2, moveID);
        ps.executeUpdate();
        ps.close();
    }

    public void insertNewSession(int sessionID, String currentPlayer, String botPlayer) throws SQLException {
        String insertNewSession = "INSERT INTO sessions VALUES(?, ?, ?, FALSE, NULL)";
        PreparedStatement ps = connection.prepareStatement(insertNewSession);
        ps.setInt(1, sessionID);
        ps.setString(2, currentPlayer);
        ps.setString(3, botPlayer);
        ps.executeUpdate();
        ps.close();
    }

    public void insertPieceLocation(int sessionID, int pieceID, int x, int y) throws SQLException {
        String insertPieceLocation = "INSERT INTO piece_locations VALUES(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insertPieceLocation);
        ps.setInt(1, sessionID);
        ps.setInt(2, pieceID);
        ps.setInt(3, x);
        ps.setInt(4, y);
        ps.executeUpdate();
        ps.close();
    }

    public void updatePieceLocation(int sessionID, int pieceID, int x, int y) throws SQLException {
        String updatePieceLocation = "UPDATE piece_locations SET position_x = ?, position_y = ? WHERE sessionID = ? AND pieceID = ?";
        PreparedStatement ps = connection.prepareStatement(updatePieceLocation);
        ps.setInt(1, x);
        ps.setInt(2, y);
        ps.setInt(3, sessionID);
        ps.setInt(4, pieceID);
        ps.executeUpdate();
        ps.close();
    }

    public void updateSession (int sessionID, String winningPlayer) throws SQLException {
        String updateSession = "UPDATE sessions SET player_won = ?, is_finished = TRUE WHERE sessionID = ?";
        PreparedStatement ps = connection.prepareStatement(updateSession);
        ps.setString(1, winningPlayer);
        ps.setInt(2, sessionID);
        ps.executeUpdate();
        ps.close();
    }

    public String getBotNameFromDifficulty (int difficulty) throws SQLException {
        String selectBotName = "SELECT bot_name FROM bot_players WHERE bot_difficulty = ?";
        PreparedStatement ps = connection.prepareStatement(selectBotName);
        ps.setInt(1, difficulty);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    public String getBotNameFromSession (int sessionID) throws SQLException {
        String selectBotName = "SELECT bot_name FROM sessions WHERE sessionID = ?";
        PreparedStatement ps = connection.prepareStatement(selectBotName);
        ps.setInt(1, sessionID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString(1);
    }

}
