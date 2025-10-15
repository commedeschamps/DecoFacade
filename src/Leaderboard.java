import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Leaderboard {
    private static final Leaderboard instance = new Leaderboard();
    private static final Map<Integer, StudentPoints> studentPointsMap = new HashMap<>();

    private Leaderboard() {
    }

    public static Leaderboard getInstance() {
        return instance;
    }

    public void addOrUpdateStudent(int studentId, String studentName, double points) {
        if (studentPointsMap.containsKey(studentId)) {
            studentPointsMap.get(studentId).addPoints(points);
        } else {
            studentPointsMap.put(studentId, new StudentPoints(studentId, studentName, points));
        }
    }

    public void addPoints(int studentId, double points) {
        if (studentPointsMap.containsKey(studentId)) {
            studentPointsMap.get(studentId).addPoints(points);
        }
    }

    public List<StudentPoints> getTopStudents(int limit) {
        return studentPointsMap.values().stream()
                .sorted(Comparator.comparing(StudentPoints::getPoints).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void displayLeaderboard() {
        System.out.println("\n=== Leaderboard ===");
        List<StudentPoints> topStudents = getTopStudents(10);
        if (topStudents.isEmpty()) {
            System.out.println("No students yet.");
        } else {
            for (int i = 0; i < topStudents.size(); i++) {
                StudentPoints student = topStudents.get(i);
                System.out.println((i + 1) + ". " + student.getName() + ": " + student.getPoints() + " points");
            }
        }
        System.out.println("===================\n");
    }

    public double getStudentPoints(int studentId) {
        if (studentPointsMap.containsKey(studentId)) {
            return studentPointsMap.get(studentId).getPoints();
        }
        return 0.0;
    }

    // Внутренний класс для хранения данных студента
    static class StudentPoints {
        private int studentId;
        private String name;
        private double points;

        public StudentPoints(int studentId, String name, double points) {
            this.studentId = studentId;
            this.name = name;
            this.points = points;
        }

        public int getStudentId() {
            return studentId;
        }

        public String getName() {
            return name;
        }

        public double getPoints() {
            return points;
        }

        public void addPoints(double additionalPoints) {
            this.points += additionalPoints;
        }
    }
}
