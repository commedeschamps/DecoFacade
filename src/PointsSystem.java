public class PointsSystem {
    private static final int CONTENT_VIEW_POINTS = 10;
    private static final int COURSE_COMPLETION_POINTS = 100;
    private static final int QUIZ_COMPLETION_POINTS = 20;

    public void awardPoints(int studentId, String studentName, double points, String reason) {
        Leaderboard leaderboard = Leaderboard.getInstance();
        leaderboard.addOrUpdateStudent(studentId, studentName, points);
        System.out.println("+" + points + " points: " + reason);
    }

    public void awardContentViewPoints(int studentId, String studentName) {
        awardPoints(studentId, studentName, CONTENT_VIEW_POINTS, "Viewing course content");
    }

    public void awardCourseCompletionPoints(int studentId, String studentName) {
        awardPoints(studentId, studentName, COURSE_COMPLETION_POINTS, "Course completion");
    }

    public void awardQuizPoints(int studentId, String studentName) {
        awardPoints(studentId, studentName, QUIZ_COMPLETION_POINTS, "Quiz completion");
    }

    public double getStudentPoints(int studentId) {
        return Leaderboard.getInstance().getStudentPoints(studentId);
    }
}
