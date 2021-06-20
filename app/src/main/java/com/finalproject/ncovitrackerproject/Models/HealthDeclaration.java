package com.finalproject.ncovitrackerproject.Models;

public class HealthDeclaration {
    private String Ngay;
    private String Mota;
    private String ThoiGian;
    private int Hinh;
    private String toAddress;

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

    public HealthDeclaration(String ngay, String mota, String thoiGian, int hinh, String toAddress) {
        Ngay = ngay;
        Mota = mota;
        ThoiGian = thoiGian;
        Hinh = hinh;
        this.toAddress = toAddress;
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

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
}
