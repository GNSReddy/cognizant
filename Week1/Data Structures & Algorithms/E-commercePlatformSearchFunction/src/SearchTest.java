import java.util.Arrays;
import java.util.Comparator;

public class SearchTest {

    public static void main(String[] args) {

        Product[] products = {

                new Product(101, "Keyboard", "Electronics"),
                new Product(102, "Laptop", "Electronics"),
                new Product(103, "Mouse", "Electronics"),
                new Product(104, "Monitor", "Electronics"),
                new Product(105, "Printer", "Electronics")

        };

        System.out.println("Linear Search");

        Product result =
                SearchOperations.linearSearch(products, "Mouse");

        System.out.println(result);

        Arrays.sort(products,
                Comparator.comparing(Product::getProductName));

        System.out.println();

        System.out.println("Binary Search");

        Product result2 =
                SearchOperations.binarySearch(products, "Mouse");

        System.out.println(result2);

    }

}