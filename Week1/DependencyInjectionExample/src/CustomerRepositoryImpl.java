public class CustomerRepositoryImpl
        implements CustomerRepository {

    @Override
    public String findCustomerById(int id) {

        return "Customer ID : " + id +
                "\nCustomer Name : Rahul";

    }

}