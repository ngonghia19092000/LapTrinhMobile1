package com.finalproject.ncovitrackerproject.Models;

public class Member {
    private String fullName;
    private String gender;
    private String address;
    private String birtthday;
    private String cmnd;
    private String email;

    public Member() {

    }

    public Member(String fullName, String gender, String address, String birtthday, String cmnd, String email) {
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.birtthday = birtthday;
        this.cmnd = cmnd;
        this.email = email;
    }

    public Member(String fullName, String gender, String address, String birtthday, String cmnd) {
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.birtthday = birtthday;
        this.cmnd = cmnd;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirtthday() {
        return birtthday;
    }

    public void setBirtthday(String birtthday) {
        this.birtthday = birtthday;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
