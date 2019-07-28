package com.example.ewidencja;

public class Globals {
    private static Globals instance;

    private String driver;

    private Globals(){}

    public void setDriver(String driver){
        this.driver = driver;
    }

    public String getDriver(){return this.driver;}

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}
