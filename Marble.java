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

public class Marble {

  // Creating an image
  static Image silverMarble = new Image("Images/marble1.png");
  static Image blueMarble = new Image("Images/marble2.png");
  static Image redMarble = new Image("Images/marble3.png");

  private ImageView view;
  private int color; // 0 silver, 1 blue, 2 red
  private Image image;
  private int xPos;
  private int yPos;
  private int boardLoc;

  public Marble() {
    Random rand = new Random();
    this.color = rand.nextInt(3);
    if(color == 0)
    this.image = silverMarble;
    else if(color == 1)
    this.image = blueMarble;
    else
    this.image = redMarble;

    this.view = new ImageView(image);
  }

  public Marble(int x, int y) {
    Random rand = new Random();
    this.color = rand.nextInt(3);
    if(color == 0)
    this.image = silverMarble;
    else if(color == 1)
    this.image = blueMarble;
    else
    this.image = redMarble;

    this.view = new ImageView(image);

    this.view.setX(x);
    this.view.setY(y);
    this.xPos = x;
    this.yPos = y;
  }

  public Marble(int x, int y, int color) {
    this.color = color;
    if(color == 0)
    this.image = silverMarble;
    else if(color == 1)
    this.image = blueMarble;
    else
    this.image = redMarble;

    this.view = new ImageView(image);

    this.view.setX(x);
    this.view.setY(y);
    this.xPos = x;
    this.yPos = y;
  }

  public Marble(int x, int y, int height, int width) {
    Random rand = new Random();
    this.color = rand.nextInt(3);
    if(color == 0)
    this.image = silverMarble;
    else if(color == 1)
    this.image = blueMarble;
    else
    this.image = redMarble;

    this.view = new ImageView(image);

    this.view.setX(x);
    this.view.setY(y);
    this.xPos = x;
    this.yPos = y;
    this.view.setFitHeight(height);
    this.view.setFitWidth(width);
  }

  public ImageView getView() {
    return this.view;
  }

  public int getX() {
    return this.xPos;
  }

  public int getY() {
    return this.yPos;
  }

  public void setX(int newX) {
    this.view.setX(newX);
    this.xPos = newX;
  }

  public void setY(int newY) {
    this.view.setY(newY);
    this.yPos = newY;
  }

  public int getBoardLoc() {
    return this.boardLoc;
  }

  public void setBoardLoc(int circleNum) {
    this.boardLoc = circleNum;
  }
}
