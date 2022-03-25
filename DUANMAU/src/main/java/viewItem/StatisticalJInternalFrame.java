package viewItem;

import dao.KhoaHocDAO;
import dao.ThongKeDAO;
import helper.ShareHelper;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import model.KhoaHoc;

/**
 *
 * @author ACER
 */
public class StatisticalJInternalFrame extends javax.swing.JInternalFrame {

    /**
     * Các biến cục bộ
     */
    private DefaultTableModel tableModel = new DefaultTableModel();
    private final ThongKeDAO dao = new ThongKeDAO();
    private final KhoaHocDAO daoKH = new KhoaHocDAO();

    /**
     * Creates new form StaffJInternalFrame
     *
     * @param index
     */
    public StatisticalJInternalFrame(int index) {
        initComponents();
        tabbedPane.setSelectedIndex(index);
        setFrameIcon(ShareHelper.APP_ICON_1);
        initNguoiHoc();
        initBangDiem();
        initTongHop();
        initDoanhThu();
        fillComboBoxkhoaHoc();
        fillTableNguoiHoc();
        fillTableTongHop();
        fillTableBangDiem();
        fillComboBoxNam();
        fillTableDoanhThu();
    }

    public Integer maKH;

    //Phương thức tạo bảng
    private void initNguoiHoc() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Năm", "Số người học", "Đầu tiên", "Sau cùng"});
        jtbNguoiHoc.setModel(tableModel);
    }

    private void initBangDiem() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã NH", "Họ và tên", "Điểm", "Xếp loại"});
        jtbBangDiem.setModel(tableModel);
    }

    private void initTongHop() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Chuyên đề", "Tổng số học viên", "Cao nhất", "Thấp nhất"});
        jtbTongHopDiem.setModel(tableModel);
    }

    private void initDoanhThu() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Chuyên đề", "Số khoá", "Số học viên", "Doanh thu", "HP cao nhất", "HP thấp nhất", "HP trung bình"});
        jtbDoanhThu.setModel(tableModel);
    }

    //Phương thức đổ dữ lệu từ data vào cbx
    private void fillComboBoxkhoaHoc() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxKhoaHoc.getModel(); //kết nối cbo với model
        model.removeAllElements(); //xóa tất cả item
        List<KhoaHoc> list = daoKH.selectAll();
        for (KhoaHoc kh : list) {
            model.addElement(kh.getTenCD() + "-" + kh.getMaKH());
            maKH = kh.getMaKH();
        }
        cbxKhoaHoc.setSelectedIndex(0);
    }

    private void fillComboBoxNam() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxNam.getModel(); //kết nối cbo với model
        model.removeAllElements(); //xóa tất cả item
        List<Integer> list = dao.selectNam();
        for (Integer nam : list) {
            model.addElement(nam);
        }
        cbxNam.setSelectedIndex(0);
    }

    //Phương thức đổ dữ lệu từ data vào bảng
    private void fillTableNguoiHoc() {
        try {
            DefaultTableModel model = (DefaultTableModel) jtbNguoiHoc.getModel();
            model.setRowCount(0);
            for (Object[] row : dao.selectNguoiHoc()) {
                model.addRow(row);
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            System.out.println("StatisticalJInternalFrame(fillTableNguoiHoc) - " + e);
        }
    }

    private void fillTableTongHop() {
        try {
            DefaultTableModel model = (DefaultTableModel) jtbTongHopDiem.getModel();
            model.setRowCount(0);
            for (Object[] row : dao.selectTongHopDiem()) {
                model.addRow(row);
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            System.out.println("StatisticalJInternalFrame(fillTableTongHop) - " + e);
        }
    }

    private void fillTableBangDiem() {
        try {
            DefaultTableModel model = (DefaultTableModel) jtbBangDiem.getModel();
            model.setRowCount(0);
            String kh = (String) cbxKhoaHoc.getSelectedItem();
            String[] output = kh.split("-");
            for (Object[] row : dao.selectDiemKhoa(output[1])) {
                model.addRow(row);
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            System.out.println("StatisticalJInternalFrame(fillTableBangDiem) - " + e);
        }
    }

    private void fillTableDoanhThu() {
        try {
            DefaultTableModel model = (DefaultTableModel) jtbDoanhThu.getModel();
            model.setRowCount(0);
            String nam = cbxNam.getSelectedItem().toString();
            List<Object[]> list = dao.selectDoanhThu(nam);
            for (Object[] row : list) {
                model.addRow(row);
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            System.out.println("StatisticalJInternalFrame(fillTableDoanhThu) - " + e);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbNguoiHoc = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbBangDiem = new javax.swing.JTable();
        jPanel48 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        cbxKhoaHoc = new javax.swing.JComboBox<>();
        jPanel51 = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtbTongHopDiem = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtbDoanhThu = new javax.swing.JTable();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cbxNam = new javax.swing.JComboBox<>();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        btnDoanhThu = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("TỔNG HỢP THỐNG KÊ");
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
        jLabel1.setText("TỔNG HỢP THỐNG KÊ");
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(1142, 666));
        jPanel2.setLayout(new java.awt.BorderLayout());

        tabbedPane.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setDoubleBuffered(false);
        jPanel3.setFont(new java.awt.Font("DialogInput", 3, 20)); // NOI18N
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);

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
        jScrollPane2.setViewportView(jtbNguoiHoc);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab("NGƯỜI HỌC", new javax.swing.ImageIcon(getClass().getResource("/icon/student-32.png")), jPanel3); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setFont(new java.awt.Font("DialogInput", 3, 20)); // NOI18N
        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        jtbBangDiem.setBackground(new java.awt.Color(255, 255, 255));
        jtbBangDiem.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jtbBangDiem.setForeground(new java.awt.Color(0, 0, 0));
        jtbBangDiem.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbBangDiem.setGridColor(new java.awt.Color(204, 204, 204));
        jtbBangDiem.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jtbBangDiem.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jtbBangDiem);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel48.setLayout(new javax.swing.BoxLayout(jPanel48, javax.swing.BoxLayout.X_AXIS));

        jPanel49.setBackground(new java.awt.Color(255, 255, 255));

        jPanel50.setBackground(new java.awt.Color(255, 255, 255));
        jPanel50.setLayout(new java.awt.BorderLayout());

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("KHOÁ HỌC");
        jPanel50.add(jLabel13, java.awt.BorderLayout.PAGE_START);

        cbxKhoaHoc.setBackground(new java.awt.Color(255, 255, 255));
        cbxKhoaHoc.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cbxKhoaHoc.setForeground(new java.awt.Color(0, 0, 0));
        jPanel50.add(cbxKhoaHoc, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel49Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                .addGap(49, 49, 49))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel50, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
        );

        jPanel48.add(jPanel49);

        jPanel51.setBackground(new java.awt.Color(255, 255, 255));

        jPanel52.setBackground(new java.awt.Color(255, 255, 255));
        jPanel52.setLayout(new java.awt.BorderLayout());

        btnTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(0, 0, 0));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search-48.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorder(null);
        btnTimKiem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel52.add(btnTimKiem, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 383, Short.MAX_VALUE)
            .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel51Layout.createSequentialGroup()
                    .addGap(94, 94, 94)
                    .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .addGap(86, 86, 86)))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
            .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel51Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel48.add(jPanel51);

        jPanel4.add(jPanel48, java.awt.BorderLayout.PAGE_START);

        tabbedPane.addTab("BẢNG ĐIỂM", new javax.swing.ImageIcon(getClass().getResource("/icon/ball_point_pen-32.png")), jPanel4); // NOI18N

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setDoubleBuffered(false);
        jPanel11.setFont(new java.awt.Font("DialogInput", 3, 20)); // NOI18N
        jPanel11.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(null);

        jtbTongHopDiem.setBackground(new java.awt.Color(255, 255, 255));
        jtbTongHopDiem.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jtbTongHopDiem.setForeground(new java.awt.Color(0, 0, 0));
        jtbTongHopDiem.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbTongHopDiem.setGridColor(new java.awt.Color(204, 204, 204));
        jtbTongHopDiem.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jtbTongHopDiem.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane3.setViewportView(jtbTongHopDiem);

        jPanel11.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab("TỔNG HỢP ĐIỂM", new javax.swing.ImageIcon(getClass().getResource("/icon/list2-32.png")), jPanel11); // NOI18N

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setDoubleBuffered(false);
        jPanel12.setFont(new java.awt.Font("DialogInput", 3, 20)); // NOI18N
        jPanel12.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(null);

        jtbDoanhThu.setBackground(new java.awt.Color(255, 255, 255));
        jtbDoanhThu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jtbDoanhThu.setForeground(new java.awt.Color(0, 0, 0));
        jtbDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbDoanhThu.setGridColor(new java.awt.Color(204, 204, 204));
        jtbDoanhThu.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jtbDoanhThu.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane4.setViewportView(jtbDoanhThu);

        jPanel12.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel43.setLayout(new javax.swing.BoxLayout(jPanel43, javax.swing.BoxLayout.X_AXIS));

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));

        jPanel47.setBackground(new java.awt.Color(255, 255, 255));
        jPanel47.setLayout(new java.awt.BorderLayout());

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("NĂM");
        jPanel47.add(jLabel12, java.awt.BorderLayout.PAGE_START);

        cbxNam.setBackground(new java.awt.Color(255, 255, 255));
        cbxNam.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cbxNam.setForeground(new java.awt.Color(0, 0, 0));
        jPanel47.add(cbxNam, java.awt.BorderLayout.CENTER);

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

        btnDoanhThu.setBackground(new java.awt.Color(255, 255, 255));
        btnDoanhThu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnDoanhThu.setForeground(new java.awt.Color(0, 0, 0));
        btnDoanhThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search-48.png"))); // NOI18N
        btnDoanhThu.setText("Tìm kiếm");
        btnDoanhThu.setBorder(null);
        btnDoanhThu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoanhThuActionPerformed(evt);
            }
        });
        jPanel46.add(btnDoanhThu, java.awt.BorderLayout.CENTER);

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

        jPanel12.add(jPanel43, java.awt.BorderLayout.PAGE_START);

        tabbedPane.addTab("DOANH THU", new javax.swing.ImageIcon(getClass().getResource("/icon/moneybox-32.png")), jPanel12); // NOI18N

        jPanel2.add(tabbedPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed

        fillTableBangDiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnDoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoanhThuActionPerformed
        fillTableDoanhThu();
    }//GEN-LAST:event_btnDoanhThuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoanhThu;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cbxKhoaHoc;
    private javax.swing.JComboBox<String> cbxNam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jtbBangDiem;
    private javax.swing.JTable jtbDoanhThu;
    private javax.swing.JTable jtbNguoiHoc;
    private javax.swing.JTable jtbTongHopDiem;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
}
