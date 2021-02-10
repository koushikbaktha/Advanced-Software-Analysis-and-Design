package com.asu.bmicalculator.entity;

import java.util.List;

public class BMIResponseEntity {
    private double bmi;
    private List<String> more;
    private String risk;

    public double getBmi() {
        return bmi;
    }

    public List<String> getMore() {
        return more;
    }

    public String getRisk() {
        return risk;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public void setMore(List<String> more) {
        this.more = more;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }
}
