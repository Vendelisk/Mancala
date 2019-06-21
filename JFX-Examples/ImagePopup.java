import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.control.Button;
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
/**
 * Example to show an image popup with an
 * autoplaying audio file.
 * @author Mr. Davis
 */
public class ImagePopup extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        Button btn = new Button();
        btn.setText("Click to show Image Popup");
        btn.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            imagePopupWindowShow();
          }
        });

        BorderPane pane = new BorderPane();
        pane.setCenter(btn);

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Popup Example");
        primaryStage.show();
    }

    /**
     * This method will be the image popup.
     */
    public void imagePopupWindowShow() {
        // All of our necessary variables
        File imageFile;
        File audioFile;
        Image image;
        ImageView imageView;
        BorderPane pane;
        Scene scene;
        Stage stage;

        // The path to your image can be a URL,
        // or it can be a directory on your computer.
        // If the picture is on your computer, type the path
        // likes so:
        //     C:\\Path\\To\\Image.jpg
        // If you have a Mac, it's like this:
        //     /Path/To/Image.jpg
        // Replace the path with the one on your computer
        image = new Image("marble1.png");
        imageView = new ImageView(image);

        // Our image will sit in the middle of our popup.
        pane = new BorderPane();
        pane.setCenter(imageView);
        scene = new Scene(pane);

        // Create the actual window and display it.
        stage = new Stage();
        stage.setScene(scene);
        // Without this, the audio won't stop!
        stage.setOnCloseRequest(
            e -> {
                e.consume();
                stage.close();
            }
        );
        stage.showAndWait();
    }
}
