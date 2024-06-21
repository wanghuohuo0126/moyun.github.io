package read.LoginRegister;

import read.Teacher.TeacherMainPage;
import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


public class TeacherLoginFrame extends JFrame implements ActionListener {
    static String ID = null;
    public static String rememberId(){
        String id = ID;
        return id;
    }
    public static String md5(String str) {
        byte[] digest = null;
        try{
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        String md5str = new BigInteger(1,digest).toString(16);
        return md5str;
    }
    private JTextField nameField;
    private JPasswordField passwordField;

    public TeacherLoginFrame() {
        setTitle("导师登录");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        JLabel nameLabel = new JLabel("用户名:");
        nameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("密码:   ");
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(this);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("登录")) {
            try {
                String name = nameField.getText();
                String password = new String(passwordField.getPassword());
                boolean success = userLogin(name, password); // 调用用户登录方法
                if (success) {
                    JOptionPane.showMessageDialog(this, "登录成功！");
                    dispose(); // 登录成功后关闭登录窗口
                    TeacherMainPage userFrame = new TeacherMainPage();
                    userFrame.setVisible(true); // 打开用户功能界面
                } else {
                    JOptionPane.showMessageDialog(this, "用户名或密码错误！");
                }
            } catch (SQLException | LoginException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public boolean userLogin(String name, String password) throws SQLException, LoginException {
        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
        String passwordMd5 = md5(password);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        boolean success = false; // 判断用户是否登录成功的变量
        try {
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "SELECT *FROM teacher";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String clientName = rs.getString("name");
                String clientPassword = rs.getString("password");
                if (clientName.equals(name) && clientPassword.equals(passwordMd5)) {
                    ID = rs.getString("ID");
                    success = true;
                    break; // 结束循环
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            stat.close();
            rs.close();
        }
        return success;
    }
}
