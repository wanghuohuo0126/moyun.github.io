package read.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentBookPage extends JFrame {
    private JButton listButton;

    private JButton findButton;
    public  StudentBookPage() {
        setTitle("书籍管理");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        listButton = new JButton("列出所有书籍的基本信息");
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String result = printRoom();
                    JOptionPane.showMessageDialog(StudentBookPage.this, result);
                    new StudentMainPage().setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        findButton = new JButton("查询书籍的信息");
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"根据书籍编号查询", "根据书名查询"};
                int choice = JOptionPane.showOptionDialog(StudentBookPage.this, "请选择查询方式", "查询书籍", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (choice == 0) {
                    String bookNumber = JOptionPane.showInputDialog(StudentBookPage.this, "请输入书籍编号:");
                    if (bookNumber != null && !bookNumber.isEmpty()) {
                        String result = null;
                        try {
                            result = findBoomByNumber(bookNumber);
                            if (result != null && !result.isEmpty()) {
                                JOptionPane.showMessageDialog(StudentBookPage.this, result);
                            } else {
                                JOptionPane.showMessageDialog(StudentBookPage.this, "未找到该书籍，请重新输入！");
                            }                            dispose(); // 关闭选择界面
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else if (choice == 1) {
                    String bookName = JOptionPane.showInputDialog(StudentBookPage.this, "请输入书名:");
                    if (bookName != null && !bookName.isEmpty()) {
                        try {
                            String result = findBookByName(bookName);
                            if (result != null && !result.isEmpty()) {
                                JOptionPane.showMessageDialog(StudentBookPage.this, result);
                            } else {
                                JOptionPane.showMessageDialog(StudentBookPage.this, "未找到该书籍，请重新输入！");
                            }                            dispose(); // 关闭选择界面
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        panel.add(listButton);
        panel.add(findButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static String printRoom() throws SQLException {
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
            String sql = "SELECT *FROM book";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()){
                String number =rs.getString("ID");
                String name =rs.getString("name");
                String author =rs.getString("author");
                String publisher =rs.getString("publisher");
                String content =rs.getString("content");
                sb.append("书籍编号:").append(number).append("  书名:").append(name).append("  作者").append(author).append("  出版社:").append(publisher)
                        .append("内容:").append(content).append("\n");

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
    private static String findBoomByNumber(String number) throws SQLException {
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
            String sql = "SELECT *FROM book";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()){
                String bookNumber =rs.getString("ID");
                if(bookNumber.equals(number)){
                    String name =rs.getString("name");
                    String author =rs.getString("author");
                    String publisher =rs.getString("publisher");
                    String content =rs.getString("content");
                    sb.append("书籍编号:").append(number).append("  书名:").append(name).append("  作者").append(author).append("  出版社:").append(publisher)
                            .append("内容:").append(content).append("\n");
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
    private static String  findBookByName(String name) throws SQLException {
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
            String sql = "SELECT *FROM book";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()){
                String bookName =rs.getString("name");
                if(bookName.equals(name)){
                    String number =rs.getString("ID");
                    String author =rs.getString("author");
                    String publisher =rs.getString("publisher");
                    String content =rs.getString("content");
                    sb.append("书籍编号:").append(number).append("  书名:").append(name).append("  作者").append(author).append("  出版社:").append(publisher)
                            .append("内容:").append(content).append("\n");
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
