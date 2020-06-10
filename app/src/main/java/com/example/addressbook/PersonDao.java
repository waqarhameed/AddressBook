package com.example.addressbook;

public class PersonDao {
    private String p_Name;
    private String address;
    private String address1;

    PersonDao(String p_name, String addr, String addr1, String ph, String ph1, String eml, String eml1, byte[] data) {
        this.p_Name = p_name;
        this.address = addr;
        this.address1 = addr1;
        this.image = data;

        this.ph_No = ph;
        this.ph_No1 = ph1;
        this.email = eml;
        this.email1 = eml1;
    }

    byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private byte[] image;

    String getP_Name() {
        return p_Name;
    }

    public void setP_Name(String p_Name) {
        this.p_Name = p_Name;
    }

    String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    String getPh_No() {
        return ph_No;
    }

    public void setPh_No(String ph_No) {
        this.ph_No = ph_No;
    }

    String getPh_No1() {
        return ph_No1;
    }

    public void setPh_No1(String ph_No1) {
        this.ph_No1 = ph_No1;
    }

    String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    private String ph_No;
    private String ph_No1;
    private String email;
    private String email1;

}
