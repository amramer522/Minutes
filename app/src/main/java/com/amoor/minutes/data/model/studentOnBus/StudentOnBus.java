
package com.amoor.minutes.data.model.studentOnBus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentOnBus {

    @SerializedName("student aboard")
    @Expose
    private String studentAboard;

    public String getStudentAboard() {
        return studentAboard;
    }

    public void setStudentAboard(String studentAboard) {
        this.studentAboard = studentAboard;
    }

}
