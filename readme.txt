TO RUN
java Oxo 1  //one player
java Oxo 2. //two players

BASIC
I used an MVC design, and kept the board class and display classes separate. The main game loop is handled in Oxo, which creates a new Board class object to store all necessary information. The Board class is independent. The display class deals with all input/output, and is also independent. The Minimax class deals solely with determining the best move for an enemy AI player, and to do so it uses the Board class but is otherwise independent. I use an enum for cell type (X, O, Empty or Impossible). If graphics were to be added, all that would need to be changed is the Display class. 

ERROR HANDLING
The user is repeatedly asked to input a valid move, so full cells & incorrect entries do not cause errors. The next player moves only after a valid input for the current player. 

EXTENSION
I used a variant on Minimax to create an AI player that takes into account the number of moves made before a win/lose, and gives more weight to quick wins or slow losses. Initially, I tried a few different approaches for the enemy AI, including a Monte Carlo approach, but given the small size of total possible states I determined that Minimax would be a better choice. At the end of the game, players can start again as either 1 or 2 players.

TESTING
The board class was tested extensively as this is a crucial aspect of the
program functionality. Minimax was also tested on a variety of board states,
to determine if the best moves were being selected. Smaller tests were done on conversion functions and simple helper methods within Oxo and Display. 

IMPROVEMENTS
Given more time, I would like to make the code even more DRY - particularly within the Minimax function. The minimise section and maximise sections follow a similar enough structure that they could perhaps be split into functions that call a third function (like scoreBoard() with different parameters passed in). I have chosen to submit as is because it is in working condition, clean and easy to read. Further, more extensive tests of the Minimax class could be done to include tests of full games, with automatic inputs supplied for the 'human' player during testing. 