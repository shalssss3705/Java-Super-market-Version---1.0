public class Product {
    // Attributes
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private double discount;

    // Constructors
    public Product(int productId, String productName, double price, int quantity, double discount) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }
    
    public Product(Product p, int quantity){
        this.quantity = quantity;
        this.productId = p.getProductId();
        this.productName = p.getProductName();
        this.discount = p.getDiscount();
        this.price = p.getPrice();
    }

    // Getter and Setter methods
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    public double getDiscountedPrice(){
        return price - (discount*price);
    }
    
    public double getTotalPrice(){
        return (getDiscountedPrice()*quantity);
    }
    
    public double getDiscountPercentage(){
        return (discount*100);
    }

}
