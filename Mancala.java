import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.scene.shape.*;
import javafx.scene.effect.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.util.*;
import javafx.scene.layout.*;
import java.util.*;
import java.io.File;

public class Mancala extends Application {

  // Creating a Group object
  private static Group myGroup = new Group();
  private ObservableList<Node> list;
  private ArrayList<Text> count = new ArrayList<Text>();
  private Stage primStageCopy;
  private Boolean gameOver = false;
  private Scene scene1;
  private char winner;
  Text nameA = new Text();
  Text nameB = new Text();
  Text pScores = new Text();
  Player playerA = new Player('A');
  Player playerB = new Player('B');
  int xOffA = 0;
  int yOffA = 0;
  int xOffB = 0;
  int yOffB = 0;
  int fix = 1; // forces redraw on "new game"
  int aScore = 0;
  int bScore = 0;

  public Mancala() {
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    this.primStageCopy = primaryStage;

    ArrayList<Marble> titleMarbles = new ArrayList<Marble>();

    int marbX = 50;
    int marbColor = 0;
    for(int i = 0; i < 6; ++i) {
      Marble marble = new Marble(marbX, 100, marbColor);
      if(i == 2)
      marbX += 600;
      else
      marbX += 150;
      if(i > 2)
      --marbColor;
      else if(i == 2) {
        // nothing
      }
      else
      ++marbColor;
      titleMarbles.add(marble);
    }

    Board myBoard = new Board();

    // Creating the mouse event handler
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        Object obj = e.getSource();
        int data = (int)((Circle) obj).getUserData();
        movePieces(data, -1);
      }
    };

    myGroup.getChildren().add(myBoard.getView());

    Button newGame = new Button("New Game");
    newGame.setLayoutX(30);
    newGame.setLayoutY(15);
    newGame.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
    newGame.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        resetPieces(4);
      }
    });
    myGroup.getChildren().add(newGame);

    Button rules = new Button("View Rules");
    rules.setLayoutX(30);
    rules.setLayoutY(50);
    rules.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
    rules.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        showRules(scene1);
      }
    });
    myGroup.getChildren().add(rules);

    // Add titleMarbles to Group
    int numTitleMarbs = titleMarbles.size();
    for(int i = 0; i < numTitleMarbs; ++i) {
      myGroup.getChildren().add(titleMarbles.get(i).getView());
    }

    // Add stones to group
    ArrayList<Marble> gameStones = Board.getGameStones();
    int numStones = gameStones.size();
    for(int i = 0; i < numStones; ++i) {
      myGroup.getChildren().add(gameStones.get(i).getView());
    }

    // Add transparent circles & register the event filter
    ArrayList<Circle> boardSlots = Board.getClickableAreas();
    int numSlots = boardSlots.size();
    for(int i = 0; i < numSlots; ++i) {
      boardSlots.get(i).addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
      myGroup.getChildren().add(boardSlots.get(i));
    }

    playerScores('z');
    myGroup.getChildren().add(pScores);

    // Retrieving the observable list object
    list = myGroup.getChildren();

    // Setting the text object as a node to the group object
    makePlayerA();
    makePlayerB();
    list.add(makeTitle());
    list.add(nameA);
    list.add(nameB);
    makeCountText();
    showMarbCount();

    // Creating a Scene by passing the group object, height and width
    scene1 = new Scene(myGroup, 1450, 900);

    // Set bg image
    scene1.setFill(myBoard.getPattern());

    // Setting the title to Stage.
    primaryStage.setTitle("Mancala");

    // Adding the scene to Stage
    primaryStage.setScene(scene1);

    // Displaying the contents of the stage
    primaryStage.show();
  }

  public void showRules(Scene gameScene) {
    Image rulesImage = new Image("Images/Mancala_Instructions.jpg");
    ImageView rulesView = new ImageView(rulesImage);

    // Our image will sit in the middle of our popup.
    rulesView.fitWidthProperty().bind(primStageCopy.widthProperty());

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setFitToWidth(true);
    scrollPane.setContent(rulesView);

    Button close = new Button("close");
    close.setLayoutX(30);
    close.setLayoutY(50);
    close.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
    close.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        primStageCopy.setScene(gameScene);
        primStageCopy.show();
      }
    });

    BorderPane bp = new BorderPane();
    bp.setTop(close);
    bp.setCenter(scrollPane);
    Scene scene = new Scene(bp, 1450, 900);

    primStageCopy.setScene(scene);
    primStageCopy.show();
  }

  public void endGame() {
    final Popup popup = new Popup(); popup.setX(300); popup.setY(400);

    Text text = new Text();
    if(Board.getNumMarbs(0) > Board.getNumMarbs(13)) {
      text.setText("PLAYER A WINS!!!");
      playerScores('a');
    }
    else if(Board.getNumMarbs(0) < Board.getNumMarbs(13)) {
      text.setText("PLAYER B WINS!!!");
      playerScores('b');
    }
    else
    text.setText("TIE GAME!?!?!?!?");

    popup.getContent().addAll(text);

    text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 80));
    text.setFill(Color.PURPLE);

    popup.show(primStageCopy);

    PauseTransition delay = new PauseTransition(Duration.seconds(4));
    delay.setOnFinished( event -> popup.hide() );
    delay.play();

    gameOver = true;

    xOffA = 0;
    yOffA = 0;
    xOffB = 0;
    yOffB = 0;
  }

  public static void main(String args[]){
    launch(args);
  }

  public void playerScores(char winner) {
    if(winner == 'a')
    aScore += 1;
    else if(winner == 'b')
    bScore += 1;
    else {
    }

    // Setting font to the text
    pScores.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));

    // Setting the position of the text
    pScores.setX(375);
    pScores.setY(50);

    // Setting the text to be added.
    pScores.setText(aScore + "               ||               " + bScore);

    pScores.setFill(Color.DARKGREEN);

    // Setting the Stroke
    pScores.setStrokeWidth(.5);

    // Setting the stroke color
    pScores.setStroke(Color.MEDIUMPURPLE);
  }

  public Text makeTitle() {
    // Creating a Text object
    Text text = new Text();

    // Setting font to the text
    text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));

    // Setting the position of the text
    text.setX(500);
    text.setY(150);

    // Setting the text to be added.
    text.setText("M A N C A L A");

    // Setting the Stroke
    text.setStrokeWidth(.5);

    // Setting the stroke color
    text.setStroke(Color.MEDIUMPURPLE);

    // Set flair
    text.setUnderline(true);

    // Node effects
    Glow glow = new Glow();

    // Setting the level property
    glow.setLevel(3.0);

    // Apply effects
    text.setEffect(glow);

    // Instanting the reflection class
    Reflection reflection = new Reflection();

    // Setting the bottom opacity of the reflection
    reflection.setBottomOpacity(0.0);

    // Setting the top opacity of the reflection
    reflection.setTopOpacity(0.5);

    // Setting the top offset of the reflection
    reflection.setTopOffset(0.0);

    // Setting the fraction of the reflection
    reflection.setFraction(0.7);

    // Applying reflection effect to the text
    text.setEffect(reflection);

    return text;
  }

  public void makePlayerA() {
    // Setting font to the text
    nameA.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));

    // Setting the position of the text
    nameA.setX(45);
    nameA.setY(525);

    // Setting the text to be added.
    nameA.setText("Player A");
    nameA.setFill(Color.BLUE);

    // Vertical
    nameA.setRotate(-90);
  }

  public void makePlayerB() {
    // Setting font to the text
    nameB.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));

    // Setting the position of the text
    nameB.setX(1120);
    nameB.setY(525);

    // Setting the text to be added.
    nameB.setText("Player B");

    // Vertical
    nameB.setRotate(90);
  }

  public void makeCountText() {
    for(int i = 0; i < 14; ++i) {
      Text text = new Text();
      count.add(text);
      list.add(text);
    }
  }

  public void showMarbCount() {
    int offset = 0;
    for(int i = 0; i < 14; ++i) {
      int y = 300;
      int x = 300;

      if(i > 6)
      y = 730;
      if(i == 4 || i == 10)
      offset += 215;
      else if(i == 7)
      offset = 0;
      else if(i == 0)
      x = 150;
      else if(i == 1) {
        // nothing
      }
      else
      offset += 145;
      if(i == 5 || i == 11)
      offset -= 5;

      // Adding a Text object
      count.get(i).setX(x+offset);
      count.get(i).setY(y);
      count.get(i).setText("" + Board.getNumMarbs(i));

      // Setting font to the text
      count.get(i).setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
    }
  }

  public static Group getGroup() {
    return myGroup;
  }

  public int getNextSelection(int origSelec) {
    if(origSelec == 9)
    return 10;
    else if(origSelec == 4)
    return 3;
    else if(origSelec == 0)
    return 7;
    else if(origSelec == 13)
    return 6;
    else if(origSelec <= 6)
    return origSelec - 1;
    else
    return origSelec + 1;
  }

  public void movePieces(int selection, int marblesLeft) {
    if(playerA.isTurn && (selection > 6) && marblesLeft == -1)
    return;
    else if(playerB.isTurn && (selection < 7) && marblesLeft == -1)
    return;
    else if(Board.getNumMarbs(selection) == 0)
    return;

    ArrayList<Marble> gameStones = Board.getGameStones();
    ArrayList<Integer> marblesToMove = new ArrayList<Integer>();
    int numStones = gameStones.size();
    int nextSelection = selection;
    for(int i = 0; i < numStones; ++i) {
      if(gameStones.get(i).getBoardLoc() == selection)
      marblesToMove.add(i);
    }

    if(marblesLeft == -1) { // first iteration
      marblesLeft = marblesToMove.size();
      Board.setNumMarbs(selection, 0);
    }

    Random rand = new Random();
    for(int j = 0; j < marblesLeft; ++j) {
      int i = marblesToMove.get(j);

      int offsetX = rand.nextInt(51);
      int offsetY = rand.nextInt(51);
      /*
      if(rand.nextInt(2) == 1) {
      offsetX *= -1;
      offsetY *= -1;
    }*/
    Path path = new Path();
    path.setOpacity(0);

    // Build movement paths and update stone locations
    path.getElements().add(new MoveTo(gameStones.get(i).getX(), gameStones.get(i).getY()));
    if(selection == 9) { // Center hop right
      path.getElements().add(new LineTo(gameStones.get(i).getX()+225, gameStones.get(i).getY()));
      gameStones.get(i).setBoardLoc(10);
      nextSelection = 10;
      gameStones.get(i).setX(gameStones.get(i).getX() + 225);
    }
    else if(selection == 4) { // Center hop left
      path.getElements().add(new LineTo(gameStones.get(i).getX()-225, gameStones.get(i).getY()));
      gameStones.get(i).setBoardLoc(3);
      nextSelection = 3;
      gameStones.get(i).setX(gameStones.get(i).getX() - 225);
    }
    else if(selection == 0) { // Player A Pool
      path.getElements().add(new CubicCurveTo(gameStones.get(i).getX(), gameStones.get(i).getY()+200, gameStones.get(i).getX()+150, gameStones.get(i).getY()+200, 280+offsetX, 585+offsetY));
      gameStones.get(i).setBoardLoc(7);
      nextSelection = 7;
      gameStones.get(i).setX(280+offsetX);
      gameStones.get(i).setY(585+offsetY);
    }
    else if(selection == 13) { // Player B Pool
      path.getElements().add(new CubicCurveTo(gameStones.get(i).getX(), gameStones.get(i).getY()-200, gameStones.get(i).getX()-150, gameStones.get(i).getY()-200, 1050+offsetX, 390+offsetY));
      gameStones.get(i).setBoardLoc(6);
      nextSelection = 6;
      gameStones.get(i).setX(1050+offsetX);
      gameStones.get(i).setY(390+offsetY);
    }
    else if(selection <= 6) { // Top Row
      path.getElements().add(new LineTo(gameStones.get(i).getX()-145, gameStones.get(i).getY()));
      gameStones.get(i).setBoardLoc(gameStones.get(i).getBoardLoc() - 1);
      nextSelection = selection - 1;
      gameStones.get(i).setX(gameStones.get(i).getX() - 145);
    }
    else {                    // Bottom Row
      path.getElements().add(new LineTo(gameStones.get(i).getX()+145, gameStones.get(i).getY()));
      gameStones.get(i).setBoardLoc(gameStones.get(i).getBoardLoc() + 1);
      nextSelection = selection + 1;
      gameStones.get(i).setX(gameStones.get(i).getX() + 145);
    }

    myGroup.getChildren().add(path);

    PathTransition pathTransition = new PathTransition();

    pathTransition.setDuration(Duration.seconds(1.0));
    pathTransition.setPath(path);
    pathTransition.setNode(gameStones.get(i).getView());

    pathTransition.play();
  }

  ArrayList<Circle> boardSlots = Board.getClickableAreas();
  int numSlots = boardSlots.size();
  for(int i = 0; i < numSlots; ++i) {
    boardSlots.get(i).toFront();
  }

  showMarbCount();
  if(marblesLeft != 0) {
    // Don't move into your enemies pool
    if((nextSelection == 0 && playerB.isTurn()) || (nextSelection == 13 && playerA.isTurn())) {
      movePieces(nextSelection, marblesLeft);
      if(gameOver)
      return;
    }
    else {
      Board.setNumMarbs(nextSelection, +1);
      movePieces(nextSelection, marblesLeft-1);
      if(gameOver)
      return;
    }
  }
  else if(selection == 0 || selection == 13) {
    // A pool or B pool (don't change turns)
  }
  // Empty opposite pool (B)
  else if((selection - 6) >= 0 && (Board.getNumMarbs(selection) == 1) && playerB.isTurn() && (selection - 6) != 0 && (selection - 6) != 13) {
    for(int i = 0; i < numStones; ++i) {
      if(gameStones.get(i).getBoardLoc() == (selection - 6)) {
        Path path = new Path();
        path.getElements().add(new MoveTo(gameStones.get(i).getX(), gameStones.get(i).getY()));
        path.getElements().add(new LineTo(1200+xOffB, 400+yOffB));
        gameStones.get(i).setBoardLoc(0);
        gameStones.get(i).setX(1200+xOffB);
        gameStones.get(i).setY(400+yOffB);
        xOffB += 20;
        if(xOffB > 60) {
          yOffB += 20;
          xOffB = 0;
        }

        path.setOpacity(0);

        myGroup.getChildren().add(path);

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.seconds(1.0));
        pathTransition.setPath(path);
        pathTransition.setNode(gameStones.get(i).getView());

        pathTransition.play();
        Board.setNumMarbs(13, +1);
      }
    }
    Board.setNumMarbs(selection-6, 0);
    playerB.makeTurn(nameA, nameB, playerA);
    showMarbCount();
  }
  // Empty opposite pool (A)
  else if((selection + 6) <= 13 && (Board.getNumMarbs(selection) == 1) && playerA.isTurn() && (selection + 6) != 0 && (selection + 6) != 13) {
    for(int i = 0; i < numStones; ++i) {
      if(gameStones.get(i).getBoardLoc() == (selection + 6)) {
        Path path = new Path();
        path.getElements().add(new MoveTo(gameStones.get(i).getX(), gameStones.get(i).getY()));
        path.getElements().add(new LineTo(125+xOffA, 500+yOffA));
        gameStones.get(i).setBoardLoc(0);
        gameStones.get(i).setX(125+xOffA);
        gameStones.get(i).setY(500+yOffA);
        xOffA += 20;
        if(xOffA > 60) {
          yOffA += 20;
          xOffA = 0;
        }

        path.setOpacity(0);

        myGroup.getChildren().add(path);

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.seconds(1.0));
        pathTransition.setPath(path);
        pathTransition.setNode(gameStones.get(i).getView());

        pathTransition.play();

        Board.setNumMarbs(0, +1);
      }
    }
    Board.setNumMarbs(selection+6, 0);
    playerA.makeTurn(nameA, nameB, playerB);
    showMarbCount();
  }
  else {
    if(playerA.isTurn()) {
      playerA.makeTurn(nameA, nameB, playerB);
    }
    else {
      playerB.makeTurn(nameA, nameB, playerA);
    }
  }
  // check = check if player has any moves left
  int check1 = 0;
  for(int i = 1; i < 7; ++i) {
    if(Board.getNumMarbs(i) == 0) {
      ++check1;
    }
  }
  int check2 = 0;
  for(int i = 7; i < 13; ++i) {
    if(Board.getNumMarbs(i) == 0) {
      ++check2;
    }
  }
  if(check1 == 6) {
    leftoverMarbs(1);
    endGame();
  }
  else if(check2 == 6) {
    leftoverMarbs(2);
    endGame();
  }
}

