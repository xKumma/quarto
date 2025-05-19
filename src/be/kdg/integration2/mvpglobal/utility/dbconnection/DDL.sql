-- USED FOR RECREATION,COMMENT OUT IF YOU DONT WANT TO REMOVE ALL DATA
DROP TABLE IF EXISTS HUMAN_PLAYERS CASCADE ;
DROP TABLE IF EXISTS SESSIONS CASCADE ;
DROP TABLE IF EXISTS MOVES CASCADE ;

-- TABLE: HUMAN PLAYERS
CREATE TABLE IF NOT EXISTS HUMAN_PLAYERS (
                                             username VARCHAR(16) PRIMARY KEY
                                                 constraint ch_username_length check ( length(username) >= 4 )
                                                 constraint ch_no_spaces_username CHECK (username !~ ' '),
                                             password VARCHAR(32) NOT NULL
                                                 constraint ch_pass_length check ( length(password) >= 6 )
                                                 constraint ch_no_spaces_pass CHECK (password !~ ' ')
);


-- TABLE: SESSIONS
CREATE TABLE IF NOT EXISTS SESSIONS (
                                        sessionID SERIAL PRIMARY KEY,
                                        player_username VARCHAR(16),
                                        is_finished BOOLEAN DEFAULT FALSE NOT NULL,
                                        player_won boolean DEFAULT NULL,

                                        FOREIGN KEY (player_username) REFERENCES HUMAN_PLAYERS(username) ON DELETE CASCADE
);

-- TABLE: MOVES
CREATE TABLE IF NOT EXISTS MOVES (
                                     moveID SERIAL PRIMARY KEY,
                                     sessionID SERIAL NOT NULL,
                                     move_start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     move_end_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     was_ai BOOLEAN NOT NULL,

                                     FOREIGN KEY (sessionID) REFERENCES SESSIONS(sessionID) ON DELETE CASCADE
);

