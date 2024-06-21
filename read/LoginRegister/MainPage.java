package read.LoginRegister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainPage extends JFrame implements ActionListener {
    private JButton managerLoginButton;
    private JButton teacherLoginButton;
    private JButton studentLoginButton;

    public MainPage() {
        setTitle("登录方式选择");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        JLabel label = new JLabel("请选择登录方式:");
        managerLoginButton = new JButton("管理员登录");
        managerLoginButton.addActionListener(this);
        teacherLoginButton = new JButton("导师登录");
        teacherLoginButton.addActionListener(this);
        studentLoginButton = new JButton("学生登录");
        studentLoginButton.addActionListener(this);

        panel.add(label);
        panel.add(managerLoginButton);
        panel.add(teacherLoginButton);
        panel.add(studentLoginButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == managerLoginButton) {
            new ManagerLoginFrame();
            dispose(); // 关闭选择界面
        } else if (e.getSource() == teacherLoginButton) {
            dispose(); // 关闭选择界面
            new TeacherLoginRegisterPage();
        }else if (e.getSource() == studentLoginButton) {
            dispose(); // 关闭选择界面
            new StudentLoginRegisterPage();
        }
    }

    public static void main(String[] args) {
        new MainPage();
    }
}

