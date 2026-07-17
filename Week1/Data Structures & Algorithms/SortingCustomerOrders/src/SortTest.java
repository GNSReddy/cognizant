public class SortTest {

    public static void main(String[] args) {

        Order[] orders = {

                new Order(101, "Rahul", 3500),
                new Order(102, "Anjali", 1500),
                new Order(103, "Kiran", 8000),
                new Order(104, "Amit", 2500),
                new Order(105, "Priya", 5000)

        };

        System.out.println("Original Orders\n");

        SortOperations.display(orders);

        System.out.println("\nBubble Sort\n");

        SortOperations.bubbleSort(orders);

        SortOperations.display(orders);

        Order[] orders2 = {

                new Order(101, "Rahul", 3500),
                new Order(102, "Anjali", 1500),
                new Order(103, "Kiran", 8000),
                new Order(104, "Amit", 2500),
                new Order(105, "Priya", 5000)

        };

        System.out.println("\nQuick Sort\n");

        SortOperations.quickSort(orders2, 0, orders2.length - 1);

        SortOperations.display(orders2);

    }

}