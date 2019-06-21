import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.scene.shape.*;
import javafx.scene.effect.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.util.*;
import java.util.*;

public class Player {

  private char letter;
  public boolean isTurn;
  private int score;

  public Player(char letter) {
    this.letter = letter;
    if(letter == 'A')
    isTurn = true;
  }

  public void makeTurn(Text A, Text B, Player otherPlayer) {
    if(this.letter == 'A') {
      B.setFill(Color.BLUE);
      A.setFill(Color.BLACK);
    }
    else {
      A.setFill(Color.BLUE);
      B.setFill(Color.BLACK);
    }
    changeTurn();
    otherPlayer.changeTurn();
  }

  public boolean isTurn() {
    return this.isTurn;
  }

  public void changeTurn() {
    this.isTurn = !this.isTurn;
  }
}
