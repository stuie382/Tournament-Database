SET SCHEMA TDB;

CREATE TABLE GAME
(
  GAME_ID            INT PRIMARY KEY        NOT NULL,
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
  FOREIGN KEY (HOME_PLAYER) REFERENCES PLAYER (NAME),
  FOREIGN KEY (AWAY_PLAYER) REFERENCES PLAYER (NAME),
  FOREIGN KEY (TOURNAMENT_ID) REFERENCES TOURNAMENT (TOURNAMENT_ID)
);
