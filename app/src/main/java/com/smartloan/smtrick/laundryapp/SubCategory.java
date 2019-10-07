package com.smartloan.smtrick.laundryapp;

public class SubCategory {

    public String maincatitem;
    public String subcatitem;

    public SubCategory() {
    }

    public SubCategory(String maincatitem, String subcatitem) {
        this.maincatitem = maincatitem;
        this.subcatitem = subcatitem;
    }

    public String getMaincatitem() {
        return maincatitem;
    }

    public void setMaincatitem(String maincatitem) {
        this.maincatitem = maincatitem;
    }

    public String getSubcatitem() {
        return subcatitem;
    }

    public void setSubcatitem(String subcatitem) {
        this.subcatitem = subcatitem;
    }
}
