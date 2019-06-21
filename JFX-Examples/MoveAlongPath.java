import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.*;

public class MoveAlongPath extends Application {

  @Override
  public void start(final Stage stage) throws Exception {
    final Group group = new Group();
    final Scene scene = new Scene(group, 600, 400, Color.GHOSTWHITE);
    stage.setScene(scene);
    stage.setTitle("JavaFX 2 Animations");
    stage.show();
    final Circle circle = new Circle(40, 40, 15);
    circle.setFill(Color.DARKRED);

    Image silverMarble = new Image("marble1.png");
    ImageView view = new ImageView(silverMarble);

    final Path path = new Path();
    path.getElements().add(new MoveTo(50, 50));
    path.getElements().add(new LineTo(100, 100));
    //path.getElements().add(new CubicCurveTo(50, 50, 30, 250, 150, 250));
    path.setOpacity(0.5);

    group.getChildren().add(path);
    group.getChildren().add(view);
    final PathTransition pathTransition = new PathTransition();

    pathTransition.setDuration(Duration.seconds(1.0));
    pathTransition.setDelay(Duration.seconds(.5));
    pathTransition.setPath(path);
    pathTransition.setNode(view);
    //pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
    //pathTransition.setCycleCount(Timeline.INDEFINITE);
    //pathTransition.setAutoReverse(true);
    pathTransition.play();
  }

  public static void main(final String[] arguments) {
    Application.launch(arguments);
  }
}
