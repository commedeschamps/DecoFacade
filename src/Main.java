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

        System.out.println("--- Enrolling in Courses ---");
        studentPortal.enrollInCourse(decoratedMathCourse);
        studentPortal.enrollInCourse(decoratedProgrammingCourse);

        System.out.println("\n--- Starting Learning ---");
        studentPortal.startLearning(decoratedMathCourse);

        studentPortal.startLearning(decoratedProgrammingCourse);

        System.out.println("\n--- Checking Progress ---");
        studentPortal.showProgress();

        System.out.println("\n--- Completing Courses ---");
        studentPortal.completeLearning(decoratedMathCourse);

        studentPortal.completeLearning(decoratedProgrammingCourse);

        System.out.println("\n--- Final Progress Report ---");
        studentPortal.showProgress();

        System.out.println("\n--- Attempting to Access Non-Enrolled Course ---");
        Course newCourse = new Programming();
        studentPortal.startLearning(newCourse);
    }
}
