--i need this for the graphs do not delete pliz
INSERT INTO human_players (username, password)
VALUES
    ('Emma', 'Isuckatpasswords'),
    ('Bella', 'unicorns'),
    ('Jacob', 'Twilight'),
    ('Fiona', 'cookies'),
    ('Emily', 'dogfood'),
    ('Cecilia', 'flowers'),
    ('Alan', 'showcase'),
    ('Milan', 'milksoup'),
    ('Sofia', 'programmer'),
    ('Stuard', 'worcestershire')
;

INSERT INTO sessions(player_username, is_finished, player_won)
VALUES
    ('Emma', true, false),
    ('Emma', true, true),
    ('Emma', false, NULL),
    ('Bella', true, false),
    ('Cecilia', false, NULL),
    ('Milan',  false, NULL),
    ('Stuard', true, false),
    ('Alan', false, NULL),
    ('Fiona', true, true),
    ('Emily',  true, false)
;


INSERT INTO moves(moveID, sessionID, move_start_time, move_end_time, was_ai)
VALUES
    (1, 1, '2024-12-01 12:24:24', '2024-12-01 12:24:31', false),
    (2, 1, '2024-12-01 12:24:31', '2024-12-01 12:24:45', true),
    (3, 1, '2024-12-01 12:24:31', '2024-12-01 12:24:40', false),
    (4, 1, '2024-12-01 12:24:40', '2024-12-01 12:24:42', true),
    (5, 1, '2024-12-01 12:24:40', '2024-12-01 12:24:53', false),
    (6, 1, '2024-12-01 12:24:53', '2024-12-01 12:24:57', true),
    (7, 1, '2024-12-01 12:24:24', '2024-12-01 12:24:40', false),
    (8, 1, '2024-12-01 12:24:31', '2024-12-01 12:24:50', true),
    (9, 1, '2024-12-01 12:24:31', '2024-12-01 12:24:45', false),
    (10, 1, '2024-12-01 12:24:40', '2024-12-01 12:24:49', true),
    (11, 1, '2024-12-01 12:24:40', '2024-12-01 12:24:58', false),
    (12, 1, '2024-12-01 12:24:53', '2024-12-01 12:24:54', true),
    (13, 10, '2024-12-01 12:24:24', '2024-12-01 12:24:31', false),
    (14, 10, '2024-12-01 12:24:31', '2024-12-01 12:24:45', true),
    (15, 10, '2024-12-01 12:24:31', '2024-12-01 12:24:40', false),
    (16, 10, '2024-12-01 12:24:40', '2024-12-01 12:24:42', true),
    (17, 10, '2024-12-01 12:24:40', '2024-12-01 12:24:53', false),
    (18, 10, '2024-12-01 12:24:53', '2024-12-01 12:24:57', true),
    (19, 10, '2024-12-01 12:24:24', '2024-12-01 12:24:40', false),
    (20, 10, '2024-12-01 12:24:31', '2024-12-01 12:24:50', true),
    (21, 10, '2024-12-01 12:24:31', '2024-12-01 12:24:45', false),
    (22, 10, '2024-12-01 12:24:40', '2024-12-01 12:24:49', true),
    (23, 10, '2024-12-01 12:24:40', '2024-12-01 12:24:58', false),
    (24, 10, '2024-12-01 12:24:53', '2024-12-01 12:24:54', true)
;
