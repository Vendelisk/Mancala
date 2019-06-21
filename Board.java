import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.scene.shape.*;
import javafx.scene.effect.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import java.util.*;

public class Board {

  static Image gameBoard = new Image("Images/Mancala-Board.png");
  static Image boardBG = new Image("Images/water-bg.png");
  static ImagePattern pattern = new ImagePattern(boardBG);

  static ImageView boardView = new ImageView(gameBoard);

  static ArrayList<Circle> clickableAreas = new ArrayList<Circle>();
  static ArrayList<Integer> marbsPerArea = new ArrayList<Integer>();
  static ArrayList<Marble> gameStones = new ArrayList<Marble>();

  public Board() {

    // Set location
    boardView.setX(75);
    boardView.setY(300);

    // Setting the fit height and width of the image view
    boardView.setFitHeight(400);
    boardView.setFitWidth(1250);

    marbsPerArea.add(0); // Player A Pool

    int offset = 0;
    for(int i = 0; i < 12; ++i) {
      int x = 300;
      int y = 390;
      if(i > 5)
      y = 585;
      if(i == 3 || i == 9)
      offset += 215;
      else if(i == 6 || i == 0)
      offset = 0;
      else
      offset += 145;
      if(i == 5 || i == 11)
      offset -= 5;

      Circle circle = new Circle(x+offset, y, 65);
      circle.setFill(Color.TRANSPARENT);
      circle.setUserData(i+1);
      clickableAreas.add(circle);
      marbsPerArea.add(4);
      Marble stone = new Marble(x+offset-20, y-20, 25, 25);
      Marble stone2 = new Marble(x+offset+5, y-20, 25, 25);
      Marble stone3 = new Marble(x+offset-20, y+5, 25, 25);
      Marble stone4 = new Marble(x+offset+5, y+5, 25, 25);

      stone.setBoardLoc(i+1);
      stone2.setBoardLoc(i+1);
      stone3.setBoardLoc(i+1);
      stone4.setBoardLoc(i+1);

      gameStones.add(stone);
      gameStones.add(stone2);
      gameStones.add(stone3);
      gameStones.add(stone4);
    }

    marbsPerArea.add(0); // Player B Pool
  }

  public ImageView getView() {
    return Board.boardView;
  }

  public ImagePattern getPattern() {
    return Board.pattern;
  }

  public static ArrayList<Circle> getClickableAreas() {
    return Board.clickableAreas;
  }

  public static ArrayList<Marble> getGameStones() {
    return Board.gameStones;
  }

  public static int getNumMarbs(int i) {
    return marbsPerArea.get(i);
  }

  public static void setNumMarbs(int i, int num) {
    if(num == 0) {
      marbsPerArea.set(i, 0);
    }
    else {
      marbsPerArea.set(i, marbsPerArea.get(i) + num);
    }
  }
}
