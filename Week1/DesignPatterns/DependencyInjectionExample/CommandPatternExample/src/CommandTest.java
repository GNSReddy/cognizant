public class CommandTest {

    public static void main(String[] args) {

        // Receiver
        Light light = new Light();

        // Commands
        Command lightOn = new LightOnCommand(light);
        Command lightOff = new LightOffCommand(light);

        // Invoker
        RemoteControl remote = new RemoteControl();

        System.out.println("Turning Light ON");
        remote.setCommand(lightOn);
        remote.pressButton();

        System.out.println();

        System.out.println("Turning Light OFF");
        remote.setCommand(lightOff);
        remote.pressButton();

    }

}