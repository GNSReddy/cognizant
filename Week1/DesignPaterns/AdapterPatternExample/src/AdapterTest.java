public class AdapterTest {

    public static void main(String[] args) {

        PaymentProcessor paytm = new PaytmAdapter();
        paytm.processPayment(1500);

        PaymentProcessor googlePay = new GooglePayAdapter();
        googlePay.processPayment(2500);

    }

}