package project_sample;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpForm extends JFrame {
    private UserDAO userDAO;

    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public SignUpForm(UserDAO userDAO) {
        this.userDAO = userDAO;

        setTitle("회원 가입");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 가운데에 창 표시

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel idLabel = new JLabel("아이디:");
        idLabel.setBounds(10, 20, 80, 25);
        panel.add(idLabel);

        idField = new JTextField(20);
        idField.setBounds(100, 20, 160, 25);
        panel.add(idField);

        JLabel nameLabel = new JLabel("이름:");
        nameLabel.setBounds(10, 50, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(100, 50, 160, 25);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("이메일:");
        emailLabel.setBounds(10, 80, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(100, 80, 160, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("비밀번호:");
        passwordLabel.setBounds(10, 110, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 110, 160, 25);
        panel.add(passwordField);

        JButton signupButton = new JButton("가입하기");
        signupButton.setBounds(100, 140, 100, 25);
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                UserDTO newUser = new UserDTO(id, name, email, password);
                boolean success = userDAO.signUp(newUser);
                if (success) {
                    JOptionPane.showMessageDialog(null, "회원가입 성공!");
                    dispose(); // 회원가입 폼 닫기
                } else {
                    JOptionPane.showMessageDialog(null, "회원가입 실패!");
                }
            }
        });
        panel.add(signupButton);

        add(panel);
    }
}
