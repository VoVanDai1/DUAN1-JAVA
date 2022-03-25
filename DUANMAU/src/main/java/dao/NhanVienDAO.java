package dao;

import helper.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.NhanVien;

/**
 *
 * @author ACER
 */
public class NhanVienDAO extends EntityDAO<NhanVien, Object> {

    /**
     * Tạo các giá trị là hằng
     *
     * @param final là khai báo hằng
     */
    private final String INSERT = "INSERT INTO [dbo].[NHANVIEN] ([MaNV],[MatKhau],[HoVaTen],[VaiTro]) VALUES (?,?,?,?)";
    private final String UPDATE = "UPDATE [dbo].[NHANVIEN] SET [MatKhau] = ?,[HoVaTen] = ?,[VaiTro] = ? WHERE [MaNV] = ?";
    private final String DELETE = "DELETE FROM [dbo].[NHANVIEN] WHERE [MaNV] = ?";
    private final String SELECT_ALL = "SELECT * FROM [dbo].[NHANVIEN]";
    private final String SELECT_BY_PK = "SELECT * FROM [dbo].[NHANVIEN] WHERE [MaNV] = ?";
    private final String CHECK_LOGIN = "SELECT [MaNV],[MatKhau],[VaiTro],[HoVaTen] FROM [dbo].[NHANVIEN] WHERE [MaNV] = ? AND [MatKhau] = ? AND [VaiTro] = ?";
    private final String CHECK_PASS = "SELECT [MaNV],[MatKhau] FROM [dbo].[NHANVIEN] WHERE [MaNV] = ? AND [MatKhau] = ?";
    private final String UPDATE_PASS = "UPDATE [dbo].[NHANVIEN] SET [MatKhau] = ? WHERE [MaNV] = ?";

    /**
     * Tạo các giá trị sử dụng chung trong class
     *
     * @param final là khai báo hằng
     * @param Connection giá trị kết nối CSDL
     * @param PreparedStatement thực hiện truy vấn tham số
     * @param Blob lưu trữ dưới dạng nhị phân
     */
    private List<NhanVien> list = new ArrayList<>();
    private Connection conn = null;
    private PreparedStatement pstmt = null;

    /**
     * Các phương thức được kế thừa từ EntityDAO
     *
     * @param rs
     * @return
     */
    @Override
    public NhanVien readFromResultSet(ResultSet rs) {
        try {
                NhanVien entity = new NhanVien();
                entity.setMa(rs.getString("MaNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setHoTen(rs.getString("HoVaTen"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
            return entity;
        } catch (SQLException e) {
            System.out.println("-> ReadFromResultSet (ChuyenDeDAO) - " + e);
        }
        return null;
    }
    
    @Override
    public int insert(NhanVien entity) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(INSERT);
            pstmt.setString(1, entity.getMa());
            pstmt.setString(2, entity.getMatKhau());
            pstmt.setString(3, entity.getHoTen());
            pstmt.setBoolean(4, entity.isVaiTro());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> Insert (NhanVienDAO) - " + e);
            return -1;
        }
        return 1;
    }

    @Override
    public int update(NhanVien entity) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(UPDATE);
            pstmt.setString(4, entity.getMa());
            pstmt.setString(1, entity.getMatKhau());
            pstmt.setString(2, entity.getHoTen());
            pstmt.setBoolean(3, entity.isVaiTro());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> Update (NhanVienDAO) - " + e);
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
            System.out.println("-> Delete (NhanVienDAO) - " + e);
            return -1;
        }
        return 1;
    }

    @Override
    public List<NhanVien> selectAll() {
        list.clear();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (Exception e) {
            System.out.println("-> Select_All (NhanVienDAO) - " + e);
            return list = null;
        }
        return list;
    }

    @Override
    public NhanVien selectById(Object primarykey) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_BY_PK);
            pstmt.setString(1, (String) primarykey);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return readFromResultSet(rs);
            }
        } catch (Exception e) {
            System.out.println("-> Select_By_PK (NhanVienDAO) - " + e);
        }
        return null;
    }

    @Override
    public NhanVien getEnityByPossition(Object index) {
        int id = Integer.parseInt((String) index);
        if (id >= 0 && id < list.size()) {
            return list.get(id);
        }
        return null;
    }

    public NhanVien checkLogin(String maNV, String matKhau, boolean vaiTro) throws Exception {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(CHECK_LOGIN);
            pstmt.setString(1, maNV);
            pstmt.setString(2, matKhau);
            pstmt.setBoolean(3, vaiTro);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMa(maNV);
                entity.setVaiTro(vaiTro);
                entity.setHoTen(rs.getString("HoVaTen"));
                return entity;
            }
        } catch (Exception e) {
            System.out.println("-> CheckLogin - " + e);
        }
        return null;
    }
    
    public NhanVien checkPass(String maNV, String matKhau) throws Exception {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(CHECK_PASS);
            pstmt.setString(1, maNV);
            pstmt.setString(2, matKhau);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMa(maNV);
                entity.setMatKhau(matKhau);
                return entity;
            }
        } catch (Exception e) {
            System.out.println("-> CheckPass - " + e);
        }
        return null;
    }
    
    public int updatePass(String maNV, String matKhau) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(UPDATE_PASS);
            pstmt.setString(2, maNV);
            pstmt.setString(1, matKhau);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> UpdatePass (NhanVienDAO) - " + e);
            return -1;
        }
        return 1;
    }

    @Override
    public List<NhanVien> selectByFk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NhanVien> selectByString(Object string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public NhanVien selectByKey(Object string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
