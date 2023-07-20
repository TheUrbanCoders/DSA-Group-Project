package com.example.pharmacymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HelloController {
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    private PreparedStatement prepare;
    private Connection connect;
    private ResultSet result;

    public void loginAdmin(){
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        connect = Database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, usernameField.getText());
            prepare.setString(2, passwordField.getText());

            result = prepare.executeQuery();

            Alert alert;

            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all empty fields");
                alert.showAndWait();
            }
            else {
                if (result.next()){
                    getData.username = usernameField.getText();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully logged in");
                    alert.showAndWait();

                    loginButton.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("dashborad.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username and/or password");
                    alert.showAndWait();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}