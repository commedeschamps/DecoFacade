public class MentorSupportDecorator extends CourseDecorator {
    public MentorSupportDecorator(Course course) {
        super(course);
    }

    @Override
    public void deliverContent() {
        super.deliverContent();
        addMentorSupport();
    }

    private void addMentorSupport() {
        System.out.println("Providing mentor support for the course.");
    }

    @Override
    public void onCourseComplete() {
        super.onCourseComplete();
        System.out.println("Your mentor congratulates you on completion of" + getCourseName() + "Well Done!");
    }
}
