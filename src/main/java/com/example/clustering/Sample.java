package com.example.clustering;

import com.example.clustering.kmeans.Centroid;
import com.example.clustering.kmeans.EuclideanDistance;
import com.example.clustering.kmeans.KMeans;
import com.example.clustering.kmeans.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sample {
    @FXML
    public AnchorPane box;
    @FXML
    public TextField points;
    @FXML
    public TextField clusters;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void refresh(ActionEvent actionEvent) {

        box.getChildren().clear();

        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        ScatterChart<Number,Number> sc = new ScatterChart<>(x,y);
        sc.setTitle("Кластеризация");

        Map<Centroid, List<Record>> map = getClusters();

        XYChart.Series seriesCenter = new XYChart.Series();
        ObservableList<XYChart.Data> datesCenter = FXCollections.observableArrayList();

        for (Centroid centroid: map.keySet()) {
            XYChart.Series series = new XYChart.Series();
            ObservableList<XYChart.Data> dates = FXCollections.observableArrayList();

            List<Record> records = map.get(centroid);

            for (Record record: records) {
                dates.add(new XYChart.Data(record.getFeatures().get("x"), record.getFeatures().get("y")));
            }

            datesCenter.add(new XYChart.Data(centroid.getCoordinates().get("x"), centroid.getCoordinates().get("y")));

            series.setData(dates);
            sc.getData().add(series);
        }

        seriesCenter.setData(datesCenter);
        sc.getData().add(seriesCenter);



        box.getChildren().add(sc);
    }

    private Map<Centroid, List<Record>> getClusters() {
        return KMeans.fit(getListRecords(Integer.parseInt(points.getText())), Integer.parseInt(clusters.getText()), new EuclideanDistance(), 1000);
    }

    private List<Record> getListRecords(int k) {
        List<Record> list = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            Map<String, Double> m = new HashMap<>();
            m.put("x", Math.random() * 90 + 10);
            m.put("y", Math.random() * 90 + 10);
            Record r = new Record(m);
            list.add(r);
        }
        return list;
    }
}