public class Main {
    public static void main(String[] args) {
        System.out.println("=== Learning Management System ===\n");

        // Создаем фасад для студенческого портала
        StudentPortalFacade studentPortal = new StudentPortalFacade();

        // Создаем базовые курсы
        Course mathCourse = new Calculus();
        Course programmingCourse = new Programming();

        // Декорируем математический курс с поддержкой ментора и сертификатом
        Course decoratedMathCourse = new CertificateDecorator(
                new MentorSupportDecorator(mathCourse)
        );

        // Декорируем курс программирования с геймификацией
        Course decoratedProgrammingCourse = new GamificationDecorator(programmingCourse);

        System.out.println("--- Course Enrolling ---");
        // Записываемся на курсы
        studentPortal.enrollInCourse(decoratedMathCourse);
        studentPortal.enrollInCourse(decoratedProgrammingCourse);

        System.out.println("\n--- Start Learning ---");
        // Начинаем изучение математики
        studentPortal.startLearning(decoratedMathCourse);

        System.out.println();
        // Начинаем изучение программирования
        studentPortal.startLearning(decoratedProgrammingCourse);

        System.out.println("\n--- Completing Courses ---");
        // Завершаем математику
        studentPortal.completeLearning(decoratedMathCourse);

        System.out.println();
        // Завершаем программирование
        studentPortal.completeLearning(decoratedProgrammingCourse);

        System.out.println("\n--- Attempt to access closed course ---");
        Course newCourse = new Programming();
        studentPortal.startLearning(newCourse);
    }
}
