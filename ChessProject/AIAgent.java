import java.util.*;

public class AIAgent{
  Random rand;

  public AIAgent(){
    rand = new Random();
  }

/*
  The method randomMove takes as input a stack of potential moves that the AI agent
  can make. The agent uses a rondom number generator to randomly select a move from
  the inputted Stack and returns this to the calling agent.
*/

  public Move randomMove(Stack possibilities){
    //pick a random number form the totol amount of possible moves
    int moveID = rand.nextInt(possibilities.size());//pick a number between 1 and whatever is in the stack

    System.out.println("Agent randomly selected move : "+moveID);
    for(int i=1;i < (possibilities.size()-(moveID));i++){
      possibilities.pop();
    }
    Move selectedMove = (Move)possibilities.pop();
    return selectedMove;

  }

  public Move nextBestMove(Stack whitePossibilitiesStack, Stack blackPossibilitiesStack) {
    Stack backupMove = (Stack) whitePossibilitiesStack.clone();
    Stack blackStackM = (Stack) blackPossibilitiesStack.clone();

    Move whitePieceMove, currentMovement;
    Move thebestMove = null;
    Square blackPiecePosition;
    int strength = 0;
    int SelectedPieceStrength = 0;


    while (!whitePossibilitiesStack.empty()) {
      whitePieceMove = (Move) whitePossibilitiesStack.pop();
      currentMovement = whitePieceMove;

      //check if the centre of the board is occupied or not
      if ((currentMovement.getStart().getYC() < currentMovement.getLanding().getYC())
              && (currentMovement.getLanding().getXC() == 3) && (currentMovement.getLanding().getYC() == 3)
              || (currentMovement.getLanding().getXC() == 4) && (currentMovement.getLanding().getYC() == 3)
              || (currentMovement.getLanding().getXC() == 3) && (currentMovement.getLanding().getYC() == 4)
              || (currentMovement.getLanding().getXC() == 4) && (currentMovement.getLanding().getYC() == 4)) {

        strength = 1;

        //updating the best move
        if (strength > SelectedPieceStrength) {
          SelectedPieceStrength = strength;
          thebestMove = currentMovement;
        }
      }

      //compare white landing positions to black positions, return capture if available or random if not.
      while (!blackStackM.isEmpty()) {
        strength = 0;
        blackPiecePosition = (Square) blackStackM.pop();
        if ((currentMovement.getLanding().getXC() == blackPiecePosition.getXC()) && (currentMovement.getLanding().getYC() == blackPiecePosition.getYC())) {

          //Assigning strength values to pieces
          if (blackPiecePosition.getName().equals("BlackQueen")) {
            strength = 5;
          } else if (blackPiecePosition.getName().equals("BlackRook")) {
            strength = 4;
          } else if (blackPiecePosition.getName().equals("BlackBishop") || blackPiecePosition.getName().equals("BlackKnight")) {
            strength = 3;
          } else if (blackPiecePosition.getName().equals("BlackPawn")) {
            strength = 2;
          } else {
            strength = 6;
          }
        }
        //update the best move
        if (strength > SelectedPieceStrength) {
          SelectedPieceStrength = strength;
          thebestMove = currentMovement;
        }
      }

      blackStackM = (Stack) blackPossibilitiesStack.clone();
    }

    // Choose the best move If one is not available make a random move
    if (SelectedPieceStrength > 0) {
      System.out.println("AI Agent selected - Next best move: " + SelectedPieceStrength);
      return thebestMove;
    }

    return randomMove(backupMove);

  }


  public Move twoLevelsDeep(Stack possibilities){
    Move selectedMove = new Move();
    return selectedMove;
  }
}
