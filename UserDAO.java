package project_sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static String username = "scott";
    private static String password2 = "tiger";

    
    
    public boolean signUp(UserDTO user) {
        try (Connection conn = DriverManager.getConnection(url, username, password2)) {
            String sql = "INSERT INTO signlogin(id, name, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String id, String password) {
        try (Connection conn = DriverManager.getConnection(url, username, password2)) {
            String sql = "SELECT * FROM signlogin WHERE id = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
