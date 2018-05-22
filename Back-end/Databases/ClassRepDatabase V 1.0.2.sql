-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 02, 2018 at 05:47 AM
-- Server version: 5.6.14
-- PHP Version: 5.5.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mannschaft`
--

-- --------------------------------------------------------

--
-- Table structure for table `course session`
--

CREATE TABLE IF NOT EXISTS `CourseSession` (

  `CourseCode` varchar(15) NOT NULL,
  `StartingTime` time NOT NULL,
  `EndingTime` time NOT NULL,
  `Venue` varchar(20) NOT NULL,
  `Techmail` varchar(25) NOT NULL,
  `ProgrammeAndYear` varchar(20) NOT NULL,
  `Day` varchar(10) NOT NULL,
  `ID` int NOT NULL AUTO_INCREMENT,

  PRIMARY KEY (`ID`)
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `course table`
--

--## REMOVED; ADD COURSE NAME TO SESSION TABLE

-- --------------------------------------------------------

--
-- Table structure for table `lecturer table`
--

CREATE TABLE IF NOT EXISTS `Lecturers` (
  `TechMail` varchar(20) NOT NULL,
  `FirstName` varchar(10) NOT NULL,
  `LastName` varchar(10) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Title` varchar(5) NOT NULL,
  PRIMARY KEY (`TechMail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `message table`
--

CREATE TABLE IF NOT EXISTS `Posts` (

  `PostID` int NOT NULL AUTO_INCREMENT,
  `Message` varchar(140) NOT NULL,
  `Attachment` char(1) NOT NULL,
  `Votable`  char(2) NOT NULL,
  `SentBy` text NOT NULL,
  `TimeSent` timestamp() NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `students table`
--

CREATE TABLE IF NOT EXISTS `Students` (
  `IndexNumber` varchar(10) NOT NULL,
  `FirstName` varchar(10) NOT NULL,
  `LastName` varchar(15) NOT NULL,
  `ProgrammeAndYear` varchar(25) NOT NULL,
  `Password` varchar(15) NOT NULL,
  `College` varchar(30) NOT NULL,
  PRIMARY KEY (`IndexNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vote table`
--

CREATE TABLE IF NOT EXISTS  `Votes` (

  `PostID` varchar(25) NOT NULL,
  `IndexNumber` varcahr(10) NOT NULL,
  `Vote` char(3) NOT NULL,
  FOREIGN KEY (`PostID`) REFERENCES Posts(`PostID`)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--## there is no table for files
--## HOLD ON FOR NOW

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
