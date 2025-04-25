package be.kdg.integration2.mvpglobal.dbconnection;

import be.kdg.integration2.mvpglobal.model.LeaderboardData;
import be.kdg.integration2.mvpglobal.model.LeaderboardData;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    // LIVE DB
    static final String DB_URL = "jdbc:postgresql://10.134.178.12:5432/game";
    static final String USER = "game";
    static final String PASSWORD = "7sur7";


    // USE FOR LOCAL TESTING WITHOUT VPN - change for your setup, dont push this!!
    /*
    static final String DB_URL = "jdbc:postgresql://localhost:5432/quarto";
    static final String USER = "devuser";
    static final String PASSWORD = "devpass";
    */
    static Connection connection;
    static Statement statement;

    public DBManager() throws SQLException {
        setupDatabase();
    }

    public static void setupDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            statement = connection.createStatement();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
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


    public double getTimeMove1(int sessionid , int moveid) throws SQLException {
        String query = "SELECT EXTRACT(EPOCH FROM (move_end_time - move_start_time))  FROM moves WHERE sessionId = ? and moveId = ? AND was_ai = true";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sessionid);
            ps.setInt(2, moveid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1); // Restituisce il valore in minuti (con secondi nei decimali)
                }
            }
        }
        return 20; // Valore di default se non ci sono risultati
    }

    public double getTimeMove2(int sessionid , int i) throws SQLException {
        String query = "SELECT EXTRACT(EPOCH FROM (move_end_time - move_start_time))  FROM moves WHERE sessionId = ? and moveId = ? AND was_ai = false";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sessionid);
            ps.setInt(2, i);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1); // Restituisce il valore in minuti (con secondi nei decimali)
                }
            }
        }
        return 20; // Valore di default se non ci sono risultati
    }


    public int getMoveID2(int sessionid ) throws SQLException {
        String query = "SELECT moveid FROM moves WHERE sessionID = ? order by moveid DESC LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sessionid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);

                }
            }
        }
        return 0;
    }

    public int getMoveID1(int sessionid) throws SQLException {
        String query = "SELECT moveid FROM moves WHERE sessionID = ? order by moveid asc LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sessionid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);

                }
            }
        }
        return 0;
    }

    public boolean isAI(int moveid) throws SQLException {
        String query = "SELECT was_ai FROM  moves WHERE moveId = ? and was_ai = true order by moveid asc  ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, moveid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        return false;
    }

    public static Boolean registerUser(String usernameData, String passwordData){
        try{
            if(userExists(usernameData)){
                System.out.println("username taken");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Username Taken");
                alert.setHeaderText("This username is already taken");
                alert.showAndWait();
                return false;
            }
            else{
            String registerString = "INSERT INTO human_players(username, password) VALUES(?,?)";
            PreparedStatement ps = connection.prepareStatement(registerString);
            ps.setString(1, usernameData);
            ps.setString(2, passwordData);
            ps.execute();
            ps.close();
            System.out.println("Registering USER " + usernameData + " with password " + passwordData);
            return true;
            }
        }
        catch(Exception e){
            System.out.println("Error");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Boolean loginUser(String usernameData, String passwordData){
        try{
            if(userExists(usernameData)){
                if(passwordCheck(usernameData, passwordData)){
                    System.out.println("username there");
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                System.out.println("username not in database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Username does not exist");
                alert.setHeaderText("This username is not in the database");
                alert.showAndWait();
                return false;
            }
        }
        catch(Exception e){
            System.out.println("Error at login");
            return false;
        }
    }

    public static Boolean userExists(String usernameData){
        Boolean flag=false;
        String checkString = "SELECT EXISTS (SELECT 1 FROM human_players WHERE username = ?)";
        try (PreparedStatement ps1 = connection.prepareStatement(checkString)) {
            ps1.setString(1, usernameData);
            try (ResultSet rs = ps1.executeQuery()) {
                if (rs.next()) {
                    flag= rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    public static Boolean passwordCheck(String usernameData,String passwordData){
        Boolean flag=false;
        String checkString = "SELECT password FROM human_players WHERE username = ?";
        try (PreparedStatement ps1 = connection.prepareStatement(checkString)) {
            ps1.setString(1, usernameData);
            try (ResultSet rs = ps1.executeQuery()) {
                if (rs.next()) {
                    if(rs.getString(1).equals(passwordData)){
                        flag= true;
                    }
                    else{
                        System.out.println("Password incorrect");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Wrong Password");
                        alert.setHeaderText("The password is incorrect");
                        alert.showAndWait();
                        flag= false;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    public static void fillLeaderboard(String filter){
        System.out.println("called");
        String name="Empty";
        int gamesPlayed=8;
        int wins=4;
        int losses=0;
        double averageMoves=0;
        double averageTime=0;
        LeaderboardData.LeaderboardData.clear();
        for(int i=0;i<5;i++) {
            String checkString = "SELECT player_username,player_won FROM sessions WHERE is_finished = ? ORDER BY ? OFFSET ? LIMIT ?";
            try (PreparedStatement ps1 = connection.prepareStatement(checkString)) {
                ps1.setString(1, "true");
                ps1.setString(2, filter);
                ps1.setString(3, String.valueOf(i));
                ps1.setString(4, "1");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            LeaderboardData.LeaderboardData.add(new LeaderboardData(name,gamesPlayed,wins,losses,averageMoves,averageTime));
        }
    }
}
