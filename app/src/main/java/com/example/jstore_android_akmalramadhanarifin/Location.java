package com.example.jstore_android_akmalramadhanarifin;

public class Location {
    private int id;
    private String province;
    private String description;
    private String city;

    public Location(int id, String province, String description, String city) {
        this.id = id;
        this.province = province;
        this.description = description;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
