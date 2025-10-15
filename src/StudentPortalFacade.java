import java.util.*;

public class StudentPortalFacade {
    private List<Course> enrolledCourses;
    private Map<Course, Integer> courseProgress;
    private NotificationService notificationService;
    private ProgressTracker progressTracker;

    public StudentPortalFacade(){
        this.enrolledCourses = new ArrayList<>();
        this.courseProgress = new HashMap<>();
        this.notificationService = new NotificationService();
        this.progressTracker = new ProgressTracker();
    }

    public void enrollInCourse(Course course){
        enrolledCourses.add(course);
        courseProgress.put(course, 0);
        notificationService.sendEnrolmentNotification();
        System.out.println("Enrolled in: " + course.getCourseName());
    }

    public void startLearning(Course course){
        if(enrolledCourses.contains(course)){
            System.out.println("\n--- Starting: " + course.getCourseName() + " ---");
            course.deliverContent();

            courseProgress.put(course, 50);
            progressTracker.updateProgress(course, 50);
            notificationService.sendProgressNotification(50);
        } else {
            System.out.println("You are not enrolled in this course.");
        }
    }

    public void completeLearning(Course course){
        if(enrolledCourses.contains(course)){
            System.out.println("\n--- Completing: " + course.getCourseName() + " ---");

            courseProgress.put(course, 100);
            progressTracker.completeProgress(course);

            course.onCourseComplete();

            notificationService.sendCompletionNotification();
        } else {
            System.out.println("You are not enrolled in this course.");
        }
    }

    public void showProgress() {
        System.out.println("\n=== Course Progress Report ===");
        for (Course course : enrolledCourses) {
            System.out.println(course.getCourseName() + ": " + courseProgress.get(course) + "%");
        }
    }
}
