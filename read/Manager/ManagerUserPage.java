package read.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ManagerUserPage extends JFrame implements ActionListener {
    private JButton TeacherButton;
    private JButton StudentButton;


    public ManagerUserPage() {
        setTitle("用户管理");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        JLabel label = new JLabel("请选择用户身份:");
        TeacherButton = new JButton("导师信息管理");
        TeacherButton.addActionListener(this);
        StudentButton = new JButton("学生信息管理");
        StudentButton.addActionListener(this);

        panel.add(label);
        panel.add(TeacherButton);
        panel.add(StudentButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == TeacherButton) {
            new ManagerTeacherFrame();
            dispose(); // 关闭选择界面
        } else if (e.getSource() == StudentButton) {
            dispose(); // 关闭选择界面
            new ManagerStudentFrame();
        }
    }
}


