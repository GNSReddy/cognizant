public class EmployeeOperations {

    private Employee[] employees;
    private int count;

    public EmployeeOperations(int size) {

        employees = new Employee[size];
        count = 0;

    }

    // Add Employee
    public void addEmployee(Employee employee) {

        if (count < employees.length) {

            employees[count++] = employee;

            System.out.println("Employee Added.");

        } else {

            System.out.println("Array is Full.");

        }

    }

    // Search Employee
    public Employee searchEmployee(int id) {

        for (int i = 0; i < count; i++) {

            if (employees[i].getEmployeeId() == id) {

                return employees[i];

            }

        }

        return null;

    }

    // Delete Employee
    public void deleteEmployee(int id) {

        for (int i = 0; i < count; i++) {

            if (employees[i].getEmployeeId() == id) {

                for (int j = i; j < count - 1; j++) {

                    employees[j] = employees[j + 1];

                }

                employees[count - 1] = null;

                count--;

                System.out.println("Employee Deleted.");

                return;

            }

        }

        System.out.println("Employee Not Found.");

    }

    // Display Employees
    public void displayEmployees() {

        System.out.println("\nEmployee List\n");

        for (int i = 0; i < count; i++) {

            System.out.println(employees[i]);

        }

    }

}