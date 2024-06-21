package read.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherForumPage extends JFrame implements ActionListener {
    private JButton addButton;
    private JButton deleteButton;
    private JButton addNewButton;
    private JButton deleteNewButton;

    public TeacherForumPage() {
        setTitle("论坛管理");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        addButton = new JButton("创建论坛");
        addButton.addActionListener(this);
        deleteButton = new JButton("解散论坛");
        deleteButton.addActionListener(this);
        addNewButton = new JButton("邀请新成员");
        addNewButton.addActionListener(this);
        deleteNewButton = new JButton("删除成员");
        deleteNewButton.addActionListener(this);

        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(addNewButton);
        panel.add(deleteNewButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            new TeacherAddForumPage().setVisible(true); // 确保新窗口可见
            dispose(); // 关闭选择界面
        } else if (e.getSource() == deleteButton) {
            new TeacherDeleteForumPage().setVisible(true); // 确保新窗口可见
            dispose(); // 关闭选择界面
        } else if (e.getSource() == addNewButton) {
            new TeacherAddStudentPage().setVisible(true); // 确保新窗口可见
            dispose(); // 关闭选择界面
        } else if (e.getSource() == deleteNewButton) {
            new TeacherDeleteStudentPage().setVisible(true); // 确保新窗口可见
            dispose(); // 关闭选择界面
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TeacherForumPage());
    }
}
