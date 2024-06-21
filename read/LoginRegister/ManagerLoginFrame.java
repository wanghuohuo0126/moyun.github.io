package read.LoginRegister;

import read.Manager.ManagerMainPage;
import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManagerLoginFrame extends JFrame implements ActionListener {
    private JTextField nameField;
    private JPasswordField passwordField;

    public ManagerLoginFrame() {
        setTitle("管理员登录");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        JLabel nameLabel = new JLabel("用户名:");
        JLabel passwordLabel = new JLabel("密码:   ");
        nameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            managerLogin(name, password);
            JOptionPane.showMessageDialog(this, "登录成功！");
            dispose(); // 登录成功后关闭登录窗口
            ManagerMainPage managerFrame = new ManagerMainPage();
            managerFrame.setVisible(true); // 打开管理员功能界面
        } catch (SQLException | LoginException ex) {
            JOptionPane.showMessageDialog(this, "用户名或密码错误，请重新登录！", "登录失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void managerLogin(String name, String password) throws LoginException, SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        boolean found = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "SELECT * FROM manager WHERE name = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                found = true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        if (!found) {
            throw new LoginException("用户名或密码错误");
        }
    }
}