public void leftoverMarbs(int check) {
  ArrayList<Marble> gameStones = Board.getGameStones();
  int numStones = gameStones.size();

  if(check == 1) {
    //Board.setNumMarbs(13, +(48 - (Board.getNumMarbs(0) + Board.getNumMarbs(13))));
    for(int i = 0; i < numStones; ++i) {
      if(gameStones.get(i).getBoardLoc() <= 12 && gameStones.get(i).getBoardLoc() > 0) {
        Path path = new Path();
        path.getElements().add(new MoveTo(gameStones.get(i).getX(), gameStones.get(i).getY()));
        path.getElements().add(new LineTo(1200+xOffB, 400+yOffB));
        gameStones.get(i).setX(1200+xOffB);
        gameStones.get(i).setY(400+yOffB);
        xOffB += 20;
        if(xOffB > 60) {
          yOffB += 20;
          xOffB = 0;
        }

        path.setOpacity(0);

        myGroup.getChildren().add(path);

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.seconds(1.0));
        pathTransition.setPath(path);
        pathTransition.setNode(gameStones.get(i).getView());

        pathTransition.play();
        Board.setNumMarbs(13, +1);
        Board.setNumMarbs(gameStones.get(i).getBoardLoc(), -1);
        gameStones.get(i).setBoardLoc(0);
      }
    }
    showMarbCount();
  }
  else {
    //Board.setNumMarbs(0, +(48 - (Board.getNumMarbs(0) + Board.getNumMarbs(13))));
    for(int i = 0; i < numStones; ++i) {
      if(gameStones.get(i).getBoardLoc() <= 12 && gameStones.get(i).getBoardLoc() > 0) {
        Path path = new Path();
        path.getElements().add(new MoveTo(gameStones.get(i).getX(), gameStones.get(i).getY()));
        path.getElements().add(new LineTo(125+xOffA, 500+yOffA));
        gameStones.get(i).setX(125+xOffA);
        gameStones.get(i).setY(500+yOffA);
        xOffA += 20;
        if(xOffA > 60) {
          yOffA += 20;
          xOffA = 0;
        }

        path.setOpacity(0);

        myGroup.getChildren().add(path);

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.seconds(1.0));
        pathTransition.setPath(path);
        pathTransition.setNode(gameStones.get(i).getView());

        pathTransition.play();

        Board.setNumMarbs(0, +1);
        Board.setNumMarbs(gameStones.get(i).getBoardLoc(), -1);
        gameStones.get(i).setBoardLoc(0);
      }
    }
    showMarbCount();
  }
}

