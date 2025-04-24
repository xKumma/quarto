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


INSERT INTO pieces(pieceID, type, size, shape, color)
VALUES
    (1, 'full', 'big', 'square', 'red'),
    (2, 'full', 'small', 'square', 'red'),
    (3, 'hollow', 'small', 'square', 'blue'),
    (4, 'full', 'small', 'round', 'blue'),
    (5, 'hollow', 'big', 'round', 'red'),
    (6, 'full', 'big', 'round', 'red'),
    (7, 'hollow', 'big', 'round', 'blue'),
    (8, 'hollow', 'small', 'round', 'blue'),
    (9, 'hollow', 'big', 'square', 'red'),
    (10, 'hollow', 'small', 'round', 'red'),
    (11, 'full', 'big', 'round', 'blue'),
    (12, 'full', 'small', 'square', 'blue'),
    (13, 'full', 'small', 'round', 'red'),
    (14, 'hollow', 'small', 'square', 'red'),
    (15, 'full', 'big', 'square', 'blue'),
    (16, 'hollow', 'big', 'square', 'blue')
;

INSERT INTO bot_players(bot_name, bot_difficulty)
VALUES
    ('Plamen', 1),
    ('Moira', 2),
    ('Beatrice', 3),
    ('Maxim', 4)
;

INSERT INTO sessions(player_username, bot_name, is_finished, player_won)
VALUES
    ('Emma', 'Plamen', true, false),
    ('Emma', 'Plamen', true, true),
    ('Emma', 'Moira', false, NULL),
    ('Bella', 'Maxim', true, false),
    ('Cecilia', 'Beatrice', false, NULL),
    ('Milan', 'Beatrice', false, NULL),
    ('Stuard', 'Maxim', true, false),
    ('Alan', 'Moira', false, NULL),
    ('Fiona', 'Plamen', true, true),
    ('Emily', 'Beatrice', true, false)
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
    (12, 1, '2024-12-01 12:24:53', '2024-12-01 12:24:54', true)
;
;


INSERT INTO piece_locations(sessionID, pieceID, position_x, position_y)
VALUES
    (1, 1, 1, 1),
    (1, 13, 1, 2),
    (1, 14, 2, 2),
    (1, 6, 1, 3),
    (1, 3, 3, 3),
    (1, 11, 1, 4)
;
