package read.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManagerForumPage extends JFrame {
    private JButton listUsersButton;
    private JButton deleteUserButton;
    public ManagerForumPage() {
        setTitle("论坛管理");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        listUsersButton = new JButton("列出所有论坛信息");
        listUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String result = ManagerPrintForum();
                    if (result != null && !result.isEmpty()) {
                        JOptionPane.showMessageDialog(ManagerForumPage.this, result);
                    } else {
                        JOptionPane.showMessageDialog(ManagerForumPage.this, "没有论坛信息！");
                    }
                    dispose(); // 关闭选择界面
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        deleteUserButton = new JButton("关闭论坛");
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = JOptionPane.showInputDialog(ManagerForumPage.this, "请输入要关闭的论坛ID:");
                if (userId != null && !userId.isEmpty()) {
                    int choice = JOptionPane.showConfirmDialog(ManagerForumPage.this, "删除后无法恢复，请确认是否继续删除操作", "警告", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        try {
                            deleteAndPrintResult(userId);
                            dispose(); // 关闭选择界面
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        panel.add(listUsersButton);
        panel.add(deleteUserButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private String ManagerPrintForum() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        StringBuilder sb = new StringBuilder();
        try {
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "SELECT * FROM forum";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String ID = rs.getString("ID");
                String name = rs.getString("name");
                String teacherID = rs.getString("teacherID");
                String date = rs.getString("date");
                sb.append("论坛ID:").append(ID).append("  论坛名:").append(name).append("  创建者ID:").append(teacherID)
                        .append("  创建日期:").append(date).append("\n");
            }
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
        return sb.toString();
    }
    private void deleteAndPrintResult(String userId) throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "DELETE FROM forum WHERE ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(ManagerForumPage.this, "删除成功");
                new ManagerMainPage().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(ManagerForumPage.this, "未找到该论坛，请重新输入");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
