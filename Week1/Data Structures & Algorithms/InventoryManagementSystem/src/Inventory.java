import java.util.HashMap;

public class Inventory {

    private HashMap<Integer, Product> products = new HashMap<>();

    // Add Product
    public void addProduct(Product product) {
        products.put(product.getProductId(), product);
        System.out.println("Product Added.");
    }

    // Update Product
    public void updateProduct(int id, int quantity, double price) {

        Product product = products.get(id);

        if (product != null) {
            product.setQuantity(quantity);
            product.setPrice(price);
            System.out.println("Product Updated.");
        } else {
            System.out.println("Product Not Found.");
        }
    }

    // Delete Product
    public void deleteProduct(int id) {

        if (products.remove(id) != null) {
            System.out.println("Product Deleted.");
        } else {
            System.out.println("Product Not Found.");
        }
    }

    // Display Inventory
    public void displayProducts() {

        System.out.println("\nInventory:");

        for (Product product : products.values()) {
            System.out.println(product);
        }
    }
}