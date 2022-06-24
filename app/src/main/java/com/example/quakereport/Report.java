package com.example.quakereport;

public class Report {
    private double mMagnitude;
    private String mLocation;
    private int mTime;
     public Report(double magnitude,String location, int time){
         mMagnitude=magnitude;
         mLocation=location;
         mTime=time;
     }
     public double getMagnitude(){
         return mMagnitude;
     }
     public String getLocation(){
         return mLocation;
     }
     public int getTime(){
         return mTime;
     }

}
