public class InventoryTest {

    public static void main(String[] args) {

        Inventory inventory = new Inventory();

        Product p1 = new Product(101, "Laptop", 10, 65000);
        Product p2 = new Product(102, "Mouse", 50, 700);
        Product p3 = new Product(103, "Keyboard", 20, 1500);

        inventory.addProduct(p1);
        inventory.addProduct(p2);
        inventory.addProduct(p3);

        inventory.displayProducts();

        System.out.println("\nUpdating Product...");

        inventory.updateProduct(102, 75, 650);

        inventory.displayProducts();

        System.out.println("\nDeleting Product...");

        inventory.deleteProduct(101);

        inventory.displayProducts();
    }
}