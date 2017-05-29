# Cyberena - Server Program

 ------
### About
This is a standalone command-line program that is intended to serve as the main server for the game *Cyberena*. This server program will keep track of communicating with all users that are connected to this game, host multiple game sessions.

For now, this server will be hosted locally.

### Installation
In order to install this software, you first have to open the *Cyberena* project through Eclipse. Once that project is opened, you then *right click* on the package named *com.mustard.server* (which contains all of the code necessary to compile and execute this standalone server program) and click on the *Export* submenu.

Once you have clicked on the *export* submenu, you are then prompted with a dialogue box telling you to select which type of JAR file you should compile the code into. For this, select *Java -> Runnable Jar File* and click next.

In the next dialogue box prompt, set the *Launch Configuration* to *ServerLauncher - Project Mustard-Server* and specify the *export destination path* in the text field below Launch Configuration. Finally, select *Package Required Libaries into generated JAR* and click *Finish*.

Once you hit finish, launch the system's terminal. Using that terminal, navigate to the directory on where you have place the newly compiled JAR file, and type in the following command:
```sh
java -jar [NAME OF SERVER JAR FILE].jar [PORT]
```
