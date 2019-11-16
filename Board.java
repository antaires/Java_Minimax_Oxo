/* Stores Moves on the board, automatically switches current Player
   Checks for GameOver, win, tie conditions */

class Board {
    private Type winner = Type.Empty;
    private int SIZE = 3;
    private Type board[][] = {{Type.Empty, Type.Empty, Type.Empty},
                              {Type.Empty, Type.Empty, Type.Empty},
                              {Type.Empty, Type.Empty, Type.Empty}};
    private Type currentPlayer = Type.X;
    private boolean isFull = false;

   void initBoard(){
        clearBoard();
        winner = Type.Empty;
        currentPlayer = Type.X;
        isFull = false;
   }

    Type getValue(int row, int col) {
        if ( isValid(row, col) ){
          return board[row][col];
        }
        return Type.Impossible;
    }

    String getCurrentPlayer(){
        if (currentPlayer.equals(Type.X)){
            return "X";
        }
        return "O";
    }

    Type getCurrentPlayerType(){
        return currentPlayer;
    }

    boolean makeMove(String s){
        //accepts string input eg "3b" and
        //places move according to currentPlayer on board
        int row, col;

        //reject incorrect length strings
        if (s == null || s.length() != 2 ){
           return false;
        }

        int[] coord = convertCoord(s);
        if ( !isValid(coord[0], coord[1]) ){
            return false;
        }

        row = coord[0]; col = coord[1];
        //move only if square empty
        if (makeMove(row, col)){
          return true;
        }
        //cannot make move
        return false;
    }

    boolean makeMove(int row, int col){
      //automatically keeps track of current player and places move
      if ( getValue(row, col).equals(Type.Empty) ){
          setValue(currentPlayer, row, col);
          switchPlayer();
          if ( isFull() ){ isFull = true;}
          return true;
      }
      return false;
    }

    void makeMove(int row, int col, Type player){
      //place a move while specifying player, useful for tests in other classes
      setValue(player, row, col);
      if (isFull()){ isFull = true;}
    }

    boolean isGameOver(){
      winner = isWin();
      if ( !winner.equals(Type.Empty) ){
        return true;
      }
      if ( isTie() ){
        return true;
      }
      return false;
    }

    String getWinner(){
      if (winner.equals(Type.X)){
          return "X";
      }
      return "O";
    }

    Type getWinnerType(){
      return winner;
    }

