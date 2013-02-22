-- phpMyAdmin SQL Dump
-- version 3.4.5deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: Mag 24, 2012 alle 23:19
-- Versione del server: 5.0.83
-- Versione PHP: 5.3.6-13ubuntu3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `my1204`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `attachments`
--

CREATE TABLE IF NOT EXISTS `attachments` (
  `id` int(11) NOT NULL,
  `att_name` varchar(48) NOT NULL,
  `att_type` varchar(12) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `data_sheet`
--

CREATE TABLE IF NOT EXISTS `data_sheet` (
  `fiscal_code` char(16) NOT NULL,
  `p_name` varchar(24) NOT NULL,
  `p_surname` varchar(24) NOT NULL,
  `date_born` varchar(16) NOT NULL,
  `place_born` varchar(64) default NULL,
  `address` varchar(128) default NULL,
  `city` varchar(32) default NULL,
  `occupation` varchar(64) default NULL,
  `telephone` varchar(13) default NULL,
  `exemption_code` varchar(12) NOT NULL default 'N',
  `area_code` varchar(5) NOT NULL default '00000',
  PRIMARY KEY  (`fiscal_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `data_sheet`
--

INSERT INTO `data_sheet` (`fiscal_code`, `p_name`, `p_surname`, `date_born`, `place_born`, `address`, `city`, `occupation`, `telephone`, `exemption_code`, `area_code`) VALUES
('UNODAO90NOD87AD9', 'Guido', 'Maluccio', '1967-04-03', 'Ferrara', 'via ferrarese', 'Ferrara', 'Meccanico', '8989980090988', '905', '40123'),
('NNPLOCL94M04944C', 'Paolo', 'Cinelli', '1994-11-04', 'Bologna', 'Via delle scuole 36', 'Bologna', 'Studente', '3332343546', '542', '40100'),
('MRAMLC68D274ACC8', 'Maria', 'Maluccio', '1968-12-12', 'Castel Maggiore', 'Via Ferrarese ', 'Bologna', 'Impiegata', '3332134789', 'N', '40134'),
('ETTMLC98CI987CIO', 'Ettore', 'Maluccio', '1992-02-30', 'Bologna', 'via ferrarese', 'Bologna', 'studente', '3332123456', 'N', '40123'),
('GDAMLCOKASOI89O2', 'Giada', 'Maluccio', '1993-08-21', 'Bologna', 'via ferrarese', 'Bologna', 'Studente', '3332389076', 'N', '40123');

-- --------------------------------------------------------

--
-- Struttura della tabella `disease`
--

CREATE TABLE IF NOT EXISTS `disease` (
  `dis_name` varchar(64) NOT NULL,
  `causes` varchar(4096) default NULL,
  `effects` varchar(4096) default NULL,
  `chronic` int(1) NOT NULL,
  PRIMARY KEY  (`dis_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `disease`
--

INSERT INTO `disease` (`dis_name`, `causes`, `effects`, `chronic`) VALUES
('otite', 'batterica', 'infiammazione orecchio', 0),
('raffreddore', 'influenza', 'difficoltà respiratoria', 0),
('lebbra', 'batterio Mycobacterium leprae', 'problemi derma', 1),
('polmonite cronica', 'infezione batterica, virus, fungh', 'anoressia, febbre, dolore toracico, dispnea, tosse', 1),
('asma', 'genetica, inalazione acidi, fattori allergici, fattori atmosferici', 'difficoltà respiratoria a tratti, stanchezza', 1),
('sifilide', 'batterio Treponema pallidum', 'ulcerazione della pelle', 0),
('emicrania', 'stress, stanchezza', 'mal di testa, mancanza di concentrazione', 0),
('diabete', 'genetica', 'retinopatia diabetica, problemi ai reni, ai nervi, disfunzioni sessuali, problemi cardiovascolari', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `examinations`
--

CREATE TABLE IF NOT EXISTS `examinations` (
  `id` int(11) NOT NULL,
  `patient` char(16) NOT NULL,
  `doctor` char(16) NOT NULL,
  `exam_date` varchar(16) default '--',
  `main_disease` varchar(64) default '-',
  `notes` varchar(4096) default '-',
  PRIMARY KEY  (`id`),
  KEY `patient` (`patient`),
  KEY `doctor` (`doctor`),
  KEY `main_disease` (`main_disease`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `examinations`
--

INSERT INTO `examinations` (`id`, `patient`, `doctor`, `exam_date`, `main_disease`, `notes`) VALUES
(1, 'UNODAO90NOD87AD9', '4444444444444444', '2009-03-01', 'asma', 'ciao'),
(2, 'MRAMLC68D274ACC8', '4444444444444444', '2012-05-24', 'asma', '-');

-- --------------------------------------------------------

--
-- Struttura della tabella `exam_attach`
--

CREATE TABLE IF NOT EXISTS `exam_attach` (
  `id_exam` int(11) NOT NULL,
  `id_attach` int(11) NOT NULL,
  UNIQUE KEY `constr_examAttach` (`id_exam`,`id_attach`),
  KEY `id_attach` (`id_attach`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `main_board`
--

CREATE TABLE IF NOT EXISTS `main_board` (
  `fiscal_code` char(16) NOT NULL,
  `invalid_percentage` smallint(6) default NULL,
  PRIMARY KEY  (`fiscal_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `main_board`
--

INSERT INTO `main_board` (`fiscal_code`, `invalid_percentage`) VALUES
('MRAMLC68D274ACC8', 2),
('alskfjlkasdnfiq3', 1),
('7439473857609874', 50),
('6577566477588697', 7),
('2342342342342344', 88),
('7265243243142313', 5),
('UNODAO90NOD87AD9', 60),
('NNPLOCL94M04944C', 0),
('DNFEDN90M23D4543', 10),
('ETTMLC98CI987CIO', 0),
('GDAMLCOKASOI89O2', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `medical_record`
--

CREATE TABLE IF NOT EXISTS `medical_record` (
  `fiscal_code` char(16) NOT NULL,
  `doctor` char(16) NOT NULL,
  PRIMARY KEY  (`fiscal_code`),
  KEY `doctor` (`doctor`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `medical_record`
--

INSERT INTO `medical_record` (`fiscal_code`, `doctor`) VALUES
('GDAMLCOKASOI89O2', '4444444444444444'),
('ETTMLC98CI987CIO', '4444444444444444'),
('MRAMLC68D274ACC8', '4444444444444444'),
('UNODAO90NOD87AD9', '4444444444444444'),
('NNPLOCL94M04944C', '4444444444444444');

-- --------------------------------------------------------

--
-- Struttura della tabella `medicine`
--

CREATE TABLE IF NOT EXISTS `medicine` (
  `m_name` varchar(64) NOT NULL,
  `active_principle` varchar(128) NOT NULL,
  PRIMARY KEY  (`m_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `medicine`
--

INSERT INTO `medicine` (`m_name`, `active_principle`) VALUES
('aspirina', 'acido acetilsalicilico'),
('oki', 'ketoprofene'),
('viagra', 'sildenafil'),
('penicillina', 'Penicillium notatum'),
('rocefin', 'ceftriaxone'),
('gyno-canestern', 'clotrimazolo');

-- --------------------------------------------------------

--
-- Struttura della tabella `patient_chronicDes`
--

CREATE TABLE IF NOT EXISTS `patient_chronicDes` (
  `fiscal_code` char(16) NOT NULL,
  `disease_name` varchar(64) NOT NULL,
  UNIQUE KEY `constr_patMed` (`fiscal_code`,`disease_name`),
  KEY `disease_name` (`disease_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `patient_chronicDes`
--

INSERT INTO `patient_chronicDes` (`fiscal_code`, `disease_name`) VALUES
('ETTMLC98CI987CIO', 'raffreddore'),
('MRAMLC68D274ACC8', 'emicrania'),
('NNPLOCL94M04944C', 'lebbra'),
('UNODAO90NOD87AD9', 'asma'),
('UNODAO90NOD87AD9', 'diabete'),
('UNODAO90NOD87AD9', 'emicrania'),
('UNODAO90NOD87AD9', 'polmonite cronica'),
('UNODAO90NOD87AD9', 'raffreddore');

-- --------------------------------------------------------

--
-- Struttura della tabella `patient_medicinePerm`
--

CREATE TABLE IF NOT EXISTS `patient_medicinePerm` (
  `fiscal_code` char(16) NOT NULL,
  `medicine_name` varchar(64) NOT NULL,
  UNIQUE KEY `constr_patMed` (`fiscal_code`,`medicine_name`),
  KEY `medicine_name` (`medicine_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `patient_medicinePerm`
--

INSERT INTO `patient_medicinePerm` (`fiscal_code`, `medicine_name`) VALUES
('GDAMLCOKASOI89O2', 'gyno-canestern'),
('UNODAO90NOD87AD9', 'aspirina'),
('UNODAO90NOD87AD9', 'oki'),
('UNODAO90NOD87AD9', 'rocefin'),
('UNODAO90NOD87AD9', 'viagra');

-- --------------------------------------------------------

--
-- Struttura della tabella `pharmacists`
--

CREATE TABLE IF NOT EXISTS `pharmacists` (
  `id_pharma` int(11) NOT NULL auto_increment,
  `ph_name` varchar(128) NOT NULL,
  `address` varchar(128) NOT NULL,
  `city` varchar(32) default NULL,
  `mail` varchar(256) NOT NULL,
  PRIMARY KEY  (`id_pharma`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2147483648 ;

--
-- Dump dei dati per la tabella `pharmacists`
--

INSERT INTO `pharmacists` (`id_pharma`, `ph_name`, `address`, `city`, `mail`) VALUES
(2, 'Federico Montori', 'via dei bo', 'Ferrara', 'federico.montori@studio.unibo.it'),
(1, 'Alain Di Chiappari', 'via unibo', 'Bologna', 'alain.dichiappari@studio.unibo.it'),
(3, 'Giulio Cinelli', 'via unibo', 'Borgo panigale', 'giulio.cinelli@studio.unibo.it'),
(4, 'Stefano Di Biagio', 'via dei bo', 'Teramo', 'stefano.dibiagio2@studio.unibo.it');

-- --------------------------------------------------------

--
-- Struttura della tabella `prescriptions`
--

CREATE TABLE IF NOT EXISTS `prescriptions` (
  `id` int(11) NOT NULL,
  `p_name` varchar(48) NOT NULL,
  `p_surname` varchar(24) NOT NULL,
  `address` varchar(128) NOT NULL,
  `fiscal_code` char(16) NOT NULL,
  `area_code` char(5) NOT NULL,
  `jurisdiction` varchar(512) NOT NULL,
  `prescription` varchar(256) default NULL,
  `priority` char(1) default NULL,
  `exemption_code` int(6) default NULL,
  `presc_type` char(2) NOT NULL,
  `note` varchar(4096) default NULL,
  `date` varchar(16) default NULL,
  `total` int(8) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `prescriptions`
--

INSERT INTO `prescriptions` (`id`, `p_name`, `p_surname`, `address`, `fiscal_code`, `area_code`, `jurisdiction`, `prescription`, `priority`, `exemption_code`, `presc_type`, `note`, `date`, `total`) VALUES
(12, 'Giada', 'Maluccio', 'via ferrarese', 'GDAMLCOKASOI89O2', '40123', 'GLaDOS Bologna - Project Gladoctor - Please Replace!', 'deve prendere ..', '9', 0, '67', 'Inserisci qui le tue note.', '2012-05-24', 8);

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `fiscal_code` char(16) NOT NULL,
  `username` varchar(24) NOT NULL,
  `d_name` varchar(24) NOT NULL,
  `d_surname` varchar(24) NOT NULL,
  `password` char(41) NOT NULL,
  `admin` int(1) NOT NULL,
  `substitute_of` char(16) NOT NULL default '0',
  PRIMARY KEY  (`fiscal_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`fiscal_code`, `username`, `d_name`, `d_surname`, `password`, `admin`, `substitute_of`) VALUES
('0000000000000000', 'admin', 'admin', 'Gigio', 'd033e22ae348aeb5660fc2140aec35850c4da997', 1, '0'),
('4444444444444444', 'giulio', 'giulio', 'cinelli', 'a6979dc879a84b82499ca8719c46bf4f7ff03b70', 0, '4444444444444444'),
('3333333333333333', 'federico', 'federico', 'montori', '84f2c11043e8093385729a2aa01be922aca25e5b', 0, '0'),
('1111111111111111', 'alain', 'alain', 'dichiappari', '86f7e437faa5a7fce15d1ddcb9eaeaea377667b8', 0, '4444444444444444'),
('2222222222222222', 'stefano', 'stefano', 'dibiagio', 'bd40a18d86c83374f049908c0f728599af610355', 0, '0');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
