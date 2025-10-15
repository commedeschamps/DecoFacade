public class CertificateDecorator extends CourseDecorator {
    public CertificateDecorator(Course course) {
        super(course);
    }

    @Override
    public void deliverContent() {
        super.deliverContent();
    }

    @Override
    public void onCourseComplete() {
        super.onCourseComplete();
        awardCertificate();
    }

    private void awardCertificate() {
        System.out.println("Certificate awarded for completing: " + getCourseName());
    }
}
