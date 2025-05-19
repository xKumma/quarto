package be.kdg.integration2.mvpglobal.utility.dbconnection;

import be.kdg.integration2.mvpglobal.model.HumanPlayer;
import be.kdg.integration2.mvpglobal.model.dataobjects.LeaderboardData;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

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

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DBManager getInstance() {
        if (Instance == null) {
            Instance = new DBManager();
        }
        return Instance;
    }

    /**
     * Setup database in case of valid connection but no tables.
     */
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

    /**
     * Close connection.
     */
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    /**
     * Insert new move.
     *
     * @param player    the player
     * @param x         the x
     * @param y         the y
     * @param startTime the start time
     * @param endTime   the end time
     * @throws SQLException If a database access error occurs.
     */
    public void insertNewMove(String player, int x, int y, long startTime, long endTime) throws SQLException {
        String insertNewMove =
                "INSERT INTO moves(sessionID, move_start_time, move_end_time, was_ai) VALUES(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insertNewMove);
        ps.setInt(1, currentSessionID);

        ps.setTimestamp(2, new Timestamp(startTime));
        ps.setTimestamp(3, new Timestamp(endTime));

        boolean wasAI = !player.equals(HumanPlayer.getInstance().getName());
        ps.setBoolean(4, wasAI);

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Inserts a new game session into the database.
     *
     * @param currentPlayer The username of the current player.
     * @param playerWon     A boolean indicating whether the player won the session.
     * @throws SQLException If a database access error occurs.
     */
    public void insertNewSession(String currentPlayer, boolean playerWon) throws SQLException {
        String insertNewSession = "INSERT INTO sessions(player_username, is_finished, player_won) VALUES(?, TRUE, ?)";
        PreparedStatement ps = connection.prepareStatement(insertNewSession, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, currentPlayer);


        ps.setBoolean(2, playerWon);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            currentSessionID = rs.getInt(1);
        }

        rs.close();
        ps.close();
    }

    /**
     * Gets winner name.
     *
     * @return the winner name
     * @throws SQLException If a database access error occurs.
     */
    public String getWinnerName () throws SQLException {
        String selectWasAI = "SELECT was_ai FROM moves WHERE sessionID = ? ORDER BY moveID DESC LIMIT 1";
        PreparedStatement ps = connection.prepareStatement(selectWasAI);
        ps.setInt(1, getSessionID());
        ResultSet rs = ps.executeQuery();
        rs.next();
        if (rs.getBoolean(1)) return "bot";
        else return HumanPlayer.getInstance().getName();

    }

    /**
     * Gets id for the current session.
     *
     * @return the session id
     * @throws SQLException If a database access error occurs.
     */
    public int getSessionID() throws SQLException {
        return currentSessionID;
    }

    /**
     * Gets user name from session.
     *
     * @param sessionID the session id
     * @return the user name from session
     * @throws SQLException If a database access error occurs.
     */
    public String getUserNameFromSession (int sessionID) throws SQLException {
        String selectBotName = "SELECT player_username FROM sessions WHERE sessionID = ?";
        PreparedStatement ps = connection.prepareStatement(selectBotName);
        ps.setInt(1, sessionID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString(1);
    }


    public double getTimeIN(int sessionid ) throws SQLException {
        String query = "SELECT EXTRACT(EPOCH FROM ( move_start_time))  FROM moves WHERE sessionId = ? ORDER BY moveID ASC LIMIT 1  ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sessionid);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1); // Restituisce il valore in minuti (con secondi nei decimali)
                }
            }
        }
        return 0; // Valore di default se non ci sono risultati
    }

    public double getTMAIs(int sessionid ) throws SQLException {
        String query = "SELECT EXTRACT(EPOCH FROM ( move_start_time  ))  FROM moves WHERE sessionId = ? AND was_ai = TRUE ORDER BY moveID ASC LIMIT 1  ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sessionid);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1); // Restituisce il valore in minuti (con secondi nei decimali)
                }
            }
        }
        return 0; // Valore di default se non ci sono risultati
    }

    public double getTMAIf(int sessionid ) throws SQLException {
        String query = "SELECT EXTRACT(EPOCH FROM ( move_end_time  ))  FROM moves WHERE sessionId = ? AND was_ai = TRUE ORDER BY moveID DESC LIMIT 1  ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sessionid);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1); // Restituisce il valore in minuti (con secondi nei decimali)
                }
            }
        }
        return 0; // Valore di default se non ci sono risultati
    }

    public double getTMPs(int sessionid ) throws SQLException {
        String query = "SELECT EXTRACT(EPOCH FROM ( move_start_time  ))  FROM moves WHERE sessionId = ? AND was_ai = FALSE ORDER BY moveID ASC LIMIT 1  ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sessionid);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1); // Restituisce il valore in minuti (con secondi nei decimali)
                }
            }
        }
        return 0; // Valore di default se non ci sono risultati
    }

    public double getTMPf(int sessionid ) throws SQLException {
        String query = "SELECT EXTRACT(EPOCH FROM ( move_end_time  ))  FROM moves WHERE sessionId = ? AND was_ai = FALSE ORDER BY moveID DESC LIMIT 1  ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sessionid);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1); // Restituisce il valore in minuti (con secondi nei decimali)
                }
            }
        }
        return 0; // Valore di default se non ci sono risultati
    }

    public double getTimeFIN(int sessionid ) throws SQLException {
        String query = "SELECT EXTRACT(EPOCH FROM (move_end_time ))  FROM moves WHERE sessionId = ? ORDER BY moveID DESC LIMIT 1  ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sessionid);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1); // Restituisce il valore in minuti (con secondi nei decimali)
                }
            }
        }
        return 0; // Valore di default se non ci sono risultati
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

    public int getNMovesAI(int sessionid ) throws SQLException {
        String query = "SELECT COUNT(moveid) FROM moves WHERE sessionID = ? AND was_ai = true";
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

    public int getNMovesPL(int sessionid ) throws SQLException {
        String query = "SELECT COUNT(moveid) FROM moves WHERE sessionID = ? AND was_ai = false";
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

    /**
     * Was a move made by AI boolean.
     *
     * @param moveid the moveid
     * @return true if AI made the move, false otherwise
     * @throws SQLException  If a database access error occurs.
     */
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
     *
     * @param usernameData Name that the user has entered
     * @param passwordData Password that the user has entered
     * @return returns true if user was successfully registered
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
     * Calls username check and password check and creates a player object if that is the case
     *
     * @param usernameData Name that the user has entered
     * @param passwordData Password that the user has entered
     * @return returns true if user is in the database and the passwords are matching.
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
     * Compares given Username with database
     *
     * @param usernameData Name that the user has entered
     * @return returns a flag which is true if the user exists in the DB
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
     * Compares given Username and Password with database
     *
     * @param usernameData Name that the user has entered
     * @param passwordData Password that the user has entered
     * @return returns a flag which is true when the password and username have a match in the DB
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
                        if(gamesPlayed!=0){ averageMoves=Math.round((moves/gamesPlayed)*Math.pow(10,2))/Math.pow(10,2);}
                        else{averageMoves=0;}
                        double seconds=rs.getInt(2);
                        averageTime= Math.round((seconds/moves)*Math.pow(10,4))/Math.pow(10,4);

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

    /**
     * Is connected boolean.
     *
     * @return true if connected, false otherwise
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
