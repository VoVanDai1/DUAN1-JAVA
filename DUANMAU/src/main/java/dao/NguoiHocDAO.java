package dao;

import helper.DatabaseHelper;
import helper.DateHelper;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;
import model.NguoiHoc;

/**
 *
 * @author ACER
 */
public class NguoiHocDAO extends EntityDAO<NguoiHoc, Object> {

    /**
     * Tạo các giá trị là hằng
     *
     * @param final là khai báo hằng
     */
    private final String INSERT = "INSERT INTO [dbo].[NGUOIHOC] ([MaNH] ,[HoVaTen] ,[GioiTinh],[NgaySinh] ,[SDT],[Hinh],[Email],[GhiChu],[MaNV],[NgayDK]) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE [dbo].[NGUOIHOC] SET [HoVaTen] = ?,[GioiTinh] = ?,[NgaySinh] = ?,[SDT] = ?,[Hinh] = ? ,[Email] = ?,[GhiChu] = ?,[MaNV] = ?,[NgayDK] = ? WHERE [MaNH] = ?";
    private final String DELETE = "DELETE FROM [dbo].[NGUOIHOC] WHERE [MaNH] = ?";
    private final String SELECT_ALL = "SELECT * FROM [dbo].[NGUOIHOC]";
    private final String SELECT_BY_PK = "SELECT * FROM [dbo].[NGUOIHOC] WHERE [MaNH] = ?";
    private final String SELECT_BY_STRING = "SELECT * FROM [dbo].[NGUOIHOC] WHERE [HoVaTen]  LIKE  ?";
    private final String SELECT_BY_FK = "SELECT * FROM [dbo].[NGUOIHOC] WHERE [MaNH] NOT IN (SELECT [MaNH] FROM [dbo].[HOCVIEN] WHERE [MaKH] = ?)";

    /**
     * Tạo các giá trị sử dụng chung trong class
     *
     * @param final là khai báo hằng
     * @param Connection giá trị kết nối CSDL
     * @param PreparedStatement thực hiện truy vấn tham số
     * @param Blob lưu trữ dưới dạng nhị phân
     */
    private List<NguoiHoc> list = new ArrayList<>();
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
    public NguoiHoc readFromResultSet(ResultSet rs) {
        try {
            NguoiHoc entity = new NguoiHoc();
            entity.setMaNH(rs.getString("MaNH"));
            entity.setHoTen(rs.getString("HoVaTen"));
            entity.setGioiTinh(rs.getBoolean("GioiTinh"));
            entity.setNgaySinh(rs.getDate("NgaySinh"));
            entity.setDienThoai(rs.getString("SDT"));
            image = rs.getBlob("Hinh");
            if (image != null) {
                entity.setHinh(image.getBytes(1, (int) image.length()));
            }
            entity.setEmail(rs.getString("Email"));
            entity.setGhiChu(rs.getString("GhiChu"));
            entity.setMaNV(rs.getString("MaNV"));
            entity.setNgayDK(rs.getDate("NgayDK"));
            return entity;
        } catch (SQLException e) {
            System.out.println("-> ReadFromResultSet (NguoiHocDAO) - " + e);
        }
        return null;
    }

    @Override
    public int insert(NguoiHoc entity) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(INSERT);
            pstmt.setString(1, entity.getMaNH());
            pstmt.setString(2, entity.getHoTen());
            pstmt.setBoolean(3, entity.isGioiTinh());
            pstmt.setDate(4, (Date) DateHelper.toDate(entity.getNgaySinh()));
            pstmt.setString(5, entity.getDienThoai());
            if (entity.getHinh() != null) {
                image = new SerialBlob(entity.getHinh());
                pstmt.setBlob(6, image);
            } else {
                pstmt.setBlob(6, image);
            }
            pstmt.setString(7, entity.getEmail());
            pstmt.setString(8, entity.getGhiChu());
            pstmt.setString(9, entity.getMaNV());
            pstmt.setDate(10, (Date) DateHelper.toDate(entity.getNgayDK()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> Insert (NguoiHocDAO) - " + e);
            return -1;
        }
        return 1;
    }

    @Override
    public int update(NguoiHoc entity) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(UPDATE);
            pstmt.setString(10, entity.getMaNH());
            pstmt.setString(1, entity.getHoTen());
            pstmt.setBoolean(2, entity.isGioiTinh());
            pstmt.setDate(3, (Date) DateHelper.toDate(entity.getNgaySinh()));
            pstmt.setString(4, entity.getDienThoai());
            if (entity.getHinh() != null) {
                image = new SerialBlob(entity.getHinh());
                pstmt.setBlob(5, image);
            } else {
                pstmt.setBlob(5, image);
            }
            pstmt.setString(6, entity.getEmail());
            pstmt.setString(7, entity.getGhiChu());
            pstmt.setString(8, entity.getMaNV());
            pstmt.setDate(9, (Date) DateHelper.toDate(entity.getNgayDK()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("-> Update (NguoiHocDAO) - " + e);
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
            System.out.println("-> Delete (NguoiHocDAO) - " + e);
            return -1;
        }
        return 1;
    }

    @Override
    public List<NguoiHoc> selectAll() {
        list.clear();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (Exception e) {
            System.out.println("-> Select_All (NguoiHocDAO) - " + e);
            return list = null;
        }
        return list;
    }

    @Override
    public NguoiHoc selectById(Object primarykey) {
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_BY_PK);
            pstmt.setString(1, (String) primarykey);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return readFromResultSet(rs);
            }
        } catch (Exception e) {
            System.out.println("-> Select_By_PK (NguoiHocDAO) - " + e);
        }
        return null;
    }

    @Override
    public NguoiHoc getEnityByPossition(Object index) {
        int id = Integer.parseInt((String) index);
        if (id >= 0 && id < list.size()) {
            return list.get(id);
        }
        return null;
    }

    @Override
    public List<NguoiHoc> selectByFk(Object foreignkey) {
        list.clear();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_BY_FK);
            pstmt.setInt(1, (Integer) foreignkey);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (Exception e) {
            System.out.println("-> SELECT_BY_FK (NguoiHocDAO) - " + e);
            return list = null;
        }
        return list;
    }

    @Override
    public List<NguoiHoc> selectByString(Object string) {
        list.clear();
        try {
            conn = DatabaseHelper.openConnection();
            pstmt = conn.prepareStatement(SELECT_BY_STRING);
            String txt = "%" + String.valueOf(string) + "%";
            pstmt.setString(1, txt);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (Exception e) {
            System.out.println("-> SelectByString (NguoiHocDAO) - " + e);
            return list = null;
        }
        return list;
    }

    @Override
    public NguoiHoc selectByKey(Object string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
