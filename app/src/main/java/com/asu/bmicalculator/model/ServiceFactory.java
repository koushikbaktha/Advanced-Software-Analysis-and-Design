package com.asu.bmicalculator.model;

public class ServiceFactory {
    public static ServiceInterface getService(String type){
        switch(type)
        {
            case("bmi"):
                return new BMIService();

            default:
                return null;
        }
    }
}