///////////////////////////////////////////////////////

public void resetPieces(int sleepTime) {
  ArrayList<Marble> gameStones = Board.getGameStones();
  int numStones = gameStones.size();
  ArrayList<Circle> boardSlots = Board.getClickableAreas();
  int numSlots = boardSlots.size();

  int count = 0;
  int offset = 0;
  for(int j = 0; j < numSlots; ++j) {
    int x = 300;
    int y = 390;
    if(j > 5)
    y = 585;
    if(j == 3 || j == 9)
    offset += 215;
    else if(j == 6 || j == 0)
    offset = 0;
    else
    offset += 145;
    if(j == 5 || j == 11)
    offset -= 5;

    for(int i = 0; i < (numStones / numSlots); ++i) {
      Path path = new Path();
      path.setOpacity(0);

      int xShift = -20;
      int yShift = -20;
      if(i == 1)
      xShift = 5;
      else if(i == 2)
      yShift = 5;
      else if(i == 3) {
        xShift = 5;
        yShift = 5;
      }

      // Build movement paths and update stone locations
      path.getElements().add(new MoveTo(gameStones.get(count).getX(), gameStones.get(count).getY()));
      gameStones.get(count).setBoardLoc(j+1);

      path.getElements().add(new LineTo(x+offset+xShift+fix, y+yShift+fix));
      gameStones.get(count).setX(x+offset+xShift+fix);
      gameStones.get(count).setY(y+yShift+fix);

      myGroup.getChildren().add(path);

      PathTransition pathTransition = new PathTransition();

      pathTransition.setDuration(Duration.seconds(1.0));
      pathTransition.setPath(path);
      pathTransition.setNode(gameStones.get(count).getView());

      pathTransition.play();
      ++count;
    }

    Board.setNumMarbs(j+1, 0);
    Board.setNumMarbs(j+1, +4);
  }
  Board.setNumMarbs(0, 0);
  Board.setNumMarbs(13, 0);
  showMarbCount();

  // Set pools above paths
  for(int i = 0; i < numSlots; ++i) {
    boardSlots.get(i).toFront();
  }

  if(fix == 1)
  fix = 0;
  else
  fix = 1;
}

///////////////////////////////////////////////////////
}
