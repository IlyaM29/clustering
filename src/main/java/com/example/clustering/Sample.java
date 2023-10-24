package com.example.clustering;

import com.example.clustering.dbscan.DBSCANClusterer;
import com.example.clustering.dbscan.DBSCANClusteringException;
import com.example.clustering.dbscan.DistanceMetricPoints;
import com.example.clustering.dbscan.Point;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sample {
    @FXML
    public AnchorPane box;
    @FXML
    public TextField numPoints;
    @FXML
    public TextField clusters;
    @FXML
    public TextField numPoints2;
    @FXML
    public TextField minItem;
    @FXML
    public TextField maxDist;

    @FXML
    public void kmeansRefresh(ActionEvent actionEvent) {

        box.getChildren().clear();

        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        ScatterChart<Number,Number> sc = new ScatterChart<>(x,y);
        sc.setTitle("K-means");

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

    @FXML
    public void dbscanRefresh(ActionEvent actionEvent) {

        box.getChildren().clear();

        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        ScatterChart<Number,Number> sc = new ScatterChart<>(x,y);
        sc.setTitle("DBSCAN");

        List<List<Point>> points = dbscan();

        for (List<Point> p: points) {
            XYChart.Series series = new XYChart.Series();
            ObservableList<XYChart.Data> dates = FXCollections.observableArrayList();
            for (Point point: p) {
                dates.add(new XYChart.Data(point.getX(), point.getY()));
            }
            series.setData(dates);
            sc.getData().add(series);
        }

        box.getChildren().add(sc);
    }

    private List<List<Point>> dbscan() {
        List<Point> points = getListPoint(Integer.parseInt(numPoints2.getText()));

        List<List<Point>> list;

        try {
            DBSCANClusterer clusterer = new DBSCANClusterer<>(points,
                    Integer.parseInt(minItem.getText()),
                    Double.parseDouble(maxDist.getText()),
                    new DistanceMetricPoints());
            list = clusterer.performClustering();
        } catch (DBSCANClusteringException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private Map<Centroid, List<Record>> getClusters() {
        return KMeans.fit(getListRecords(Integer.parseInt(numPoints.getText())), Integer.parseInt(clusters.getText()), new EuclideanDistance(), 1000);
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

    private List<Point> getListPoint(int k) {
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < k/4; i++) {
            Point point = new Point(Math.random() * 30 + 10, Math.random() * 30 + 10);
            list.add(point);
        }
        for (int i = 0; i < k/4; i++) {
            Point point = new Point(Math.random() * 30 + 60, Math.random() * 30 + 60);
            list.add(point);
        }
        for (int i = 0; i < k/4; i++) {
            Point point = new Point(Math.random() * 30 + 10, Math.random() * 30 + 60);
            list.add(point);
        }
        for (int i = 0; i < k/4; i++) {
            Point point = new Point(Math.random() * 30 + 60, Math.random() * 30 + 10);
            list.add(point);
        }
        return list;
    }
}