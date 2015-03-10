SET SCHEMA TDB;
CREATE TABLE TOURNAMENT
(
  TOURNAMENT_ID            INT PRIMARY KEY NOT NULL,
  TOURNAMENT_NAME          VARCHAR(50),
  TOURNAMENT_WINNER        VARCHAR(50),
  WOODEN_SPOON             VARCHAR(50),
  GOLDEN_BOOT              VARCHAR(50),
  GOLDEN_BOOT_GOALS        INT,
  UPDATED_BY_USER_ID       VARCHAR(30),
  CREATED_BY_USER_ID       VARCHAR(30),
  CREATE_DATETIME          TIMESTAMP,
  UPDATE_DATETIME_DATETIME TIMESTAMP,
  FOREIGN KEY (TOURNAMENT_WINNER) REFERENCES PLAYER (NAME),
  FOREIGN KEY (GOLDEN_BOOT) REFERENCES PLAYER (NAME),
  FOREIGN KEY (WOODEN_SPOON) REFERENCES PLAYER (NAME)
);
