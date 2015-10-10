:soccer: Tournament-Database :soccer:
===================
A Simple database for logging results of football tournaments.

:soccer: Author
---------------
Stuart Clark

:soccer: Version
----------------
0.1 (alpha)

:soccer: Requirements
---------------------
Requires Oracle Java version 1.8 onwards in order to function. Also requires
a valid Maven installation in order to be built from source. You will also need
to have Apache Derby installed, and the file path handy.

:soccer: Installation
---------------------
To create a new database with only the static data included, you will need to edit
the 'create.bat' file to include the correct path for your Derby installation (e.g E:\myTools\db-derby-10-bin).
When this is correct, run the file. When this has completed, your database is ready for the system
to use.

You can now build using the 'mvn package' goal. This will create a a directory called 'Tournament'
in the root of the project, and in the 'target' directory several .jar files
will also be created.

The .jar with the '-shaded' suffix is an *'uberjar'*. This contains all the necessary files in order
to run the application on any system that has Java installed. At this point you will need to connect
to the 'Tournament' database (either through the Derby command line tools, your IDE, etc) and run
all of the SQL scripts found within the ~src/main/scripts directory. These should
be run in numerical order.

:soccer: Deployment
-------------------
To deploy the application to users, take a copy of the *'uberjar'* created above and a copy of
the 'Tournament' directory. Place these two files into a new directory, and use the new directory
as a basis for the deployment.

The application will create a log file and a Derby log file when it is running. These files are safe
to delete when the application is not running, but should be helpful for tracking down any problems.


# :soccer: Current Extension plans
* Allow users to create and run custom SQL queries through the GUI
* Allow users to save and recall custom queries
* Improve installation so that developers would not need to run the SQL files themselves


