public class GamificationDecorator extends CourseDecorator {
    public GamificationDecorator(Course course) {
        super(course);

    }
    @Override
    public void deliverContent() {
        super.deliverContent();
        addGamification();
    }
    private void addGamification() {
        System.out.println("Adding gamification elements to the course.");
    }
}
