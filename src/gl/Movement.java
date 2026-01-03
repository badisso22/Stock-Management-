package gl;

import java.time.LocalDateTime;

public class Movement {
    private int id;
    private LocalDateTime movementDate;
    private MovementType type;
    private int quantity;
    private int productId;

    public Movement(int id, LocalDateTime movementDate, MovementType type,
                    int quantity, int productId) {
        this.id = id;
        this.movementDate = movementDate;
        this.type = type;
        this.quantity = quantity;
        this.productId = productId;
    }
    
    public Movement(MovementType type, int quantity, int productId) {
        this(0, LocalDateTime.now(), type, quantity, productId);
    }
    

    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public LocalDateTime getMovementDate() {
		return movementDate;
	}


	public void setMovementDate(LocalDateTime movementDate) {
		this.movementDate = movementDate;
	}


	public MovementType getType() {
		return type;
	}


	public void setType(MovementType type) {
		this.type = type;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "Movement [id=" + id + ", movementDate=" + movementDate + ", type=" + type + ", quantity=" + quantity+ ", productId=" + productId + "]";
	}
	
	

}
