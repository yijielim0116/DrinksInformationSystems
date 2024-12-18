package org.example.assignment2demo;

public class Ingredients {

    private String ingname;
    private String ingdecription;
    private double ABV;

    public Ingredients(String ingname, String ingdecription, double ABV) {
        this.ingname = ingname;
        this.ingdecription = ingdecription;
        this.ABV = ABV;
    }

    public String getIngname() {
        return ingname;
    }

    public void setIngname(String ingname) {
        this.ingname = ingname;
    }

    public String getIngdecription() {
        return ingdecription;
    }

    public void setIngdecription(String ingdecription) {
        this.ingdecription = ingdecription;
    }

    public double getABV() {
        return ABV;
    }

    public void setABV(double ABV) {
        this.ABV = ABV;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "ingname='" + ingname + '\'' +
                ", ingdecription='" + ingdecription + '\'' +
                ", ABV=" + ABV +
                '}';
    }
}
