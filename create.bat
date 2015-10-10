
set DERBY_INSTALL=E:\jars\db-derby-10.10.2.0-bin
set CLASSPATH=%DERBY_INSTALL%\lib\derby.jar;%DERBY_INSTALL%\lib\derbytools.jar;.

java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_000_schema.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_010_player.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_011_player.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_020_tournament.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_030_game.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_031_game.sql
java -Dij.database=jdbc:derby:Tournament\db1;create=true org.apache.derby.tools.ij src\main\scripts\tournament_040_countries.sql

pause