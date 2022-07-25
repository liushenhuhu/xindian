package com.ruoyi.xindian.pdf.domain;

/**
 * @Author: han
 * @Date: 2022-07-24
 */
public class AdmissionCard {
    private String name;
    private String age;
    private String hospital;
    private String equipment;
    private String image;

    public AdmissionCard() {
    }

    public AdmissionCard(String name, String age, String hospital, String equipment, String image) {
        this.name = name;
        this.age = age;
        this.hospital = hospital;
        this.equipment = equipment;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "table{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", hospital='" + hospital + '\'' +
                ", equipment='" + equipment + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
