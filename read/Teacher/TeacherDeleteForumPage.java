package read.Teacher;

import read.LoginRegister.TeacherLoginFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class TeacherDeleteForumPage extends JFrame {
    static String ID = TeacherLoginFrame.rememberId();
    private static JTextField idField;
    public TeacherDeleteForumPage() {
        setTitle("解散论坛");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));

        JLabel weekLabel = new JLabel("请输入论坛ID:");
        idField = new JTextField(10);
        panel.add(weekLabel);
        panel.add(idField);

        JButton addButton = new JButton("解散");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                try {
                    deleteForum(id);
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

    public static void deleteForum(String id) throws SQLException {
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
            String sql = "SELECT *FROM forum";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String forumID = rs.getString("ID");
                String teacherID = rs.getString("teacherID");
                if (id.equals(forumID) && teacherID.equals(ID)) {
                    String sql1 = "DELETE FROM forum WHERE ID = ? and teacherID = ?";
                    ps = conn.prepareStatement(sql1);
                    ps.setString(1, id);
                    ps.setString(2, ID);
                    ps.executeUpdate();
                    judge = true;

                }
                if (!judge) {
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
