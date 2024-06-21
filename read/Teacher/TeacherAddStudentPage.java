package read.Teacher;

import read.LoginRegister.TeacherLoginFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TeacherAddStudentPage extends JFrame implements ActionListener {
    static String forumID = TearchAddForumPage.rememberId();
    private JTextField idField;


    public TeacherAddStudentPage() {
        setTitle("邀请成员");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JLabel idLabel = new JLabel("学生ID:");
        idField = new JTextField(20);

        JButton registerButton = new JButton("添加");
        registerButton.addActionListener(this);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(registerButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("添加")) {
            try {
                addStudent(); // 调用用户注册方法
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addStudent() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
        String id = idField.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "UPDATE student SET forumID=? WHERE ID=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, forumID);
            ps.setString(2, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "添加成功！");
            dispose(); // 关闭当前登录窗口
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "添加失败！请检查输入信息。");
        } finally {
            conn.close();
            ps.close();
        }
    }
}

