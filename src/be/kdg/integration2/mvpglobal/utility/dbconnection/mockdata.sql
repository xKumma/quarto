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


INSERT INTO pieces(pieceID, type, size, shape, color , abstract)
VALUES
    (0, 'full', 'big', 'square', 'red', 0000),
    (1, 'full', 'small', 'square', 'red', 0001),
    (2, 'hollow', 'small', 'square', 'blue',0010),
    (3, 'full', 'small', 'round', 'blue',0011),
    (4, 'hollow', 'big', 'round', 'red',0100),
    (5, 'full', 'big', 'round', 'red',0101),
    (6, 'hollow', 'big', 'round', 'blue',0110),
    (7, 'hollow', 'small', 'round', 'blue',0111),
    (8, 'hollow', 'big', 'square', 'red',1000),
    (9, 'hollow', 'small', 'round', 'red',1001),
    (10, 'full', 'big', 'round', 'blue',1010),
    (11, 'full', 'small', 'square', 'blue',1011),
    (12, 'full', 'small', 'round', 'red',1100),
    (13, 'hollow', 'small', 'square', 'red',1101),
    (14, 'full', 'big', 'square', 'blue',1110),
    (15, 'hollow', 'big', 'square', 'blue',1111)
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
	(2, 1, '2024-12-01 12:24:31', '2024-12-01 12:24:31', true),
	(3, 1, '2024-12-01 12:24:31', '2024-12-01 12:24:40', false),
	(4, 1, '2024-12-01 12:24:40', '2024-12-01 12:24:40', true),
	(5, 1, '2024-12-01 12:24:40', '2024-12-01 12:24:53', false),
	(6, 1, '2024-12-01 12:24:53', '2024-12-01 12:24:53', true)
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