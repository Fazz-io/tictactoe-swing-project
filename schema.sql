-- ==============================================
-- ES234211 - Programming Fundamental
-- Simple Game Project - Database Schema
-- Database: PostgreSQL
-- ==============================================

-- Create database (run this separately in psql if needed)
-- CREATE DATABASE game_project;

-- Connect to the database first:
-- \c game_project

-- Drop table if exists (for fresh setup)
DROP TABLE IF EXISTS players;

-- Create players table (ONE TABLE ONLY)
CREATE TABLE players (
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    wins     INT DEFAULT 0,
    losses   INT DEFAULT 0,
    draws    INT DEFAULT 0,
    score    INT DEFAULT 0
);

-- Insert sample players
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student1', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student2', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student3', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student4', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student5', '12345', 0, 0, 0, 0);
