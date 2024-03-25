import java.util.Scanner;

public class Main {
    private static final int ROW = 3;
    private static final int COL = 3;
    private static String board[][] = new String [ROW][COL];
    private static Scanner in = new Scanner(System.in);
    private static String winner = "";
    private static boolean tie = false;

    public static void main(String[] args) {
        boolean playGame;
        boolean gameOver = false;
        boolean playerWins = false;

        String player1 = "X";
        String player2 = "O";
        String playerSwap;

        System.out.println("Hello, welcome to tic tac toe!");
        playGame = SafeInput.getYNconfirm(in,"would you like to play?");
        if(playGame) {//prevents the loop from running if the player initially decides not to play
            do {
                int moveCnt = 0;//used to count the number of moves that have taken place
                tie = false;
                clearBoard();

                do {
                    displayBoard();

                    makePlayerMove(player1);//get player 1 to move
                    moveCnt += 1;

                    if (checkForWin(player1)) {
                        gameOver = true;
                        playerWins = true;
                    }
                    if(tieCheck(player1, player2, moveCnt) && moveCnt>=5 && !gameOver) {//seperated to prevent ties from overwriting wins made in the final move
                        gameOver = true;
                        playerWins = false;
                    }

                    if (!gameOver) {//if player1 hasn't won the game, move on to player2
                        displayBoard();

                        makePlayerMove(player2);//get player 2 to move
                        moveCnt += 1;

                        if (checkForWin(player2)) {
                            gameOver = true;
                            playerWins = true;
                        }
                        if(tieCheck(player1, player2, moveCnt) && moveCnt>=5 && !gameOver) {
                            gameOver = true;
                            playerWins = false;
                        }
                    }


                } while (!gameOver);
                displayBoard();
                if (tie && !playerWins) {
                    System.out.println("There are no valid moves left and the game has ended in a tie!");
                    System.out.println("no one wins, and no one loses.");
                } else {
                    System.out.println("player " + winner + " has won the game.");
                }

                playGame = SafeInput.getYNconfirm(in, "would you like to play again?");

                playerSwap = player1;
                player1 = player2;
                player2 = playerSwap;//swaps the players for next game, should the players choose to continue

            } while (playGame);
        }

        System.out.println("Goodbye!");
    }



    private static void displayBoard(){
        for(int row=0; row<ROW; row++){
            for(int col=0; col<COL; col++){
                System.out.print("[" + board[row][col] + "]");
            }
            System.out.println();
        }
    }

    private static void clearBoard(){
        for(int row=0; row<ROW; row++){
            for(int col=0; col<COL; col++){
                board[row][col] = " ";
            }
        }

    }

    private static void makePlayerMove(String playerSymbol){
        int inputRow;
        int inputCol;
        boolean done; //set to false if needed
        boolean confirmation = false;

            do {
                System.out.println("player " + playerSymbol + " turn, please input a valid/empty row and column");
                inputRow = SafeInput.getRangedInt(in, "Row", 3, 1);
                inputCol = SafeInput.getRangedInt(in, "Column", 3, 1);

                inputRow -= 1;
                inputCol -= 1;//set the chosen row and column to match how arrays count

                done = isValidMove(inputRow, inputCol);//check for valid move
            } while (!done);//loop to get valid move
        /* only turn on if necessary

        do{
            System.out.println("you have chosen row: " + (inputRow+1) + ", and column: " + (inputCol+1));
            confirmation = SafeInput.getYNconfirm(in,"Are you sure this is the move you meant/want to make?");

        }while(!confirmation);//loop to allow players to confirm thier moves.

        */

        board[inputRow][inputCol] = playerSymbol;
    }
    private static boolean isValidMove(int row, int col){
        boolean retBool = false;
        if (board[row][col].equals(" ")){
            retBool = true;
        }
        else{
            System.out.println("invalid move, please try again");
            retBool = false;
        }
        return retBool;
    }
    private static boolean checkForWin(String playerSymbol){
        boolean retBool;
        if(colCheck(playerSymbol) || rowCheck(playerSymbol) || diaCheck(playerSymbol)){
            winner = playerSymbol;
            return true;
        }
        return false;
    }

    private static boolean colCheck(String playerSymbol){
        for(int col=0; col<COL; col++){ //for each column
            if(board[0][col].equals(playerSymbol) && board[1][col].equals(playerSymbol) && board[2][col].equals(playerSymbol))
                return true;
        }
            return false;
    }
    private static boolean rowCheck(String playerSymbol){
        for(int row=0; row<ROW; row++){ //for each column
            if(board[row][0].equals(playerSymbol) && board[row][1].equals(playerSymbol) && board[row][2].equals(playerSymbol))
                return true;
        }
        return false;
    }
    private static boolean diaCheck(String playerSymbol){
        boolean retBool = false;

        if(
                (board[0][0].equals(playerSymbol) && board[1][1].equals(playerSymbol) && board[2][2].equals(playerSymbol))
                || (board[0][2].equals(playerSymbol) && board[1][1].equals(playerSymbol) && board[2][0].equals(playerSymbol))
                //manually checks the diagonals as this is the simplest way to do it
        ){
            retBool = true;
        }
        else{
            retBool=false;
        }

        return retBool;
    }
    private static boolean tieCheck(String player1, String player2,int moveCnt){
        //when we run out of moves, the game ends in a tie
        //if we have less than 3 spaces, and no one has won yet, the game also ends in a tie


        if(moveCnt>6){
            if(
                    !(checkForWin(player1))&&
                            !(checkForWin(player2))

            ){
                tie=true;
                return true;
            }
        }
        else if(moveCnt==9){
            tie = true;
            return true;
        }
        else{
            return false;
        }
        return false;
    }
}