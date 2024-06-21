package read.Student;

import read.LoginRegister.StudentLoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
;


public class StudentTeacherPage extends JFrame implements ActionListener {
    static String ID = StudentLoginFrame.rememberId();
    private JTextField idField;
    private JPasswordField nameField;

    public StudentTeacherPage() {
        setTitle("导师选择");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JLabel idLabel = new JLabel("导师ID:");
        idField = new JTextField(20);
        JLabel nameLabel = new JLabel("导师名:   ");
        nameField = new JPasswordField(20);
        JButton registerButton = new JButton("选择");
        registerButton.addActionListener(this);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(idField);
        panel.add(registerButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("创建")) {
            try {
                addTeacher(); // 调用用户注册方法
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addTeacher() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
        String id = idField.getText();
        String name = nameField.getText();
        boolean judge = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "SELECT *FROM teacher";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String teacherID = rs.getString("ID");
                String teacherName = rs.getString("name");
                if (id.equals(teacherID) && name.equals(teacherName)) {
                    String sql1 = "SELECT *FROM student";
                    stat = conn.createStatement();
                    rs = stat.executeQuery(sql);
                    while (rs.next()) {
                        String studentID = rs.getString("ID");
                        String teacherID1 = rs.getString("teacherID");
                        if(ID.equals(studentID)&&teacherID1==null){
                            String sql2 = "UPDATE student SET teacherID=? WHERE ID=?";
                            ps = conn.prepareStatement(sql2);
                            ps.setString(1, id);
                            ps.setString(2, ID);
                            ps.executeUpdate();
                            judge = true;
                        }
                    }
                }
                if (!judge) {
                    JOptionPane.showMessageDialog(null, "删除失败，未找到该学生!");
                }
                if (judge) {
                    JOptionPane.showMessageDialog(null, "删除成功!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "创建失败！请检查输入信息。");
        } finally {
            conn.close();
            ps.close();
        }
    }
}

