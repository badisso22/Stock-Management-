package gl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final StockService service = new StockService();
    private static final ProductDAO productDAO = new ProductDAO();
    private static final MovementDAO movementDAO = new MovementDAO();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- STOCK MANAGEMENT ---");
            System.out.println("1. Add product");
            System.out.println("2. List products");
            System.out.println("3. Update product");
            System.out.println("4. Delete product");
            System.out.println("5. Record stock entry");
            System.out.println("6. Record stock exit");
            System.out.println("7. Show movement history for a product");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            try {
                switch (choice) {
                    case 1 -> addProduct(sc);
                    case 2 -> listProducts();
                    case 3 -> updateProduct(sc);
                    case 4 -> deleteProduct(sc);
                    case 5 -> recordEntry(sc);
                    case 6 -> recordExit(sc);
                    case 7 -> showHistory(sc);
                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        sc.close();
    }

    private static void addProduct(Scanner sc) throws SQLException {
        System.out.print("Code: ");
        String code = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Category: ");
        String category = sc.nextLine();
        System.out.print("Unit price: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Initial quantity: ");
        int qty = Integer.parseInt(sc.nextLine());

        service.createProduct(code, name, category, price, qty);
        System.out.println("Product created.");
    }

    private static void listProducts() throws SQLException {
        List<Product> list = productDAO.findAll();
        if (list.isEmpty()) {
            System.out.println("No products.");
        } else {
            for (Product p : list) {
                System.out.println(p);
            }
        }
    }

    private static void updateProduct(Scanner sc) throws SQLException {
        System.out.print("Product code to update: ");
        String code = sc.nextLine();
        Product existing = productDAO.findByCode(code);
        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Leave field empty to keep current value.");
        System.out.println("Current name: " + existing.getName());
        System.out.print("New name: ");
        String name = sc.nextLine();
        if (name.isBlank()) name = existing.getName();

        System.out.println("Current category: " + existing.getCategory());
        System.out.print("New category: ");
        String category = sc.nextLine();
        if (category.isBlank()) category = existing.getCategory();

        System.out.println("Current unit price: " + existing.getUnitPrice());
        System.out.print("New unit price: ");
        String priceStr = sc.nextLine();
        double price = priceStr.isBlank()
                ? existing.getUnitPrice()
                : Double.parseDouble(priceStr);

        System.out.println("Current quantity: " + existing.getQuantity());
        System.out.print("New quantity: ");
        String qtyStr = sc.nextLine();
        int qty = qtyStr.isBlank()
                ? existing.getQuantity()
                : Integer.parseInt(qtyStr);

        Product updated = new Product(existing.getId(), code, name, category, price, qty);
        productDAO.update(updated);
        System.out.println("Product updated.");
    }

    private static void deleteProduct(Scanner sc) throws SQLException {
        System.out.print("Product code to delete: ");
        String code = sc.nextLine();
        Product existing = productDAO.findByCode(code);
        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }
        productDAO.deleteByCode(code);
        System.out.println("Product deleted.");
    }

    private static void recordEntry(Scanner sc) throws SQLException {
        System.out.print("Product code: ");
        String code = sc.nextLine();
        System.out.print("Quantity: ");
        int qty = Integer.parseInt(sc.nextLine());
        if (service.recordEntry(code, qty)) {
            System.out.println("Entry recorded.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void recordExit(Scanner sc) throws SQLException {
        System.out.print("Product code: ");
        String code = sc.nextLine();
        System.out.print("Quantity: ");
        int qty = Integer.parseInt(sc.nextLine());
        if (service.recordExit(code, qty)) {
            System.out.println("Exit recorded.");
        } else {
            System.out.println("Product not found or insufficient stock.");
        }
    }

    private static void showHistory(Scanner sc) throws SQLException {
        System.out.print("Product code: ");
        String code = sc.nextLine();
        Product p = productDAO.findByCode(code);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        var list = movementDAO.findByProduct(p.getId());
        if (list.isEmpty()) {
            System.out.println("No movements for this product.");
        } else {
            for (Movement m : list) {
                System.out.println(m);
            }
        }
    }
}
