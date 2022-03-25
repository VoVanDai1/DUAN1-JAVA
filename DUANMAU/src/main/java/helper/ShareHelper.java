package helper;

import java.awt.Image;
import javax.swing.ImageIcon;
import model.NhanVien;

/**
 *
 * @author ACER
 */
public class ShareHelper {

    /**
     * Ảnh biểu tượng của ứng dụng, xuất hiện trên mọi cửa sổ
     */
    public static final Image APP_ICON;
    public static final ImageIcon APP_ICON_1;

    static {
        /**
         * Tải biểu tượng ứng dụng 
         * CÁCH TẢI ẢNH TỪ TRONG PROJECT
         * icon là thư mục con của src
         * 
         */
        String file = "/icon/fpt.png";
        APP_ICON = new ImageIcon(ShareHelper.class.getResource(file)).getImage();
        APP_ICON_1 = new ImageIcon(ShareHelper.class.getResource(file));
    }

    /**
     * Đối tượng này chứa thông tin người sử dụng sau khi đăng nhnập
     */
    public static NhanVien USER = null;

    /**
     * Xóa thông tin của người sử dụng khi có yêu cầu đăng xuất
     *
     * @return
     */
    public static boolean logoff() {
        ShareHelper.USER = null;
        return true;
    }

    /**
     * Kiểm tra xem đăng nhập hay chưa
     *
     * @return đăng nhập hay chưa
     */
    public static boolean authenticated() {
        return ShareHelper.USER != null;
    }
}
