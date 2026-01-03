package gl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void insert(Product p) throws SQLException {
        String sql = "INSERT INTO product(code, name, category, unit_price, quantity) VALUES (?,?,?,?,?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setString(3, p.getCategory());
            ps.setDouble(4, p.getUnitPrice());
            ps.setInt(5, p.getQuantity());
            ps.executeUpdate();
        }
    }

    public List<Product> findAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT id, code, name, category, unit_price, quantity FROM product";
        try (Connection con = DB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("unit_price"),
                    rs.getInt("quantity")
                );
                list.add(p);
            }
        }
        return list;
    }

    public Product findByCode(String code) throws SQLException {
        String sql = "SELECT id, code, name, category, unit_price, quantity FROM product WHERE code = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("unit_price"),
                        rs.getInt("quantity")
                    );
                }
            }
        }
        return null;
    }

    public void updateQuantity(int productId, int newQty) throws SQLException {
        String sql = "UPDATE product SET quantity = ? WHERE id = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, newQty);
            ps.setInt(2, productId);
            ps.executeUpdate();
        }
    }

    public void update(Product p) throws SQLException {
        String sql = "UPDATE product SET name = ?, category = ?, unit_price = ?, quantity = ? WHERE code = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getCategory());
            ps.setDouble(3, p.getUnitPrice());
            ps.setInt(4, p.getQuantity());
            ps.setString(5, p.getCode());
            ps.executeUpdate();
        }
    }

    public void deleteByCode(String code) throws SQLException {
        String sql = "DELETE FROM product WHERE code = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.executeUpdate();
        }
    }
}
