package com.oceloy.model;

import javafx.scene.layout.HBox;

public class CalibrationTableModelFinish {
    String ticket, date, vehicle, status, product, tonase;
    HBox action;

    public CalibrationTableModelFinish(String ticket, String date, String vehicle, String status, String product,
            String tonase, HBox action) {
        this.ticket = ticket;
        this.date = date;
        this.vehicle = vehicle;
        this.status = status;
        this.product = product;
        this.tonase = tonase;
        this.action = action;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTonase() {
        return tonase;
    }

    public void setTonase(String tonase) {
        this.tonase = tonase;
    }

    public HBox getAction() {
        return action;
    }

    public void setAction(HBox action) {
        this.action = action;
    }
}
