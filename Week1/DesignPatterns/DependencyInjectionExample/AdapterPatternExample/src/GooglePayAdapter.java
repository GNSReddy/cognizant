public class GooglePayAdapter implements PaymentProcessor {

    private GooglePayGateway googlePay;

    public GooglePayAdapter() {
        googlePay = new GooglePayGateway();
    }

    @Override
    public void processPayment(double amount) {
        googlePay.sendPayment(amount);
    }

}