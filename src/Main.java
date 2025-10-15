import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Learning Management System ===\n");

        StudentPortalFacade studentPortal = new StudentPortalFacade();

        Course mathCourse = new Calculus();
        Course programmingCourse = new Programming();

        Course decoratedMathCourse = new CertificateDecorator(
                new MentorSupportDecorator(mathCourse)
        );

        Course decoratedProgrammingCourse = new GamificationDecorator(programmingCourse);

        System.out.println("--- Course Enrolling ---");
        studentPortal.enrollInCourse(decoratedMathCourse);
        studentPortal.enrollInCourse(decoratedProgrammingCourse);

        System.out.println("\n--- Start Learning ---");
        studentPortal.startLearning(decoratedMathCourse);

        System.out.println();
        studentPortal.startLearning(decoratedProgrammingCourse);

        System.out.println("\n--- Completing Courses ---");
        studentPortal.completeLearning(decoratedMathCourse);

        System.out.println();
        studentPortal.completeLearning(decoratedProgrammingCourse);

        System.out.println("\n--- Export/Import Progress ---");
        Path exportFile = Path.of("progress.json");
        studentPortal.exportProgress(exportFile);
        System.out.println("Progress exported to " + exportFile.toAbsolutePath());
        studentPortal.importProgress(exportFile);
        System.out.println("Reloaded progress: " + studentPortal.getAllCourseProgress());

        System.out.println("\n--- Attempt to access closed course ---");
        Course newCourse = new Programming();
        studentPortal.startLearning(newCourse);
    }
}
