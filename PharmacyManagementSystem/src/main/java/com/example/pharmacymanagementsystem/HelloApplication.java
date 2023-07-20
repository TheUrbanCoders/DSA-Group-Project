package com.example.pharmacymanagementsystem;

/* Group 26: CHECOS
    10910921
    10917439
    10893933
    10862078
    10904093
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pharmacy Management App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}