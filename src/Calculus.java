public class Calculus implements Course {
    @Override
    public void deliverContent() {
        System.out.println("Delivering Calculus content...");
    }
    @Override
    public String getCourseName() {
        return "Calculus 1";
    }
    @Override
    public void onCourseComplete() {
        // Базовый курс не имеет дополнительных действий при завершении
    }
}
