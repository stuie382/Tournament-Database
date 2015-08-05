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
INSERT INTO TDB.PLAYER (name,CREATE_DATETIME,UPDATE_DATETIME,CREATED_BY_USER_ID,UPDATED_BY_USER_ID)
    values ('AI',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Admin','Admin');
