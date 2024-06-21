package read.LoginRegister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentRegisterFrame extends JFrame implements ActionListener {
    public static String md5(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String md5str = new BigInteger(1, digest).toString(16);
        return md5str;
    }
    private JTextField nameField;
    private JPasswordField passwordField;
    private JTextField idField;
    private JTextField phoneField;
    private JTextField mailField;

    public StudentRegisterFrame() {
        setTitle("学生注册");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JLabel nameLabel = new JLabel("用户名:");
        nameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("密码:   ");
        passwordField = new JPasswordField(20);
        JLabel idLabel = new JLabel("ID:       ");
        idField = new JTextField(20);
        JLabel phoneLabel = new JLabel("电话号码:");
        phoneField = new JTextField(18);
        JLabel mailLabel = new JLabel("邮箱:   ");
        mailField = new JTextField(20);
        JButton registerButton = new JButton("注册");
        registerButton.addActionListener(this);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(mailLabel);
        panel.add(mailField);
        panel.add(registerButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("注册")) {
            try {
                userRegister(); // 调用用户注册方法
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void userRegister() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());
        String passwordMd5 = md5(password);
        String id = idField.getText();
        String phone = phoneField.getText();
        String mail = mailField.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "INSERT INTO student(ID,name,phone,mail,password) VALUES(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.setString(4, mail);
            ps.setString(5, passwordMd5);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "注册成功！");
            StudentLoginFrame loginFrame = new StudentLoginFrame();
            loginFrame.setVisible(true);
            dispose(); // 关闭当前登录窗口
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "注册失败！请检查输入信息。");
        } finally {
            conn.close();
            ps.close();
        }
    }
}

