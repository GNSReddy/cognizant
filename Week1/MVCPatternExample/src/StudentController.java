public class StudentController {

    private Student model;
    private StudentView view;

    public StudentController(Student model, StudentView view) {
        this.model = model;
        this.view = view;
    }

    public void updateStudentName(String name) {
        model.setName(name);
    }

    public void updateStudentGrade(String grade) {
        model.setGrade(grade);
    }

    public void showStudent() {
        view.displayStudentDetails(model);
    }

}