package com.egci428.assignment2;

/**
 * Created by Mek on 27/11/2559.
 */
public class Profile {
    private long id;
    private String user;
    private String pass;
    private String latitude;
    private String longitude;

    public long getId(){ return id;}

    public void setId(long id) {
        this.id = id;
    }

    public String getUser(){ return  user; }

    public String getPass(){ return  pass; }

    public String getLatitude(){ return  latitude; }

    public String getLongitude() { return longitude; }

    public Profile(String u, String p, String lat, String longi){
        this.user = u;
        this.pass = p;
        this.latitude = lat;
        this.longitude = longi;
    }
}
