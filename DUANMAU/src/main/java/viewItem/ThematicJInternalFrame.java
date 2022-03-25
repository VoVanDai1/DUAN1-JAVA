package viewItem;

import dao.ChuyenDeDAO;
import helper.DataValidator;
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
import model.ChuyenDe;

/**
 *
 * @author ACER
 */
public class ThematicJInternalFrame extends javax.swing.JInternalFrame {

    /**
     * Các biến cục bộ
     */
    private DefaultTableModel tableModel;
    private ChuyenDeDAO dao = new ChuyenDeDAO();
    private int pos = -1;
    private byte[] personalImage;
    private ImageIcon icon = new ImageIcon(getClass().
            getResource("/icon/administrator-256.png"));

    /**
     * Creates new form StaffJInternalFrame
     */
    public ThematicJInternalFrame() {
        initComponents();
        init();
        fillTable();
        setFrameIcon(ShareHelper.APP_ICON_1);
    }

    //Phương thức tạo bảng
    private void init() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã CĐ", "Tên CĐ", "Thời lượng", "Học phí"});
        jtbQLChuyenDe.setModel(tableModel);
    }

    //Phương thức xuất dữ liệu từ data
    private void setModel(ChuyenDe entity) {
        try {
            txtMaChuyenDe.setText(entity.getMaCD());
            txtTenChuyenDe.setText(entity.getTenCD());
            txtThoiLuong.setText(String.valueOf(entity.getThoiLuong()));
            txtHocPhi.setText(String.valueOf(entity.getHocPhi()));
            txtMoTa.setText(String.valueOf(entity.getMoTa()));
            if (entity.getHinh() != null) {
                try {
                    Image img = ImageHelper.createImageFromByteArray(entity.getHinh(), "jpg");
                    lbHinh.setIcon(new ImageIcon(img));
                } catch (IOException e) {
                    System.out.println("ThematicJInternalFrame(setModel/entity.getHinh) - " + e);
                }
                personalImage = entity.getHinh();
            } else {
                lbHinh.setIcon(icon);
            }
        } catch (Exception e) {
            System.out.println("ThematicJInternalFrame(setModel) - " + e);
        }
    }

    //Phương thức lấy dữ liệu từ form
    private ChuyenDe getModel() {
        try {
            ChuyenDe entity = new ChuyenDe();
            entity.setMaCD(txtMaChuyenDe.getText());
            entity.setTenCD(txtTenChuyenDe.getText());
            entity.setHocPhi(Float.parseFloat(txtHocPhi.getText()));
            entity.setThoiLuong(Integer.parseInt(txtThoiLuong.getText()));
            entity.setMoTa(txtMoTa.getText());
            entity.setHinh(personalImage);
            return entity;
        } catch (NumberFormatException e) {
            System.out.println("ThematicJInternalFrame(getModel) - " + e);
            return null;
        }
    }

    //Phương thức đổ dữ lệu từ data vào bảng
    private void fillTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) jtbQLChuyenDe.getModel();
            model.setRowCount(0);
            for (ChuyenDe entity : dao.selectAll()) {
                model.addRow(new Object[]{
                    entity.getMaCD(),
                    entity.getTenCD(),
                    entity.getThoiLuong(),
                    entity.getHocPhi()
                });
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            System.out.println("ThematicJInternalFrame(fillTable) - " + e);
        }
    }

    //Phương thức làm mới
    private void news() {
        txtMaChuyenDe.setText("");
        txtTenChuyenDe.setText("");
        txtThoiLuong.setText("");
        txtHocPhi.setText("");
        txtMoTa.setText("");
        lbHinh.setIcon(icon);

        txtMaChuyenDe.setBackground(Color.white);
        txtTenChuyenDe.setBackground(Color.white);
        txtThoiLuong.setBackground(Color.white);
        txtHocPhi.setBackground(Color.white);
        txtMoTa.setBackground(Color.white);
    }

    //Phương thức kiểm lỗi và thông báo
    private StringBuilder check() {
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtMaChuyenDe, sb, "Mã chuyên đề không được để trống !");
        DataValidator.validateEmpty(txtTenChuyenDe, sb, "Tên chuyên đề không được để trống !");
        DataValidator.validateEmpty(txtThoiLuong, sb, "Thời lượng chuyên đề không được để trống !");
        DataValidator.validateCheckInt(txtThoiLuong, sb, "Thời lượng nhận kiểu số nguyên !");
        DataValidator.validateEmpty(txtHocPhi, sb, "Học phí chuyên đề không được để trống !");
        DataValidator.validateCheckFloat(txtHocPhi, sb, "Học phí nhận kiểu số thực !");
        DataValidator.validateEmpty(txtMoTa, sb, "Mô tả chuyên đề không được để trống !");
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
            System.out.println("ThematicJInternalFrame(openFile) - " + e);
        }
    }

    //Phương thức clickTable
    private void clickTable() {
        try {
            int row = jtbQLChuyenDe.getSelectedRow();
            if (row >= 0) {
                String maCĐ = (String) jtbQLChuyenDe.getValueAt(row, 0);
                ChuyenDe entity = dao.selectById(maCĐ);
                setModel(entity);
            }
            tabbedPane.setSelectedIndex(0);
        } catch (Exception e) {
            System.out.println("ThematicJInternalFrame(clickTable) - " + e);
        }
    }

    //Phương thức control
    private void control(ActionEvent evt) {
        List<ChuyenDe> list = dao.selectAll();
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
        ChuyenDe entity = dao.getEnityByPossition(String.valueOf(pos));
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
            ChuyenDe entity = getModel();
            if (dao.selectById(entity.getMaCD()) != null) {
                MessageDialogHelper.showMessageDialog(this, "Chuyên đề này đã tồn tại !", "Thông báo");
                txtMaChuyenDe.setText("");
            } else if (dao.insert(entity) > 0) {
                MessageDialogHelper.showMessageDialog(this, "Lưu chuyên đề thành công !", "Thông báo");
                news();
                fillTable();
                tabbedPane.setSelectedIndex(1);
            }
        } catch (Exception e) {
            System.out.println("ThematicJInternalFrame(save) - " + e);
        }
    }

    //Phương thức xoá
    private void delete() {
        try {
            StringBuilder sb = new StringBuilder();
            DataValidator.validateEmpty(txtMaChuyenDe, sb, "Mã chuyên đề không được để trống !");
            if (sb.length() > 0) {
                MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            } else {
                if (MessageDialogHelper.showComfirmDialog(this, "Bạn có muốn xoá chuyên đề không ?", "Cảnh báo")
                        == JOptionPane.NO_OPTION) {
                } else {
                    ChuyenDe entity = dao.selectById(txtMaChuyenDe.getText());
                    if (entity == null) {
                        MessageDialogHelper.showMessageDialog(this, "Không tìm thấy chuyên đề !", "Thông báo");
                        news();
                    } else if (dao.delete(txtMaChuyenDe.getText()) > 0) {
                        MessageDialogHelper.showMessageDialog(this, "Xoá dữ liệu chuyên đề thành công !", "Thông báo");
                        news();
                        fillTable();
                        tabbedPane.setSelectedIndex(1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ThematicJInternalFrame(delete) - " + e);
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
            ChuyenDe entity = getModel();
            if (dao.update(entity) > 0) {
                MessageDialogHelper.showMessageDialog(this, "Cập nhập chuyên đề thành công !", "Thông báo");
                news();
                fillTable();
                tabbedPane.setSelectedIndex(1);
            }
        } catch (Exception e) {
            System.out.println("ThematicJInternalFrame(update) - " + e);
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
        jPanel15 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaChuyenDe = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTenChuyenDe = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
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
        jtbQLChuyenDe = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("QUẢN LÝ CHUYÊN ĐỀ");
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
        jLabel1.setText("QUẢN LÝ CHUYÊN ĐỀ");
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
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.X_AXIS));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setMinimumSize(new java.awt.Dimension(300, 300));
        jPanel7.setPreferredSize(new java.awt.Dimension(300, 300));

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
        jPanel12.add(btnOpenFile);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(83, 83, 83))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.add(jPanel7);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setMinimumSize(new java.awt.Dimension(550, 0));
        jPanel10.setPreferredSize(new java.awt.Dimension(550, 0));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.Y_AXIS));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setMinimumSize(new java.awt.Dimension(550, 90));
        jPanel13.setPreferredSize(new java.awt.Dimension(550, 90));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Mã chuyên đề");
        jLabel4.setMaximumSize(new java.awt.Dimension(72, 20));
        jLabel4.setMinimumSize(new java.awt.Dimension(72, 20));
        jLabel4.setPreferredSize(new java.awt.Dimension(72, 20));
        jPanel15.add(jLabel4, java.awt.BorderLayout.CENTER);

        txtMaChuyenDe.setBackground(new java.awt.Color(255, 255, 255));
        txtMaChuyenDe.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtMaChuyenDe.setForeground(new java.awt.Color(0, 0, 0));
        txtMaChuyenDe.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtMaChuyenDe.setCaretColor(new java.awt.Color(0, 0, 0));
        txtMaChuyenDe.setMinimumSize(new java.awt.Dimension(4, 45));
        txtMaChuyenDe.setPreferredSize(new java.awt.Dimension(4, 45));
        jPanel15.add(txtMaChuyenDe, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                .addGap(57, 57, 57))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jPanel10.add(jPanel13);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setMinimumSize(new java.awt.Dimension(550, 90));
        jPanel16.setPreferredSize(new java.awt.Dimension(550, 90));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Tên chuyên đề");
        jLabel5.setMaximumSize(new java.awt.Dimension(72, 20));
        jLabel5.setMinimumSize(new java.awt.Dimension(72, 20));
        jLabel5.setPreferredSize(new java.awt.Dimension(72, 20));
        jPanel17.add(jLabel5, java.awt.BorderLayout.CENTER);

        txtTenChuyenDe.setBackground(new java.awt.Color(255, 255, 255));
        txtTenChuyenDe.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtTenChuyenDe.setForeground(new java.awt.Color(0, 0, 0));
        txtTenChuyenDe.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTenChuyenDe.setCaretColor(new java.awt.Color(0, 0, 0));
        txtTenChuyenDe.setMinimumSize(new java.awt.Dimension(4, 45));
        txtTenChuyenDe.setPreferredSize(new java.awt.Dimension(4, 45));
        jPanel17.add(txtTenChuyenDe, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                .addGap(57, 57, 57))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jPanel10.add(jPanel16);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setMinimumSize(new java.awt.Dimension(550, 90));
        jPanel27.setPreferredSize(new java.awt.Dimension(550, 90));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setLayout(new java.awt.BorderLayout());

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Thời lượng");
        jLabel6.setMaximumSize(new java.awt.Dimension(72, 20));
        jLabel6.setMinimumSize(new java.awt.Dimension(72, 20));
        jLabel6.setPreferredSize(new java.awt.Dimension(72, 20));
        jPanel28.add(jLabel6, java.awt.BorderLayout.CENTER);

        txtThoiLuong.setBackground(new java.awt.Color(255, 255, 255));
        txtThoiLuong.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtThoiLuong.setForeground(new java.awt.Color(0, 0, 0));
        txtThoiLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtThoiLuong.setCaretColor(new java.awt.Color(0, 0, 0));
        txtThoiLuong.setMinimumSize(new java.awt.Dimension(4, 45));
        txtThoiLuong.setPreferredSize(new java.awt.Dimension(4, 45));
        jPanel28.add(txtThoiLuong, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                .addGap(57, 57, 57))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jPanel10.add(jPanel27);

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setMinimumSize(new java.awt.Dimension(550, 90));
        jPanel29.setPreferredSize(new java.awt.Dimension(550, 90));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setLayout(new java.awt.BorderLayout());

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Học phí");
        jLabel7.setMaximumSize(new java.awt.Dimension(72, 20));
        jLabel7.setMinimumSize(new java.awt.Dimension(72, 20));
        jLabel7.setPreferredSize(new java.awt.Dimension(72, 20));
        jPanel30.add(jLabel7, java.awt.BorderLayout.CENTER);

        txtHocPhi.setBackground(new java.awt.Color(255, 255, 255));
        txtHocPhi.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtHocPhi.setForeground(new java.awt.Color(0, 0, 0));
        txtHocPhi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtHocPhi.setCaretColor(new java.awt.Color(0, 0, 0));
        txtHocPhi.setMinimumSize(new java.awt.Dimension(4, 45));
        txtHocPhi.setPreferredSize(new java.awt.Dimension(4, 45));
        jPanel30.add(txtHocPhi, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                .addGap(57, 57, 57))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

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
        jLabel2.setText("Mô tả");
        jLabel2.setMaximumSize(new java.awt.Dimension(64, 30));
        jLabel2.setMinimumSize(new java.awt.Dimension(64, 30));
        jLabel2.setPreferredSize(new java.awt.Dimension(64, 30));
        jPanel5.add(jLabel2, java.awt.BorderLayout.CENTER);

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jScrollPane2.setMinimumSize(new java.awt.Dimension(100, 100));

        txtMoTa.setBackground(new java.awt.Color(255, 255, 255));
        txtMoTa.setColumns(20);
        txtMoTa.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtMoTa.setForeground(new java.awt.Color(0, 0, 0));
        txtMoTa.setRows(5);
        txtMoTa.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        txtMoTa.setCaretColor(new java.awt.Color(0, 0, 0));
        txtMoTa.setMinimumSize(new java.awt.Dimension(0, 75));
        txtMoTa.setSelectedTextColor(new java.awt.Color(102, 102, 102));
        jScrollPane2.setViewportView(txtMoTa);

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
                btnFirstActionPerformed(evt);
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
                btnFirstActionPerformed(evt);
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
                btnFirstActionPerformed(evt);
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
                btnFirstActionPerformed(evt);
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
                btnFirstActionPerformed(evt);
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
                btnFirstActionPerformed(evt);
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
                btnFirstActionPerformed(evt);
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
                btnFirstActionPerformed(evt);
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

        jtbQLChuyenDe.setBackground(new java.awt.Color(255, 255, 255));
        jtbQLChuyenDe.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jtbQLChuyenDe.setForeground(new java.awt.Color(0, 0, 0));
        jtbQLChuyenDe.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbQLChuyenDe.setGridColor(new java.awt.Color(204, 204, 204));
        jtbQLChuyenDe.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jtbQLChuyenDe.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jtbQLChuyenDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbQLChuyenDeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbQLChuyenDe);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab("DANH SÁCH", new javax.swing.ImageIcon(getClass().getResource("/icon/list2-32.png")), jPanel4); // NOI18N

        jPanel2.add(tabbedPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenFileActionPerformed
        openFile();
    }//GEN-LAST:event_btnOpenFileActionPerformed

    private void jtbQLChuyenDeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbQLChuyenDeMouseClicked
        clickTable();
    }//GEN-LAST:event_jtbQLChuyenDeMouseClicked

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        control(evt);
    }//GEN-LAST:event_btnFirstActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnOpenFile;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtbQLChuyenDe;
    private javax.swing.JLabel lbHinh;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtMaChuyenDe;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenChuyenDe;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables
}
