--
-- Blackjack Database
-- Owners: Team 2
-- Created by: Tyler Schindel
--

DROP DATABASE IF EXISTS blackjack;


CREATE DATABASE blackjack;

USE blackjack;

CREATE TABLE Users (
	userID		smallInt NOT NULL AUTO_INCREMENT,
	userName	varchar(25) NOT NULL DEFAULT '',
	password	varchar(20) NOT NULL DEFAULT '',
	joinDate	date NOT NULL,
	fName		varchar(15) NOT NULL DEFAULT '',
	lName		varchar(15) NOT NULL DEFAULT '',
	PRIMARY KEY(userID),
	INDEX usersName_Ind (userID, userName)
);

-- New table idea, breaking up Users
/*

CREATE TABLE UserInfo (
	ID		smallint NOT NULL AUTO_INCREMENT,
	userName	varchar(25) NOT NULL DEFAULT '',
	password	varchar(20) NOT NULL DEFAULT '',
	PRIMARY KEY (ID),
);	

CREATE TABLE Users (
	userID		smallInt NOT NULL AUTO_INCREMENT,
	joinDate	date NOT NULL,
	fName		varchar(15) NOT NULL DEFAULT '',
	lName		varchar(15) NOT NULL DEFAULT '',
	FOREIGN KEY(userID) REFERENCES UserInfo(ID)
		ON DELETE CASCADE,
	INDEX usersName_Ind (userID, userName)
);

-- Furthermore, extend all the other tables (except ServerStats) to UserInfo
*/

/*
	All player's financial data
	- Emergency Funding allows emergency funds to be withdrawn during a session
*/
CREATE TABLE FinancialData (
	accountID				smallInt NOT NULL AUTO_INCREMENT,
	bank					double(10, 2) DEFAULT '0.0',
	creditCardNum			varchar(16) DEFAULT NULL,
	allowEmergencyFunding	tinyint(1) DEFAULT '0',
	emergencyFundAmt		double(5, 2) DEFAULT NULL,
	FOREIGN KEY (accountID) REFERENCES Users(userID)
		ON DELETE CASCADE,
	INDEX acctID_Ind (accountID)
);

/*
	All player's money handling stats
*/
CREATE TABLE UserMoneyStats (
	moneyID				smallInt NOT NULL AUTO_INCREMENT,
	userEarnings		double(100, 2) DEFAULT '0.0',
	topBet				double(7, 2) DEFAULT '0.0',
	singleHandHigh		double(5, 2) DEFAULT '0.0',
	singleSessionHigh	double(10, 2) DEFAULT '0.0',
	FOREIGN KEY (moneyID) REFERENCES Users(userID)
		ON DELETE CASCADE,
	INDEX mnyID_Ind (moneyID)
);

/*
	All player's card handling stats
*/
CREATE TABLE UserCardStats (
	cardID				smallInt NOT NULL AUTO_INCREMENT,
	numOfBlackjacks		smallInt DEFAULT '0',
	numOfHits			smallInt DEFAULT '0',
	numOfStands			smallInt DEFAULT '0',
	numOfDouble			smallInt DEFAULT '0',
	percentOfWins		double(4,2) DEFAULT '0.0',
	FOREIGN KEY (cardID) REFERENCES Users(userID)
		ON DELETE CASCADE,
	INDEX crdID_Ind (cardID)
);

/*
	ServerID is an indication of the current server session (as of last restart)
*/
CREATE TABLE ServerStats (
	serverID			smallInt NOT NULL AUTO_INCREMENT,
	numNewUsers			smallInt DEFAULT '0',
	numUsersPlayed		smallInt DEFAULT '0',
	totalAmtBet			double DEFAULT '0.0',
	dealerWins			smallInt DEFAULT '0',
	userWins			smallInt DEFAULT '0',
	dealerEarnings		double DEFAULT '0.0',
	totalBlackjacks		tinyint DEFAULT '0',
	lastServerReboot	date NOT NULL,
	PRIMARY KEY (serverID),
	INDEX svrID_Ind (serverID)
);
