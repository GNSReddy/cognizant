public class MVCTest {

    public static void main(String[] args) {

        Student student =
                new Student("Rahul", "101", "A");

        StudentView view =
                new StudentView();

        StudentController controller =
                new StudentController(student, view);

        System.out.println("Original Student");

        controller.showStudent();

        System.out.println();

        System.out.println("After Update");

        controller.updateStudentName("Anjali");

        controller.updateStudentGrade("A+");

        controller.showStudent();

    }

}