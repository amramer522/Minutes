
package com.amoor.minutes.data.model.drivers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driverbu {

    @SerializedName("driver")
    @Expose
    private String driver;
    @SerializedName("driver_number")
    @Expose
    private String driverNumber;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("line")
    @Expose
    private String line;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("bus_capacity")
    @Expose
    private String busCapacity;
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("driver_photo")
    @Expose
    private String driverPhoto;
    @SerializedName("date")
    @Expose
    private String date;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBusCapacity() {
        return busCapacity;
    }

    public void setBusCapacity(String busCapacity) {
        this.busCapacity = busCapacity;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverPhoto() {
        return driverPhoto;
    }

    public void setDriverPhoto(String driverPhoto) {
        this.driverPhoto = driverPhoto;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
