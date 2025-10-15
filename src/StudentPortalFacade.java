import persistence.CourseProgressRepository;
import persistence.JdbcCourseProgressRepository;
import serialization.ProgressSerializationService;

import java.nio.file.Path;
import java.util.Map;

public class StudentPortalFacade {
    private final CourseProgressRepository courseProgressRepository;
    private final ProgressSerializationService serializationService;
    private final NotificationService notificationService;
    private final ProgressTracker progressTracker;

    public StudentPortalFacade() {
        this(new JdbcCourseProgressRepository("jdbc:sqlite:student_portal.db"), new ProgressSerializationService());
    }

    public StudentPortalFacade(CourseProgressRepository courseProgressRepository,
                               ProgressSerializationService serializationService) {
        this.courseProgressRepository = courseProgressRepository;
        this.serializationService = serializationService;
        this.notificationService = new NotificationService();
        this.progressTracker = new ProgressTracker();
    }

    public void enrollInCourse(Course course) {
        if (!courseProgressRepository.isEnrolled(course.getCourseName())) {
            courseProgressRepository.enrollCourse(course.getCourseName());
            notificationService.sendEnrolmentNotification();
        }
    }

    public void startLearning(Course course) {
        if (courseProgressRepository.isEnrolled(course.getCourseName())) {
            course.deliverContent();
            progressTracker.trackActivity(course);
            notificationService.sendReminderNotification();
        } else {
            System.out.println("You are not enrolled in this course.");
        }
    }

    public void completeLearning(Course course) {
        if (courseProgressRepository.isEnrolled(course.getCourseName())) {
            courseProgressRepository.updateProgress(course.getCourseName(), 100);
            progressTracker.stopTracking(course);
            notificationService.sendCompletionNotification();
        } else {
            System.out.println("You are not enrolled in this course.");
        }
    }

    public int getCourseProgress(Course course) {
        if (courseProgressRepository.isEnrolled(course.getCourseName())) {
            return courseProgressRepository.getProgress(course.getCourseName());
        }
        return 0;
    }

    public Map<String, Integer> getAllCourseProgress() {
        return courseProgressRepository.findAllProgress();
    }

    public void exportProgress(Path targetFile) {
        serializationService.exportProgress(getAllCourseProgress(), targetFile);
    }

    public void importProgress(Path sourceFile) {
        Map<String, Integer> importedProgress = serializationService.importProgress(sourceFile);
        courseProgressRepository.replaceAll(importedProgress);
    }
}
