package com.amoor.minutes.data.rest;

import com.amoor.minutes.data.model.about.About;
import com.amoor.minutes.data.model.busLine.BusLine;
import com.amoor.minutes.data.model.drivers.Driverbu;
import com.amoor.minutes.data.model.feedback.Feedback;
import com.amoor.minutes.data.model.links.Link;
import com.amoor.minutes.data.model.locationSend.LocationSend;
import com.amoor.minutes.data.model.login.Login;
import com.amoor.minutes.data.model.profile.Profile;
import com.amoor.minutes.data.model.register.User;
import com.amoor.minutes.data.model.studentOnBus.StudentOnBus;
import com.amoor.minutes.data.model.tweet.Tweet;
import com.amoor.minutes.data.model.university.University;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiServices {


    // Regiseter
    @POST("register.php")
    @FormUrlEncoded
    Call<User> register(@Field("s_id") String student_id,
                        @Field("name") String userName,
                        @Field("password") String password,
                        @Field("password_confirmation") String confirm_password,
                        @Field("mob") String mobile_number,
                        @Field("email") String email,
                        @Field("address") String home_address,
                        @Field("line") String bus_line,
                        @Field("university") String university,
                        @Field("college") String faculty,
                        @Field("level") String level);


    // Login
    @POST("login.php")
    @FormUrlEncoded
    Call<Login> login(@Field("s_id") String studentId,
                      @Field("password") String password);


    // Feedback
    @POST("feedbak.php")
    @FormUrlEncoded
    Call<Feedback> sendFeedback(
            @Field("name") String name,
            @Field("s_id") String s_id,
            @Field("mesg") String mesg
    );

    //get tweets
    @POST("get_tweets.php")
    @FormUrlEncoded
    Call<List<Tweet>> getTweets(
            @Field("line") String line
    );


    // getProfileData
    @POST("profile.php")
    @FormUrlEncoded
    Call<Profile> getProfileData(@Field("access_token") String access_token);


    // send Student Location
    @POST("location_send.php")
    @FormUrlEncoded
    Call<LocationSend> sendStudentLocation(
            @Field("user_access_token") String user_access_token,
            @Field("driver_id") String driver_id,
            @Field("time") String time,
            @Field("note") String note,
            @Field("lang") double langt,
            @Field("long") double longt

    );


    // get Buses
    @POST("bus_data.php")
    @FormUrlEncoded
    Call<List<Driverbu>> getLineBuses(
            @Field("line") String line
    );

    //get links
    @POST("bus_sett.php")
    @FormUrlEncoded
    Call<List<Link>> getLinks(
            @Field("university") String university
    );


    //get bus lines
    @GET("all_route.php")
    Call<List<BusLine>> getBusLines();


    //get bus lines
    @GET("university.php")
    Call<List<University>> getUniversites();


    //get links
    @POST("count.php")
    @FormUrlEncoded
    Call<StudentOnBus> getNumOfStudentsOnBus(
            @Field("driver_id") String driver_id
    );


    //get about details
    @GET("about_us.php")
    Call<About> getAboutUsDetails();


}
