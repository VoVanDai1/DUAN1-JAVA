package model;

import helper.DateHelper;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class NguoiHoc {

    private String maNH, hoTen, dienThoai, email, ghiChu, maNV;
    private Date ngaySinh, ngayDK = DateHelper.now();
    private boolean gioiTinh;
    private byte[] hinh;

    public NguoiHoc() {
    }

    public NguoiHoc(String maNH, String hoTen, String dienThoai, byte[] hinh, String email, String ghiChu, String maNV, Date ngaySinh, Boolean gioiTinh) {
        this.maNH = maNH;
        this.hoTen = hoTen;
        this.dienThoai = dienThoai;
        this.hinh = hinh;
        this.email = email;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Date getNgayDK() {
        return ngayDK;
    }

    public void setNgayDK(Date ngayDK) {
        this.ngayDK = ngayDK;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}
