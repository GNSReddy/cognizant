public class BuilderTest {

    public static void main(String[] args) {

        Computer gamingPC = new Computer.Builder()
                .setCpu("Intel Core i9")
                .setRam(32)
                .setStorage(1000)
                .setGraphicsCard(true)
                .setBluetooth(true)
                .build();

        gamingPC.display();

        System.out.println();

        Computer officePC = new Computer.Builder()
                .setCpu("Intel Core i5")
                .setRam(8)
                .setStorage(512)
                .setGraphicsCard(false)
                .setBluetooth(true)
                .build();

        officePC.display();
    }
}