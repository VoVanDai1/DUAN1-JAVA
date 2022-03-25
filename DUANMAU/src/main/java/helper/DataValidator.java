package helper;

import java.awt.Color;
import java.util.regex.Pattern;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ACER
 */
public class DataValidator {

    /**
     * Kiểm tra JTextField có bằng rỗng
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateEmpty(JTextField field, StringBuilder sb, String errorMessage) {
        if (field.getText().equals("")) {
            sb.append(errorMessage).append("\n");
            field.setBackground(Color.yellow);
            field.requestFocus();
        } else {
            field.setBackground(Color.white);
        }
    }

    /**
     * Kiểm tra jTextArea có bằng rỗng
     *
     * @param jTextArea
     * @param sb
     * @param errorMessage
     */
    public static void validateEmpty(JTextArea jTextArea, StringBuilder sb, String errorMessage) {
        if (jTextArea.getText().equals("")) {
            sb.append(errorMessage).append("\n");
            jTextArea.setBackground(Color.yellow);
            jTextArea.requestFocus();
        } else {
            jTextArea.setBackground(Color.white);
        }
    }

    /**
     * Kiểm tra JPasswordField có bằng rỗng
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateEmpty(JPasswordField field, StringBuilder sb, String errorMessage) {
        String password = new String(field.getPassword());
        if (password.equals("")) {
            sb.append(errorMessage).append("\n");
            field.setBackground(Color.yellow);
            field.requestFocus();
        } else {
            field.setBackground(Color.white);
        }
    }

    /**
     * Kiểm tra định dạng email
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateEmail(JTextField field, StringBuilder sb, String errorMessage) {
        String emailPattern = "^[_A-Za-z0-9-\\\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        if (pattern.matcher(field.getText()).find()) {
            field.setBackground(Color.white);
        } else {
            sb.append(errorMessage).append("\n");
            field.setBackground(Color.yellow);
            field.requestFocus();
        }
    }

    /**
     * Kiểm tra định dạng mã người hoạc
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateCheckMANH(JTextField field, StringBuilder sb, String errorMessage) {
        String codePattern = "^(NH)\\d";
        Pattern pattern = Pattern.compile(codePattern);
        if (pattern.matcher(field.getText()).find()) {
            field.setBackground(Color.white);
        } else {
            sb.append(errorMessage).append("\n");
            field.setBackground(Color.yellow);
            field.requestFocus();
        }
    }

    /**
     * Kiểm tra định dạng mã nhân viên
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateCheckMANV(JTextField field, StringBuilder sb, String errorMessage) {
        String codePattern = "^(NV)\\d";
        Pattern pattern = Pattern.compile(codePattern);
        if (pattern.matcher(field.getText()).find()) {
            field.setBackground(Color.white);
        } else {
            sb.append(errorMessage).append("\n");
            field.setBackground(Color.yellow);
            field.requestFocus();
        }
    }

    /**
     * Kiểm tra chuỗi có phải là kiểu int
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateCheckInt(JTextField field, StringBuilder sb, String errorMessage) {
        try {
            int i = Integer.parseInt(field.getText());
            field.setBackground(Color.white);
        } catch (NumberFormatException e) {
            sb.append(errorMessage).append("\n");
            field.setBackground(Color.yellow);
            field.requestFocus();
        }
    }

    /**
     * Kiểm tra chuỗi có phải là kiểu float
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateCheckFloat(JTextField field, StringBuilder sb, String errorMessage) {
        try {
            float i = Float.valueOf(field.getText());
            field.setBackground(Color.white);
        } catch (NumberFormatException e) {
            sb.append(errorMessage).append("\n");
            field.setBackground(Color.yellow);
            field.requestFocus();
        }
    }

    /**
     * Xếp loại điểm cho người học
     *
     * @param diem
     * @return
     */
    public static String getRank(float diem) {
        String xepLoai = "Xuất sắc";
        if (diem < 0) {
            xepLoai = "Chưa nhập";

        } else if (diem < 3) {
            xepLoai = "Kém";

        } else if (diem < 5) {
            xepLoai = "Yếu";

        } else if (diem < 6.5) {

            xepLoai = "Trung bình";

        } else if (diem < 7.5) {
            xepLoai = "Khá";

        } else if (diem < 9) {
            xepLoai = "Giỏi";

        }
        return xepLoai;
    }
}
