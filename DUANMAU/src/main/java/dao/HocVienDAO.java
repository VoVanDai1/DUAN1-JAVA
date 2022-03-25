package dao;

import helper.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.HocVien;

/**
 *
 * @author ACER
 */
public class HocVienDAO extends EntityDAO<HocVien, Object> {

    /**
     * Tạo các giá trị là hằng
     *
     * @param final là khai báo hằng
     */
    private final String INSERT = "INSERT INTO [dbo].[HOCVIEN] ([MaKH],[MaNH],[Diem]) VALUES (?,?,?)";
    private final String UPDATE = "UPDATE [dbo].[HOCVIEN] SET [MaKH] = ?,[Diem] = ? WHERE [MaNH] = ?";
    private final String DELETE = "DELETE FROM [dbo].[HOCVIEN] WHERE [MaNH] = ?";
    private final String SELECT_ALL = "SELECT * FROM [dbo].[HOCVIEN]";
    private final String SELECT_BY_PK = "SELECT * FROM [dbo].[HOCVIEN] WHERE [MaNH] = ?";
    private final String SELECT_BY_FK = "SELECT * FROM [dbo].[HOCVIEN] WHERE [MaKH] = ?";
    private final String SELECT_BY_HV_KH = "SELECT hv.*, nh.[HoVaTen] FROM [dbo].[HOCVIEN] hv "
            + "JOIN [dbo].[NGUOIHOC] nh ON nh.MaNH=hv.MaNH WHERE MaKH = ?";

    /**
     * Tạo các giá trị sử dụng chung trong class
     *
     * @param final là khai báo hằng
     * @param Connection giá trị kết nối CSDL
     * @param PreparedStatement thực hiện truy vấn tham số
     * @param Blob lưu trữ dưới dạng nhị phân
     */
    private List<HocVien> list = new ArrayList<>();
    private Connection conn = null;
    private PreparedStatement pstmt = null;

    /**
     * Các phương thức được kế thừa từ EntityDAO
     *
     * @param rs
     * @return
     */
    @Override
    public HocVien readFromResultSet(ResultSet rs) {
        try {
            HocVien entity = new HocVien();
            entity.setMaKH(rs.getInt("MaHV"));
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setMaNH(rs.getString("MaNH"));
                entity.setDiem(rs.getFloat("Diem"));
            return entity;
        } catch (SQLException e) {
            System.out.println("-> ReadFromResultSet (HocVienDAO) - " + e);
        }
        return null;
    }
    
    @Override
    public int insert(HocVien entity) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(INSERT);
            pstmt.setInt(1, entity.getMaKH());
            pstmt.setString(2, entity.getMaNH());
            pstmt.setFloat(3, entity.getDiem());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> Insert (HocVienDAO) - " + e);
            return -1;
        }
        return 1;
    }

    @Override
    public int update(HocVien entity) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(UPDATE);
            pstmt.setInt(2, entity.getMaKH());
            pstmt.setString(3, entity.getMaNH());
            pstmt.setFloat(1, entity.getDiem());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> Update (HocVienDAO) - " + e);
            return -1;
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
            System.out.println("-> Delete (HocVienDAO) - " + e);
            return -1;
        }
        return 1;
    }

    @Override
    public List<HocVien> selectAll() {
        list.clear();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (Exception e) {
            System.out.println("-> Select_All (HocVienDAO) - " + e);
            return list = null;
        }
        return list;
    }

    @Override
    public HocVien selectById(Object primarykey) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_BY_PK);
            pstmt.setString(1, (String) primarykey);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return readFromResultSet(rs);
            }
        } catch (Exception e) {
            System.out.println("-> Select_By_PK (HocVienDAO) - " + e);
        }
        return null;
    }

    @Override
    public HocVien getEnityByPossition(Object index) {
        int id = Integer.parseInt((String) index);
        if (id >= 0 && id < list.size()) {
            return list.get(id);
        }
        return null;
    }

    @Override
    public List<HocVien> selectByFk(Object foreignkey) {
        list.clear();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_BY_FK);
            pstmt.setString(1, (String) foreignkey);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (Exception e) {
            System.out.println("-> Select_By_FK (HocVienDAO) - " + e);
            return list = null;
        }
        return list;
    }

    @Override
    public List<HocVien> selectByString(Object string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //    tổng hợp học viên thoe mã khoá học
    public List<Object[]> selectHVKH(Object maKH) {
        List<Object[]> listObjects = new ArrayList<>();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_BY_HV_KH);
            pstmt.setInt(1, (Integer)maKH);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] model = {
                    rs.getInt("MaHV"), 
                    rs.getString("MaNH"),
                    rs.getString("HoVaTen"), 
                    rs.getFloat("Diem")
                };
                listObjects.add(model);
            }
        } catch (Exception e) {
            System.out.println("-> selectHVKH (HocVienDAO) - " + e);
            return listObjects = null;
        }
        return listObjects;
    }

    @Override
    public HocVien selectByKey(Object string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
