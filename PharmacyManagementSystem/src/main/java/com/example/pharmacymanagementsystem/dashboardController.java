package com.example.pharmacymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class dashboardController implements Initializable {

    @FXML
    private Button addMedicinesBtn;

    @FXML
    private Button addMedicines_addBtn;

    @FXML
    private TextField addMedicines_brandName;

    @FXML
    private Button addMedicines_clearBtn;

    @FXML
    private Button addMedicines_deleteBtn;

    @FXML
    private AnchorPane addMedicines_form;

    @FXML
    private TextField addMedicines_medicineID;

    @FXML
    private TextField addMedicines_price;

    @FXML
    private TextField addMedicines_productName;

    @FXML
    private TextField addMedicines_search;

    @FXML
    private ComboBox<?> addMedicines_status;

    @FXML
    private TableView<medicineData> addMedicines_table;

    @FXML
    private TableColumn<medicineData, String> addMedicines_table_brandName;

    @FXML
    private TableColumn<medicineData, String> addMedicines_table_date;

    @FXML
    private TableColumn<medicineData, String> addMedicines_table_medicineID;

    @FXML
    private TableColumn<medicineData, String> addMedicines_table_price;

    @FXML
    private TableColumn<medicineData, String> addMedicines_table_productName;

    @FXML
    private TableColumn<medicineData, String> addMedicines_table_status;

    @FXML
    private TableColumn<medicineData, String> addMedicines_table_type;

    @FXML
    private ComboBox<?> addMedicines_type;

    @FXML
    private Button addMedicines_updateBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_totalCustomers;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private Label dashboard_viewMedicines;

    @FXML
    private Button logout;

    @FXML
    private ComboBox<?> puchase_type;

    @FXML
    private Button purchaseBtn;

    @FXML
    private Button purchase_addBtn;

    @FXML
    private TextField purchase_amount;

    @FXML
    private Label purchase_balance;

    @FXML
    private ComboBox<?> purchase_brand;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private ComboBox<?> purchase_medicineID;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private ComboBox<?> purchase_productName;

    @FXML
    private TableView<?> purchase_tableView;

    @FXML
    private TableColumn<?, ?> purchase_table_brand;

    @FXML
    private TableColumn<?, ?> purchase_table_medicineID;

    @FXML
    private TableColumn<?, ?> purchase_table_price;

    @FXML
    private TableColumn<?, ?> purchase_table_pruductName;

    @FXML
    private TableColumn<?, ?> purchase_table_quantity;

    @FXML
    private TableColumn<?, ?> purchase_table_type;

    @FXML
    private Label purchase_total;

    @FXML
    private Label username;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void addMedicinesAdd(){
        String sql = "INSERT INTO medicine (medicine_id, brand, productName, type, status, price, date) VALUES (?,?,?,?,?,?,?)";

        connect = Database.connectDb();

        try{

            Alert alert;

            if (addMedicines_medicineID.getText().isEmpty() || addMedicines_brandName.getText().isEmpty()
                || addMedicines_productName.getText().isEmpty()
                || addMedicines_type.getSelectionModel().getSelectedItem() == null
                || addMedicines_status.getSelectionModel().getSelectedItem() == null
                || addMedicines_price.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {

                String checkData = "SELECT medicine_id FROM medicine WHERE medicine_id = '"+
                        addMedicines_medicineID.getText() +"'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Medicine ID: " + addMedicines_medicineID.getText() + " already exists!");
                    alert.showAndWait();
                }else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addMedicines_medicineID.getText());
                    prepare.setString(2, addMedicines_brandName.getText());
                    prepare.setString(3, addMedicines_productName.getText());
                    prepare.setString(4, (String) addMedicines_type.getSelectionModel().getSelectedItem());
                    prepare.setString(5, (String) addMedicines_status.getSelectionModel().getSelectedItem());
                    prepare.setString(6, addMedicines_price.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(7, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added");
                    alert.showAndWait();

                    addMedicineShowListData();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addMedicineUpdate(){
        String sql = "UPDATE medicine SET brand = '" + addMedicines_brandName.getText() + "', productName = '" +
                addMedicines_productName.getText() + "', type = '" +
                addMedicines_type.getSelectionModel().getSelectedItem() + "', status = '" +
                addMedicines_status.getSelectionModel().getSelectedItem() + "', price = '" +
                addMedicines_price.getText() + "' WHERE medicine_id = '" +
                addMedicines_medicineID.getText() + "'";

        connect = Database.connectDb();

        try {
            Alert alert;

            if (addMedicines_medicineID.getText().isEmpty() || addMedicines_brandName.getText().isEmpty()
                    || addMedicines_productName.getText().isEmpty()
                    || addMedicines_type.getSelectionModel().getSelectedItem() == null
                    || addMedicines_status.getSelectionModel().getSelectedItem() == null
                    || addMedicines_price.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update this medicine?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    addMedicineShowListData();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void addMedicineDelete(){
        String sql = "DELETE FROM medicine WHERE medicine_id = '" + addMedicines_medicineID.getText() + "'";
        connect = Database.connectDb();
        try{
            Alert alert;

            if (addMedicines_medicineID.getText().isEmpty() || addMedicines_brandName.getText().isEmpty()
                    || addMedicines_productName.getText().isEmpty()
                    || addMedicines_type.getSelectionModel().getSelectedItem() == null
                    || addMedicines_status.getSelectionModel().getSelectedItem() == null
                    || addMedicines_price.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this medicine?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    addMedicineShowListData();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private String[] addMedicineListT = {"Hydrochodone", "Antibiotics", "Metformin", "Lorsartan", "Albuterol"};
    public void addMedicineListType(){
        List<String> listT = new ArrayList<>();

        for (String data: addMedicineListT){
            listT.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listT);
        addMedicines_type.setItems(listData);
    }

    private String[] addMedicineStatus = {"Available", "Not Available"};
    public void addMedicineListStatus(){
        List<String> listS = new ArrayList<>();

        for (String data: addMedicineStatus){
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        addMedicines_status.setItems(listData);
    }

    public ObservableList<medicineData> addMedicinesListData(){
        ObservableList<medicineData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM medicine";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            medicineData medData;

            while(result.next()){
                medData = new medicineData(result.getInt("medicine_id"), result.getString("brand")
                        , result.getString("productName"), result.getString("type")
                        , result.getString("status"), result.getDouble("price")
                        , result.getDate("date"));

                listData.add(medData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<medicineData> addMedicineList;
    public void addMedicineShowListData(){
        addMedicineList = addMedicinesListData();

        addMedicines_table_medicineID.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        addMedicines_table_brandName.setCellValueFactory(new PropertyValueFactory<>("brand"));
        addMedicines_table_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addMedicines_table_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        addMedicines_table_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addMedicines_table_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        addMedicines_table_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addMedicines_table.setItems(addMedicineList);
    }

//    public void addMedicineSearch(){
//        FilteredList<medicineData> filter = new FilteredList<>(addMedicineList, e->true);
//        addMedicines_search.textProperty().addListener(Observable, oldValue, newValue) -> {
//            filter.setPredicate(predicateMedicineData -> {
//                if (newValue == null || newValue.isEmpty()){
//                    return true;
//                }
//                String searchKey = newValue.toLowerCase();
//                return false;
//            });
//        });
//    }

    public void addMedicineSelect(){
        medicineData medData = addMedicines_table.getSelectionModel().getSelectedItem();
        int num = addMedicines_table.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addMedicines_medicineID.setText(String.valueOf(medData.getMedicineId()));
        addMedicines_brandName.setText(medData.getBrand());
        addMedicines_productName.setText(medData.getProductName());
        addMedicines_price.setText(String.valueOf(medData.getPrice()));
    }

    public void purchaseAdd(){
        String sql = "INSERT INTO";
    }

    public void purchaseType(){
        String sql = "SELECT type FROM medicine WHERE status = 'Available'";

        connect = Database.connectDb();

        try {
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                listData.add(result.getString("type"));
            }
            puchase_type.setItems(listData);
            purchaseMedicineID();
        }catch (Exception e){e.printStackTrace();}
    }

    public void purchaseMedicineID(){
        String sql = "SELECT * FROM medicine WHERE type = '" + puchase_type.getSelectionModel().getSelectedItem()+ "'";

        connect = Database.connectDb();

        try{
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                listData.add(result.getString("medicine_id"));
            }
            purchase_medicineID.setItems(listData);
            purchaseMedicineBrand();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void purchaseMedicineBrand(){
        String sql = "SELECT * FROM medicine WHERE medicine_id = '" + purchase_medicineID.getSelectionModel().getSelectedItem()+ "'";

        connect = Database.connectDb();

        try{
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                listData.add(result.getString("brand"));
            }
            purchase_brand.setItems(listData);
            purchaseProductName();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void purchaseProductName(){
        String sql = "SELECT * FROM medicine WHERE brand = '" +
                purchase_brand.getSelectionModel().getSelectedItem()+ "'";

        connect = Database.connectDb();

        try{
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                listData.add(result.getString("productName"));
            }
            purchase_productName.setItems(listData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event){
        if (event.getSource() == dashboardBtn){

            dashboard_form.setVisible(true);
            addMedicines_form.setVisible(false);
            purchase_form.setVisible(false);

        } else if (event.getSource() == addMedicinesBtn) {

            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(true);
            purchase_form.setVisible(false);

            addMedicineShowListData();
            addMedicineListStatus();
            addMedicineListType();

        } else if (event.getSource() == purchaseBtn) {

            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(false);
            purchase_form.setVisible(true);

            purchaseType();
            purchaseMedicineID();
            purchaseMedicineBrand();
            purchaseProductName();
        }
    }

    public void displayUsername(){
        String user = getData.username;
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));
    }

    public void logout(){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Maessage");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        displayUsername();
//        addMedicineShowListData();
//        addMedicineListStatus();
//        addMedicineListType();
//
//        purchaseType();
//        purchaseMedicineID();
//        purchaseMedicineBrand();
//        purchaseProductName();
    }
}
