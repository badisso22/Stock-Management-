package gl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    public User login(String username, String password) {
        if (username == null || password == null) return null;
        String sql = "SELECT username, role FROM users WHERE username = ? AND password = crypt(?, password) AND active = TRUE";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String user = rs.getString("username");
                    String roleStr = rs.getString("role");
                    Role role = Role.valueOf(roleStr);
                    return new User(user, role);
                }
            }
        } catch (SQLException e) {
            System.out.println("Auth DB error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public boolean createUser(String username, String plainPassword, Role role) {
        if (username == null || plainPassword == null || role == null) return false;
        String sql = "INSERT INTO users (username, password, role) VALUES (?, crypt(?, gen_salt('bf')), ?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, plainPassword);
            ps.setString(3, role.name());
            int updated = ps.executeUpdate();
            return updated == 1;
        } catch (SQLException e) {
            System.out.println("Create user error: " + e.getMessage());
            return false;
        }
    }
}
