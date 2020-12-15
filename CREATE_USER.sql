
CREATE user 'younes'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON *.* TO 'younes'@'localhost' WITH GRANT OPTION;
GRANT PROXY ON ''@'%' TO 'younes'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON marketbenackcha .* TO 'younes'@'localhost' WITH GRANT OPTION;
