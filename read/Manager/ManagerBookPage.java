package read.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManagerBookPage extends JFrame {
    private JButton addButton;
    private JButton deleteButton;
    public  ManagerBookPage() {
        setTitle("书籍管理");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        addButton = new JButton("添加书籍的信息");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookID = JOptionPane.showInputDialog(ManagerBookPage.this, "请输入书籍ID:");
                String bookName = JOptionPane.showInputDialog(ManagerBookPage.this, "请输入书名:");
                String author = JOptionPane.showInputDialog(ManagerBookPage.this, "请输入作者:");
                String publisher = JOptionPane.showInputDialog(ManagerBookPage.this, "请输入出版社:");
                String content = JOptionPane.showInputDialog(ManagerBookPage.this, "请输入书籍内容:");
                if (bookID != null && !bookID.isEmpty()) {
                    try {
                        addBook(bookID,bookName,author,publisher,content);
                        dispose(); // 关闭选择界面
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        deleteButton = new JButton("删除书籍的信息");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookID = JOptionPane.showInputDialog(ManagerBookPage.this, "请输入要删除的书籍ID:");
                if (bookID != null && !bookID.isEmpty()) {
                    int choice = JOptionPane.showConfirmDialog(ManagerBookPage.this, "删除后无法恢复，请确认是否继续删除操作", "警告", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        try {
                            deleteBook(bookID);
                            dispose(); // 关闭选择界面
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        panel.add(addButton);
        panel.add(deleteButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addBook(String bookID,String bookName,String author,String publisher,String content) throws SQLException {
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
            String sql = "INSERT INTO book(ID,name,author,publisher,content) VALUES(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, bookID);
            ps.setString(2, bookName);
            ps.setString(3, author);
            ps.setString(4, publisher);
            ps.setString(5, content);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }
    private void deleteBook(String ID) throws SQLException {
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
            String sql = "DELETE FROM book WHERE ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, ID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(ManagerBookPage.this, "删除成功");
                new ManagerMainPage().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(ManagerBookPage.this, "未找到该书籍，请重新输入");
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

