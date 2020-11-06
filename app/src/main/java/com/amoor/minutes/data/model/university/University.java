
package com.amoor.minutes.data.model.university;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class University {

    @SerializedName("university")
    @Expose
    private String university;

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

}
