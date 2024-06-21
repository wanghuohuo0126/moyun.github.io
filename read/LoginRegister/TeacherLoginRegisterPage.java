package read.LoginRegister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherLoginRegisterPage extends JFrame implements ActionListener {
    private JButton loginButton;
    private JButton registerButton;
    public TeacherLoginRegisterPage() {
        setTitle("导师登录/注册");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        JLabel label = new JLabel("请选择操作:");
        loginButton = new JButton("登录");
        loginButton.addActionListener(this);
        registerButton = new JButton("注册");
        registerButton.addActionListener(this);

        panel.add(label);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            TeacherLoginFrame TeacherLoginFrame = new TeacherLoginFrame();
            TeacherLoginFrame.setVisible(true);
            dispose();
        } else if (e.getSource() == registerButton) {
            TeacherRegisterFrame TeacherRegisterFrame = new TeacherRegisterFrame();
            TeacherRegisterFrame.setVisible(true);
            dispose();
        }
    }
}

