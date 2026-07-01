public class StrategyTest {

    public static void main(String[] args) {

        PaymentContext context =
                new PaymentContext(new CreditCardPayment());

        context.makePayment(5000);

        System.out.println();

        context.setPaymentStrategy(new PayPalPayment());

        context.makePayment(3000);

    }

}