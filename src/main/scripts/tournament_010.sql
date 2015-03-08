CREATE TABLE GAME
(
  GAME_ID         INT PRIMARY KEY        NOT NULL,
  HOME_PLAYER     VARCHAR(50)            NOT NULL,
  AWAY_PLAYER     VARCHAR(50)            NOT NULL,
  HOME_GOALS      INT DEFAULT 0          NOT NULL,
  AWAY_GOALS      INT DEFAULT 0          NOT NULL,
  EXTRA_TIME      VARCHAR(1) DEFAULT 'N' NOT NULL,
  HOME_PENS       INT DEFAULT 0          NOT NULL,
  AWAY_PENS       INT DEFAULT 0          NOT NULL,
  WINNER          VARCHAR(50)            NOT NULL,
  TOURNAMENT_ID   INT                    NOT NULL,
  KNOCK_OUT       VARCHAR(1) DEFAULT 'N' NOT NULL,
  CREATE_DATETIME DATE,
  UPDATE_DATETIME DATE,
  CREATED_USER    VARCHAR(30),
  UPDATED_USER    VARCHAR(30)
);
CREATE TABLE PLAYER
(
  NAME VARCHAR(50) PRIMARY KEY NOT NULL
);
CREATE TABLE TOURNAMENT
(
  TOURNAMENT_ID            INT PRIMARY KEY NOT NULL,
  TOURNAMENT_NAME          VARCHAR(50),
  TOURNAMENT_WINNER        VARCHAR(50),
  WOODEN_SPOON             VARCHAR(50),
  GOLDEN_BOOT              VARCHAR(50),
  GOLDEN_BOOT_GOALS        INT,
  UPDATED_USER             VARCHAR(30),
  CREATED_USER             VARCHAR(30),
  CREATE_DATETIME          DATE,
  UPDATE_DATETIME_DATETIME DATE
);
ALTER TABLE GAME ADD FOREIGN KEY (HOME_PLAYER) REFERENCES PLAYER (NAME);
ALTER TABLE GAME ADD FOREIGN KEY (AWAY_PLAYER) REFERENCES PLAYER (NAME);
ALTER TABLE GAME ADD FOREIGN KEY (TOURNAMENT_ID) REFERENCES TOURNAMENT (TOURNAMENT_ID);
ALTER TABLE TOURNAMENT ADD FOREIGN KEY (TOURNAMENT_WINNER) REFERENCES PLAYER (NAME);
ALTER TABLE TOURNAMENT ADD FOREIGN KEY (GOLDEN_BOOT) REFERENCES PLAYER (NAME);
ALTER TABLE TOURNAMENT ADD FOREIGN KEY (WOODEN_SPOON) REFERENCES PLAYER (NAME);
