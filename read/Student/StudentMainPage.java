package read.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentMainPage extends JFrame implements ActionListener {
    public StudentMainPage() {
        setTitle("学生功能界面");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1));
        JButton passwordButton = new JButton("密码管理");
        JButton informationButton = new JButton("个人信息管理");
        JButton bookButton = new JButton("书籍管理");
        JButton teacherButton = new JButton("导师选择");
        JButton logoutButton = new JButton("退出登录");

        passwordButton.addActionListener(this);
        informationButton.addActionListener(this);
        bookButton.addActionListener(this);
        logoutButton.addActionListener(this);

        panel.add(passwordButton);
        panel.add(informationButton);
        panel.add(bookButton);
        panel.add(logoutButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("密码管理")) {
            new StudentPasswordPage().setVisible(true);
        } else if (e.getActionCommand().equals("个人信息管理")) {
            new StudentInformationPage().setVisible(true);
        } else if (e.getActionCommand().equals("书籍管理")) {
            new StudentBookPage().setVisible(true);
        }else if (e.getActionCommand().equals("导师选择")) {
            new StudentTeacherPage().setVisible(true);
        } else if (e.getActionCommand().equals("退出登录")) {
            // 执行退出登录的相关操作
            dispose(); // 关闭当前窗口
        }
    }
}

