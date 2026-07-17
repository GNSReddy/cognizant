public class EmployeeTest {

    public static void main(String[] args) {

        EmployeeOperations operations =
                new EmployeeOperations(10);

        operations.addEmployee(
                new Employee(101,
                        "Rahul",
                        "Manager",
                        65000));

        operations.addEmployee(
                new Employee(102,
                        "Anjali",
                        "Developer",
                        50000));

        operations.addEmployee(
                new Employee(103,
                        "Kiran",
                        "Tester",
                        45000));

        operations.displayEmployees();

        System.out.println();

        System.out.println("Searching Employee...\n");

        Employee employee =
                operations.searchEmployee(102);

        if (employee != null)

            System.out.println(employee);

        System.out.println();

        operations.deleteEmployee(101);

        operations.displayEmployees();

    }

}