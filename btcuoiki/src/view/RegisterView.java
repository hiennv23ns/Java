package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPasswordField textPass;
    private JTextField textName;
    private JTextField textFullName;
    private JButton btnRegister;
    private JButton btnBackToLogin;

    // Database connection details
    private static final String DB_URL = "jdbc:sqlserver://DESKTOP-40UK884:1433;databaseName=QLTV;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "sa"; 
    private static final String DB_PASSWORD = "123456"; 

    public RegisterView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Đăng ký");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(-10, 10, 596, 30);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Tên đăng nhập");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_1.setBounds(0, 37, 586, 30);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Mật khẩu");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2.setBounds(0, 113, 586, 23);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Tên đầy đủ");
        lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_3.setBounds(0, 190, 586, 23);
        contentPane.add(lblNewLabel_3);

        textPass = new JPasswordField();
        textPass.setBounds(0, 142, 586, 37);
        contentPane.add(textPass);

        textName = new JTextField();
        textName.setBounds(0, 66, 586, 37);
        contentPane.add(textName);

        textFullName = new JTextField();
        textFullName.setBounds(0, 219, 586, 37);
        contentPane.add(textFullName);

        btnRegister = new JButton("Đăng ký");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = textName.getText();
                String passWord = String.valueOf(textPass.getPassword());
                String fullName = textFullName.getText();

                if (userName.equals("")) {
                    JOptionPane.showMessageDialog(null, "Tên đăng nhập rỗng");
                    textName.requestFocus();
                } else if (passWord.equals("")) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu rỗng");
                    textPass.requestFocus();
                } else if (fullName.equals("")) {
                    JOptionPane.showMessageDialog(null, "Tên đầy đủ rỗng");
                    textFullName.requestFocus();
                } else {
                    if (registerUser(userName, passWord, fullName)) {
                        JOptionPane.showMessageDialog(null, "Đăng ký thành công!");
                        // Clear fields
                        textName.setText("");
                        textPass.setText("");
                        textFullName.setText("");
                        dispose(); 
                        LoginView loginView = new LoginView();
                        loginView.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Đăng ký thất bại. Vui lòng thử lại sau.");
                    }
                }
            }
        });
        btnRegister.setFont(new Font("Arial", Font.BOLD, 20));
        btnRegister.setBounds(218, 300, 151, 30);
        contentPane.add(btnRegister);

        btnBackToLogin = new JButton("Quay lại");
        btnBackToLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
            }
        });
        btnBackToLogin.setFont(new Font("Arial", Font.BOLD, 20));
        btnBackToLogin.setBounds(400, 300, 151, 30);
        contentPane.add(btnBackToLogin);
    }

    private boolean registerUser(String userName, String passWord, String fullName) {
        boolean isRegistered = false;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO Account (Username, Password, Fullname) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, userName);
                pstmt.setString(2, passWord); 
                pstmt.setString(3, fullName);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    isRegistered = true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đăng ký người dùng: " + ex.getMessage());
        }
        return isRegistered;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegisterView frame = new RegisterView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
