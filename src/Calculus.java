public class Calculus implements Course {
    @Override
    public void deliverContent() {
        System.out.println("Delivering Calculus content...");
    }
    @Override
    public String getCourseName() {
        return "Calculus 1";
    }
}
