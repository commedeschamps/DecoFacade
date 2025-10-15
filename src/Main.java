public class Main {
    public static void main(String[] args) {
        System.out.println("=== Learning Management System ===\n");

        StudentPortalFacade studentPortal = new StudentPortalFacade();

        Student john = new Student("John Doe", "john@email.com");
        Student jane = new Student("Jane Smith", "jane@email.com");

        Course mathCourse = new Calculus();
        Course programmingCourse = new Programming();

        Course decoratedMathCourse = new CertificateDecorator(
                new MentorSupportDecorator(mathCourse)
        );

        GamificationDecorator decoratedProgrammingCourse = new GamificationDecorator(programmingCourse);
        decoratedProgrammingCourse.setStudent(john);

        GamificationDecorator mathWithGamification = new GamificationDecorator(new Calculus());
        mathWithGamification.setStudent(jane);

        System.out.println("--- Enrolling in Courses ---");
        studentPortal.enrollInCourse(decoratedMathCourse);
        studentPortal.enrollInCourse(decoratedProgrammingCourse);
        studentPortal.enrollInCourse(mathWithGamification);

        System.out.println("\n--- Starting Learning ---");
        studentPortal.startLearning(decoratedMathCourse);
        studentPortal.startLearning(decoratedProgrammingCourse);
        studentPortal.startLearning(mathWithGamification);

        System.out.println("\n--- Checking Progress ---");
        studentPortal.showProgress();

        System.out.println("\n--- Completing Courses ---");
        studentPortal.completeLearning(decoratedMathCourse);
        studentPortal.completeLearning(decoratedProgrammingCourse);
        studentPortal.completeLearning(mathWithGamification);

        System.out.println("\n--- Final Progress Report ---");
        studentPortal.showProgress();

        System.out.println("\n--- Attempting to Access Non-Enrolled Course ---");
        Course newCourse = new Programming();
        studentPortal.startLearning(newCourse);

        System.out.println("\n--- Final Leaderboard ---");
        studentPortal.showLeaderboard();
    }
}
