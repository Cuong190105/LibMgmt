-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 09, 2024 at 10:54 PM
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
-- Database: `accountdb`
--
CREATE DATABASE IF NOT EXISTS `accountdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `accountdb`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `Username` varchar(255) NOT NULL,
  `HashedPassword` varchar(255) NOT NULL,
  `UID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`Username`, `HashedPassword`, `UID`) VALUES
('anhtuan', 'password2', 2),
('baotran', 'password16', 16),
('dinhquang', 'password8', 8),
('duongkhoa', 'password18', 18),
('hoangnam', 'password10', 10),
('khanhvan', 'password20', 20),
('kimanh', 'password3', 3),
('kingaloo', 'lollypop', 1),
('lehuynh', 'password19', 19),
('librarian', 'librarian', 21),
('minhhoang', 'password4', 4),
('ngochuy', 'password12', 12),
('nhathao', 'password17', 17),
('phuongthao', 'password13', 13),
('thanhha', 'password11', 11),
('thibich', 'password5', 5),
('thuylinh', 'password9', 9),
('trongtuan', 'password14', 14),
('tuyetmai', 'password7', 7),
('vanhieu', 'password6', 6),
('yenngoc', 'password15', 15);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`Username`),
  ADD KEY `UID` (`UID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`UID`) REFERENCES `librarydb`.`user` (`UID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
