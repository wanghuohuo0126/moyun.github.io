package read.Teacher;

import read.LoginRegister.TeacherLoginFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TeacherInformationPage extends JFrame {
    private static JTextField nameField;
    private static JTextField phoneField;
    private static JTextField mailField;
    static String ID = TeacherLoginFrame.rememberId();

    public TeacherInformationPage(){
        setTitle("修改个人信息");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 3));

        JLabel timeLabel = new JLabel("用户名:");
        nameField = new JTextField(10);
        panel.add(timeLabel);
        panel.add(nameField);

        JLabel numberLabel = new JLabel("用户电话:");
        phoneField = new JTextField(10);
        panel.add(numberLabel);
        panel.add(phoneField);

        JLabel courseLabel = new JLabel("用户邮箱:  ");
        mailField = new JTextField(10);
        panel.add(courseLabel);
        panel.add(mailField);

        JButton addButton = new JButton("修改个人信息");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String mail = mailField.getText();
                try {
                    boolean judge = applyRoom(ID,name,phone,mail);
                    if (judge == true){
                        JOptionPane.showMessageDialog(TeacherInformationPage.this, "修改成功!");
                    }
                    if (judge == false){
                        JOptionPane.showMessageDialog(TeacherInformationPage.this, "修改失败，请重新输入!");
                    }
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
    public static boolean applyRoom(String ID,String name,String phone,String mail) throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/moyun";
        boolean judge = true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            conn = DriverManager.getConnection(url, "root", "root");
            String sql1 = "UPDATE teacher SET name=?,phone=?,mail=? WHERE ID=?";
            ps = conn.prepareStatement(sql1);
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, mail);
            ps.setString(4, ID);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            judge = false;
        }finally {
            stat.close();
            conn.close();
            rs.close();
        }
        return judge;
    }
}
