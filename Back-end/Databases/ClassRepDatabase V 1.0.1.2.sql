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

CREATE TABLE IF NOT EXISTS `Course_session` (

--## REPLACE `Course_Code` WITH `Course (Code)`

  `Course(code)` varchar(15) NOT NULL,
  `Course_Name` varchar(15) NOT NULL,
  `Starting_Time` time NOT NULL,
  `Ending_Time` time NOT NULL,
  `Venue` varchar(20) NOT NULL,
  `Techmail` varchar(25) NOT NULL,
  `Programme_Year` varchar(20) NOT NULL,
  `Day` varchar(10) NOT NULL,

--## THIS WILL HAVE TO BE CHANGED TOO
  PRIMARY KEY (`Course(code)`)
  
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

CREATE TABLE IF NOT EXISTS `lecturer table` (
  `Tech_MAil` varchar(20) NOT NULL,
  `First_Name` varchar(10) NOT NULL,
  `Last_Name` varchar(10) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Title` varchar(5) NOT NULL,
  PRIMARY KEY (`Tech_MAil`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `message table`
--

CREATE TABLE IF NOT EXISTS `Posts table` (

--## CHANGE TABLE NAME TO `Posts`
--## `Post ID` --> VARCHAR(25 or 30)
--## there's no column for the `Message Text`, `Attachment` --> CHAR (1), `Votable` --> CHAR (Y)
  `Post_ID` varchar(25) NOT NULL,
  `Message` text NOT NULL,
  `Attachment` char(1) NOT NULL,
  `Votable`  char(2) NOT NULL,
  `Sent_By` text NOT NULL,

--## REVIEW TIMESTAMP FORMAT. 6 IS NOT ENOUGH FOR TO STORE DATE AND TIME. IF POSSIBLE LEAVE IT BLANK

  `Time_sent` timestamp() NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `students table`
--

CREATE TABLE IF NOT EXISTS `students table` (
  `Index_Number` varchar(10) NOT NULL,
  `First_Name` varchar(10) NOT NULL,
  `Last_Name` varchar(15) NOT NULL,

--## include `College`

  `Programme_Year` varchar(25) NOT NULL,
  `Password` varchar(15) NOT NULL,
  `College` varchar(30) NOT NULL,
  PRIMARY KEY (`Index_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vote table`
--

CREATE TABLE IF NOT EXISTS `Votes table` (

--## CHANGE NAME TO `Votes`
--## INCLUDE `Message ID` --> VARCHAR(25 or 30)[this should be the same as the message ID in the Messages table(Posts)]
--## INCLUDE `Index Number`
--## CHANGE `For` AND `Vote_down` TO `Vote` --> CHAR(3)

  `Message_ID` varchar(25) NOT NULL,
  `Index_Number` varcahr(10) NOT NULL,
  `Vote` char(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--## there is no table for files
--## HOLD ON FOR NOW

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
