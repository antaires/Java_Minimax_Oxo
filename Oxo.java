/* Plays the game Noughts and Crosses with user input. Prints the board
   With no arguments, run the unit tests. Can play as 1 or 2 players
   If player inputs invalid move, they will be prompted for a valid move
   */

class Oxo {
    private int SIZE = 3;
    private String currentBoard;
    private String currentPlayer;
    private Board board = new Board();
    private Display display = new Display();
    private Minimax minimax = new Minimax();
    private String move;

    public static void main(String[] args) {
        Oxo program = new Oxo();
        program.run(args);
    }

    // Deal with the command line arguments
    void run(String[] args) {
        boolean testing = false;
        assert(testing = true);
        if (args.length == 0 && testing) test();
        else if (args.length == 1) play(args);
        else usage();
    }

    // Give a usage message and shut down.
    void usage() {
        System.err.println("Use:");
        System.err.println("  java -ea Oxo");
        System.err.println("  java Oxo number_of_players");
        System.exit(1);
    }

    void play(String[] args){
       int num_players = convertInt(args[0].charAt(0));
       display.Intro();

       if (num_players == 1){
         players_1();
       } else if (num_players == 2){
         players_2();
       } else {
         System.out.println("\nYou must pick either 1 or 2 players");
         System.exit(1);
       }
    }

    //1 player allows user to play against the Minimax AI
    private void players_1(){
      board.initBoard();
      while (!board.isGameOver()){
         currentPlayer = board.getCurrentPlayer();
         if (board.getCurrentPlayerType().equals(minimax.getPlayingAs())){
            //CP moves
            move = minimax.getBestMove(board);
         } else {
           //human moves
            move = display.getMove(currentPlayer);
         }
         board.makeMove(move);
         display.drawBoard(board.getBoard());
      }
      newGame();
    }

    //allows 2 human players to take turns
    private void players_2(){
      String move;
      board.initBoard();
      while (!board.isGameOver()){
         currentPlayer = board.getCurrentPlayer();
         move = display.getMove(currentPlayer);
         board.makeMove(move);
         display.drawBoard(board.getBoard());
      }
      newGame();
    }

    private int convertInt(char c){
      try {
         int i =  Character.getNumericValue(c);
         if (i < 1 || i > 2){return -1;}
         return i;
      } catch (NumberFormatException e){
         return -1;
      }
    }

    private void newGame(){
      checkTieWin();
      if (display.newGame()){
         board.initBoard();
         if (display.oneOrTwoPlayers().equals("2")){
           players_2();
         } else {
           players_1();
         }
      }
    }

    private void checkTieWin(){
      if (board.isTie()){
         display.isTie();
      } else {
         display.isWin(board.getWinner());
      }
    }

    // ---------- Testing ----------
    // Run the tests
    void test() {
        testConvertInt();

    }
    // Check that convert works correctly.
    void testConvertInt() {
       assert(convertInt('1') == 1);
       assert(convertInt('2') == 2);
       assert(convertInt('9') == -1);
       assert(convertInt('5') == -1);
    }
}
