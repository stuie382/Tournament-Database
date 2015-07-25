/*
* Copyright (c) Stuart Clark
*
* This project by Stuart Clark is free software: you can redistribute it and/or modify it under the terms
* of the GNU General Public License as published by the Free Software Foundation, either version 3 of the
* License, or (at your option) any later version. This project is distributed in the hope that it will be
* useful for educational purposes, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
* or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License along with this project.
* If not, please see the GNU website.
*/
SET SCHEMA TDB;

CREATE TABLE GAME
(
  GAME_ID            INT PRIMARY KEY        NOT NULL GENERATED ALWAYS AS IDENTITY,
  HOME_PLAYER        VARCHAR(50)            NOT NULL,
  AWAY_PLAYER        VARCHAR(50)            NOT NULL,
  HOME_GOALS         INT DEFAULT 0          NOT NULL,
  AWAY_GOALS         INT DEFAULT 0          NOT NULL,
  EXTRA_TIME         VARCHAR(1) DEFAULT 'N' NOT NULL,
  HOME_PENS          INT DEFAULT 0          NOT NULL,
  AWAY_PENS          INT DEFAULT 0          NOT NULL,
  WINNER             VARCHAR(50)            NOT NULL,
  TOURNAMENT_ID      INT                    NOT NULL,
  KNOCK_OUT          VARCHAR(1) DEFAULT 'N' NOT NULL,
  CREATE_DATETIME    TIMESTAMP,
  UPDATE_DATETIME    TIMESTAMP,
  CREATED_BY_USER_ID VARCHAR(30),
  UPDATED_BY_USER_ID VARCHAR(30),
  FOREIGN KEY (HOME_PLAYER) REFERENCES TDB.PLAYER (NAME),
  FOREIGN KEY (AWAY_PLAYER) REFERENCES TDB.PLAYER (NAME),
  FOREIGN KEY (TOURNAMENT_ID) REFERENCES TDB.TOURNAMENT (TOURNAMENT_ID)
);
