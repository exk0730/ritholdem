
@echo off
@REM

echo Connecting to mySQL using 'root' account to execute the SQL script Blackjack\sql\script.sql...


mysql -u root -p -B < ..\sql\script.sql && echo Database 'blackjack' have been created. && echo User 'blackjack' with password 'blackjack' have been created. && pause