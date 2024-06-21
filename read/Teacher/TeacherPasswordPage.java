package read.Teacher;

import read.LoginRegister.TeacherLoginFrame;
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

public class TeacherPasswordPage extends JFrame implements ActionListener {
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
    public TeacherPasswordPage() {
        setTitle("密码管理");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        JButton passwordButton = new JButton("重置密码");
        JButton forgetButton = new JButton("忘记密码");

        passwordButton.addActionListener(this);
        forgetButton.addActionListener(this);

        panel.add(passwordButton);
        panel.add(forgetButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("重置密码")) {
            String password = JOptionPane.showInputDialog(TeacherPasswordPage.this, "请输入新密码:");
            try {
                String ID = TeacherLoginFrame.rememberId();
                changePassword(ID,password);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getActionCommand().equals("忘记密码")) {
            String computerAmount = JOptionPane.showInputDialog(TeacherPasswordPage.this, "请输入电子邮箱:");
            JOptionPane.showMessageDialog(this, "密码已发送，请注意查收！");
        }
    }
    public static void changePassword(String ID, String password) throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
        String regex = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
        if(password.matches(regex)==false){
            System.out.println("密码不符合规定，请重新输入!");
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "UPDATE teacher SET password=? WHERE ID=?";
            ps = conn.prepareStatement(sql);
            String passwordMd5 = md5(password);
            ps.setString(1,passwordMd5);
            ps.setString(2,ID);
            ps.executeUpdate();
            System.out.println("修改成功！");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            conn.close();
            ps.close();
        }
    }
}
