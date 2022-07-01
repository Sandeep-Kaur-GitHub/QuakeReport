package com.example.quakereport;

public class Report {
    private double mMagnitude;
    private String mLocation;
    private Long mTime;
    String mUrl;
     public Report(double magnitude,String location, Long time , String url){
         mMagnitude=magnitude;
         mLocation=location;
         mTime=time;
         mUrl=url;
     }
     public double getMagnitude(){
         return mMagnitude;
     }
     public String getLocation(){
         return mLocation;
     }
     public Long getTime(){
         return mTime;
     }
     public String getUrl(){return mUrl;}

}
