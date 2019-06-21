import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.Node;


public class LineChartExample extends Application {

    @Override public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Month");
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");

        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
        series1.setName("Portfolio 1");

        series1.getData().add(new XYChart.Data<String, Number>("Jan", 23));
        series1.getData().add(new XYChart.Data<String, Number>("Feb", 14));
        series1.getData().add(new XYChart.Data<String, Number>("Mar", 15));
        series1.getData().add(new XYChart.Data<String, Number>("Apr", 24));
        series1.getData().add(new XYChart.Data<String, Number>("May", 34));
        series1.getData().add(new XYChart.Data<String, Number>("Jun", 36));
        series1.getData().add(new XYChart.Data<String, Number>("Jul", 22));
        series1.getData().add(new XYChart.Data<String, Number>("Aug", 45));
        series1.getData().add(new XYChart.Data<String, Number>("Sep", 43));
        series1.getData().add(new XYChart.Data<String, Number>("Oct", 17));
        series1.getData().add(new XYChart.Data<String, Number>("Nov", 29));
        series1.getData().add(new XYChart.Data<String, Number>("Dec", 25));

        XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
        series2.setName("Portfolio 2");
        series2.getData().add(new XYChart.Data<String, Number>("Jan", 33));
        series2.getData().add(new XYChart.Data<String, Number>("Feb", 34));
        series2.getData().add(new XYChart.Data<String, Number>("Mar", 25));
        series2.getData().add(new XYChart.Data<String, Number>("Apr", 44));
        series2.getData().add(new XYChart.Data<String, Number>("May", 39));
        series2.getData().add(new XYChart.Data<String, Number>("Jun", 16));
        series2.getData().add(new XYChart.Data<String, Number>("Jul", 55));
        series2.getData().add(new XYChart.Data<String, Number>("Aug", 54));
        series2.getData().add(new XYChart.Data<String, Number>("Sep", 48));
        series2.getData().add(new XYChart.Data<String, Number>("Oct", 27));
        series2.getData().add(new XYChart.Data<String, Number>("Nov", 37));
        series2.getData().add(new XYChart.Data<String, Number>("Dec", 29));

        XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
        series3.setName("Portfolio 3");
        series3.getData().add(new XYChart.Data<String, Number>("Jan", 44));
        series3.getData().add(new XYChart.Data<String, Number>("Feb", 35));
        series3.getData().add(new XYChart.Data<String, Number>("Mar", 36));
        series3.getData().add(new XYChart.Data<String, Number>("Apr", 33));
        series3.getData().add(new XYChart.Data<String, Number>("May", 31));
        series3.getData().add(new XYChart.Data<String, Number>("Jun", 26));
        series3.getData().add(new XYChart.Data<String, Number>("Jul", 22));
        series3.getData().add(new XYChart.Data<String, Number>("Aug", 25));
        series3.getData().add(new XYChart.Data<String, Number>("Sep", 43));
        series3.getData().add(new XYChart.Data<String, Number>("Oct", 44));
        series3.getData().add(new XYChart.Data<String, Number>("Nov", 45));
        series3.getData().add(new XYChart.Data<String, Number>("Dec", 44));

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series1, series2, series3);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
