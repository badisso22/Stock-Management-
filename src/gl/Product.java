package gl;

public class Product {
    private int id;
    private String code;
    private String name;
    private String category;
    private double unitPrice;
    private int quantity;

    public Product(int id, String code, String name, String category, double unitPrice, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
    
    public Product(String code, String name, String category, double unitPrice, int quantity) {
        this(0, code, name, category, unitPrice, quantity);
    }

    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public double getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", code=" + code + ", name=" + name + ", category=" + category + ", unitPrice="+ unitPrice + ", quantity=" + quantity + "]";
	}
	
	

}
