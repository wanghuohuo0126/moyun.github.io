package read.Teacher;

import read.LoginRegister.TeacherLoginFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TearchAddForumPage extends JFrame implements ActionListener {
    static String forumID = null;

    public static String rememberId() {
        String id = forumID;
        return id;
    }

    static String ID = TeacherLoginFrame.rememberId();
    private JTextField idField;
    private JTextField nameField; // 修改为JTextField
    private JTextField dateField;

    public TearchAddForumPage() {
        setTitle("请输入论坛信息");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JLabel idLabel = new JLabel("论坛ID:");
        idField = new JTextField(20);
        JLabel nameLabel = new JLabel("论坛名:   ");
        nameField = new JTextField(20); // 修改为JTextField
        JLabel dateLabel = new JLabel("创建日期:");
        dateField = new JTextField(18);
        JButton registerButton = new JButton("创建");
        registerButton.addActionListener(this);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(registerButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("创建")) {
            try {
                addForum(); // 调用用户注册方法
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addForum() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
        String id = idField.getText();
        String name = nameField.getText();
        String date = dateField.getText();
        forumID = id;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "INSERT INTO forum(ID, name, teacherID, date) VALUES(?, ?, ?, ?)"; // 修正SQL语句
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, ID);
            ps.setString(4, date);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "创建成功！");
            dispose(); // 关闭当前窗口
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "创建失败！请检查输入信息。");
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TearchAddForumPage frame = new TearchAddForumPage();
            frame.setVisible(true); // 确保窗口可见
        });
    }
}


//package read.Teacher;
//
//import read.LoginRegister.TeacherLoginFrame;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.*;
//
//
//public class TearchAddForumPage extends JFrame implements ActionListener {
//    static String forumID = null;
//    public static String rememberId(){
//        String id = forumID;
//        return id;
//    }
//    static String ID = TeacherLoginFrame.rememberId();
//    private JTextField idField;
//    private JPasswordField nameField;
//    private JTextField dateField;
//
//    public TearchAddForumPage() {
//        setTitle("请输入论坛信息");
//        setSize(300, 300);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
//        JLabel idLabel = new JLabel("论坛ID:");
//        idField = new JTextField(20);
//        JLabel nameLabel = new JLabel("论坛名:   ");
//        nameField = new JPasswordField(20);
//        JLabel dateLabel = new JLabel("创建日期:");
//        dateField = new JTextField(18);
//        JButton registerButton = new JButton("创建");
//        registerButton.addActionListener(this);
//
//        panel.add(idLabel);
//        panel.add(idField);
//        panel.add(nameLabel);
//        panel.add(nameField);
//        panel.add(idField);
//        panel.add(dateLabel);
//        panel.add(dateField);
//        panel.add(registerButton);
//
//        add(panel);
//        setLocationRelativeTo(null);
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals("创建")) {
//            try {
//                addForum(); // 调用用户注册方法
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    public void addForum() throws SQLException {
//        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
//        String id = idField.getText();
//        String name = nameField.getText();
//        String date = dateField.getText();
//        forumID = id;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = DriverManager.getConnection(url, "root", "root");
//            String sql = "INSERT INTO forum(ID,name,teacherID,date) VALUES(?,?,?,?,?)";
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, id);
//            ps.setString(2, name);
//            ps.setString(3, ID);
//            ps.setString(4, date);
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(this, "创建成功！");
//            dispose(); // 关闭当前登录窗口
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "创建失败！请检查输入信息。");
//        } finally {
//            conn.close();
//            ps.close();
//        }
//    }
//}
//
