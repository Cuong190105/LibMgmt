-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 07, 2024 at 12:12 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `librarydb`
--
CREATE DATABASE IF NOT EXISTS `testdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE IF NOT EXISTS `librarydb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `librarydb`;

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--

DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `UID` int(11) NOT NULL,
  `DocID` int(11) NOT NULL,
  `BorrowingDate` date DEFAULT NULL,
  `DueDate` date DEFAULT NULL,
  `ReturnDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `UID` int(11) NOT NULL,
  `DocID` int(11) NOT NULL,
  `Content` text DEFAULT NULL,
  `Rating` int(11) DEFAULT NULL,
  `Date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `document`
--

DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `DocID` int(11) NOT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `Author` varchar(255) DEFAULT NULL,
  `Publisher` varchar(255) DEFAULT NULL,
  `PublishYear` int(11) DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `Tags` varchar(255) DEFAULT NULL,
  `Visited` int(11) DEFAULT NULL,
  `thesis` tinyint(1) DEFAULT 0,
  `ISBN` varchar(50) DEFAULT NULL,
  `Votes` int(11) DEFAULT NULL,
  `Score` double DEFAULT NULL,
  `Cover` mediumblob DEFAULT NULL,
  `Description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `documentcontent`
--

DROP TABLE IF EXISTS `documentcontent`;
CREATE TABLE `documentcontent` (
  `docID` int(11) DEFAULT NULL,
  `content` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `UID` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Sex` varchar(10) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `Address` text DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `SSN` varchar(50) DEFAULT NULL,
  `isLibrarian` tinyint(1) DEFAULT NULL,
  `avatar` mediumblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `borrow`
--
ALTER TABLE `borrow`
  ADD KEY `UID` (`UID`),
  ADD KEY `DocID` (`DocID`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD KEY `UID` (`UID`),
  ADD KEY `DocID` (`DocID`);

--
-- Indexes for table `document`
--
ALTER TABLE `document`
  ADD PRIMARY KEY (`DocID`);

--
-- Indexes for table `documentcontent`
--
ALTER TABLE `documentcontent`
  ADD KEY `docID` (`docID`) USING BTREE;

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `document`
--
ALTER TABLE `document`
  MODIFY `DocID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `UID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`UID`) REFERENCES `user` (`UID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`DocID`) REFERENCES `document` (`DocID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`UID`) REFERENCES `user` (`UID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`DocID`) REFERENCES `document` (`DocID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `documentcontent`
--
ALTER TABLE `documentcontent`
  ADD CONSTRAINT `documentcontent_ibfk_1` FOREIGN KEY (`docID`) REFERENCES `document` (`DocID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
