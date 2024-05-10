package project_sample;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class UserMain extends JFrame {
	 private UserDAO userDAO;

	    private JTextField idField;
	    private JPasswordField passwordField;
	    private JButton loginButton;
	    private JButton signupButton;

	    public UserMain() {
	        userDAO = new UserDAO();

	        setTitle("회원 관리 프로그램");
	        setSize(300, 200);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null); // 화면 가운데에 창 표시

	        JPanel panel = new JPanel();
	        panel.setLayout(null);

	        JLabel idLabel = new JLabel("아이디:");
	        idLabel.setBounds(10, 20, 80, 25);
	        panel.add(idLabel);

	        idField = new JTextField(20);
	        idField.setBounds(100, 20, 160, 25);
	        panel.add(idField);

	        JLabel passwordLabel = new JLabel("비밀번호:");
	        passwordLabel.setBounds(10, 50, 80, 25);
	        panel.add(passwordLabel);

	        passwordField = new JPasswordField(20);
	        passwordField.setBounds(100, 50, 160, 25);
	        panel.add(passwordField);

	        loginButton = new JButton("로그인");
	        loginButton.setBounds(10, 80, 100, 25);
	        loginButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                String id = idField.getText();
	                String password = new String(passwordField.getPassword());
	                boolean loggedIn = userDAO.login(id, password);
	                if (loggedIn) {
	                    JOptionPane.showMessageDialog(null, "로그인 성공!");
	                    dispose(); // 로그인 창 닫기
	                    MainMenu mainMenu = new MainMenu();
	                    mainMenu.setVisible(true);
	                } else {
	                    JOptionPane.showMessageDialog(null, "로그인 실패!");
	                }
	            }
	        });
	        panel.add(loginButton);

	        signupButton = new JButton("회원가입");
	        signupButton.setBounds(120, 80, 100, 25);
	        signupButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                SignUpForm signUpForm = new SignUpForm(userDAO);
	                signUpForm.setVisible(true);
	            }
	        });
	        panel.add(signupButton);

	        add(panel);
	    }

	    public static void main(String[] args) {
	    	UserMain main = new UserMain();
	        main.setVisible(true);
	    }
	}