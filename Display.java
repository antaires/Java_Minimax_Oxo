/*Handles the input, output (player move choices/displaying the board)
  Takes the board in as a string "ooxeeoxxx" */

import java.util.*;
import java.io.*;

class Display {
      private int SIZE = 3;
      private String move;
      private String playAgain;
      private String playingAs;
      private Type currentPlayer = Type.X; //X always starts

      void Intro(){
         String intro = "\n\nWelcome to Noughts and Crosses!\n" +
         "\nPlayers take turns making moves - first to get 3 in a row wins." +
         "\nEnter by selecting the grid value:\n" +
         "\n                      1  2  3   " +
         "\n                    a a1 a2 a3  " +
         "\n                    b b1 b2 b3  " +
         "\n                    c c1 c2 c3 \n";
         System.out.println(intro);
         System.out.println("");
      }

      String oneOrTwoPlayers(){
        String oneOrTwo = "\nOne or two players? (1/2)";
        System.out.println(oneOrTwo);
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        return choice;
      }

      String getMove(String currentPlayer){
          System.out.println("Player " + currentPlayer + "s move: ");
          Scanner scanner = new Scanner(System.in);
          move = scanner.nextLine();
          System.out.println("You picked " + move);
          return move;
      }

      void isTie(){
        String tie = "\nThe game is a tie!\n";
        System.out.println(tie);
        System.out.println("");
      }

      void isWin(String winner){
        String win = "\nPlayer " + winner + " wins!\n";
        System.out.println(win);
        System.out.println("");
      }

      boolean newGame(){
        System.out.println("\nPlay again? (y/n)");
        Scanner scanner = new Scanner(System.in);
        playAgain = scanner.nextLine();
        playAgain = playAgain.toLowerCase();
        System.out.println("");
        if (playAgain.equals("y")){
           return true;
        }
        return false;
      }

      void drawBoard(String b){
         b = b.toLowerCase();
         String rows = "abc";
         int r = 0;
         System.out.print("\n  1 2 3");
         for (int i = 0; i < SIZE * 3; i++){
            if (i % SIZE == 0 ){
              System.out.print("\n" + rows.charAt(r++) +" " );
            }
            if (b.charAt(i) == 'x'){
                System.out.print("X ");
            } else if (b.charAt(i) == 'o'){
                System.out.print("O ");
            } else {
                System.out.print("  ");
            }
        }
        System.out.println("");
      }

      Type playingAs(){
        System.out.println("\nWould you like to play as X or O? (o/x)");
        Scanner scanner = new Scanner(System.in);
        playingAs = scanner.nextLine();
        playingAs = playingAs.toLowerCase();
        System.out.println("");
        if (playingAs.equals("x")){
          return Type.X;
        }
        return Type.O;
      }

      // ---------- Testing -----------

      public static void main(String[] args) {
          Display program = new Display();
          program.run();
      }
      // Run the tests
      private void run() {
          boolean testing = false;
          assert(testing = true);
          if (! testing) throw new Error("Use java -ea Board");
      }
}
