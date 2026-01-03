package gl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovementDAO {

    public void insert(Movement m) throws SQLException {
        String sql = "INSERT INTO movement (movement_date, type, quantity, product_id) "
                   + "VALUES (?,?,?,?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(m.getMovementDate()));
            ps.setObject(2, m.getType().name(), java.sql.Types.OTHER); 
            ps.setInt(3, m.getQuantity());
            ps.setInt(4, m.getProductId());
            ps.executeUpdate();
        }
    }

    public List<Movement> findByProduct(int productId) throws SQLException {
        List<Movement> list = new ArrayList<>();
        String sql = "SELECT id, movement_date, type, quantity, product_id "
                   + "FROM movement WHERE product_id = ? ORDER BY movement_date DESC";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movement m = new Movement(
                        rs.getInt("id"),
                        rs.getTimestamp("movement_date").toLocalDateTime(),
                        MovementType.valueOf(rs.getString("type")),
                        rs.getInt("quantity"),
                        rs.getInt("product_id")
                    );
                    list.add(m);
                }
            }
        }
        return list;
    }
}
