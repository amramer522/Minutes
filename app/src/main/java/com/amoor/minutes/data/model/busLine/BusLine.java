
package com.amoor.minutes.data.model.busLine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusLine {

    @SerializedName("route")
    @Expose
    private String route;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

}
