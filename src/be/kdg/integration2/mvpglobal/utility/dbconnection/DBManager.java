package be.kdg.integration2.mvpglobal.utility.dbconnection;

import be.kdg.integration2.mvpglobal.model.HumanPlayer;
import be.kdg.integration2.mvpglobal.model.LeaderboardData;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private static DBManager Instance;

    // LIVE DB
    static final String DB_URL = "jdbc:postgresql://10.134.178.12:5432/game";
    static final String USER = "game";
    static final String PASSWORD = "7sur7";


    // USE FOR LOCAL TESTING WITHOUT VPN - change for your setup, dont push this!!
//    static final String DB_URL = "jdbc:postgresql://localhost:5432/quarto";
//    static final String USER = "devuser";
//    static final String PASSWORD = "devpass";

    private static final Path DDL_PATH = Paths.get("src/be/kdg/integration2/mvpglobal/utility/dbconnection/DDL.sql");
    private int currentSessionID = -1;

    private Connection connection;

    private DBManager() {
        setupDatabase();
    }

    public static DBManager getInstance() {
        if (Instance == null) {
            Instance = new DBManager();
        }
        return Instance;
    }

    private void setupDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();

            // Check if there are any tables in the database
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet tables = meta.getTables(null, null, "%", new String[] {"TABLE"});

            boolean hasTables = tables.next(); // true if there's at least one table

            if (!hasTables) {
                // Load and execute the DDL.sql file
                System.out.println(DDL_PATH.toAbsolutePath().toString());
                String ddlSql = Files.readString(DDL_PATH);
                for (String sql : ddlSql.split(";")) {
                    sql = sql.trim();
                    if (!sql.isEmpty()) {
                        if (statement != null) {
                            statement.execute(sql);
                        }
                    }
                }
            }
            tables.close();
        }
        catch (SQLException | IOException e) {
            System.err.println("Error setting up database: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
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

    public void insertNewMove(String player, String piece, int x, int y, long startTime, long endTime) throws SQLException {
        String insertNewMove =
                "INSERT INTO moves(sessionID, move_start_time, move_end_time, was_ai) VALUES(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insertNewMove);
        ps.setInt(1, currentSessionID);

        ps.setTimestamp(2, new Timestamp(startTime));
        ps.setTimestamp(3, new Timestamp(endTime));

        // We might not even need the piece related tables as we dont have to load a state from the DB

        boolean wasAI = !player.equals(HumanPlayer.getInstance().getName());
        ps.setBoolean(4, wasAI);

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

    public void insertNewSession(String currentPlayer, int difficulty, boolean playerWon) throws SQLException {
        String insertNewSession = "INSERT INTO sessions(player_username, bot_name, is_finished, player_won) VALUES(?, ?, TRUE, ?)";
        PreparedStatement ps = connection.prepareStatement(insertNewSession, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, currentPlayer);

        String botPlayer = getBotNameFromDifficulty(difficulty);
        ps.setString(2, botPlayer);

        ps.setBoolean(3, playerWon);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            currentSessionID = rs.getInt(1);
        }

        rs.close();
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
        ps.setInt(1, difficulty+1);
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

    public int getSessionid() throws SQLException {
        String query = "SELECT sessionid  FROM moves order by sessionid DESC LIMIT 1 ";
        try (PreparedStatement ps = connection.prepareStatement(query) ) {

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public String getUserNameFromSession (int sessionID) throws SQLException {
        String selectBotName = "SELECT player_username FROM sessions WHERE sessionID = ?";
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
        return 0; // Valore di default se non ci sono risultati
    }

    public double getTimeMove2(int sessionid , int moveid) throws SQLException {
        String query = "SELECT EXTRACT(EPOCH FROM (move_end_time - move_start_time))  FROM moves WHERE sessionId = ? and moveId = ? AND was_ai = false";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sessionid);
            ps.setInt(2, moveid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0;
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
        String query = "SELECT was_ai FROM  moves WHERE moveId = ?  order by moveid asc  ";
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

    /**
     * If the user does not exist in the DB it will create this user in the database with his chosen password
     * @param usernameData
     * Name that the user has entered
     * @param passwordData
     * Password that the user has entered
     * @return
     */
    public Boolean registerUser(String usernameData, String passwordData){
        try{
            if(userExists(usernameData)){
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
            new HumanPlayer(usernameData);
            return true;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * returns true if user is in the database and the passwords are matching.
     * If the user is not in the DB it will show an alert saying that the user does not exist.
     * @param usernameData
     * Name that the user has entered
     * @param passwordData
     * Password that the user has entered
     * @return
     */
    public Boolean loginUser(String usernameData, String passwordData){
        try{
            if(userExists(usernameData)){
                if(passwordCheck(usernameData, passwordData)){
                    new HumanPlayer(usernameData);
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Username does not exist");
                alert.setHeaderText("This username is not in the database");
                alert.showAndWait();
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
    }

    /**
     * Checks if the entered username already exists in the database
     * @param usernameData
     * Name that the user has entered
     * @return
     */
    public Boolean userExists(String usernameData){
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


    /**
     * Returns true if the password given is matching the password of the given username.
     * If not it will show an alert saying that the password is wrong.
     * @param usernameData
     * Name that the user has entered
     * @param passwordData
     * Password that the user has entered
     */
    public Boolean passwordCheck(String usernameData,String passwordData){
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

    /**
     * Will first clear the current leaderboard and then fill it with fresh data.
     * It will create a row with name,gamesPlayed,wins,losses,averageMoves,averageTime for each user
     *
     */
    public void fillLeaderboard(){
        String tempName="Empty";
        String name="Empty";
        int gamesPlayed=8;
        int wins=0;
        int losses=0;
        double averageMoves=0;
        double averageTime=0;
        LeaderboardData.LeaderboardData.clear();

        //General:

        for(int i=0;i<30;i++) {
            String checkString = "SELECT player_username,SUM(CASE WHEN player_won IS TRUE THEN 1 ELSE 0 END),COUNT(*) AS games_played FROM sessions WHERE is_finished = ? GROUP BY player_username OFFSET ? LIMIT ?";
            try (PreparedStatement ps1 = connection.prepareStatement(checkString)) {
                ps1.setBoolean(1, true);
                ps1.setInt(2, i);
                ps1.setInt(3, 1);
                try (ResultSet rs = ps1.executeQuery()) {
                    if (rs.next()) {
                        name=rs.getString(1);
                        wins=rs.getInt(2);
                        gamesPlayed=rs.getInt(3);
                        losses=gamesPlayed-wins;
                    }
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

           // Average Moves/ Time:

            String checkString2 = "SELECT COUNT(moveid),SUM(EXTRACT(EPOCH FROM move_end_time) - EXTRACT(EPOCH FROM move_start_time)) AS totalSeconds FROM moves WHERE was_ai = ? AND sessionid IN (SELECT sessionid FROM sessions WHERE player_username=? )";
            try (PreparedStatement ps1 = connection.prepareStatement(checkString2)) {
                ps1.setBoolean(1, false);
                ps1.setString(2, name);
                try (ResultSet rs = ps1.executeQuery()) {
                    if (rs.next()) {
                        double moves=rs.getInt(1);
                        if(gamesPlayed!=0){averageMoves=moves/gamesPlayed;}
                        else{averageMoves=0;}
                        double seconds=rs.getInt(2);
                        averageTime=seconds/moves;
                    }
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            if (name.equals(tempName)){break;} //To prevent loop from displaying same rows over and over
            else{tempName=name;}

            LeaderboardData.LeaderboardData.add(new LeaderboardData(name,gamesPlayed,wins,losses,averageMoves,averageTime));
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