    String getBoard(){
      //returns the current board as a String
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < SIZE; i++){
          for(int j = 0; j < SIZE; j++){
              if (getValue(i,j).equals(Type.O)){
                builder.append("o");
              } else if (getValue(i,j).equals(Type.X)){
                builder.append("x");
              } else {
                builder.append("e");
              }
          }
      }
      return builder.toString();
    }

    Board cloneBoard(){
      //creates a clone of the current board
       Board new_board = new Board();
       new_board.initBoard();
       for(int i = 0; i < SIZE; i++){
         for(int j = 0; j < SIZE; j++){
             new_board.board[i][j] = board[i][j];
         }
       }
       return new_board;
    }

    void setBoard(String s){
      //set the board from the String, useful for testing the AI Minimax
       int k = 0;
       for (int i = 0; i < SIZE; i++){
         for (int j = 0; j < SIZE; j++){
            board[i][j] = charToType(s.charAt(k++));
         }
       }
    }

    boolean isTie(){
      if( isFull() && isWin().equals(Type.Empty) ){
        return true;
      }
      return false;
    }

    boolean containsType(Type t){
      //to check for the existence of empty squares
       for(int i = 0; i < SIZE; i ++){
         for(int j = 0; j < SIZE; j++){
           if (board[i][j].equals(t)){
             return true;
           }
         }
       }
       return false;
    }

    boolean isEmpty(){
      for(int i = 0; i < SIZE; i ++){
        for(int j = 0; j < SIZE; j++){
          if (!board[i][j].equals(Type.Empty)){
            return false;
          }
        }
      }
      return true;
    }

    //---------Private methods ------------

    private void clearBoard(){
      //set the board to al empty
       for (int i = 0; i < SIZE; i++){
           for(int j = 0; j < SIZE; j++){
               board[i][j] = Type.Empty;
           }
       }
       isFull = false;
    }

    private Type isWin(){
      //returns Type.O or Type.X for win, Type.Empty for no win
      Type temp = Type.Empty;

      temp = checkRows();
      if (!temp.equals(Type.Empty)){
        return temp;
      }

      temp = checkCols();
      if (!temp.equals(Type.Empty)){
        return temp;
      }

      temp = checkDiag();

      return temp;
    }

    private Type checkRows(){
      for (int i = 0; i < SIZE; i++){
        if ((!board[i][0].equals(Type.Empty)) &&
            board[i][0].equals(board[i][1]) &&
            board[i][0].equals(board[i][2]) ){
              return board[i][0];
            }
      }
      return Type.Empty;
    }

    private Type checkCols(){
      //checking for win
      for (int i = 0; i < SIZE; i++){
        if ((!board[0][i].equals(Type.Empty)) &&
            board[0][i].equals(board[1][i]) &&
            board[0][i].equals(board[2][i]) ){
              return board[0][i];
            }
      }
      return Type.Empty;
    }

    private Type checkDiag(){
      //checking for win of either player
      if ((!board[0][0].equals(Type.Empty)) &&
          board[0][0].equals(board[1][1]) &&
          board[0][0].equals(board[2][2]) ){
            return board[0][0];
      }
      if ((!board[0][2].equals(Type.Empty)) &&
          board[0][2].equals(board[1][1]) &&
          board[0][2].equals(board[2][0]) ){
            return board[0][2];
          }
      return Type.Empty;
    }

    private boolean isValid(int row, int col){
      //to check if a move is valid
      if (row < 0 || row > SIZE ||
          col < 0 || col > SIZE ){
          return false;
      }
      return true;
    }

    private int[] convertCoord(String s){
          //accepts input "b2" and returns numeric board coordinates
          //returns negative values for incorrect input
          int[] coord   = {0, 0};
          int i;

          coord[0] = convertChar(s.charAt(0));
          coord[1] = convertInt(s.charAt(1));

          return coord;
    }

    private int convertChar(char c){
      //convert letter to coordinate
      c = Character.toLowerCase(c);
      switch( c ){
        case 'a':
           return 0;
        case 'b':
           return 1;
        case 'c':
           return 2;
        default:
           return -1;
      }
    }

    private int convertInt(char c){
      //convert char to int
      try {
         int i =  Character.getNumericValue(c);
         if (i < 1 || i > SIZE){return -1;}
         return i-1;
      } catch (NumberFormatException e){
         return -1;
      }
    }

     private void switchPlayer(){
         if ( currentPlayer.equals(Type.X) ){
             currentPlayer = Type.O;
             return;
         }
         currentPlayer = Type.X;
     }

     private boolean setValue(Type value, int row, int col){
       //sets the value of a square to given value
        if ( isValid(row, col) ){
           board[row][col] = value;
           return true;
        }
        return false;
     }

     private void setBoard(char[][] setup){
       //sets the board to a char array configuration
        for(int i = 0; i < SIZE; i++){
          for(int j = 0; j < SIZE; j++){
            board[i][j] = charToType(setup[i][j]);
          }
        }
     }

     private Type charToType(char i){
       //to translate the board setup from a String
       i = Character.toLowerCase(i);
       switch(i){
         case 'x':
            return Type.X;
         case 'o':
            return Type.O;
         case 'e':
            return Type.Empty;
         default:
            return Type.Impossible;
       }
     }

     private boolean isFull(){
       //to check for game over
       for(int i = 0; i < SIZE; i ++){
         for(int j = 0; j < SIZE; j++){
           if (board[i][j].equals(Type.Empty)){
             return false;
           }
         }
       }
       return true;
     }

    // ---------- Testing -----------

    public static void main(String[] args) {
        Board program = new Board();
        program.run();
    }

    // Run the tests
    private void run() {
        boolean testing = false;
        assert(testing = true);
        if (! testing) throw new Error("Use java -ea Board");

        testClearBoard();
        testInitBoard();
        testIsValid();
        testGetValue();
        testSetValue();
        testSwitchPlayer();
        testConvertChar();
        testConvertInt();
        testConvertCoord();
        testMakeMove();
        testCharToType();
        testSetBoard();
        testIsWin();
        testIsTie();
        testIsGameOver();

        System.out.println("All tests pass");
    }

    private void testClearBoard() {
        for (int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                assert( board[i][j].equals(Type.Empty) );
            }
        }
    }

    private void testInitBoard(){
        initBoard();
        assert( currentPlayer.equals(Type.X) );
        assert( isFull == false );
    }

    private void testIsValid(){
       //all coords within board are valid
       for(int i = 0; i < SIZE; i++){
         for(int j = 0; j < SIZE; j++){
           assert( isValid(i,j) );
         }
       }
       //invalid coordinates
       assert( !isValid(-1,0) );
       assert( !isValid(0,-1) );
       assert( !isValid(500,0) );
       assert( !isValid(0,500) );
       assert( !isValid(-100,0) );
    }

    private void testGetValue(){
       //returns Empty type for initialized Board
       for (int i = 0; i < SIZE; i++){
           for(int j = 0; j < SIZE; j++){
               assert( getValue(i,j).equals(Type.Empty) );
           }
       }
       //test out of bounds inputs
       assert( getValue(-1, 0).equals(Type.Impossible) );
       assert( getValue(4, 4).equals(Type.Impossible) );
       assert( getValue(1, 10).equals(Type.Impossible) );
       assert( getValue(33, 2000).equals(Type.Impossible) );
    }

    private void testSetValue(){
       clearBoard();

       //set the top row to X
       for(int i = 0; i < SIZE; i++){
          setValue(Type.X, 0, i);
       }
       assert( getValue(0,0).equals(Type.X) );
       assert( getValue(0,1).equals(Type.X) );
       assert( getValue(0,2).equals(Type.X) );
       assert( getValue(1,0).equals(Type.Empty) );
       assert( getValue(1,1).equals(Type.Empty) );
       assert( getValue(1,2).equals(Type.Empty) );

       //set the middle row to O
       for(int i = 0; i < SIZE; i++){
          setValue(Type.O, 1, i);
       }
       assert( getValue(0,0).equals(Type.X) );
       assert( getValue(1,0).equals(Type.O) );
       assert( getValue(1,1).equals(Type.O) );
       assert( getValue(1,2).equals(Type.O) );
       assert( getValue(2,0).equals(Type.Empty) );
       assert( getValue(2,1).equals(Type.Empty) );
       assert( getValue(2,2).equals(Type.Empty) );

       //test out of bounds inputs
       assert( setValue(Type.X, -1, 0) == false );
       assert( setValue(Type.X, 4, 4)  == false );
       assert( setValue(Type.X, 1, 10) == false );
       assert( setValue(Type.X, 33, 2000) == false );
    }

    private void testSwitchPlayer(){
      currentPlayer = Type.X;
      switchPlayer();
      assert( currentPlayer.equals(Type.O) );
      switchPlayer();
      assert( currentPlayer.equals(Type.X) );
    }

    private void testConvertChar(){
      assert( convertChar('a') == 0 );
      assert( convertChar('b') == 1 );
      assert( convertChar('c') == 2 );
      assert( convertChar('A') == 0 );
      assert( convertChar('B') == 1 );
      assert( convertChar('C') == 2 );
      //illegal
      assert( convertChar('d') == -1 );
      assert( convertChar('e') == -1 );
      assert( convertChar('f') == -1 );
      assert( convertChar('x') == -1 );
      assert( convertChar('y') == -1 );
      assert( convertChar('z') == -1 );
      assert( convertChar('0') == -1 );
      assert( convertChar('1') == -1 );
      assert( convertChar('2') == -1 );
    }

    private void testConvertInt(){
      assert( convertInt('1') == 0 );
      assert( convertInt('2') == 1 );
      assert( convertInt('3') == 2 );
      //illegal
      assert( convertInt('9') == -1 );
      assert( convertInt('5') == -1 );
      assert( convertInt('0') == -1 );
    }

    private void testConvertCoord(){
       int coord[] = {0,0};
       //all correct entries
       coord = convertCoord("a1");
       assert( (coord[0] == 0 && coord[1] == 0) );
       coord = convertCoord("a2");
       assert( (coord[0] == 0 && coord[1] == 1) );
       coord = convertCoord("a3");
       assert( (coord[0] == 0 && coord[1] == 2) );
       coord = convertCoord("b1");
       assert( (coord[0] == 1 && coord[1] == 0) );
       coord = convertCoord("b2");
       assert( (coord[0] == 1 && coord[1] == 1) );
       coord = convertCoord("b3");
       assert( (coord[0] == 1 && coord[1] == 2) );
       coord = convertCoord("c1");
       assert( (coord[0] == 2 && coord[1] == 0) );
       coord = convertCoord("c2");
       assert( (coord[0] == 2 && coord[1] == 1) );
       coord = convertCoord("c3");
       assert( (coord[0] == 2 && coord[1] == 2) );
       //illegal
       coord = convertCoord("d1");
       assert( coord[0] == -1 && coord[1] == 0 );
       coord = convertCoord("f5");
       assert( coord[0] == -1 && coord[1] == -1 );
       coord = convertCoord("z0");
       assert( coord[0] == -1 && coord[1] == -1 );
       coord = convertCoord("05");
       assert( coord[0] == -1 && coord[1] == -1 );
       coord = convertCoord("1b");
       assert( coord[0] == -1 && coord[1] == -1 );
    }

    private void testMakeMove(){
      initBoard();
      //starts with X to move
      assert( makeMove("a1") );
      assert( getValue(0,0).equals(Type.X) );
      //O automatically next to move
      assert( makeMove("a2") );
      assert( getValue(0,1).equals(Type.O) );
      //cannot move on a full square
      assert( makeMove("a2") == false );
      assert( getValue(0,1).equals(Type.O) );
      //move legal moves
      assert( makeMove("c3") );
      assert( getValue(2,2).equals(Type.X) );
      assert( makeMove("b1") );
      assert( getValue(1,0).equals(Type.O) );
      //works with capitol letters
      assert( makeMove("A3") );
      assert( getValue(0,2).equals(Type.X) );
      //test invalid entries
      assert( makeMove("d3") == false );
      assert( makeMove("b5") == false );
      assert( makeMove("0d") == false );
      assert( makeMove("-1d") == false );
      assert( makeMove("a0") == false );
      assert( makeMove("z9") == false );
      assert( makeMove("-1d") == false );
    }

    private void testCharToType(){
       assert( charToType('X').equals(Type.X) );
       assert( charToType('O').equals(Type.O) );
       assert( charToType('x').equals(Type.X) );
       assert( charToType('o').equals(Type.O) );
       assert( charToType('e').equals(Type.Empty) );
       assert( charToType('E').equals(Type.Empty) );
       assert( charToType('a').equals(Type.Impossible) );
       assert( charToType('1').equals(Type.Impossible) );
       assert( charToType('b').equals(Type.Impossible) );

    }

    private void testSetBoard(){
       char[][] b1 = {{'e', 'e', 'x'},{'o','o','x'},{'e','o','x'}};
       char[][] b2 = {{'o', 'o', 'o'},{'o','o','o'},{'o','o','o'}};
       char[][] b3 = {{'x', 'x', 'x'},{'x','x','x'},{'x','x','x'}};

       initBoard();

       setBoard(b1);
       for(int i = 0; i < SIZE; i++){
         assert( board[i][2].equals(Type.X) );
       }
       assert( board[0][0].equals(Type.Empty) );
       assert( board[0][1].equals(Type.Empty) );
       assert( board[1][0].equals(Type.O) );
       assert( board[1][1].equals(Type.O) );
       assert( board[2][0].equals(Type.Empty) );
       assert( board[2][1].equals(Type.O) );

       setBoard(b2);
       for(int i = 0; i < SIZE; i++){
         for(int j = 0; j < SIZE; j++){
           assert( board[i][j].equals(Type.O) );
         }
       }

       setBoard(b3);
       for(int i = 0; i < SIZE; i++){
         for(int j = 0; j < SIZE; j++){
           assert( board[i][j].equals(Type.X) );
         }
       }
    }

    private void testIsWin(){
      //this also tests checkRows(), checkCols(), checkDiag()
      char[][] rowWin0 = {{'o','o','o'},{'e', 'e', 'e'},{'e', 'e', 'e'}};
      char[][] rowWin1 = {{'e', 'e', 'e'},{'o','o','o'},{'e', 'e', 'e'}};
      char[][] rowWin2 = {{'e', 'e', 'e'},{'e', 'e', 'e'},{'o','o','o'}};
      char[][] colWin0 = {{'x', 'e', 'e'},{'x', 'e', 'e'},{'x','e','e'}};
      char[][] colWin1 = {{'e', 'x', 'e'},{'e', 'x', 'e'},{'e','x','o'}};
      char[][] colWin2 = {{'e', 'e', 'x'},{'e', 'e', 'x'},{'o','o','x'}};
      char[][] diaWin1 = {{'o', 'e', 'x'},{'e', 'o', 'e'},{'e','e','o'}};
      char[][] diaWin2 = {{'e', 'e', 'x'},{'e', 'x', 'x'},{'x','o','e'}};
      char[][] noWin0  = {{'o','e','o'},{'e', 'e', 'e'},{'e', 'e', 'e'}};
      char[][] noWin1  = {{'x','e','o'},{'x', 'e', 'o'},{'e', 'e', 'e'}};
      char[][] noWin2  = {{'e','e','o'},{'e', 'e', 'e'},{'e', 'x', 'x'}};

      clearBoard();
      assert( isWin().equals(Type.Empty) );

      setBoard(rowWin0);
      assert( isWin().equals(Type.O) );
      setBoard(rowWin1);
      assert( isWin().equals(Type.O) );
      setBoard(rowWin2);
      assert( isWin().equals(Type.O) );

      setBoard(colWin0);
      assert( isWin().equals(Type.X) );
      setBoard(colWin1);
      assert( isWin().equals(Type.X) );
      setBoard(colWin2);
      assert( isWin().equals(Type.X) );

      setBoard(diaWin1);
      assert( isWin().equals(Type.O) );
      setBoard(diaWin2);
      assert( isWin().equals(Type.X) );

      setBoard(noWin0);
      assert( isWin().equals(Type.Empty) );
      setBoard(noWin1);
      assert( isWin().equals(Type.Empty) );
      setBoard(noWin2);
      assert( isWin().equals(Type.Empty) );
    }

    private void testIsTie(){
      char[][] noTie0 = {{'o','o','o'},{'o', 'x', 'x'},{'o', 'x', 'x'}};
      char[][] noTie1 = {{'o','e','o'},{'e', 'e', 'e'},{'x', 'e', 'x'}};
      char[][] isTie0 = {{'o','x','o'},{'x', 'x', 'o'},{'o', 'o', 'x'}};
      char[][] isTie1 = {{'o','x','x'},{'x', 'o', 'o'},{'o', 'x', 'x'}};

      setBoard(noTie0);
      assert( !isTie() );
      setBoard(noTie1);
      assert( !isTie() );

      setBoard(isTie0);
      assert( isTie() );
      setBoard(isTie1);
      assert( isTie() );
    }

    private void testIsGameOver(){
      char[][] rowWin0 = {{'o','o','o'},{'e', 'e', 'e'},{'e', 'e', 'e'}};
      char[][] colWin0 = {{'x', 'e', 'e'},{'x', 'e', 'e'},{'x','e','e'}};
      char[][] noTie1 = {{'o','e','o'},{'e', 'e', 'e'},{'x', 'e', 'x'}};
      char[][] isTie1 = {{'o','x','x'},{'x', 'o', 'o'},{'o', 'x', 'x'}};

      clearBoard();
      assert( !isGameOver() );
      assert( winner.equals(Type.Empty) );

      setBoard(rowWin0);
      assert( isGameOver() );
      assert( winner.equals(Type.O) );

      setBoard(colWin0);
      assert( isGameOver() );
      assert( winner.equals(Type.X) );

      setBoard(noTie1);
      assert( isGameOver() == false );
      assert( winner.equals(Type.Empty) );

      setBoard(isTie1);
      assert( isGameOver() );
      assert( winner.equals(Type.Empty) );
    }
}
