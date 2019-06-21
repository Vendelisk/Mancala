import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class TilePaneExample extends Application {
   @Override
   public void start(Stage stage) {
      //Creating an array of Buttons
      Button[] buttons = new Button[] {
         new Button("SunDay"),
         new Button("MonDay"),
         new Button("TuesDay"),
         new Button("WednesDay"),
         new Button("ThursDay"),
         new Button("FriDay"),
         new Button("SaturDay")
      };
      //Creating a Tile Pane
      TilePane tilePane = new TilePane();

      //Setting the orientation for the Tile Pane
      tilePane.setOrientation(Orientation.HORIZONTAL);

      //Setting the alignment for the Tile Pane
      tilePane.setTileAlignment(Pos.CENTER_LEFT);

      //Setting the preferred columns for the Tile Pane
      tilePane.setPrefRows(4);

      //Retrieving the observable list of the Tile Pane
      ObservableList<Node> list = tilePane.getChildren();

      //Adding the array of buttons to the pane
      list.addAll(buttons);

      //Creating a scene object
      Scene scene = new Scene(tilePane);

      //Setting title to the Stage
      stage.setTitle("Tile Pane Example");

      //Adding scene to the stage
      stage.setScene(scene);

      //Displaying the contents of the stage
      stage.show();
   }
   public static void main(String args[]){
      launch(args);
   }
}
