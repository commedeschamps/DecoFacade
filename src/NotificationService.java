public class NotificationService{
    public void sendEnrolmentNotification(){
        System.out.println("Notification: Successfully enrolled in Course.");

    }
    public void sendProgressNotification(int progress){
        System.out.println(("Notification: You've reached" + progress + " %"));

    }
    public void sendCompletionNotification(){
        System.out.println("Notification: Congratulations! You have finished this course!");

    }
    public void sendReminderNotification(){
        System.out.println("Reminder: Do not forget to continue the course!");

    }
}
