public abstract class CourseDecorator implements Course {
    protected Course decoratedCourse;

    public CourseDecorator(Course course) {
        this.decoratedCourse = course;
    }

    @Override
    public  void deliverContent(){
        decoratedCourse.deliverContent();
    }
    @Override
    public String getCourseName() {
        return decoratedCourse.getCourseName();
    }
}
