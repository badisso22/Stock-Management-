package gl;

import java.sql.SQLException;

public class StockService {
    private final ProductDAO productDAO = new ProductDAO();
    private final MovementDAO movementDAO = new MovementDAO();

    public void createProduct(String code, String name, String category,
                              double unitPrice, int initialQty) throws SQLException {
        Product p = new Product(code, name, category, unitPrice, initialQty);
        productDAO.insert(p);
    }

    public boolean recordEntry(String code, int qty) throws SQLException {
        Product p = productDAO.findByCode(code);
        if (p == null) return false;

        int newQty = p.getQuantity() + qty;
        Movement m = new Movement(MovementType.ENTRY, qty, p.getId());
        movementDAO.insert(m);
        productDAO.updateQuantity(p.getId(), newQty);
        return true;
    }

    public boolean recordExit(String code, int qty) throws SQLException {
        Product p = productDAO.findByCode(code);
        if (p == null || qty > p.getQuantity()) {
            return false;
        }

        int newQty = p.getQuantity() - qty;
        Movement m = new Movement(MovementType.EXIT, qty, p.getId());
        movementDAO.insert(m);
        productDAO.updateQuantity(p.getId(), newQty);
        return true;
    }
}
