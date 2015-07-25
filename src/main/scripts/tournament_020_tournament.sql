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
CREATE TABLE TOURNAMENT
(
  TOURNAMENT_ID      INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY,
  TOURNAMENT_NAME    VARCHAR(50),
  TOURNAMENT_WINNER  VARCHAR(50),
  WOODEN_SPOON       VARCHAR(50),
  GOLDEN_BOOT        VARCHAR(50),
  GOLDEN_BOOT_GOALS  INT,
  UPDATED_BY_USER_ID VARCHAR(30),
  CREATED_BY_USER_ID VARCHAR(30),
  CREATE_DATETIME    TIMESTAMP,
  UPDATE_DATETIME    TIMESTAMP
);
