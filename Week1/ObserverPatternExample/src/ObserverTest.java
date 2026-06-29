public class ObserverTest {

    public static void main(String[] args) {

        StockMarket stockMarket = new StockMarket();

        Observer mobile = new MobileApp("Rahul");

        Observer web = new WebApp("Anjali");

        stockMarket.registerObserver(mobile);

        stockMarket.registerObserver(web);

        System.out.println("Updating Stock Price...\n");

        stockMarket.setStock("TCS", 4250.50);

        System.out.println();

        stockMarket.setStock("Infosys", 1590.75);

    }

}