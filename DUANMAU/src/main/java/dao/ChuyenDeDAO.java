package dao;

import helper.DatabaseHelper;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;
import model.ChuyenDe;

/**
 *
 * @author ACER
 */
public class ChuyenDeDAO extends EntityDAO<ChuyenDe, Object> {

    /**
     * Tạo các giá trị là hằng
     *
     * @param final là khai báo hằng
     */
    private final String INSERT = "INSERT INTO [dbo].[CHUYENDE] ([MaCD] ,[TenCD],[HocPhi],[ThoiLuong],[Hinh],[Mota]) VALUES (?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE [dbo].[CHUYENDE] SET [TenCD] = ?,[HocPhi] = ?,[ThoiLuong] = ?,[Hinh] = ?,[Mota] = ? WHERE [MaCD] = ?";
    private final String DELETE = "DELETE FROM [dbo].[CHUYENDE] WHERE [MaCD] = ?";
    private final String SELECT_ALL = "SELECT * FROM [dbo].[CHUYENDE]";
    private final String SELECT_BY_PK = "SELECT * FROM [dbo].[CHUYENDE] WHERE [MaCD] = ?";
    private final String SELECT_BY_STRING = "SELECT * FROM [dbo].[CHUYENDE] WHERE [TenCD] = ?";

    /**
     * Tạo các giá trị sử dụng chung trong class
     *
     * @param final là khai báo hằng
     * @param Connection giá trị kết nối CSDL
     * @param PreparedStatement thực hiện truy vấn tham số
     * @param Blob lưu trữ dưới dạng nhị phân
     */
    private List<ChuyenDe> list = new ArrayList<>();
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private Blob image = null;

    /**
     * Các phương thức được kế thừa từ EntityDAO
     *
     * @param rs
     * @return trả về
     */
    @Override
    public ChuyenDe readFromResultSet(ResultSet rs) {
        try {
            ChuyenDe entity = new ChuyenDe();
            entity.setMaCD(rs.getString("MaCD"));
            entity.setTenCD(rs.getString("TenCD"));
            entity.setHocPhi(rs.getFloat("HocPhi"));
            entity.setThoiLuong(rs.getInt("ThoiLuong"));
            image = rs.getBlob("Hinh");
            if (image != null) {
                entity.setHinh(image.getBytes(1, (int) image.length()));
            }
            entity.setMoTa(rs.getString("Mota"));
            return entity;
        } catch (SQLException e) {
            System.out.println("-> ReadFromResultSet (ChuyenDeDAO) - " + e);
        }
        return null;
    }

    @Override
    public int insert(ChuyenDe entity) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(INSERT);
            pstmt.setString(1, entity.getMaCD());
            pstmt.setString(2, entity.getTenCD());
            pstmt.setFloat(3, entity.getHocPhi());
            pstmt.setInt(4, entity.getThoiLuong());
            if (entity.getHinh() != null) {
                image = new SerialBlob(entity.getHinh());
                pstmt.setBlob(5, image);
            } else {
                pstmt.setBlob(5, image);
            }
            pstmt.setString(6, entity.getMoTa());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> Insert (ChuyenDeDAO) - " + e);
            return -1;
        }
        return 1;
    }

    @Override
    public int update(ChuyenDe entity) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(UPDATE);
            pstmt.setString(6, entity.getMaCD());
            pstmt.setString(1, entity.getTenCD());
            pstmt.setFloat(2, entity.getHocPhi());
            pstmt.setInt(3, entity.getThoiLuong());
            if (entity.getHinh() != null) {
                image = new SerialBlob(entity.getHinh());
                pstmt.setBlob(4, image);
            } else {
                pstmt.setBlob(4, image);
            }
            pstmt.setString(5, entity.getMoTa());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> Update (ChuyenDeDAO) - " + e);
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
            System.out.println("-> Delete (ChuyenDeDAO) - " + e);
            return -1;
        }
        return 1;
    }

    @Override
    public List<ChuyenDe> selectAll() {
        list.clear();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (Exception e) {
            System.out.println("-> Select_All (ChuyenDeDAO) - " + e);
            return list = null;
        }
        return list;
    }

    @Override
    public ChuyenDe selectById(Object primarykey) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_BY_PK);
            pstmt.setString(1, (String) primarykey);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return readFromResultSet(rs);
            }
        } catch (Exception e) {
            System.out.println("-> Select_By_PK (ChuyenDeDAO) - " + e);
        }
        return null;
    }

    @Override
    public ChuyenDe getEnityByPossition(Object index) {
        int id = Integer.parseInt((String) index);
        if (id >= 0 && id < list.size()) {
            return list.get(id);
        }
        return null;
    }

    @Override
    public List<ChuyenDe> selectByFk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ChuyenDe> selectByString(Object string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ChuyenDe selectByKey(Object string) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_BY_STRING);
            pstmt.setString(1, (String) string);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return readFromResultSet(rs);
            }
        } catch (Exception e) {
            System.out.println("-> SELECT_BY_STRING (ChuyenDeDAO) - " + e);
        }
        return null;
    }
}
