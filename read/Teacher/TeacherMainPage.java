package read.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherMainPage extends JFrame implements ActionListener {
    public TeacherMainPage() {
        setTitle("导师功能界面");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1));
        JButton passwordButton = new JButton("密码管理");
        JButton informationButton = new JButton("个人信息管理");
        JButton bookButton = new JButton("书籍管理");
        JButton forumButton = new JButton("论坛管理");
        JButton logoutButton = new JButton("退出登录");

        passwordButton.addActionListener(this);
        informationButton.addActionListener(this);
        bookButton.addActionListener(this);
        forumButton.addActionListener(this);
        logoutButton.addActionListener(this);

        panel.add(passwordButton);
        panel.add(informationButton);
        panel.add(bookButton);
        panel.add(forumButton);
        panel.add(logoutButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("密码管理")) {
            new TeacherPasswordPage().setVisible(true);
        } else if (e.getActionCommand().equals("个人信息管理")) {
            new TeacherInformationPage().setVisible(true);
        } else if (e.getActionCommand().equals("书籍管理")) {
            new TeacherBookPage().setVisible(true);
        }else if (e.getActionCommand().equals("论坛管理")) {
            new TeacherForumPage().setVisible(true);
        } else if (e.getActionCommand().equals("退出登录")) {
            // 执行退出登录的相关操作
            dispose(); // 关闭当前窗口
        }
    }
}

