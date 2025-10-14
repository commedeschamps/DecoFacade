public class ProgressTracker {
    public void updateProgress(Course course, int progress) {
        System.out.println("Updating progress for course: " + course.getCourseName() + " - " + progress + "% completed.");
    }
    public void completeProgress(Course course) {
        System.out.println("Course completed: " + course.getCourseName() + ". Congratulations!");
    }
    public void trackActivity(Course course) {
        System.out.println("Progress tracked for students in course:" + course.getCourseName());
    }
    public void stopTracking(Course course) {
        System.out.println("Stopped tracking progress for course: " + course.getCourseName());
    }
}
