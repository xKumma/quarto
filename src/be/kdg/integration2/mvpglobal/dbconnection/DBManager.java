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
    }

    public String getpassword (String username) throws SQLException {
        String querypassword = "SELECT password FROM human_players WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(querypassword);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    public ArrayList<Time> getAllSessionMoveTime(Integer sessionID) throws SQLException {
        String selectMovetime = "SELECT ( CAST( move_end_time AS TIMESTAMP)   - CAST( move_start_time AS TIMESTAMP) ) FROM moves WHERE sessionID = ? AND was_ai = FALSE";
        PreparedStatement ps = connection.prepareStatement(selectMovetime);
        ps.setInt(1, sessionID);
        ResultSet rs = ps.executeQuery();
        ArrayList<Time> moves = new ArrayList<>();
        while (rs.next()) {
            Time moveTime = rs.getTime(1);
            moves.add(moveTime);
        }
        return moves;
    }
}
