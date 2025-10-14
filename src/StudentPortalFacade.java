import javax.management.Notification;
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

    }
    public void startLearning(Course course){
        if(enrolledCourses.contains(course)){
            course.deliverContent();
            progressTracker.trackActivity(course);
            notificationService.sendReminderNotification();

        } else {
            System.out.println("You are not enrolled in this course.");
        }
    }
    public void completeLearning(Course course){
        if(enrolledCourses.contains(course)){
            courseProgress.put(course, 100);
            progressTracker.stopTracking(course);
            notificationService.sendCompletionNotification();


        } else {
            System.out.println("You are not enrolled in this course.");
        }
    }
}

