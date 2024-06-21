package read.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.*;

public class ManagerTeacherFrame extends JFrame {
    private JButton listUsersButton;
    private JButton deleteUserButton;
    private JButton findUserButton;

    public ManagerTeacherFrame() {
        setTitle("导师信息管理");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        listUsersButton = new JButton("列出所有导师信息");
        listUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String result = ManagerPrintUser();
                    if (result != null && !result.isEmpty()) {
                        JOptionPane.showMessageDialog(ManagerTeacherFrame.this, result);
                    } else {
                        JOptionPane.showMessageDialog(ManagerTeacherFrame.this, "没有导师信息！");
                    }
                    dispose(); // 关闭选择界面
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        deleteUserButton = new JButton("删除导师信息");
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = JOptionPane.showInputDialog(ManagerTeacherFrame.this, "请输入要删除的导师ID:");
                if (userId != null && !userId.isEmpty()) {
                    int choice = JOptionPane.showConfirmDialog(ManagerTeacherFrame.this, "删除后无法恢复，请确认是否继续删除操作", "警告", JOptionPane.YES_NO_OPTION);
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
        findUserButton = new JButton("查询导师信息");
        findUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"根据导师ID查询", "根据导师名查询"};
                int choice = JOptionPane.showOptionDialog(ManagerTeacherFrame.this, "请选择查询方式", "查询用户", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (choice == 0) {
                    String userId = JOptionPane.showInputDialog(ManagerTeacherFrame.this, "请输入导师ID:");
                    if (userId != null && !userId.isEmpty()) {
                        try {
                            String result = findUserByIdAndPrintResult(userId);
                            if (result != null && !result.isEmpty()) {
                                JOptionPane.showMessageDialog(ManagerTeacherFrame.this, result);
                            } else {
                                JOptionPane.showMessageDialog(ManagerTeacherFrame.this, "没有导师信息！");
                            }
                            dispose(); // 关闭选择界面
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (choice == 1) {
                    String userName = JOptionPane.showInputDialog(ManagerTeacherFrame.this, "请输入导师名:");
                    if (userName != null && !userName.isEmpty()) {
                        try {
                            String result = findUserByNameAndPrintResult(userName);
                            if (result != null && !result.isEmpty()) {
                                JOptionPane.showMessageDialog(ManagerTeacherFrame.this, result);
                            } else {
                                JOptionPane.showMessageDialog(ManagerTeacherFrame.this, "没有导师信息！");
                            }
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
        panel.add(findUserButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private String ManagerPrintUser() throws SQLException {
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
            String sql = "SELECT * FROM teacher";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String ID = rs.getString("ID");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String mail = rs.getString("mail");
                sb.append("导师ID:").append(ID).append("  导师名:").append(name).append("  导师手机号").append(phone)
                        .append("  导师邮箱:").append(mail).append("\n");
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
            String sql = "DELETE FROM teacher WHERE ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(ManagerTeacherFrame.this, "删除成功");
                new ManagerMainPage().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(ManagerTeacherFrame.this, "未找到该导师，请重新输入");
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
    private String findUserByIdAndPrintResult(String userId) throws SQLException {
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
        try{
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "SELECT *FROM teacher";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()){
                String ID =rs.getString("ID");
                if(ID.equals(userId)){
                    String name =rs.getString("name");
                    String phone =rs.getString("phone");
                    String mail =rs.getString("mail");
                    sb.append("导师ID:").append(ID).append("  导师名:").append(name).append("  导师手机号").append(phone)
                            .append("  导师邮箱:").append(mail).append("\n");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            conn.close();
            stat.close();
            rs.close();
        }
        return sb.toString();
    }

    private String findUserByNameAndPrintResult(String userName) throws SQLException {
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
        try{
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "SELECT *FROM teacher";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()){
                String name =rs.getString("name");
                if(name.equals(userName)){
                    String ID =rs.getString("ID");
                    String phone =rs.getString("phone");
                    String mail =rs.getString("mail");
                    sb.append("导师ID:").append(ID).append("  导师名:").append(name).append("  导师手机号").append(phone)
                            .append("  导师邮箱:").append(mail).append("\n");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            conn.close();
            stat.close();
            rs.close();
        }
        return sb.toString();
    }
}

