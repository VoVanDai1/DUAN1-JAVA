package model;

import dao.ChuyenDeDAO;
import dao.NhanVienDAO;
import helper.DateHelper;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class KhoaHoc {
    private String maCD, ghiChu, maNV;
    private float hocPhi;
    private int thoiLuong, maKH;
    private Date ngayKG,ngayTao = DateHelper.now();
    private String tenCD, tenNV;
    
    public String getTenNV(){
        try {
            NhanVienDAO dao = new NhanVienDAO();
            NhanVien entity = dao.selectById(getMaNV());
            if (entity != null) {
                tenNV = entity.getHoTen();
            }
        } catch (Exception e) {
            System.out.println("KhoaHoc(getTenNV) - " + e);
        }
        return tenNV;
    }
    
    public String getTenCD(){
        try {
            ChuyenDeDAO dao = new ChuyenDeDAO();
            ChuyenDe entity = dao.selectById(getMaCD());
            if (entity != null) {
                tenCD = entity.getTenCD();
            }
        } catch (Exception e) {
            System.out.println("KhoaHoc(getTenCD) - " + e);
        }
        return tenCD;
    }

    public KhoaHoc() {
    }

    public KhoaHoc(String maCD, String ghiChu, String maNV, float hocPhi, int thoiLuong, int maKH, Date ngayKG) {
        this.maCD = maCD;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
        this.hocPhi = hocPhi;
        this.thoiLuong = thoiLuong;
        this.maKH = maKH;
        this.ngayKG = ngayKG;
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
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

    public float getHocPhi() {
        return hocPhi;
    }

    public void setHocPhi(float hocPhi) {
        this.hocPhi = hocPhi;
    }

    public int getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public Date getNgayKG() {
        return ngayKG;
    }

    public void setNgayKG(Date ngayKG) {
        this.ngayKG = ngayKG;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
}
