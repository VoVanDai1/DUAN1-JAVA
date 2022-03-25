package dao;

import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author ACER
 */
abstract class EntityDAO <EnityType, KeyType>{
    /**
     * Thêm mới thực thể vào CSDL
     * @param EnityType là thực thể chứa thông tin bản ghi
     */
    public abstract int insert(EnityType entity);

    /**
     * Cập nhập thực thể vào CSDL
     * @param EnityType là thực thể chứa thông tin bản ghi
     */
    public abstract int update(EnityType entity);

    /**
     * Xoá thực thể vào CSDL
     * @param KeyType là khoá chính bảng ghi
     */
    public abstract int delete(KeyType primarykey);

    /**
     * Trả về danh sách thực thể
     * @param EnityType là thực thể chứa thông tin bản ghi
     */
    public abstract List<EnityType> selectAll();

    /**
     * Trả về thực thể
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract EnityType selectById(KeyType primarykey);
    
    /**
     * Trả về thực thể
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract List<EnityType> selectByFk(KeyType foreignkey);
    
    /**
     * Trả về list
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract List<EnityType> selectByString(KeyType string);
    
    /**
     * Trả về thực thể
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract EnityType selectByKey(KeyType string);

    /**
     * Trả về vị trí thực thể
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract EnityType getEnityByPossition(KeyType index);
    
    /**
     * Hàm dọc dữ liệu
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract EnityType readFromResultSet(ResultSet rs);
}
