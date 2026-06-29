public class PaytmAdapter implements PaymentProcessor {

    private PaytmGateway paytm;

    public PaytmAdapter() {
        paytm = new PaytmGateway();
    }

    @Override
    public void processPayment(double amount) {
        paytm.payMoney(amount);
    }

}