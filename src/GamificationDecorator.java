public class GamificationDecorator extends CourseDecorator {
    private PointsSystem pointsSystem;
    private Student currentStudent;

    public GamificationDecorator(Course course) {
        super(course);
        this.pointsSystem = new PointsSystem();
    }

    public void setStudent(Student student) {
        this.currentStudent = student;
        Leaderboard.getInstance().addOrUpdateStudent(student.getId(), student.getName(), 0);
    }

    @Override
    public void deliverContent() {
        super.deliverContent();
        addGamification();
    }

    private void addGamification() {
        System.out.println("Adding gamification elements to the course.");

        if (currentStudent != null) {
            pointsSystem.awardContentViewPoints(currentStudent.getId(), currentStudent.getName());
            System.out.println("Your current points: " + pointsSystem.getStudentPoints(currentStudent.getId()));
        }
    }

    @Override
    public void onCourseComplete() {
        super.onCourseComplete();

        if (currentStudent != null) {
            pointsSystem.awardCourseCompletionPoints(currentStudent.getId(), currentStudent.getName());
            double totalPoints = pointsSystem.getStudentPoints(currentStudent.getId());
            System.out.println("Final Score: " + totalPoints + " points! You're on the leaderboard!");
            Leaderboard.getInstance().displayLeaderboard();
        } else {
            System.out.println("Final Score: 100 points! You're on the leaderboard!");
        }
    }
}
