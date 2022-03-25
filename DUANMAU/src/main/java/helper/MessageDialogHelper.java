package helper;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class MessageDialogHelper {

    private static String file;
    private static Icon icon;

    /**
     * showMessage
     *
     * @param parent
     * @param content
     * @param title
     */
    public static void showMessageDialog(Component parent, String content, String title) {
        file = "/icon/google_alerts-64.png";
        icon = new ImageIcon(MessageDialogHelper.class.getResource(file));
        JOptionPane.showMessageDialog(parent, content, title, 0, icon);
    }

    /**
     * showError
     *
     * @param parent
     * @param content
     * @param title
     */
    public static void showErrorDialog(Component parent, String content, String title) {
        file = "/icon/error-64.png";
        icon = new ImageIcon(MessageDialogHelper.class.getResource(file));
        JOptionPane.showMessageDialog(parent, content, title, 0, icon);
    }

    /**
     * showComfirm
     *
     * @param parent
     * @param content
     * @param title
     * @return int
     */
    public static int showComfirmDialog(Component parent, String content, String title) {
        file = "/icon/question-64.png";
        icon = new ImageIcon(MessageDialogHelper.class.getResource(file));
        int choose = JOptionPane.showConfirmDialog(parent, content, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, icon);
        return choose;
    }
}
