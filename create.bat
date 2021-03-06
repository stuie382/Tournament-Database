REM Set up the class path for the ij tool
set DERBY_INSTALL=<path\to\derby\home>
set CLASSPATH=%DERBY_INSTALL%\lib\derby.jar;%DERBY_INSTALL%\lib\derbytools.jar;.
REM Run each of the SQL scripts to create the database
REM and populate the static data required
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_000_schema.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_010_player.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_011_player.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_020_tournament.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_030_game.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_031_game.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_040_countries.sql
REM Pause so the user can see any messages in the console window.
pause
