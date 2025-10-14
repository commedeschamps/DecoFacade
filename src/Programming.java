public class Programming implements Course {
    public void deliverContent() {
        System.out.println("Delivering Programming course content...");
    }

    @Override
    public String getCourseName() {
        return "Programming Course";
    }
}
