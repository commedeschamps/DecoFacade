package persistence;

import java.util.Map;

public interface CourseProgressRepository {
    void enrollCourse(String courseName);

    void updateProgress(String courseName, int progress);

    boolean isEnrolled(String courseName);

    int getProgress(String courseName);

    Map<String, Integer> findAllProgress();

    void replaceAll(Map<String, Integer> progressSnapshot);
}
