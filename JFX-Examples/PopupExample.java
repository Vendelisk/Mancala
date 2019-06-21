import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.text.*;

public class PopupExample extends Application {
  public static void main(String[] args) { launch(args); }

  @Override public void start(final Stage primaryStage) {
    primaryStage.setTitle("Popup Example");
    final Popup popup = new Popup(); popup.setX(300); popup.setY(200);

    Text text = new Text("THIS IS TEXT");

    popup.getContent().addAll(text);

    text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 80));
    text.setFill(Color.PURPLE);

    Button show = new Button("Show");
    show.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        popup.show(primaryStage);
      }
    });

    Button hide = new Button("Hide");
    hide.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        popup.hide();
      }
    });

    HBox layout = new HBox(10);
    layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
    layout.getChildren().addAll(show, hide);
    primaryStage.setScene(new Scene(layout));
    primaryStage.show();
  }
}
