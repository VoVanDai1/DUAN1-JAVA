package views;

import dao.NhanVienDAO;
import helper.DataValidator;
import helper.MessageDialogHelper;
import helper.ShareHelper;
import model.NhanVien;

/**
 *
 * @author ACER
 */
public class ChanePassJDialog extends javax.swing.JDialog {

    /**
     * Creates new form LoginJDialog
     */
    public ChanePassJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        NhanVien entity = ShareHelper.USER;
        txtTenDangNhap.setText(entity.getMa());
    }

    private void chanePass() {
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtPassOld, sb, "Vui lòng nhập mật khẩu cũ !");
        DataValidator.validateEmpty(txtPassNew, sb, "Vui lòng nhập mật khẩu mới !");
        DataValidator.validateEmpty(txtPassConfim, sb, "Vui lòng nhập xác nhận mật khẩu !");
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(this, sb.toString(), "Cảnh báo");
            return;
        }
        NhanVienDAO dao = new NhanVienDAO();
        try {
            NhanVien model = dao.checkPass(txtTenDangNhap.getText(), new String(txtPassOld.getPassword()));
            if (model == null) {
                MessageDialogHelper.showErrorDialog(this, "Sai mật khẩu cũ !", "Lỗi");
                return;
            }
            String passNew = new String(txtPassNew.getPassword());
            String passConfim = new String(txtPassConfim.getPassword());
            if (!passNew.equals(passConfim)) {
                MessageDialogHelper.showErrorDialog(this, "Xác nhận mật khẩu sai !", "Lỗi");
                return;
            }
            if (dao.updatePass(txtTenDangNhap.getText(), passNew) > 0) {
                MessageDialogHelper.showMessageDialog(this, "Đổi mật khẩu thành công !", "Thông báo");
                if (ShareHelper.logoff()) {
                    MessageDialogHelper.showMessageDialog(this, "Vui lòng đăng nhập lại ! ", "Thông báo");
                    this.dispose();
                }
                return;
            }
            MessageDialogHelper.showErrorDialog(this, "Đổi mật khẩu thất bại !", "Lỗi");
        } catch (Exception e) {
            System.out.println("ChanePassJDialog(init) - " + e);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txtTenDangNhap = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtPassOld = new javax.swing.JPasswordField();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtPassNew = new javax.swing.JPasswordField();
        jPanel14 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtPassConfim = new javax.swing.JPasswordField();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        btnDoiMatKhau = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        btnTroVe = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1037, 500));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1037, 500));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setMaximumSize(new java.awt.Dimension(500, 500));
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 500));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/FPT_banner.jpg"))); // NOI18N
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setMinimumSize(new java.awt.Dimension(537, 80));
        jPanel7.setPreferredSize(new java.awt.Dimension(537, 106));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setMinimumSize(new java.awt.Dimension(200, 40));
        jPanel10.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel10.setLayout(new java.awt.BorderLayout());

        txtTenDangNhap.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtTenDangNhap.setForeground(new java.awt.Color(51, 51, 51));
        txtTenDangNhap.setCaretColor(new java.awt.Color(0, 0, 0));
        jPanel10.add(txtTenDangNhap, java.awt.BorderLayout.CENTER);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Tên đăng nhập");
        jPanel10.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel4.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setMinimumSize(new java.awt.Dimension(537, 80));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setPreferredSize(new java.awt.Dimension(200, 50));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Mật khẩu cũ");
        jPanel11.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        txtPassOld.setBackground(new java.awt.Color(255, 255, 255));
        txtPassOld.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtPassOld.setForeground(new java.awt.Color(51, 51, 51));
        txtPassOld.setCaretColor(new java.awt.Color(0, 0, 0));
        jPanel11.add(txtPassOld, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel4.add(jPanel8);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setMinimumSize(new java.awt.Dimension(537, 80));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(200, 50));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Mật khẩu mới");
        jPanel19.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        txtPassNew.setBackground(new java.awt.Color(255, 255, 255));
        txtPassNew.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtPassNew.setForeground(new java.awt.Color(51, 51, 51));
        txtPassNew.setCaretColor(new java.awt.Color(0, 0, 0));
        jPanel19.add(txtPassNew, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel4.add(jPanel18);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setMinimumSize(new java.awt.Dimension(537, 80));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(200, 50));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Xác nhận mật khẩu");
        jPanel17.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        txtPassConfim.setBackground(new java.awt.Color(255, 255, 255));
        txtPassConfim.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtPassConfim.setForeground(new java.awt.Color(51, 51, 51));
        txtPassConfim.setCaretColor(new java.awt.Color(0, 0, 0));
        jPanel17.add(txtPassConfim, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel4.add(jPanel14);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(17, 67, 6));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ĐỔI MẬT KHẨU");
        jLabel2.setMaximumSize(new java.awt.Dimension(217, 80));
        jLabel2.setMinimumSize(new java.awt.Dimension(217, 80));
        jLabel2.setPreferredSize(new java.awt.Dimension(217, 80));
        jPanel5.add(jLabel2, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setMinimumSize(new java.awt.Dimension(537, 70));
        jPanel6.setPreferredSize(new java.awt.Dimension(537, 70));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel15.setPreferredSize(new java.awt.Dimension(200, 70));
        jPanel15.setLayout(new java.awt.BorderLayout());

        btnDoiMatKhau.setBackground(new java.awt.Color(255, 255, 255));
        btnDoiMatKhau.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnDoiMatKhau.setForeground(new java.awt.Color(0, 0, 0));
        btnDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/login-48.png"))); // NOI18N
        btnDoiMatKhau.setText("Đổi mật khẩu");
        btnDoiMatKhau.setBorder(null);
        btnDoiMatKhau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });
        jPanel15.add(btnDoiMatKhau, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel15);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel16.setPreferredSize(new java.awt.Dimension(200, 70));
        jPanel16.setLayout(new java.awt.BorderLayout());

        btnTroVe.setBackground(new java.awt.Color(255, 255, 255));
        btnTroVe.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnTroVe.setForeground(new java.awt.Color(0, 0, 0));
        btnTroVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/stop-48.png"))); // NOI18N
        btnTroVe.setText("Trở về");
        btnTroVe.setBorder(null);
        btnTroVe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTroVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTroVeActionPerformed(evt);
            }
        });
        jPanel16.add(btnTroVe, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel16);

        jPanel3.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel3);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTroVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTroVeActionPerformed

        this.dispose();
    }//GEN-LAST:event_btnTroVeActionPerformed

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
        chanePass();
    }//GEN-LAST:event_btnDoiMatKhauActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChanePassJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChanePassJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChanePassJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChanePassJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChanePassJDialog dialog = new ChanePassJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnTroVe;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPasswordField txtPassConfim;
    private javax.swing.JPasswordField txtPassNew;
    private javax.swing.JPasswordField txtPassOld;
    private javax.swing.JTextField txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}
