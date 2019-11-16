
/* Takes in a Board class object and returns the best move for the computer
  player as a string (such as "a1" or "c3" etc) using the Minimax algorithm.

  Uses a varient that takes into account number of moves made before a win,
  to preference moves that lead to quick wins or slow losses

  */

class Minimax {
    private int SIZE = 3;
    private Type computerPlayer = Type.X;
    private Type humanPlayer = Type.O;
    private int WINPOINTS = 10;
    private int LOSEPOINTS = -10;
    private Type maximizer = Type.X;
    private Type minimizer = Type.O;

    String getBestMove(Board board){
      //works through each possible move and gives it a score based on whether
      //the human player or the CP player won, or a tie
        String bestMoveString = "";
        int bestMove_i = -1;
        int bestMove_j = -1;
        int bestMoveValue = -1;
        int currentMove = -10000;
        boolean isMaximizingPlayer = computerPlayer.equals(maximizer);
        Board clone = board.cloneBoard();

        //if first move, pick random start
        if (board.isEmpty()){
          return numToString( (int)(Math.random()*(8)) );
        }

        //get each empty square
        for (int i = 0; i < SIZE; i ++){
          for(int j = 0; j < SIZE; j++){
            if (clone.getValue(i, j).equals(Type.Empty)){
              clone.makeMove(i, j, computerPlayer);
              currentMove = miniMax(clone, 0, !isMaximizingPlayer);
              //undo the move
              clone.makeMove(i, j, Type.Empty);

              if(getPlayingAs().equals(maximizer)){
                 if (currentMove > bestMoveValue) {
                  bestMoveValue = currentMove;
                  bestMove_i = i; bestMove_j = j;
                 }
              } else {
                if (currentMove < bestMoveValue) {
                 bestMoveValue = currentMove;
                 bestMove_i = i; bestMove_j = j;
                }
              }
            }
          }
        }
        bestMoveString = numToString(coordToNum(bestMove_i, bestMove_j));
        return bestMoveString;
    }

    void setPlayingAs(Type p){
      computerPlayer = p;
      if (computerPlayer.equals(Type.X)){
         humanPlayer = Type.O;
      } else {
         humanPlayer = Type.X;
      }
    }

    Type getPlayingAs(){
      return computerPlayer;
    }

    private int evaluate(Board board, int depth){
      //determines point value of initial move based on game outcome
      //depth is the number of moves made prior to game end state
       if (isWinner(board, maximizer)){
         return WINPOINTS - depth;
       }
       if (isWinner(board, minimizer)){
         return LOSEPOINTS + depth;
       }
       return 0;
    }

    private int miniMax(Board board, int depth, boolean isMaximizingPlayer){
    //  Minimax: https://en.wikipedia.org/wiki/Minimax
        if (board.isGameOver()){
          return evaluate(board, depth);
        }

        int bestValue;
        Board clone = board.cloneBoard();

        if (isMaximizingPlayer) {
           bestValue = -10000;
           for (int i = 0; i < SIZE; i++){
             for(int j = 0; j < SIZE; j++){
               if (clone.getValue(i, j).equals(Type.Empty)){
                 clone.makeMove(i, j, maximizer);
                 int value = miniMax(clone, depth+1, false);
                 bestValue = max(bestValue, value);
                 clone.makeMove(i, j, Type.Empty);
               }
             }
           }
           return bestValue;
        } else {
          //is minimizing player
          bestValue = 10000;
          for (int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
              if (clone.getValue(i, j).equals(Type.Empty)){
                clone.makeMove(i, j, minimizer);
                int value = miniMax(clone, depth+1, true);
                bestValue = min(bestValue, value);
                clone.makeMove(i, j, Type.Empty);
              }
            }
          }
        }
      return bestValue;
    }

    private int max(int a, int b){
      if (a > b){
        return a;
      }
      return b;
    }

    private int min(int a, int b){
      if (a < b){
        return a;
      }
      return b;
    }

    private int coordToNum(int i, int j){
      if (i == 0 && j == 0){return 0;}
      if (i == 0 && j == 1){return 1;}
      if (i == 0 && j == 2){return 2;}
      if (i == 1 && j == 0){return 3;}
      if (i == 1 && j == 1){return 4;}
      if (i == 1 && j == 2){return 5;}
      if (i == 2 && j == 0){return 6;}
      if (i == 2 && j == 1){return 7;}
      if (i == 2 && j == 2){return 8;}
      return -1;
    }

    private String numToString(int move){
      switch(move){
         case 0: return "a1";
         case 1: return "a2";
         case 2: return "a3";
         case 3: return "b1";
         case 4: return "b2";
         case 5: return "b3";
         case 6: return "c1";
         case 7: return "c2";
         case 8: return "c3";
      }
      return "";
    }

     private boolean isWinner(Board b, Type player){
       if (b.isGameOver() && !(b.isTie()) && b.getWinnerType().equals(player)){
         return true;
       }
       return false;
     }

     private Type switchPlayer(Type player){
       if(player.equals(Type.X)){
         return Type.O;
       }
       return Type.X;
     }

    // ---------- Testing -----------

    public static void main(String[] args) {
        Minimax program = new Minimax();
        program.run();
    }

    // Run the tests
    private void run() {
        boolean testing = false;
        assert(testing = true);
        if (! testing) throw new Error("Use java -ea Minimax");

        testCoordToNum();
        testGetBestMove();

    }

    void testCoordToNum(){

       assert (coordToNum(0, 0) == 0);
       assert (coordToNum(1, 1) == 4);
       assert (coordToNum(2, 2) == 8);

    }

    void testGetBestMove(){
        Board b = new Board();
        b.setBoard("xoxooxeee");
        setPlayingAs(Type.X);
        assert (getBestMove(b).equals("c3"));

        b.setBoard("xoxooxeee");
        setPlayingAs(Type.O);
        assert (getBestMove(b).equals("c2"));

        b.setBoard("eoexexoee");
        setPlayingAs(Type.X);
        assert (getBestMove(b).equals("b2"));

        b.setBoard("eoexexoee");
        setPlayingAs(Type.X);
        assert (getBestMove(b).equals("b2"));
    }
}
