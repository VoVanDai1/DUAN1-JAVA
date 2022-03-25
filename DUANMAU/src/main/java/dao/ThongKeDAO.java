package dao;

import helper.DataValidator;
import helper.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class ThongKeDAO {

    /**
     * Tạo các giá trị sử dụng chung trong class
     *
     * @param final là khai báo hằng
     * @param Connection giá trị kết nối CSDL
     * @param PreparedStatement thực hiện truy vấn tham số
     */
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private final String SELECT_SP_NH = "EXEC [dbo].[sp_ThongKeNguoiHoc]";
    private final String SELECT_SP_THD = "EXEC [dbo].[sp_ThongKeDiem]";
    private final String SELECT_SP_DKH = "EXEC [dbo].[sp_BangDiem] ?";
    private final String SELECT_SP_DT = "EXEC [dbo].[sp_ThongKeDoanhThu] ?";
    private final String SELECT_NAM = "select distinct year(NgayKG) as nam from KhoaHoc order by year(NgayKG) desc";

    /*
    thống kê số người học của trung tâm theo từng năm
    return 1 <Object[]> list : Năm - số lượng - ngày người đầu tiên đk - ngày người cc đk
     */
    public List<Object[]> selectNguoiHoc() {
        List<Object[]> list = new ArrayList<>();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_SP_NH);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] model = {
                    rs.getInt("Nam"),
                    rs.getInt("SoLuong"),
                    rs.getDate("DauTien"),
                    rs.getDate("CuoiCung")
                };
                list.add(model);
            }
        } catch (Exception e) {
            System.out.println("-> selectNguoiHoc (ThongKeDAO) - " + e);
        }
        return list;
    }

    /*
    tổng hợp điểm của theo từng chuyên đề
    @return <Object[]> list : tên chuyên đề - số HV - điểm thấp nhất - điểm cao nhất - điểm trung bình
     */
    public List<Object[]> selectTongHopDiem() {
        List<Object[]> list = new ArrayList<>();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_SP_THD);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] model = {
                    rs.getString("ChuyenDe"),
                    rs.getInt("SoHV"),
                    rs.getDouble("ThapNhat"),
                    rs.getDouble("CaoNhat"),
                    rs.getDouble("TrungBinh")
                };
                list.add(model);
            }
        } catch (Exception e) {
            System.out.println("-> selectTongHopDiem (ThongKeDAO) - " + e);
        }
        return list;
    }

    /*
    tổng hợp điểm của theo khoá
    @return <Object[]> list : tên chuyên đề - số HV - điểm thấp nhất - điểm cao nhất - điểm trung bình
     */
    public List<Object[]> selectDiemKhoa(String maKH) {
        List<Object[]> list = new ArrayList<>();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_SP_DKH);
            pstmt.setString(1, maKH);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] model = {
                    rs.getString("MaNH"),
                    rs.getString("HoVaTen"),
                    rs.getFloat("Diem"),
                    DataValidator.getRank(rs.getFloat("Diem"))
                };
                list.add(model);
            }
        } catch (Exception e) {
            System.out.println("-> selectDiemKhoa (ThongKeDAO) - " + e);
        }
        return list;
    }

    /*
    tổng hợp điểm của theo khoá
    @return <Object[]> list : tên chuyên đề - số HV - điểm thấp nhất - điểm cao nhất - điểm trung bình
     */
    public List<Integer> selectNam() {
        List<Integer> list = new ArrayList<>();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_NAM);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int nam = rs.getInt(1);
                list.add(nam);
            }
        } catch (Exception e) {
            System.out.println("-> selectNam (ThongKeDAO) - " + e);
        }
        return list;
    }

    /*
    tổng hợp doanh thu theo năm
    @return <Object[]> list : tên chuyên đề - số HV - điểm thấp nhất - điểm cao nhất - điểm trung bình
     */
    public List<Object[]> selectDoanhThu(String nam) {
        List<Object[]> list = new ArrayList<>();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_SP_DT);
            pstmt.setString(1, nam);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] model = {
                    rs.getString("ChuyenDe"),
                    rs.getInt("SoKH"),
                    rs.getInt("SoHV"),
                    rs.getDouble("DoanhThu"),
                    rs.getDouble("ThapNhat"),
                    rs.getDouble("CaoNhat"),
                    rs.getDouble("TrungBinh")
                };
                list.add(model);
            }
        } catch (Exception e) {
            System.out.println("-> selectDoanhThu (ThongKeDAO) - " + e);
        }
        return list;
    }

}
