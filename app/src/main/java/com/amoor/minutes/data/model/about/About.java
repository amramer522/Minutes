
package com.amoor.minutes.data.model.about;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class About {

    @SerializedName("about us")
    @Expose
    private String aboutUs;

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

}
