Welcome to the 0.1 alpha release of the FIFA Tournament Database.

In this directory you should have the following three files:
  tourny-0.1-SNAPSHOT-shaded.jar
  README.txt (this file)
  Tournament

The tourny...jar file is the main application - double-click this to start it up!
The Tournament directory is the embedded database. DO NOT MODIFY THIS FILE
The README.txt is what you are currently reading (before clicking anything else, as you are a 
sensible person).

As this is an alpha release, expect that things will be broken/unimplemented/not work properly.

This is expected.

Any issues that are found, please go here https://github.com/stuie382/Tournament-Database/issues
and log your issue (requires free sign up). Ideally I would like to know:
  What action you were doing
  What you think the application should of done
  What the application actually did
When submitting an issue, please add an appropriate label (right hand side -> labels), and
select the '0.1 Alpha Release' milestone (right hand side -> milestones).
Finally if you could please email a copy of the tourny_log.txt and the derby.log files, using the
same subject text as the issue raised on the above website link, that would be very much
appreciated!
<stu382@hotmail.co.uk>

The two log files are safe to delete before each new session of using the application. Assuming no
errors or weird things occurred during the last session, this should be considered 'best practice'
as the files can get quite long. 

APPLICATION USAGE
-----------------
When you first boot the application, you will have four options to choose from:
  Manage Players
  Manage Games
  Manage Tournaments
  Play A Games
  
MANAGE PLAYERS
This screen will allow you to view all the players currently registered to play in a tournament.
It will also allow you to add new players to the database. Player names can only contain 
alphanumeric characters. Right-clicking on the table of player names will allow you to export the 
results to a CSV file.
NOTE - Player names need to be unique, I would suggest using both the first and last name of the 
player, with each word in correct sentence case (as a name should be!). If two players have the 
same first and last name, I would suggest adding a middle name or a nickname to ensure they are 
unique.
NOTE - On first run, the only player who exists in the system is 'AI'. If a tournament does not
have sufficient numbers of players, the AI player can be used to at least make an even number of 
players in the tournament.

MANAGE GAMES
This screen will allow you to view a history of all the games that have ever been played. 
Right-clicking on the results table will allow you to export the results to a CSV file.
The Add Game button is not yet implemented, so it does nothing.

MANAGE TOURNAMENTS
This screen will allow you to view a history of all the tournaments that have ever been played.
Right-clicking on the results table will allow you to export the results to a CSV file.
This screen will also allow you to create a new tournament. Tournament names can only contain
alphanumeric characters and should be unique (although this is not currently enforced). As a naming
convention, I would suggest having at minimum the month and year in the tournament name (going 
down to date and time if the tournament frequency requires it).

PLAY A GAME
This screen will allow you to play any number of games for a tournament. Starting from the top,
the screen allows you to pick two players to play a game (players cannot play themselves). Once the
players have been selected, the Game Type should be selected. The Game Type tells the system whether
the game being played is a group stage game, a knock out game, or the final!

The home/away scores need to be populated for all game types. If two players have drawn a group 
stage game, then the winner will be recorded as 'DRAW' in the system.

If the game is a knock out stage game, then you have the option of recording the penalties scored
by each player in the penalty shoot out. If a knock out game does not go to penalties, then the
penalties should be recorded as zero for each player.

If the game is the final, then in addition to populating the penalties field as above (as the final
is still a knock out game) you can record the team used for each player in the final. Each player
can potentially be playing as the same team, but the system will ask the user to double check that
is correct before proceeding and saving the game.

Additionally if the game is marked as the final, then when the game is saved, the tournament will 
be updated to record the tournament winner and runner up (currently called wooden spoon), as well 
as calculating the golden boot winner and the number of goals scored to win the boot. Note this 
calculation does not include any penalties scored in a penalty shoot out (as per FIFA standards). 
If more than one player is eligible to receive the golden boot, then it will be marked as a tie 
(not as per FIFA standards) and the names of all eligible players will be listed.

Before you can save a game, you will need to select which tournament that the game belongs to.
Be sure that you have the right tournament, as the save action cannot be undone.

Finally the reset/save/cancel buttons do what you would expect them to do. In addition to saving,
the save button will also reset the screen ready for the next game to be entered. If the game being
saved is the final, it will popup the final tournament information (winner/runner up, golden boot)
then close the 'Play a Game' window.

HINTS
  * When a drop down is selected, you can start typing to quickly jump to different parts of the 
    list.
  * Once you have selected a tournament on the Play a Game screen, it should remember which 
    tournament was selected after reset/save has been clicked.
  * On first starting the application, the first time you perform an action that requires a database
    connection, the system will pause briefly. This is so the database connections can be created.
    On subsequent accesses, the performance should be satisfactory.

