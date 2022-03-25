package viewItem;

import dao.NhanVienDAO;
import helper.DataValidator;
import helper.DateHelper;
import helper.MessageDialogHelper;
import helper.ShareHelper;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.NhanVien;

/**
 *
 * @author ACER
 */
public class StaffJInternalFrame extends javax.swing.JInternalFrame {

    /**
     * Các biến cục bộ
     */
    private DefaultTableModel tableModel;
    private final NhanVienDAO dao = new NhanVienDAO();
    private int pos = -1;

    /**
     * Creates new form StaffJInternalFrame
     */
    public StaffJInternalFrame() {
        initComponents();
        init();
        fillTable();
        setFrameIcon(ShareHelper.APP_ICON_1);
    }

    //Phương thức tạo bảng
    private void init() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã nhân viên", "Họ và tên", "Chức vụ", "Mật khẩu"});
        jtbNhanVien.setModel(tableModel);
    }

    //Phương thức xuất dữ liệu từ data
    private void setModel(NhanVien entity) {
        try {
            txtTenDangNhap.setText(entity.getMa());
            txtMatKhau.setText(entity.getMatKhau());
            txtHoVaTen.setText(entity.getHoTen());
            if (entity.isVaiTro() == true) {
                rbtTruongPhong.setSelected(true);
            } else {
                rbtNhanVien.setSelected(true);
            }
        } catch (Exception e) {
            System.out.println("StaffJInternalFrame(setModel) - " + e);
        }
    }

    //Phương thức lấy dữ liệu từ form
    private NhanVien getModel() {
        try {
            NhanVien entity = new NhanVien();
            entity.setMa(txtTenDangNhap.getText());
            entity.setMatKhau(txtMatKhau.getText());
            entity.setHoTen(txtHoVaTen.getText());
            entity.setVaiTro(!rbtNhanVien.isSelected());
            return entity;
        } catch (NumberFormatException e) {
            System.out.println("StaffJInternalFrame(getModel) - " + e);
            return null;
        }
    }

    //Phương thức đổ dữ lệu từ data vào bảng
    private void fillTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) jtbNhanVien.getModel();
            model.setRowCount(0);
            for (NhanVien entity : dao.selectAll()) {
                model.addRow(new Object[]{
                    entity.getMa(),
                    entity.getHoTen(),
                    entity.isVaiTro() == false ? "Nhân viên" : "Trưởng phòng",
                    entity.getMatKhau()
                });
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            System.out.println("StaffJInternalFrame(fillTable) - " + e);
        }
    }

    //Phương thức làm mới
    private void news() {
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        txtXacNhanMatKhau.setText("");
        txtHoVaTen.setText("");

        txtTenDangNhap.setBackground(Color.white);
        txtMatKhau.setBackground(Color.white);
        txtXacNhanMatKhau.setBackground(Color.white);
        txtHoVaTen.setBackground(Color.white);
    }

    //Phương thức kiểm lỗi và thông báo
    private StringBuilder check() {
        StringBuilder sb = new StringBuilder();
        DataValidator.validateCheckMANV(txtTenDangNhap, sb, "Mã hoặc tên đăng nhập nhân viên không đúng định dạng !");
        DataValidator.validateEmpty(txtMatKhau, sb, "Mật khẩu nhân viên không được để trống !");
        DataValidator.validateEmpty(txtXacNhanMatKhau, sb, "Xác nhận mật khẩu không được để trống !");
        String pass = new String(txtMatKhau.getPassword());
        String pass1 = new String(txtXacNhanMatKhau.getPassword());
        if (!pass.equals(pass1)) {
            sb.append("Xác nhận mật khẩu không khớt với mật khẩu !").append("\n");
        }
        DataValidator.validateEmpty(txtHoVaTen, sb, "Họ và tên nhân viên không đúng định dạng !");
        return sb;
    }

    //Phương thức clickTable
    private void clickTable() {
        try {
            int row = jtbNhanVien.getSelectedRow();
            if (row >= 0) {
                String maNH = (String) jtbNhanVien.getValueAt(row, 0);
                NhanVien entity = dao.selectById(maNH);
                setModel(entity);
            }
            tabbedPane.setSelectedIndex(0);
        } catch (Exception e) {
            System.out.println("StaffJInternalFrame(clickTable) - " + e);
        }
    }

    //Phương thức control
    private void control(ActionEvent evt) {
        List<NhanVien> list = dao.selectAll();
        String cmd = evt.getActionCommand();
        switch (cmd) {
            case "First":
                pos = 0;
                selectPos(pos);
                break;
            case "Next":
                pos++;
                if (pos >= list.size() - 1) {
                    pos = list.size() - 1;
                }
                selectPos(pos);
                break;
            case "Back":
                pos--;
                if (pos <= 0) {
                    pos = 0;
                }
                selectPos(pos);
                break;
            case "Last":
                pos = list.size() - 1;
                selectPos(pos);
                break;
            case "Làm mới":
                news();
                break;
            case "Thêm":
                save();
                break;
            case "Xoá":
                delete();
                break;
            case "Sửa":
                update();
                break;
            default:
                break;
        }
    }

    private void selectPos(int pos) {
        NhanVien entity = dao.getEnityByPossition(String.valueOf(pos));
        setModel(entity);
    }

    //Phương thức lưu
    private void save() {
        StringBuilder sb = check();
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            return;
        }
        try {
            NhanVien entity = getModel();
            if (dao.selectById(entity.getMa()) != null) {
                MessageDialogHelper.showMessageDialog(this, "Nhân viên này đã tồn tại !", "Thông báo");
                txtTenDangNhap.setText("");
            } else if (dao.insert(entity) > 0) {
                MessageDialogHelper.showMessageDialog(this, "Lưu nhân viên thành công !", "Thông báo");
                news();
                fillTable();
                tabbedPane.setSelectedIndex(1);
            }
        } catch (Exception e) {
            System.out.println("StaffJInternalFrame(save) - " + e);
        }
    }

    //Phương thức xoá
    private void delete() {
        try {
            StringBuilder sb = new StringBuilder();
            DataValidator.validateCheckMANV(txtTenDangNhap, sb, "Mã hoặc tên đăng nhập nhân viên không đúng định dạng !");
            if (sb.length() > 0) {
                MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            } else {
                if (MessageDialogHelper.showComfirmDialog(this, "Bạn có muốn xoá nhân viên này không ?", "Cảnh báo")
                        == JOptionPane.NO_OPTION) {
                } else {
                    NhanVien entity = dao.selectById(txtTenDangNhap.getText());
                    if (entity == null) {
                        MessageDialogHelper.showMessageDialog(this, "Không tìm thấy nhân viên !", "Thông báo");
                        news();
                    } else if (dao.delete(txtTenDangNhap.getText()) > 0) {
                        MessageDialogHelper.showMessageDialog(this, "Xoá dữ liệu nhân viên thành công !", "Thông báo");
                        news();
                        fillTable();
                        tabbedPane.setSelectedIndex(1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("StaffJInternalFrame(delete) - " + e);
        }
    }

    //Phương thức cập nhập
    private void update() {
        StringBuilder sb = check();
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            return;
        }
        try {
            NhanVien entity = getModel();
            if (dao.update(entity) > 0) {
                MessageDialogHelper.showMessageDialog(this, "Cập nhập nhân viên thành công !", "Thông báo");
                news();
                fillTable();
                tabbedPane.setSelectedIndex(1);
            }
        } catch (Exception e) {
            System.out.println("StaffJInternalFrame(update) - " + e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTenDangNhap = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        jPanel8 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtXacNhanMatKhau = new javax.swing.JPasswordField();
        jPanel7 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtHoVaTen = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        rbtTruongPhong = new javax.swing.JRadioButton();
        rbtNhanVien = new javax.swing.JRadioButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        btnSua = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        btnXoa = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        btnLamMoi = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        btnNext = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        btnLast = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbNhanVien = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("QUẢN LÝ NHÂN VIÊN");
        setMinimumSize(new java.awt.Dimension(1142, 770));
        setPreferredSize(new java.awt.Dimension(1142, 770));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1142, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(1142, 50));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 0));
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(1142, 666));
        jPanel2.setLayout(new java.awt.BorderLayout());

        tabbedPane.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setDoubleBuffered(false);
        jPanel3.setFont(new java.awt.Font("DialogInput", 3, 20)); // NOI18N
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Tên đăng nhập hoặc mã nhân viên");
        jLabel2.setMaximumSize(new java.awt.Dimension(72, 20));
        jLabel2.setMinimumSize(new java.awt.Dimension(72, 20));
        jLabel2.setPreferredSize(new java.awt.Dimension(72, 20));
        jPanel11.add(jLabel2, java.awt.BorderLayout.CENTER);

        txtTenDangNhap.setBackground(new java.awt.Color(255, 255, 255));
        txtTenDangNhap.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtTenDangNhap.setForeground(new java.awt.Color(0, 0, 0));
        txtTenDangNhap.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTenDangNhap.setCaretColor(new java.awt.Color(0, 0, 0));
        txtTenDangNhap.setMinimumSize(new java.awt.Dimension(4, 50));
        txtTenDangNhap.setPreferredSize(new java.awt.Dimension(4, 50));
        jPanel11.add(txtTenDangNhap, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                .addGap(57, 57, 57))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jPanel3.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Mật khẩu");
        jLabel3.setMaximumSize(new java.awt.Dimension(72, 20));
        jLabel3.setMinimumSize(new java.awt.Dimension(72, 20));
        jLabel3.setPreferredSize(new java.awt.Dimension(72, 20));
        jPanel12.add(jLabel3, java.awt.BorderLayout.CENTER);

        txtMatKhau.setBackground(new java.awt.Color(255, 255, 255));
        txtMatKhau.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtMatKhau.setForeground(new java.awt.Color(51, 51, 51));
        txtMatKhau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtMatKhau.setCaretColor(new java.awt.Color(0, 0, 0));
        txtMatKhau.setMinimumSize(new java.awt.Dimension(4, 50));
        txtMatKhau.setPreferredSize(new java.awt.Dimension(4, 50));
        jPanel12.add(txtMatKhau, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                .addGap(57, 57, 57))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jPanel3.add(jPanel6);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Xác nhận mật khẩu");
        jLabel4.setMaximumSize(new java.awt.Dimension(72, 20));
        jLabel4.setMinimumSize(new java.awt.Dimension(72, 20));
        jLabel4.setPreferredSize(new java.awt.Dimension(72, 20));
        jPanel13.add(jLabel4, java.awt.BorderLayout.CENTER);

        txtXacNhanMatKhau.setBackground(new java.awt.Color(255, 255, 255));
        txtXacNhanMatKhau.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtXacNhanMatKhau.setForeground(new java.awt.Color(51, 51, 51));
        txtXacNhanMatKhau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtXacNhanMatKhau.setCaretColor(new java.awt.Color(0, 0, 0));
        txtXacNhanMatKhau.setMinimumSize(new java.awt.Dimension(4, 50));
        txtXacNhanMatKhau.setPreferredSize(new java.awt.Dimension(4, 50));
        jPanel13.add(txtXacNhanMatKhau, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                .addGap(57, 57, 57))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jPanel3.add(jPanel8);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Họ và tên");
        jLabel6.setMaximumSize(new java.awt.Dimension(72, 20));
        jLabel6.setMinimumSize(new java.awt.Dimension(72, 20));
        jLabel6.setPreferredSize(new java.awt.Dimension(72, 20));
        jPanel15.add(jLabel6, java.awt.BorderLayout.CENTER);

        txtHoVaTen.setBackground(new java.awt.Color(255, 255, 255));
        txtHoVaTen.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtHoVaTen.setForeground(new java.awt.Color(0, 0, 0));
        txtHoVaTen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtHoVaTen.setCaretColor(new java.awt.Color(0, 0, 0));
        txtHoVaTen.setMinimumSize(new java.awt.Dimension(4, 50));
        txtHoVaTen.setPreferredSize(new java.awt.Dimension(4, 50));
        jPanel15.add(txtHoVaTen, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                .addGap(57, 57, 57))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jPanel3.add(jPanel7);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.X_AXIS));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Vai trò");
        jLabel7.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabel7.setMinimumSize(new java.awt.Dimension(100, 20));
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel16.add(jLabel7);

        rbtTruongPhong.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbtTruongPhong);
        rbtTruongPhong.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        rbtTruongPhong.setForeground(new java.awt.Color(0, 0, 0));
        rbtTruongPhong.setSelected(true);
        rbtTruongPhong.setText("Trưởng phòng");
        rbtTruongPhong.setBorder(null);
        rbtTruongPhong.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rbtTruongPhong.setMargin(new java.awt.Insets(2, 8, 2, 2));
        rbtTruongPhong.setMaximumSize(new java.awt.Dimension(200, 32));
        rbtTruongPhong.setMinimumSize(new java.awt.Dimension(200, 32));
        rbtTruongPhong.setPreferredSize(new java.awt.Dimension(200, 32));
        jPanel16.add(rbtTruongPhong);

        rbtNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbtNhanVien);
        rbtNhanVien.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        rbtNhanVien.setForeground(new java.awt.Color(0, 0, 0));
        rbtNhanVien.setText("Nhân viên");
        rbtNhanVien.setBorder(null);
        rbtNhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rbtNhanVien.setMargin(new java.awt.Insets(2, 8, 2, 2));
        jPanel16.add(rbtNhanVien);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                .addGap(57, 57, 57))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jPanel3.add(jPanel10);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 5));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.LINE_AXIS));

        btnThem.setBackground(new java.awt.Color(255, 255, 255));
        btnThem.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(51, 51, 51));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/more-32.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setBorder(null);
        btnThem.setBorderPainted(false);
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem.setMaximumSize(new java.awt.Dimension(100, 50));
        btnThem.setMinimumSize(new java.awt.Dimension(100, 50));
        btnThem.setPreferredSize(new java.awt.Dimension(100, 50));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel18.add(btnThem);

        jPanel14.add(jPanel18);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setLayout(new javax.swing.BoxLayout(jPanel19, javax.swing.BoxLayout.LINE_AXIS));

        btnSua.setBackground(new java.awt.Color(255, 255, 255));
        btnSua.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(51, 51, 51));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/available_updates-32.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setBorder(null);
        btnSua.setBorderPainted(false);
        btnSua.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSua.setMaximumSize(new java.awt.Dimension(100, 50));
        btnSua.setMinimumSize(new java.awt.Dimension(100, 50));
        btnSua.setPreferredSize(new java.awt.Dimension(100, 50));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel19.add(btnSua);

        jPanel14.add(jPanel19);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.LINE_AXIS));

        btnXoa.setBackground(new java.awt.Color(255, 255, 255));
        btnXoa.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(51, 51, 51));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-32.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.setBorder(null);
        btnXoa.setBorderPainted(false);
        btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa.setMaximumSize(new java.awt.Dimension(100, 50));
        btnXoa.setMinimumSize(new java.awt.Dimension(100, 50));
        btnXoa.setPreferredSize(new java.awt.Dimension(100, 50));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel20.add(btnXoa);

        jPanel14.add(jPanel20);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setLayout(new javax.swing.BoxLayout(jPanel21, javax.swing.BoxLayout.LINE_AXIS));

        btnLamMoi.setBackground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(51, 51, 51));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh-32.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setBorder(null);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLamMoi.setMaximumSize(new java.awt.Dimension(100, 50));
        btnLamMoi.setMinimumSize(new java.awt.Dimension(100, 50));
        btnLamMoi.setPreferredSize(new java.awt.Dimension(100, 50));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel21.add(btnLamMoi);

        jPanel14.add(jPanel21);

        jPanel9.add(jPanel14);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 5));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        btnFirst.setBackground(new java.awt.Color(255, 255, 255));
        btnFirst.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnFirst.setForeground(new java.awt.Color(51, 51, 51));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/first-32.png"))); // NOI18N
        btnFirst.setActionCommand("First");
        btnFirst.setBorder(null);
        btnFirst.setBorderPainted(false);
        btnFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFirst.setMaximumSize(new java.awt.Dimension(100, 50));
        btnFirst.setMinimumSize(new java.awt.Dimension(100, 50));
        btnFirst.setPreferredSize(new java.awt.Dimension(100, 50));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel23.add(btnFirst);

        jPanel22.add(jPanel23);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setLayout(new javax.swing.BoxLayout(jPanel24, javax.swing.BoxLayout.LINE_AXIS));

        btnBack.setBackground(new java.awt.Color(255, 255, 255));
        btnBack.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnBack.setForeground(new java.awt.Color(51, 51, 51));
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/previous-32.png"))); // NOI18N
        btnBack.setActionCommand("Back");
        btnBack.setBorder(null);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.setMaximumSize(new java.awt.Dimension(100, 50));
        btnBack.setMinimumSize(new java.awt.Dimension(100, 50));
        btnBack.setPreferredSize(new java.awt.Dimension(100, 50));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel24.add(btnBack);

        jPanel22.add(jPanel24);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.LINE_AXIS));

        btnNext.setBackground(new java.awt.Color(255, 255, 255));
        btnNext.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnNext.setForeground(new java.awt.Color(51, 51, 51));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/next-32.png"))); // NOI18N
        btnNext.setActionCommand("Next");
        btnNext.setBorder(null);
        btnNext.setBorderPainted(false);
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext.setMaximumSize(new java.awt.Dimension(100, 50));
        btnNext.setMinimumSize(new java.awt.Dimension(100, 50));
        btnNext.setPreferredSize(new java.awt.Dimension(100, 50));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel25.add(btnNext);

        jPanel22.add(jPanel25);

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setLayout(new javax.swing.BoxLayout(jPanel26, javax.swing.BoxLayout.LINE_AXIS));

        btnLast.setBackground(new java.awt.Color(255, 255, 255));
        btnLast.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnLast.setForeground(new java.awt.Color(51, 51, 51));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/last-32.png"))); // NOI18N
        btnLast.setActionCommand("Last");
        btnLast.setBorder(null);
        btnLast.setBorderPainted(false);
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLast.setMaximumSize(new java.awt.Dimension(100, 50));
        btnLast.setMinimumSize(new java.awt.Dimension(100, 50));
        btnLast.setPreferredSize(new java.awt.Dimension(100, 50));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel26.add(btnLast);

        jPanel22.add(jPanel26);

        jPanel9.add(jPanel22);

        jPanel3.add(jPanel9);

        tabbedPane.addTab("CẬP NHẬP", new javax.swing.ImageIcon(getClass().getResource("/icon/installing_updates-32.png")), jPanel3); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setFont(new java.awt.Font("DialogInput", 3, 20)); // NOI18N
        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        jtbNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        jtbNhanVien.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jtbNhanVien.setForeground(new java.awt.Color(0, 0, 0));
        jtbNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtbNhanVien.setGridColor(new java.awt.Color(204, 204, 204));
        jtbNhanVien.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jtbNhanVien.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jtbNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbNhanVien);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab("DANH SÁCH", new javax.swing.ImageIcon(getClass().getResource("/icon/list2-32.png")), jPanel4); // NOI18N

        jPanel2.add(tabbedPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        control(evt);
    }//GEN-LAST:event_btnThemActionPerformed

    private void jtbNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbNhanVienMouseClicked
        clickTable();
    }//GEN-LAST:event_jtbNhanVienMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtbNhanVien;
    private javax.swing.JRadioButton rbtNhanVien;
    private javax.swing.JRadioButton rbtTruongPhong;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextField txtHoVaTen;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTenDangNhap;
    private javax.swing.JPasswordField txtXacNhanMatKhau;
    // End of variables declaration//GEN-END:variables
}
