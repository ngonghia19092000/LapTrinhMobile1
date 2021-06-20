package com.finalproject.ncovitrackerproject.Models;

public class HealthDeclaration {
    private String Ngay;
    private String Mota;
    private String ThoiGian;
    private int Hinh;
    private String email;

    public HealthDeclaration() {
    }

    public HealthDeclaration(String ngay, String mota, String thoiGian) {
        Ngay = ngay;
        Mota = mota;
        ThoiGian = thoiGian;
    }

    public HealthDeclaration(String ngay, String mota, String thoiGian, int hinh) {
        Ngay = ngay;
        Mota = mota;
        ThoiGian = thoiGian;
        Hinh = hinh;
    }

    public HealthDeclaration(String email, String ngay, String mota, String thoiGian, int hinh) {
        Ngay = ngay;
        Mota = mota;
        ThoiGian = thoiGian;
        Hinh = hinh;
        email = email;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }

}