package com.example.clustering;

import com.example.clustering.kmeans.Centroid;
import com.example.clustering.kmeans.EuclideanDistance;
import com.example.clustering.kmeans.KMeans;
import com.example.clustering.kmeans.Record;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        primaryStage.setTitle("JavaFX Chart (Series)");

        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }
}