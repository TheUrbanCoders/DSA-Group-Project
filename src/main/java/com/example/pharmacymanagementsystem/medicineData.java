package com.example.pharmacymanagementsystem;

import java.sql.Date;

public class medicineData {
    private Integer medicineId;
    private String brand;
    private String productName;
    private String type;
    private String status;
    private Double price;
    private Date date;

    public medicineData(Integer medicineId, String brand, String productName, String type, String status, Double price, Date date){
        this.medicineId = medicineId;
        this.brand = brand;
        this.productName = productName;
        this.type = type;
        this.status = status;
        this.price = price;
        this.date = date;
    }

    public Integer getMedicineId(){
        return medicineId;
    }

    public String getBrand(){
        return brand;
    }

    public String getProductName(){
        return productName;
    }

    public String getType(){
        return type;
    }

    public String getStatus(){
        return status;
    }

    public Double getPrice(){
        return price;
    }

    public Date getDate(){
        return date;
    }
}
