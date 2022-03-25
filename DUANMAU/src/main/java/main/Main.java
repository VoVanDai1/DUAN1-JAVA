package main;

import helper.MessageDialogHelper;
import helper.ShareHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.NhanVien;
import viewItem.CourseJInternalFrame;
import viewItem.LearnerJInternalFrame;
import viewItem.StaffJInternalFrame;
import viewItem.StatisticalJInternalFrame;
import views.LoaddingJDialog;
import viewItem.ThematicJInternalFrame;
import views.ChanePassJDialog;
import views.InfoJDialog;
import views.LoginJDialog;

/**
 *
 * @author ACER
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        init();
    }

    private void init() {
        setIconImage(ShareHelper.APP_ICON); //đặt icon góc trên trái
        setExtendedState(JFrame.MAXIMIZED_BOTH);    //cho toàn màn hình 
        loaddingJDialog();
        loginJDialog();
        new Timer(1000, new ActionListener() {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");

            @Override
            public void actionPerformed(ActionEvent e) {
                lbDongHo.setText(sdf.format(new Date()));
            }
        }).start();
    }

    /**
     * Phương thức điều kiển chuyển đổi cửa sổ
     */
    private void openWindown(String cmd) {
        switch (cmd) {
            case "Kết thúc":
                exit();
                break;
            case "Exit":
                exit();
                break;
            case "Đăng nhập":
                logIn();
                break;
            case "Login":
                logIn();
                break;
            case "Đăng xuất":
                logOut();
                break;
            case "Đổi mật khẩu":
                chanePass();
                break;
            case "Người học":
                openLearner();
                break;
            case "QLNguoiHoc":
                openLearner();
                break;
            case "QLChuyenDe":
                openThematic();
                break;
            case "Chuyên đề":
                openThematic();
                break;
           case "Khoá học":
                openCourse();
                break;
           case "Nhân viên":
                openStaff();
                break;
           case "Người học từng năm":
                openStatistical(0);
                break;
           case "Bảng điểm khoá":
                openStatistical(1);
                break;
           case "Bảng điểm từng khoá học":
                openStatistical(2);
                break;
           case "Doanh thu từng chuyên đề":
                openStatistical(3);
                break;
           case "Giới thiệu sản phẩm":
                openInfo();
                break;
           case "GioiThieu":
                openInfo();
                break;
            default:
                throw new AssertionError();
        }
    }

    //Thoát
    private void exit() {
        if (MessageDialogHelper.showComfirmDialog(this, "Bạn có muốn thoát chương trình ?",
                "Thoát chương trình") == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    //Đăng xuất
    private void logOut() {
        if (ShareHelper.logoff()) {
            MessageDialogHelper.showMessageDialog(this, "Đã đăng xuất thành công !", "Thông báo");
            logIn();
        }
    }

    //Đăng nhập
    private void logIn() {
        NhanVien entity = ShareHelper.USER;
        if (entity != null) {
            MessageDialogHelper.showErrorDialog(this, "Bạn đã đăng nhập rồi !", "Thông báo");
            return;
        }
        for (JInternalFrame frmChild : jdesktop.getAllFrames()) {
            frmChild.dispose();
        }
        loginJDialog();
    }

    //Đổi mật khẩu
    private void chanePass() {
        NhanVien entity = ShareHelper.USER;
        if (entity == null) {
            MessageDialogHelper.showErrorDialog(this, "Bạn chưa đăng nhập !", "Thông báo");
            return;
        }
        for (JInternalFrame frmChild : jdesktop.getAllFrames()) {
            frmChild.dispose();
        }
        new ChanePassJDialog(this, true).setVisible(true);
    }

    //Quản lý chuyên đề
    private void openThematic() {
            ThematicJInternalFrame thematicJF = new ThematicJInternalFrame();
            openX(thematicJF);
    }

    //Quản lý người học
    private void openLearner() {
            LearnerJInternalFrame learnerJF = new LearnerJInternalFrame();
            openX(learnerJF);
    }
    
    //Quản lý khoá học
    private void openCourse() {
            CourseJInternalFrame courseJF = new CourseJInternalFrame();
            openX(courseJF);
    }
    
    //Quản lý nhân viên
    private void openStaff() {
            StaffJInternalFrame staffJF = new StaffJInternalFrame();
            openX(staffJF);
    }
    
    //Thống kê tổng hợp
    private void openStatistical(int index) {
            StatisticalJInternalFrame statisticalJF = new StatisticalJInternalFrame(index);
            openX(statisticalJF);
    }
    
    //Giới thiệu sản phẩm
    private void openInfo() {
        new InfoJDialog(this, true).setVisible(true);
    }

    /**
     * Hiễn thị các cửa sổ trước khi vào Main
     */
    private void loaddingJDialog() {
        new LoaddingJDialog(this, true).setVisible(true);
    }

    private void loginJDialog() {
        new LoginJDialog(this, true).setVisible(true);
    }

    public void openX(JInternalFrame x) {
        for (JInternalFrame frmChild : jdesktop.getAllFrames()) {
            frmChild.dispose();
        }
        jdesktop.add(x);
        x.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnDangNhap = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        btnQLNguoiHoc = new javax.swing.JButton();
        btnQLChuyenDe = new javax.swing.JButton();
        btnHuongDan = new javax.swing.JButton();
        btnGioiThieu = new javax.swing.JButton();
        jdesktop = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        lbDongHo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmDangNhap = new javax.swing.JMenuItem();
        jmDangXuat = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmDoiMatKhau = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jmThoat = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmQLNguoiHoc = new javax.swing.JMenuItem();
        jmChuyenDe = new javax.swing.JMenuItem();
        jmKhoaHoc = new javax.swing.JMenuItem();
        jmNhanVien = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jmNguoiHocTheoNam = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jmBangDiemKhoa = new javax.swing.JMenuItem();
        jmBangDiemTungKhoa = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jmDoanhThu = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jmHuongDan = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jmGioiThieu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HỆ THỐNG QUẢN LÝ SINH VIÊN");
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1100, 600));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);
        jToolBar1.setToolTipText("");
        jToolBar1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jToolBar1.setPreferredSize(new java.awt.Dimension(1100, 70));

        btnDangNhap.setBackground(new java.awt.Color(255, 255, 255));
        btnDangNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/login-48.png"))); // NOI18N
        btnDangNhap.setActionCommand("Login");
        btnDangNhap.setBorder(null);
        btnDangNhap.setBorderPainted(false);
        btnDangNhap.setFocusable(false);
        btnDangNhap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDangNhap.setMaximumSize(new java.awt.Dimension(100, 70));
        btnDangNhap.setMinimumSize(new java.awt.Dimension(100, 70));
        btnDangNhap.setPreferredSize(new java.awt.Dimension(100, 70));
        btnDangNhap.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDangNhap);

        btnThoat.setBackground(new java.awt.Color(255, 255, 255));
        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/stop-48.png"))); // NOI18N
        btnThoat.setActionCommand("Exit");
        btnThoat.setBorder(null);
        btnThoat.setBorderPainted(false);
        btnThoat.setFocusable(false);
        btnThoat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThoat.setMaximumSize(new java.awt.Dimension(100, 70));
        btnThoat.setMinimumSize(new java.awt.Dimension(100, 70));
        btnThoat.setPreferredSize(new java.awt.Dimension(100, 70));
        btnThoat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnThoat);

        btnQLNguoiHoc.setBackground(new java.awt.Color(255, 255, 255));
        btnQLNguoiHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/student-48.png"))); // NOI18N
        btnQLNguoiHoc.setActionCommand("QLNguoiHoc");
        btnQLNguoiHoc.setBorder(null);
        btnQLNguoiHoc.setBorderPainted(false);
        btnQLNguoiHoc.setFocusable(false);
        btnQLNguoiHoc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQLNguoiHoc.setMaximumSize(new java.awt.Dimension(100, 70));
        btnQLNguoiHoc.setMinimumSize(new java.awt.Dimension(100, 70));
        btnQLNguoiHoc.setPreferredSize(new java.awt.Dimension(100, 70));
        btnQLNguoiHoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnQLNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnQLNguoiHoc);

        btnQLChuyenDe.setBackground(new java.awt.Color(255, 255, 255));
        btnQLChuyenDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/list-48.png"))); // NOI18N
        btnQLChuyenDe.setActionCommand("QLChuyenDe");
        btnQLChuyenDe.setBorder(null);
        btnQLChuyenDe.setBorderPainted(false);
        btnQLChuyenDe.setFocusable(false);
        btnQLChuyenDe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQLChuyenDe.setMaximumSize(new java.awt.Dimension(100, 70));
        btnQLChuyenDe.setMinimumSize(new java.awt.Dimension(100, 70));
        btnQLChuyenDe.setPreferredSize(new java.awt.Dimension(100, 70));
        btnQLChuyenDe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnQLChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnQLChuyenDe);

        btnHuongDan.setBackground(new java.awt.Color(255, 255, 255));
        btnHuongDan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/web_shield-48.png"))); // NOI18N
        btnHuongDan.setActionCommand("HuongDan");
        btnHuongDan.setBorder(null);
        btnHuongDan.setBorderPainted(false);
        btnHuongDan.setFocusable(false);
        btnHuongDan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHuongDan.setMaximumSize(new java.awt.Dimension(100, 70));
        btnHuongDan.setMinimumSize(new java.awt.Dimension(100, 70));
        btnHuongDan.setPreferredSize(new java.awt.Dimension(100, 70));
        btnHuongDan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHuongDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnHuongDan);

        btnGioiThieu.setBackground(new java.awt.Color(255, 255, 255));
        btnGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/about-48.png"))); // NOI18N
        btnGioiThieu.setActionCommand("GioiThieu");
        btnGioiThieu.setBorder(null);
        btnGioiThieu.setBorderPainted(false);
        btnGioiThieu.setFocusable(false);
        btnGioiThieu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGioiThieu.setMaximumSize(new java.awt.Dimension(100, 70));
        btnGioiThieu.setMinimumSize(new java.awt.Dimension(100, 70));
        btnGioiThieu.setPreferredSize(new java.awt.Dimension(100, 70));
        btnGioiThieu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGioiThieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGioiThieu);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jdesktop.setBackground(new java.awt.Color(255, 255, 255));
        jdesktop.setMinimumSize(new java.awt.Dimension(1100, 500));
        jdesktop.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
        getContentPane().add(jdesktop, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1100, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 50));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 470, 0));

        lbDongHo.setBackground(new java.awt.Color(255, 255, 255));
        lbDongHo.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lbDongHo.setForeground(new java.awt.Color(153, 0, 0));
        lbDongHo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/alarm_clock-48.png"))); // NOI18N
        lbDongHo.setText("10:50");
        jPanel1.add(lbDongHo);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/system_information-48.png"))); // NOI18N
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setBorder(null);
        jMenuBar1.setForeground(new java.awt.Color(0, 0, 0));
        jMenuBar1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jMenuBar1.setPreferredSize(new java.awt.Dimension(1100, 40));

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setBorder(null);
        jMenu1.setForeground(new java.awt.Color(0, 0, 0));
        jMenu1.setText("Hệ thống");
        jMenu1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jMenu1.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenu1.setName(""); // NOI18N
        jMenu1.setOpaque(false);
        jMenu1.setPreferredSize(new java.awt.Dimension(100, 40));

        jmDangNhap.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        jmDangNhap.setBackground(new java.awt.Color(255, 255, 255));
        jmDangNhap.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmDangNhap.setForeground(new java.awt.Color(51, 51, 51));
        jmDangNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/login-26.png"))); // NOI18N
        jmDangNhap.setText("Đăng nhập");
        jmDangNhap.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmDangNhap.setPreferredSize(new java.awt.Dimension(220, 40));
        jmDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu1.add(jmDangNhap);

        jmDangXuat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        jmDangXuat.setBackground(new java.awt.Color(255, 255, 255));
        jmDangXuat.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmDangXuat.setForeground(new java.awt.Color(51, 51, 51));
        jmDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout-26.png"))); // NOI18N
        jmDangXuat.setText("Đăng xuất");
        jmDangXuat.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmDangXuat.setPreferredSize(new java.awt.Dimension(220, 40));
        jmDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu1.add(jmDangXuat);

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.add(jSeparator1);

        jmDoiMatKhau.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        jmDoiMatKhau.setBackground(new java.awt.Color(255, 255, 255));
        jmDoiMatKhau.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmDoiMatKhau.setForeground(new java.awt.Color(51, 51, 51));
        jmDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/password1-26.png"))); // NOI18N
        jmDoiMatKhau.setText("Đổi mật khẩu");
        jmDoiMatKhau.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmDoiMatKhau.setPreferredSize(new java.awt.Dimension(220, 40));
        jmDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu1.add(jmDoiMatKhau);

        jSeparator2.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.add(jSeparator2);

        jmThoat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        jmThoat.setBackground(new java.awt.Color(255, 255, 255));
        jmThoat.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmThoat.setForeground(new java.awt.Color(51, 51, 51));
        jmThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/stop-26.png"))); // NOI18N
        jmThoat.setText("Kết thúc");
        jmThoat.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmThoat.setPreferredSize(new java.awt.Dimension(220, 40));
        jmThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu1.add(jmThoat);

        jMenuBar1.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(255, 255, 255));
        jMenu2.setBorder(null);
        jMenu2.setForeground(new java.awt.Color(0, 0, 0));
        jMenu2.setText("Quản lý");
        jMenu2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jMenu2.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenu2.setName(""); // NOI18N
        jMenu2.setOpaque(false);
        jMenu2.setPreferredSize(new java.awt.Dimension(100, 40));

        jmQLNguoiHoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jmQLNguoiHoc.setBackground(new java.awt.Color(255, 255, 255));
        jmQLNguoiHoc.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmQLNguoiHoc.setForeground(new java.awt.Color(51, 51, 51));
        jmQLNguoiHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/student-26.png"))); // NOI18N
        jmQLNguoiHoc.setText("Người học");
        jmQLNguoiHoc.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmQLNguoiHoc.setPreferredSize(new java.awt.Dimension(230, 40));
        jmQLNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu2.add(jmQLNguoiHoc);

        jmChuyenDe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jmChuyenDe.setBackground(new java.awt.Color(255, 255, 255));
        jmChuyenDe.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmChuyenDe.setForeground(new java.awt.Color(51, 51, 51));
        jmChuyenDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/list-26.png"))); // NOI18N
        jmChuyenDe.setText("Chuyên đề");
        jmChuyenDe.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmChuyenDe.setPreferredSize(new java.awt.Dimension(230, 40));
        jmChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu2.add(jmChuyenDe);

        jmKhoaHoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jmKhoaHoc.setBackground(new java.awt.Color(255, 255, 255));
        jmKhoaHoc.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmKhoaHoc.setForeground(new java.awt.Color(51, 51, 51));
        jmKhoaHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/lock-26.png"))); // NOI18N
        jmKhoaHoc.setText("Khoá học");
        jmKhoaHoc.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmKhoaHoc.setPreferredSize(new java.awt.Dimension(230, 40));
        jmKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu2.add(jmKhoaHoc);

        jmNhanVien.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jmNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        jmNhanVien.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmNhanVien.setForeground(new java.awt.Color(51, 51, 51));
        jmNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/group-26.png"))); // NOI18N
        jmNhanVien.setText("Nhân viên");
        jmNhanVien.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmNhanVien.setPreferredSize(new java.awt.Dimension(230, 40));
        jmNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu2.add(jmNhanVien);

        jMenuBar1.add(jMenu2);

        jMenu3.setBackground(new java.awt.Color(255, 255, 255));
        jMenu3.setBorder(null);
        jMenu3.setForeground(new java.awt.Color(0, 0, 0));
        jMenu3.setText("Thống kê");
        jMenu3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jMenu3.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenu3.setName(""); // NOI18N
        jMenu3.setOpaque(false);
        jMenu3.setPreferredSize(new java.awt.Dimension(100, 40));

        jmNguoiHocTheoNam.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jmNguoiHocTheoNam.setBackground(new java.awt.Color(255, 255, 255));
        jmNguoiHocTheoNam.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmNguoiHocTheoNam.setForeground(new java.awt.Color(51, 51, 51));
        jmNguoiHocTheoNam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user_folder-26.png"))); // NOI18N
        jmNguoiHocTheoNam.setText("Người học từng năm");
        jmNguoiHocTheoNam.setToolTipText("");
        jmNguoiHocTheoNam.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmNguoiHocTheoNam.setPreferredSize(new java.awt.Dimension(300, 40));
        jmNguoiHocTheoNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu3.add(jmNguoiHocTheoNam);

        jSeparator3.setBackground(new java.awt.Color(255, 255, 255));
        jMenu3.add(jSeparator3);

        jmBangDiemKhoa.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jmBangDiemKhoa.setBackground(new java.awt.Color(255, 255, 255));
        jmBangDiemKhoa.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmBangDiemKhoa.setForeground(new java.awt.Color(51, 51, 51));
        jmBangDiemKhoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ball_point_pen-26.png"))); // NOI18N
        jmBangDiemKhoa.setText("Bảng điểm khoá");
        jmBangDiemKhoa.setToolTipText("");
        jmBangDiemKhoa.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmBangDiemKhoa.setPreferredSize(new java.awt.Dimension(300, 40));
        jmBangDiemKhoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu3.add(jmBangDiemKhoa);

        jmBangDiemTungKhoa.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jmBangDiemTungKhoa.setBackground(new java.awt.Color(255, 255, 255));
        jmBangDiemTungKhoa.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmBangDiemTungKhoa.setForeground(new java.awt.Color(51, 51, 51));
        jmBangDiemTungKhoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/share_point-26.png"))); // NOI18N
        jmBangDiemTungKhoa.setText("Bảng điểm từng khoá học");
        jmBangDiemTungKhoa.setToolTipText("");
        jmBangDiemTungKhoa.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmBangDiemTungKhoa.setPreferredSize(new java.awt.Dimension(300, 40));
        jmBangDiemTungKhoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu3.add(jmBangDiemTungKhoa);

        jSeparator4.setBackground(new java.awt.Color(255, 255, 255));
        jMenu3.add(jSeparator4);

        jmDoanhThu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jmDoanhThu.setBackground(new java.awt.Color(255, 255, 255));
        jmDoanhThu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmDoanhThu.setForeground(new java.awt.Color(51, 51, 51));
        jmDoanhThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/moneybox-26.png"))); // NOI18N
        jmDoanhThu.setText("Doanh thu từng chuyên đề");
        jmDoanhThu.setToolTipText("");
        jmDoanhThu.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmDoanhThu.setPreferredSize(new java.awt.Dimension(300, 40));
        jmDoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu3.add(jmDoanhThu);

        jMenuBar1.add(jMenu3);

        jMenu4.setBackground(new java.awt.Color(255, 255, 255));
        jMenu4.setBorder(null);
        jMenu4.setForeground(new java.awt.Color(0, 0, 0));
        jMenu4.setText("Trợ giúp");
        jMenu4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jMenu4.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenu4.setName(""); // NOI18N
        jMenu4.setOpaque(false);
        jMenu4.setPreferredSize(new java.awt.Dimension(100, 40));

        jmHuongDan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jmHuongDan.setBackground(new java.awt.Color(255, 255, 255));
        jmHuongDan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmHuongDan.setForeground(new java.awt.Color(51, 51, 51));
        jmHuongDan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/web_shield-26.png"))); // NOI18N
        jmHuongDan.setText("Hướng dẫn sử dụng");
        jmHuongDan.setToolTipText("");
        jmHuongDan.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmHuongDan.setPreferredSize(new java.awt.Dimension(280, 40));
        jmHuongDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu4.add(jmHuongDan);

        jSeparator5.setBackground(new java.awt.Color(255, 255, 255));
        jMenu4.add(jSeparator5);

        jmGioiThieu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jmGioiThieu.setBackground(new java.awt.Color(255, 255, 255));
        jmGioiThieu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jmGioiThieu.setForeground(new java.awt.Color(51, 51, 51));
        jmGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/about-26.png"))); // NOI18N
        jmGioiThieu.setText("Giới thiệu sản phẩm");
        jmGioiThieu.setToolTipText("");
        jmGioiThieu.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jmGioiThieu.setPreferredSize(new java.awt.Dimension(280, 40));
        jmGioiThieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLChuyenDeActionPerformed(evt);
            }
        });
        jMenu4.add(jmGioiThieu);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnQLChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLChuyenDeActionPerformed
        // TODO add your handling code here:
        String cmd = evt.getActionCommand();
        openWindown(cmd);
    }//GEN-LAST:event_btnQLChuyenDeActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JButton btnGioiThieu;
    private javax.swing.JButton btnHuongDan;
    private javax.swing.JButton btnQLChuyenDe;
    private javax.swing.JButton btnQLNguoiHoc;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JDesktopPane jdesktop;
    private javax.swing.JMenuItem jmBangDiemKhoa;
    private javax.swing.JMenuItem jmBangDiemTungKhoa;
    private javax.swing.JMenuItem jmChuyenDe;
    private javax.swing.JMenuItem jmDangNhap;
    private javax.swing.JMenuItem jmDangXuat;
    private javax.swing.JMenuItem jmDoanhThu;
    private javax.swing.JMenuItem jmDoiMatKhau;
    private javax.swing.JMenuItem jmGioiThieu;
    private javax.swing.JMenuItem jmHuongDan;
    private javax.swing.JMenuItem jmKhoaHoc;
    private javax.swing.JMenuItem jmNguoiHocTheoNam;
    private javax.swing.JMenuItem jmNhanVien;
    private javax.swing.JMenuItem jmQLNguoiHoc;
    private javax.swing.JMenuItem jmThoat;
    private javax.swing.JLabel lbDongHo;
    // End of variables declaration//GEN-END:variables
}
