Tetris Game Design Pattern Info

It is a little difficult to look at the code and then determine what kinds of design patterns have been implemented (or perhaps I am not as familiar with them as I should be). However, since we are required to have used at least two, I will write about two which I believe are present in my code:

Factory Pattern:

The factory pattern uses a common interface which leaves out the details of creating an object from the client. For example in our project, the implementation of this occurs in the creation of the tetrominos. In the base tetromino class there is a general pattern for creating, moving, and rotating a tetromino. However, each tetromino is different since it is a different shape. Therefore, we implement an enum which allows the specific shapes to be designed and their behavior implemented differently.

Command Pattern:

The second design pattern I believed we used is the command pattern. This design pattern utilizes an object as an instruction. What this means is that an object invoked in another class will carry out a set of instructions (determined in the constructor of that object). For example, in our case we had the GamePlay class which essentially made all the moves necessary for the game to start, progress, and end. We invoked an object of this type from our class TetrisPlay which allowed then started the game. However, we also implemented the ability to control the game end and start from our TetrisPlay class for ease.


Cite Sources:

Some of the concepts and desgign for:
- drawing/painting the tetris pieces
- game piece movements and behavior

were obtained from the following sources:

http://www.java2s.com/Code/Java/Game/TetrisGame.htm
http://zetcode.com/tutorials/javagamestutorial/tetris/

 
