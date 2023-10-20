package com.example.clustering;

import com.example.clustering.kmeans.Centroid;
import com.example.clustering.kmeans.EuclideanDistance;
import com.example.clustering.kmeans.KMeans;
import com.example.clustering.kmeans.Record;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("JavaFX Chart (Series)");

        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        ScatterChart<Number,Number> sc = new ScatterChart<>(x,y);
        sc.setTitle("Кластеризация");

        Map<Centroid, List<Record>> map = getClusters();

        XYChart.Series seriesCenter = new XYChart.Series();
        ObservableList<XYChart.Data> datasCenter = FXCollections.observableArrayList();

        for (Centroid centroid: map.keySet()) {
            XYChart.Series series = new XYChart.Series();
            ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();

            List<Record> records = map.get(centroid);

            for (Record record: records) {
                datas.add(new XYChart.Data(record.getFeatures().get("x"), record.getFeatures().get("y")));
            }

            datasCenter.add(new XYChart.Data(centroid.getCoordinates().get("x"), centroid.getCoordinates().get("y")));

            series.setData(datas);
            sc.getData().add(series);
        }

        seriesCenter.setData(datasCenter);
        sc.getData().add(seriesCenter);

        Scene scene = new Scene(sc, 600,600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private static Map<Centroid, List<Record>> getClusters() {
        return KMeans.fit(getListRecords(1000), 7, new EuclideanDistance(), 1000);
    }

    private static List<Record> getListRecords(int k) {
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