public class Student {
    private static int idCounter = 1;
    private int id;
    private String name;
    private String email;

    public Student(String name) {
        this.id = idCounter++;
        this.name = name;
    }

    public Student(String name, String email) {
        this.id = idCounter++;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "'}";
    }
}

