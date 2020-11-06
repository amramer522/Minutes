
package com.amoor.minutes.data.model.locationSend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationSend {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user access token")
    @Expose
    private String userAccessToken;
    @SerializedName("driver id")
    @Expose
    private String driverId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("date")
    @Expose
    private String date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserAccessToken() {
        return userAccessToken;
    }

    public void setUserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
