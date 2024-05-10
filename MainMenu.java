package project_sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainMenu extends JFrame {
    private JTextArea menuTextArea;
    private JTextField menuField;
    private JTextField locationField;
    private JTextField priceField;

    public MainMenu() {
        setTitle("메인 메뉴");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 가운데에 창 표시

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 메뉴 리스트를 보여줄 텍스트 영역 생성
        menuTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(menuTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 메뉴 추가 입력 필드 및 버튼
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("메뉴:"));
        menuField = new JTextField();
        inputPanel.add(menuField);
        inputPanel.add(new JLabel("위치:"));
        locationField = new JTextField();
        inputPanel.add(locationField);
        inputPanel.add(new JLabel("가격:"));
        priceField = new JTextField();
        inputPanel.add(priceField);
        JButton addButton = new JButton("추가");
        addButton.addActionListener(e -> addMenu());
        inputPanel.add(addButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        loadMenuList();

        add(panel);
    }

    private void loadMenuList() {
        try {
            // 데이터베이스 연결
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");

            // 쿼리 실행
            String query = "SELECT * FROM menulist";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // 메뉴 리스트를 텍스트 영역에 추가
            StringBuilder menuListText = new StringBuilder();
            while (resultSet.next()) {
                String menu = resultSet.getString("menu");
                String location = resultSet.getString("location");
                int price = resultSet.getInt("price");
                menuListText.append("메뉴: ").append(menu).append(", 위치: ").append(location).append(", 가격: ").append(price).append("\n");
            }
            menuTextArea.setText(menuListText.toString());

            // 연결 및 리소스 닫기
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "메뉴 리스트를 가져오는 데 실패했습니다.");
        }
    }

    private void addMenu() {
        try {
            String menu = menuField.getText();
            String location = locationField.getText();
            int price = Integer.parseInt(priceField.getText());

            // 데이터베이스에 메뉴 추가
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
            String sql = "INSERT INTO menulist(menu, location, price) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, menu);
            preparedStatement.setString(2, location);
            preparedStatement.setInt(3, price);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "메뉴가 추가되었습니다.");
                // 메뉴 추가 후 메뉴 리스트 새로고침
                loadMenuList();
            } else {
                JOptionPane.showMessageDialog(null, "메뉴 추가에 실패했습니다.");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "메뉴 추가에 실패했습니다.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "가격은 숫자로 입력해주세요.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        });
    }
}
