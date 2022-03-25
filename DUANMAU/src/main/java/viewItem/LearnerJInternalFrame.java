package viewItem;

import dao.NguoiHocDAO;
import helper.DataValidator;
import helper.DateHelper;
import helper.ImageHelper;
import helper.MessageDialogHelper;
import helper.ShareHelper;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import model.NguoiHoc;

/**
 *
 * @author ACER
 */
public class LearnerJInternalFrame extends javax.swing.JInternalFrame {

    /**
     * Các biến cục bộ
     */
    private DefaultTableModel tableModel;
    private final NguoiHocDAO dao = new NguoiHocDAO();
    private int pos = -1;
    private byte[] personalImage;
    private final ImageIcon icon = new ImageIcon(getClass().
            getResource("/icon/administrator-256.png"));

    /**
     * Creates new form StaffJInternalFrame
     */
    public LearnerJInternalFrame() {
        initComponents();
        init();
        fillTable();
        setFrameIcon(ShareHelper.APP_ICON_1);
        txtNgayDK.setText(DateHelper.toString(DateHelper.now()));
        txtMaNV.setText(ShareHelper.USER.getMa());
    }

    //Phương thức tạo bảng
    private void init() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã NH", "Họ và tên", "Giới tính", "Ngày sinh", "Điện thoại", "Email", "Mã NV", "Ngày đăng ký"});
        jtbNguoiHoc.setModel(tableModel);
    }

    //Phương thức xuất dữ liệu từ data
    private void setModel(NguoiHoc entity) {
        try {
            txtMaNH.setText(entity.getMaNH());
            txtHoVaten.setText(entity.getHoTen());
            txtNgaySinh.setText(String.valueOf(entity.getNgaySinh()));
            txtEmail.setText(entity.getEmail());
            txtSoDienThoai.setText(entity.getDienThoai());
            txtMaNV.setText(entity.getMaNV());
            txtNgayDK.setText(String.valueOf(entity.getNgayDK()));
            txtGhiChu.setText(entity.getGhiChu());
            if (entity.getHinh() != null) {
                try {
                    Image img = ImageHelper.createImageFromByteArray(entity.getHinh(), "jpg");
                    lbHinh.setIcon(new ImageIcon(img));
                } catch (IOException e) {
                    System.out.println("LearnerJInternalFrame(setModel/entity.getHinh) - " + e);
                }
                personalImage = entity.getHinh();
            } else {
                lbHinh.setIcon(icon);
            }
            if (entity.isGioiTinh() == true) {
                rbtNu.setSelected(true);
            } else {
                rbtNam.setSelected(true);
            }
        } catch (Exception e) {
            System.out.println("LearnerJInternalFrame(setModel) - " + e);
        }
    }

    //Phương thức lấy dữ liệu từ form
    private NguoiHoc getModel() {
        try {
            NguoiHoc entity = new NguoiHoc();
            entity.setMaNH(txtMaNH.getText());
            entity.setHoTen(txtHoVaten.getText());
            entity.setDienThoai(txtSoDienThoai.getText());
            entity.setEmail(txtEmail.getText());
            entity.setGhiChu(txtGhiChu.getText());
            entity.setMaNV(txtMaNV.getText());
            entity.setNgaySinh(DateHelper.toDate(txtNgaySinh.getText()));
            entity.setNgayDK(DateHelper.now());
            entity.setHinh(personalImage);
            entity.setGioiTinh(!rbtNam.isSelected());
            return entity;
        } catch (NumberFormatException e) {
            System.out.println("LearnerJInternalFrame(getModel) - " + e);
            return null;
        }
    }

    //Phương thức đổ dữ lệu từ data vào bảng
    private void fillTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) jtbNguoiHoc.getModel();
            model.setRowCount(0);
            for (NguoiHoc entity : dao.selectAll()) {
                model.addRow(new Object[]{
                    entity.getMaNH(),
                    entity.getHoTen(),
                    entity.isGioiTinh() == false ? "Nam" : "Nữ",
                    entity.getNgaySinh(),
                    entity.getDienThoai(),
                    entity.getEmail(),
                    entity.getMaNV(),
                    entity.getNgayDK()
                });
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            System.out.println("LearnerJInternalFrame(fillTable) - " + e);
        }
    }

    //Phương thức làm mới
    private void news() {
        txtMaNH.setText("");
        txtHoVaten.setText("");
        txtNgaySinh.setText("");
        txtEmail.setText("");
        txtSoDienThoai.setText("");
        txtMaNV.setText("");
        txtNgayDK.setText("");
        txtGhiChu.setText("");
        lbHinh.setIcon(icon);

        txtMaNH.setBackground(Color.white);
        txtHoVaten.setBackground(Color.white);
        txtNgaySinh.setBackground(Color.white);
        txtEmail.setBackground(Color.white);
        txtSoDienThoai.setBackground(Color.white);
        txtGhiChu.setBackground(Color.white);
    }

    //Phương thức kiểm lỗi và thông báo
    private StringBuilder check() {
        StringBuilder sb = new StringBuilder();
        DataValidator.validateCheckMANH(txtMaNH, sb, "Mã người học trống hoặc không đúng định dạng !");
        DataValidator.validateEmpty(txtHoVaten, sb, "Họ và tên người học không được để trống !");
        DataValidator.validateEmpty(txtNgaySinh, sb, "Ngày sinh người học không được để trống !");
        DataValidator.validateEmail(txtEmail, sb, "Email người học trống hoặc không đúng định dạng !");
        DataValidator.validateEmpty(txtSoDienThoai, sb, "Số điện thoại người học không được để trống !");
        DataValidator.validateEmpty(txtGhiChu, sb, "Ghi chú người học không được để trống !");
        if (personalImage == null) {
            sb.append("Vui lòng chọn hình ảnh !");
        }
        return sb;
    }

    //Mở hộp thư file
    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".jpg");
                }
            }

            @Override
            public String getDescription() {
                return "Image File (*.jpg)";
            }
        });

        if (chooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        try {
            ImageIcon icon = new ImageIcon(file.getPath());
            Image img = ImageHelper.resize(icon.getImage(), 256, 256);

            ImageIcon resizedIcon = new ImageIcon(img);
            lbHinh.setIcon(resizedIcon);

            personalImage = ImageHelper.toByteArray(img, "jpg");
        } catch (IOException e) {
            System.out.println("LearnerJInternalFrame(openFile) - " + e);
        }
    }

    //Phương thức clickTable
    private void clickTable() {
        try {
            int row = jtbNguoiHoc.getSelectedRow();
            if (row >= 0) {
                String maNH = (String) jtbNguoiHoc.getValueAt(row, 0);
                NguoiHoc entity = dao.selectById(maNH);
                setModel(entity);
            }
            tabbedPane.setSelectedIndex(0);
        } catch (Exception e) {
            System.out.println("LearnerJInternalFrame(clickTable) - " + e);
        }
    }

    //Phương thức control
    private void control(ActionEvent evt) {
        List<NguoiHoc> list = dao.selectAll();
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
            case "Tìm kiếm":
                search();
                break;
            default:
                break;
        }
    }

    private void selectPos(int pos) {
        NguoiHoc entity = dao.getEnityByPossition(String.valueOf(pos));
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
            NguoiHoc entity = getModel();
            if (dao.selectById(entity.getMaNH()) != null) {
                MessageDialogHelper.showMessageDialog(this, "Người học này đã tồn tại !", "Thông báo");
                txtMaNH.setText("");
            } else if (dao.insert(entity) > 0) {
                MessageDialogHelper.showMessageDialog(this, "Lưu người học thành công !", "Thông báo");
                news();
                fillTable();
                tabbedPane.setSelectedIndex(1);
            }
        } catch (Exception e) {
            System.out.println("LearnerJInternalFrame(save) - " + e);
        }
    }

    //Phương thức xoá
    private void delete() {
        try {
            StringBuilder sb = new StringBuilder();
            DataValidator.validateCheckMANH(txtMaNH, sb, "Mã người học trống hoặc không đúng định dạng !");
            if (sb.length() > 0) {
                MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            } else {
                if (MessageDialogHelper.showComfirmDialog(this, "Bạn có muốn xoá người học này không ?", "Cảnh báo")
                        == JOptionPane.NO_OPTION) {
                } else {
                    NguoiHoc entity = dao.selectById(txtMaNH.getText());
                    if (entity == null) {
                        MessageDialogHelper.showMessageDialog(this, "Không tìm thấy người học !", "Thông báo");
                        news();
                    } else if (dao.delete(txtMaNH.getText()) > 0) {
                        MessageDialogHelper.showMessageDialog(this, "Xoá dữ liệu người học thành công !", "Thông báo");
                        news();
                        fillTable();
                        tabbedPane.setSelectedIndex(1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("LearnerJInternalFrame(delete) - " + e);
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
            NguoiHoc entity = getModel();
            if (dao.update(entity) > 0) {
                MessageDialogHelper.showMessageDialog(this, "Cập nhập người học thành công !", "Thông báo");
                news();
                fillTable();
                tabbedPane.setSelectedIndex(1);
            }
        } catch (Exception e) {
            System.out.println("LearnerJInternalFrame(update) - " + e);
        }
    }

    //Phương thức tìm kiếm 
    private void search() {
        if (txtTimKiem.getText().equals("")) {
            fillTable();
        } else {
            try {
                DefaultTableModel model = (DefaultTableModel) jtbNguoiHoc.getModel();
                model.setRowCount(0);
                for (NguoiHoc entity : dao.selectByString(txtTimKiem.getText())) {
                    model.addRow(new Object[]{
                        entity.getMaNH(),
                        entity.getHoTen(),
                        entity.isGioiTinh() == false ? "Nam" : "Nữ",
                        entity.getNgaySinh(),
                        entity.getDienThoai(),
                        entity.getEmail(),
                        entity.getMaNV(),
                        entity.getNgayDK()
                    });
                }
                model.fireTableDataChanged();
            } catch (Exception e) {
                System.out.println("LearnerJInternalFrame(search-fillTable) - " + e);
            }
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
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lbHinh = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        btnOpenFile = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        txtMaNH = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        txtHoVaten = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        rbtNam = new javax.swing.JRadioButton();
        rbtNu = new javax.swing.JRadioButton();
        jPanel34 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        txtNgaySinh = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        txtEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        txtMaNV = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        txtNgayDK = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
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
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbNguoiHoc = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("QUẢN LÝ NGƯỜI HỌC");
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
        jLabel1.setText("QUẢN LÝ NGƯỜI HỌC");
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

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setMinimumSize(new java.awt.Dimension(1050, 300));
        jPanel6.setPreferredSize(new java.awt.Dimension(1050, 300));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.X_AXIS));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setMinimumSize(new java.awt.Dimension(256, 300));
        jPanel7.setPreferredSize(new java.awt.Dimension(256, 300));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.LINE_AXIS));

        lbHinh.setBackground(new java.awt.Color(255, 255, 255));
        lbHinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/administrator-256.png"))); // NOI18N
        jPanel11.add(lbHinh);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setMaximumSize(new java.awt.Dimension(256, 69));
        jPanel12.setMinimumSize(new java.awt.Dimension(256, 69));
        jPanel12.setPreferredSize(new java.awt.Dimension(256, 69));
        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.LINE_AXIS));

        btnOpenFile.setBackground(new java.awt.Color(255, 255, 255));
        btnOpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/opened_folder-48.png"))); // NOI18N
        btnOpenFile.setBorder(null);
        btnOpenFile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOpenFile.setMaximumSize(new java.awt.Dimension(256, 69));
        btnOpenFile.setMinimumSize(new java.awt.Dimension(256, 69));
        btnOpenFile.setPreferredSize(new java.awt.Dimension(256, 69));
        btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOpenFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(83, 83, 83))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOpenFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(268, 268, 268)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel6.add(jPanel7);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setMinimumSize(new java.awt.Dimension(794, 300));
        jPanel10.setPreferredSize(new java.awt.Dimension(794, 300));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.Y_AXIS));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setMinimumSize(new java.awt.Dimension(550, 90));
        jPanel13.setPreferredSize(new java.awt.Dimension(550, 90));
        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.X_AXIS));

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setMinimumSize(new java.awt.Dimension(275, 90));
        jPanel31.setPreferredSize(new java.awt.Dimension(275, 90));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel15.setPreferredSize(new java.awt.Dimension(200, 70));
        jPanel15.setLayout(new java.awt.BorderLayout());

        txtMaNH.setBackground(new java.awt.Color(255, 255, 255));
        txtMaNH.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        txtMaNH.setForeground(new java.awt.Color(0, 0, 0));
        txtMaNH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtMaNH.setCaretColor(new java.awt.Color(51, 51, 51));
        jPanel15.add(txtMaNH, java.awt.BorderLayout.CENTER);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Mã người học");
        jPanel15.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel13.add(jPanel31);

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setMinimumSize(new java.awt.Dimension(275, 90));
        jPanel32.setPreferredSize(new java.awt.Dimension(275, 90));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel17.setPreferredSize(new java.awt.Dimension(200, 70));
        jPanel17.setLayout(new java.awt.BorderLayout());

        txtHoVaten.setBackground(new java.awt.Color(255, 255, 255));
        txtHoVaten.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        txtHoVaten.setForeground(new java.awt.Color(0, 0, 0));
        txtHoVaten.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtHoVaten.setCaretColor(new java.awt.Color(51, 51, 51));
        jPanel17.add(txtHoVaten, java.awt.BorderLayout.CENTER);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Họ và tên");
        jPanel17.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel13.add(jPanel32);

        jPanel10.add(jPanel13);

        jPanel16.setBackground(new java.awt.Color(153, 255, 153));
        jPanel16.setMinimumSize(new java.awt.Dimension(550, 90));
        jPanel16.setPreferredSize(new java.awt.Dimension(550, 90));
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setMinimumSize(new java.awt.Dimension(275, 90));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel28.setPreferredSize(new java.awt.Dimension(200, 70));
        jPanel28.setLayout(new java.awt.BorderLayout());

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Giới tính");
        jPanel28.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        rbtNam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbtNam);
        rbtNam.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        rbtNam.setForeground(new java.awt.Color(0, 0, 0));
        rbtNam.setSelected(true);
        rbtNam.setText("Nam");
        rbtNam.setBorder(null);
        rbtNam.setMaximumSize(new java.awt.Dimension(100, 19));
        rbtNam.setMinimumSize(new java.awt.Dimension(100, 19));
        rbtNam.setPreferredSize(new java.awt.Dimension(100, 19));
        jPanel28.add(rbtNam, java.awt.BorderLayout.CENTER);

        rbtNu.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbtNu);
        rbtNu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        rbtNu.setForeground(new java.awt.Color(0, 0, 0));
        rbtNu.setText("Nữ");
        rbtNu.setBorder(null);
        rbtNu.setMaximumSize(new java.awt.Dimension(100, 19));
        rbtNu.setMinimumSize(new java.awt.Dimension(100, 19));
        rbtNu.setPreferredSize(new java.awt.Dimension(100, 19));
        jPanel28.add(rbtNu, java.awt.BorderLayout.LINE_END);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel16.add(jPanel33);

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setMinimumSize(new java.awt.Dimension(275, 90));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel30.setPreferredSize(new java.awt.Dimension(200, 70));
        jPanel30.setLayout(new java.awt.BorderLayout());

        txtNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        txtNgaySinh.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        txtNgaySinh.setForeground(new java.awt.Color(0, 0, 0));
        txtNgaySinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtNgaySinh.setCaretColor(new java.awt.Color(51, 51, 51));
        jPanel30.add(txtNgaySinh, java.awt.BorderLayout.CENTER);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Ngày sinh");
        jPanel30.add(jLabel7, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel16.add(jPanel34);

        jPanel10.add(jPanel16);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setMinimumSize(new java.awt.Dimension(550, 90));
        jPanel27.setPreferredSize(new java.awt.Dimension(550, 90));
        jPanel27.setLayout(new javax.swing.BoxLayout(jPanel27, javax.swing.BoxLayout.LINE_AXIS));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setMinimumSize(new java.awt.Dimension(275, 90));

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel36.setPreferredSize(new java.awt.Dimension(200, 70));
        jPanel36.setLayout(new java.awt.BorderLayout());

        txtSoDienThoai.setBackground(new java.awt.Color(255, 255, 255));
        txtSoDienThoai.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        txtSoDienThoai.setForeground(new java.awt.Color(0, 0, 0));
        txtSoDienThoai.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtSoDienThoai.setCaretColor(new java.awt.Color(51, 51, 51));
        jPanel36.add(txtSoDienThoai, java.awt.BorderLayout.CENTER);

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Điện thoại");
        jPanel36.add(jLabel8, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel27.add(jPanel35);

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setMinimumSize(new java.awt.Dimension(275, 90));

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel38.setPreferredSize(new java.awt.Dimension(200, 70));
        jPanel38.setLayout(new java.awt.BorderLayout());

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(0, 0, 0));
        txtEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtEmail.setCaretColor(new java.awt.Color(51, 51, 51));
        jPanel38.add(txtEmail, java.awt.BorderLayout.CENTER);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Email");
        jPanel38.add(jLabel9, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel27.add(jPanel37);

        jPanel10.add(jPanel27);

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setMinimumSize(new java.awt.Dimension(550, 90));
        jPanel29.setPreferredSize(new java.awt.Dimension(550, 90));
        jPanel29.setLayout(new javax.swing.BoxLayout(jPanel29, javax.swing.BoxLayout.X_AXIS));

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setMinimumSize(new java.awt.Dimension(275, 90));

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel40.setPreferredSize(new java.awt.Dimension(200, 70));
        jPanel40.setLayout(new java.awt.BorderLayout());

        txtMaNV.setBackground(new java.awt.Color(255, 255, 255));
        txtMaNV.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        txtMaNV.setForeground(new java.awt.Color(0, 0, 0));
        txtMaNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtMaNV.setCaretColor(new java.awt.Color(51, 51, 51));
        jPanel40.add(txtMaNV, java.awt.BorderLayout.CENTER);

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Mã nhân viên");
        jPanel40.add(jLabel10, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel29.add(jPanel39);

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setMinimumSize(new java.awt.Dimension(275, 90));

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel42.setPreferredSize(new java.awt.Dimension(200, 70));
        jPanel42.setLayout(new java.awt.BorderLayout());

        txtNgayDK.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayDK.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        txtNgayDK.setForeground(new java.awt.Color(0, 0, 0));
        txtNgayDK.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtNgayDK.setCaretColor(new java.awt.Color(51, 51, 51));
        jPanel42.add(txtNgayDK, java.awt.BorderLayout.CENTER);

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Ngày đăng ký");
        jPanel42.add(jLabel11, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel29.add(jPanel41);

        jPanel10.add(jPanel29);

        jPanel6.add(jPanel10);

        jPanel3.add(jPanel6);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setMaximumSize(new java.awt.Dimension(32767, 200));
        jPanel8.setMinimumSize(new java.awt.Dimension(0, 200));
        jPanel8.setPreferredSize(new java.awt.Dimension(0, 200));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Ghi chú");
        jLabel2.setMaximumSize(new java.awt.Dimension(64, 15));
        jLabel2.setMinimumSize(new java.awt.Dimension(64, 15));
        jLabel2.setPreferredSize(new java.awt.Dimension(64, 15));
        jPanel5.add(jLabel2, java.awt.BorderLayout.CENTER);

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jScrollPane2.setMinimumSize(new java.awt.Dimension(100, 100));

        txtGhiChu.setBackground(new java.awt.Color(255, 255, 255));
        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtGhiChu.setForeground(new java.awt.Color(0, 0, 0));
        txtGhiChu.setRows(5);
        txtGhiChu.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        txtGhiChu.setCaretColor(new java.awt.Color(0, 0, 0));
        txtGhiChu.setMinimumSize(new java.awt.Dimension(0, 75));
        txtGhiChu.setSelectedTextColor(new java.awt.Color(102, 102, 102));
        jScrollPane2.setViewportView(txtGhiChu);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1044, Short.MAX_VALUE)
                .addGap(39, 39, 39))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setMaximumSize(new java.awt.Dimension(11111111, 70));
        jPanel9.setMinimumSize(new java.awt.Dimension(1050, 70));
        jPanel9.setPreferredSize(new java.awt.Dimension(1050, 70));

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

        jPanel43.setLayout(new javax.swing.BoxLayout(jPanel43, javax.swing.BoxLayout.X_AXIS));

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));

        jPanel47.setBackground(new java.awt.Color(255, 255, 255));
        jPanel47.setLayout(new java.awt.BorderLayout());

        txtTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        txtTimKiem.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        txtTimKiem.setForeground(new java.awt.Color(0, 0, 0));
        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTimKiem.setCaretColor(new java.awt.Color(51, 51, 51));
        jPanel47.add(txtTimKiem, java.awt.BorderLayout.CENTER);

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("TÌM KIẾM");
        jPanel47.add(jLabel12, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel47, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                .addGap(49, 49, 49))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel47, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
        );

        jPanel43.add(jPanel44);

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));

        jPanel46.setBackground(new java.awt.Color(255, 255, 255));
        jPanel46.setLayout(new java.awt.BorderLayout());

        btnTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(0, 0, 0));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search-48.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorder(null);
        btnTimKiem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel46.add(btnTimKiem, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 383, Short.MAX_VALUE)
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel45Layout.createSequentialGroup()
                    .addGap(94, 94, 94)
                    .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .addGap(86, 86, 86)))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel45Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel43.add(jPanel45);

        jPanel4.add(jPanel43, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        jtbNguoiHoc.setBackground(new java.awt.Color(255, 255, 255));
        jtbNguoiHoc.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jtbNguoiHoc.setForeground(new java.awt.Color(0, 0, 0));
        jtbNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbNguoiHoc.setGridColor(new java.awt.Color(204, 204, 204));
        jtbNguoiHoc.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jtbNguoiHoc.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jtbNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbNguoiHocMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbNguoiHoc);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab("DANH SÁCH", new javax.swing.ImageIcon(getClass().getResource("/icon/list2-32.png")), jPanel4); // NOI18N

        jPanel2.add(tabbedPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtbNguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbNguoiHocMouseClicked
        clickTable();
    }//GEN-LAST:event_jtbNguoiHocMouseClicked

    private void btnOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenFileActionPerformed
        openFile();
    }//GEN-LAST:event_btnOpenFileActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        control(evt);
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnOpenFile;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
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
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtbNguoiHoc;
    private javax.swing.JLabel lbHinh;
    private javax.swing.JRadioButton rbtNam;
    private javax.swing.JRadioButton rbtNu;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoVaten;
    private javax.swing.JTextField txtMaNH;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayDK;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
