public class DecoratorTest {

    public static void main(String[] args) {

        System.out.println("Email Notification");
        Notifier email = new EmailNotifier();
        email.send("Meeting at 10 AM");

        System.out.println();

        System.out.println("Email + SMS");
        Notifier sms = new SMSNotifierDecorator(
                new EmailNotifier());

        sms.send("Project Deadline");

        System.out.println();

        System.out.println("Email + SMS + Slack");

        Notifier slack =
                new SlackNotifierDecorator(
                        new SMSNotifierDecorator(
                                new EmailNotifier()));

        slack.send("Server Down");

    }

}