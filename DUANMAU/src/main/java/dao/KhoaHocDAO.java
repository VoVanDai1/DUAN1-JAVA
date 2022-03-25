package dao;

import helper.DatabaseHelper;
import helper.DateHelper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.KhoaHoc;

/**
 *
 * @author ACER
 */
public class KhoaHocDAO extends EntityDAO<KhoaHoc, Object> {

    /**
     * Tạo các giá trị là hằng
     *
     * @param final là khai báo hằng
     */
    private final String INSERT = "INSERT INTO [dbo].[KHOAHOC] ([MaCD],[HocPhi],[ThoiLuong],[NgayKG],[GhiChu],[MaNV],[NgayTao]) VALUES (?,?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE [dbo].[KHOAHOC] SET [MaCD] = ?,[HocPhi] = ?,[ThoiLuong] = ?,[NgayKG] = ?,[GhiChu] = ?,[MaNV] = ?,[NgayTao] = ? WHERE [MaKH] = ?";
    private final String DELETE = "DELETE FROM [dbo].[KHOAHOC] WHERE [MaKH] = ?";
    private final String SELECT_ALL = "SELECT * FROM [dbo].[KHOAHOC]";
    private final String SELECT_BY_PK = "SELECT * FROM [dbo].[KHOAHOC] WHERE [MaKH] = ?";

    /**
     * Tạo các giá trị sử dụng chung trong class
     *
     * @param final là khai báo hằng
     * @param Connection giá trị kết nối CSDL
     * @param PreparedStatement thực hiện truy vấn tham số
     * @param Blob lưu trữ dưới dạng nhị phân
     */
    private List<KhoaHoc> list = new ArrayList<>();
    private Connection conn = null;
    private PreparedStatement pstmt = null;

    /**
     * Các phương thức được kế thừa từ EntityDAO
     *
     * @param rs
     * @return trả về
     */
    @Override
    public KhoaHoc readFromResultSet(ResultSet rs) {
        try {
            KhoaHoc entity = new KhoaHoc();
            entity.setMaKH(rs.getInt("MaKH"));
            entity.setMaCD(rs.getString("MaCD"));
            entity.setHocPhi(rs.getFloat("HocPhi"));
            entity.setThoiLuong(rs.getInt("ThoiLuong"));
            entity.setNgayKG(rs.getDate("NgayKG"));
            entity.setGhiChu(rs.getString("GhiChu"));
            entity.setMaNV(rs.getString("MaNV"));
            entity.setNgayTao(rs.getDate("NgayTao"));
            return entity;
        } catch (SQLException e) {
            System.out.println("-> ReadFromResultSet (KhoaHocDAO) - " + e);
        }
        return null;
    }

    @Override
    public int insert(KhoaHoc entity) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(INSERT);
            pstmt.setString(1, entity.getMaCD());
            pstmt.setFloat(2, entity.getHocPhi());
            pstmt.setInt(3, entity.getThoiLuong());
            pstmt.setDate(4, (Date) DateHelper.toDate(entity.getNgayKG()));
            pstmt.setString(5, entity.getGhiChu());
            pstmt.setString(6, entity.getMaNV());
            pstmt.setDate(7, (Date) DateHelper.toDate(entity.getNgayTao()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> Insert (KhoaHocDAO) - " + e);
            return -1;
        }
        return 1;
    }

    @Override
    public int update(KhoaHoc entity) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(UPDATE);
            pstmt.setString(1, entity.getMaCD());
            pstmt.setFloat(2, entity.getHocPhi());
            pstmt.setInt(3, entity.getThoiLuong());
            pstmt.setDate(4, (Date) DateHelper.toDate(entity.getNgayKG()));
            pstmt.setString(5, entity.getGhiChu());
            pstmt.setString(6, entity.getMaNV());
            pstmt.setDate(7, (Date) DateHelper.toDate(entity.getNgayTao()));
            pstmt.setInt(8, entity.getMaKH());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> Update (KhoaHocDAO) - " + e);
        }
        return 1;
    }

    @Override
    public int delete(Object primarykey) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(DELETE);
            pstmt.setString(1, (String) primarykey);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> Delete (KhoaHocDAO) - " + e);
            return -1;
        }
        return 1;
    }

    @Override
    public List<KhoaHoc> selectAll() {
        list.clear();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (Exception e) {
            System.out.println("-> Select_All (KhoaHocDAO) - " + e);
            return list = null;
        }
        return list;
    }

    @Override
    public KhoaHoc selectById(Object primarykey) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_BY_PK);
            pstmt.setInt(1, (int) primarykey);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return readFromResultSet(rs);
            }
        } catch (Exception e) {
            System.out.println("-> Select_By_PK (KhoaHocDAO) - " + e);
        }
        return null;
    }

    @Override
    public KhoaHoc getEnityByPossition(Object index) {
        int id = Integer.parseInt((String) index);
        if (id >= 0 && id < list.size()) {
            return list.get(id);
        }
        return null;
    }

    @Override
    public List<KhoaHoc> selectByFk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<KhoaHoc> selectByString(Object string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public KhoaHoc selectByKey(Object string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
