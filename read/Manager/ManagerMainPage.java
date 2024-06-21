package read.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMainPage extends JFrame implements ActionListener {
    public ManagerMainPage() {
        setTitle("管理员功能界面");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1));
        JButton passwordButton = new JButton("密码管理");
        JButton userButton = new JButton("用户管理");
        JButton roomButton = new JButton("论坛管理");
        JButton applicationButton = new JButton("书籍管理");
        JButton logoutButton = new JButton("退出登录");

        passwordButton.addActionListener(this);
        userButton.addActionListener(this);
        roomButton.addActionListener(this);
        applicationButton.addActionListener(this);
        logoutButton.addActionListener(this);

        panel.add(passwordButton);
        panel.add(userButton);
        panel.add(roomButton);
        panel.add(applicationButton);
        panel.add(logoutButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("密码管理")) {

            new ManagerPasswordPage().setVisible(true);

        } else if (e.getActionCommand().equals("用户管理")) {

            new ManagerUserPage().setVisible(true);

        } else if (e.getActionCommand().equals("论坛管理")) {

            new ManagerForumPage().setVisible(true);

        } else if (e.getActionCommand().equals("书籍管理")) {

            new ManagerBookPage().setVisible(true);

        } else if (e.getActionCommand().equals("退出登录")) {
            // 执行退出登录的相关操作
            dispose(); // 关闭当前窗口
        }
    }
}

