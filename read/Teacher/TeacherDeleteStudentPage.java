package read.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class TeacherDeleteStudentPage extends JFrame {
    static String forumID = TearchAddForumPage.rememberId();
    private static JTextField idField;
    public TeacherDeleteStudentPage() {
        setTitle("删除成员");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));

        JLabel weekLabel = new JLabel("请输入学生ID:");
        idField = new JTextField(10);
        panel.add(weekLabel);
        panel.add(idField);

        JButton addButton = new JButton("删除");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                try {
                    deleteStudent(id);
                    dispose(); // 关闭选择界面
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(addButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void deleteStudent(String id) throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
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
            String sql = "SELECT *FROM student";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String ID = rs.getString("ID");
                String forumid = rs.getString("forumID");
                String teacherID = rs.getString("teacherID");
                if (id.equals(ID) && forumid.equals(forumID)) {
                    String sql1 = "UPDATE student SET forumID=? WHERE ID=?";
                    ps = conn.prepareStatement(sql1);
                    ps.setString(1, null);
                    ps.setString(2, id);
                    ps.executeUpdate();
                    judge = true;

                }
                if (!judge) {
                    JOptionPane.showMessageDialog(null, "删除失败，未找到该学生!");
                }
                if (judge) {
                    JOptionPane.showMessageDialog(null, "删除成功!");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            stat.close();
            conn.close();
            rs.close();
        }
    }
}
