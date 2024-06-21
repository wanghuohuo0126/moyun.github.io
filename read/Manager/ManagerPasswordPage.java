package read.Manager;

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

public class ManagerPasswordPage extends JFrame implements ActionListener {
    private JButton changeOwnPasswordButton;
    private JButton changeUserPasswordButton;
    public ManagerPasswordPage() {
        setTitle("密码管理");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        changeOwnPasswordButton = new JButton("修改自身密码");
        changeOwnPasswordButton.addActionListener((ActionListener) this);
        changeUserPasswordButton = new JButton("修改指定用户的密码");
        changeUserPasswordButton.addActionListener((ActionListener) this);

        panel.add(changeOwnPasswordButton);
        panel.add(changeUserPasswordButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeOwnPasswordButton) {
            new ChangeManagerPasswordFrame().setVisible(true);
            dispose(); // 关闭选择界面
        } else if (e.getSource() == changeUserPasswordButton) {
            new ChangeUserPasswordFrame().setVisible(true);
            dispose(); // 关闭选择界面
        }
    }
    public class ChangeManagerPasswordFrame extends JFrame implements ActionListener {
        private JTextField nameField;
        private JPasswordField passwordField;

    public ChangeManagerPasswordFrame() {
            setTitle("管理员密码修改");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
            JLabel nameLabel = new JLabel("用户名:");
            nameField = new JTextField(20);
            JLabel passwordLabel = new JLabel("新密码:");
            passwordField = new JPasswordField(20);
            JButton submitButton = new JButton("提交");

            submitButton.addActionListener(this);

            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(passwordLabel);
            panel.add(passwordField);
            panel.add(submitButton);

            add(panel);
            setLocationRelativeTo(null);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("提交")) {
                String name = nameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                boolean success = changeManagerPassword(name, password);
                if (success) {
                    JOptionPane.showMessageDialog(null, "修改成功！");
                    dispose(); // 关闭当前窗口
                } else {
                    JOptionPane.showMessageDialog(null, "修改失败，请重试。");
                }
            }
        }

        private boolean changeManagerPassword(String name, String password) {
            String url = "jdbc:mysql://127.0.0.1:3306/moyun";
            boolean success = false;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection conn = null;
            PreparedStatement ps = null;
            try{
                conn = DriverManager.getConnection(url, "root", "root");
                String sql = "UPDATE manager SET password=? WHERE name=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, password);
                ps.setString(2, name);
                ps.executeUpdate();
                success = true;
            }catch (SQLException e){
                e.printStackTrace();
                success = false;
            }finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return success;
        }
    }
    public class ChangeUserPasswordFrame extends JFrame implements ActionListener {
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

        public ChangeUserPasswordFrame() {
            setTitle("用户密码修改");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
            JLabel nameLabel = new JLabel("用户名:");
            nameField = new JTextField(20);
            JLabel passwordLabel = new JLabel("新密码:");
            passwordField = new JPasswordField(20);
            JButton submitButton = new JButton("提交");

            submitButton.addActionListener(this);

            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(passwordLabel);
            panel.add(passwordField);
            panel.add(submitButton);

            add(panel);
            setLocationRelativeTo(null);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("提交")) {
                String name = nameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                boolean success = changeUserPassword(name, password);
                if (success) {
                    JOptionPane.showMessageDialog(null, "修改成功！");
                    dispose(); // 关闭当前窗口
                } else {
                    JOptionPane.showMessageDialog(null, "修改失败，请重试。");
                }
            }
        }

        private boolean changeUserPassword(String name, String password) {
            String url = "jdbc:mysql://127.0.0.1:3306/moyun";
            boolean success = false;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection conn = null;
            PreparedStatement ps = null;
            String newPassword = md5(password);
            try {
                conn = DriverManager.getConnection(url, "root", "root");
                String sql = "UPDATE teacher SET password=? WHERE name=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, newPassword);
                ps.setString(2, name);
                ps.executeUpdate();
                success = true;
            } catch (SQLException e) {
                e.printStackTrace();
                success = false;
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return success;
        }
    }
}
