

--
-- Adding new user
--
GRANT ALL PRIVILEGES ON blackjack.* TO 'blackjack'@'localhost' IDENTIFIED BY 'blackjack';
FLUSH PRIVILEGES;

--
-- Creating the database
--
DROP DATABASE IF EXISTS blackjack;
CREATE DATABASE blackjack;

--
-- Select this database
--
USE blackjack;


--
-- Structure of table `FinancialData`
--

CREATE TABLE IF NOT EXISTS `FinancialData` (
  `userID` smallint(6) NOT NULL,
  `bank` double(10,2) default '0.00',
  `creditCardNum` varchar(16) default NULL,
  PRIMARY KEY  (`userID`),
  KEY `acctID_Ind` (`bank`)
) TYPE=MyISAM;

--
-- Content of table `FinancialData`
--

INSERT INTO `FinancialData` (`userID`, `bank`, `creditCardNum`) VALUES
(1, 4030.00, '555893245'),
(2, 44560.00, '888772345'),
(3, 560.00, NULL),
(4, 10000.00, NULL);

-- --------------------------------------------------------

--
-- Structure of table `ServerStats`
--

CREATE TABLE IF NOT EXISTS `ServerStats` (
  `serverID` smallint(6) NOT NULL auto_increment,
  `numNewUsers` smallint(6) default '0',
  `numUsersPlayed` smallint(6) default '0',
  `totalAmtBet` double default '0',
  `dealerWins` smallint(6) default '0',
  `userWins` smallint(6) default '0',
  `dealerEarnings` double default '0',
  `totalBlackjacks` tinyint(4) default '0',
  `lastServerReboot` date NOT NULL,
  PRIMARY KEY  (`serverID`)
) TYPE=MyISAM  AUTO_INCREMENT=2 ;

--
-- Content of table `ServerStats`
--

INSERT INTO `ServerStats` (`serverID`, `numNewUsers`, `numUsersPlayed`, `totalAmtBet`, `dealerWins`, `userWins`, `dealerEarnings`, `totalBlackjacks`, `lastServerReboot`) VALUES
(1, 7, 3, 13005, 46, 27, 473, 20, '2008-07-03');

-- --------------------------------------------------------

--
-- Structure of table `UserCardStats`
--

CREATE TABLE IF NOT EXISTS `UserCardStats` (
  `userID` smallint(6) NOT NULL,
  `numOfBlackjacks` smallint(6) default '0',
  `numOfHits` smallint(6) default '0',
  `numOfStands` smallint(6) default '0',
  `numOfDoubles` smallint(6) default '0',
  `numOfWins` smallint(6) default '0',
  `numOfLoss` smallint(6) default '0',
`numOfPushes` smallint(6) default '0',
  PRIMARY KEY  (`userID`)
) TYPE=MyISAM;

--
-- Content of table `UserCardStats`
--

INSERT INTO `UserCardStats` (`userID`, `numOfBlackjacks`, `numOfHits`, `numOfStands`, `numOfDoubles`, `numOfWins`, `numOfLoss`, `numOfPushes`) VALUES
(1, 4, 27, 18, 3, 30, 50, 7),
(2, 13, 57, 13, 9, 83, 70, 5),
(3, 1, 5, 2, 2, 12, 90, 0),
(4, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Structure of table `UserInfo`
--

CREATE TABLE IF NOT EXISTS `UserInfo` (
  `userID` smallint(6) NOT NULL,
  `fName` varchar(15) NOT NULL default '',
  `lName` varchar(15) NOT NULL default '',
  `email` varchar(40) NOT NULL default '',
  `dateLastPlayed` date default NULL,
  `dateJoined` date NOT NULL,
  PRIMARY KEY  (`userID`)
) TYPE=MyISAM;

--
-- Content of table `UserInfo`
--

INSERT INTO `UserInfo` (`userID`, `fName`, `lName`, `email`, `dateLastPlayed`, `dateJoined`) VALUES
(1, 'Slick', 'Devil', 'toTheExtreme@maxx.com', '2009-01-15', '2008-08-13'),
(2, 'James', 'Bond', 'shootToKill@goldeneye.com', '2009-01-15', '2008-12-13'),
(3, 'Paul', 'Bunyon', 'blueOxBuddy@outdoors.com', '2009-01-15', '2007-04-18'),
(4, 'Thomas', 'Anderson', 'neo@thematrix.com', '2009-01-15', '2007-04-18');

-- --------------------------------------------------------

--
-- Structure of table `UserMoneyStats`
--

CREATE TABLE IF NOT EXISTS `UserMoneyStats` (
  `userID` smallint(6) NOT NULL,
  `userEarnings` double(100,2) default '0.00',
  `topBet` double(10,2) default '0.00',
  `singleHandHigh` double(10,2) default '0.00',
  `singleSessionHigh` double(10,2) default '0.00',
  PRIMARY KEY  (`userID`)
) TYPE=MyISAM;

--
-- Content of table `UserMoneyStats`
--

INSERT INTO `UserMoneyStats` (`userID`, `userEarnings`, `topBet`, `singleHandHigh`, `singleSessionHigh`) VALUES
(1, 4500.00, 125.00, 100.00, 475.00),
(2, 7500.00, 755.00, 1005.00, 4067.00),
(3, 40.00, 5.00, 18.00, 27.00),
(4, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Structure of table `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `userID` smallint(6) NOT NULL auto_increment,
  `userName` varchar(25) NOT NULL default '',
  `pwd` varchar(20) NOT NULL default '',
  PRIMARY KEY  (`userID`)
) TYPE=MyISAM ;

--
-- Content of table `Users`
--

INSERT INTO `Users` (`userID`, `userName`, `pwd`) VALUES
(1, 'CoolPlayer', 'coolGuy'),
(2, 'Bond007', 'bangbang'),
(3, 'MountainMan', 'isleepwithbears'),
(4, 'foo', 'blah');

