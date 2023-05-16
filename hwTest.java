package hibernate;
import java.sql.SQLException;

public class hwTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Address ad1 = new Address(123, "road", "place", "CA", 12345);
		//ad1.createAddress();
		
		Address ad2 = new Address(111, "rock", "stone", "CA", 44444);
		//ad2.createAddress();
		
		
		Customer c1 = new Customer(300, "name", "123-456-7890","name@gmail");
		//c1.createCustomer(ad1);
		
	//	Customer heeh = new Customer();
	//	heeh = heeh.searchCust(200);
		
	//	Address bloo = new Address();
	//	bloo = bloo.searchAddress(123);
		
		
		Order o1 = new Order(34343, "2023-05-13", "chips", 2.50);
		o1.createOrder(c1);
		
	//	Order meh = new Order();
	//	meh = meh.searchOrder(34343);
		
		//c1.updateCustomerName(300, "steve");
		
		Address ad3 = new Address(999, "eagle", "rock", "CA", 22333);
	//	c1.updateCustomerAddress(300, ad3);
		
		Customer c3 = new Customer(34, "hank", "444-444-44444", "hank@hill.com");
		
		//o1.updateOrderCustomer(34343, c3);
		
		//o1.deleteOrder(o1.getNumber());
		
		
	}
}
