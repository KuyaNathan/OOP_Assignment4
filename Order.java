package hibernate;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
//import javax.persistence.ManyToOne;
//import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ConnectionFactory;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "number")
	private int number;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "item")
	private String item;
	
	@Column(name = "price")
	private double price;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="order_customer_id")
	private Customer customer;
	
	
	public Order() {
		super();
	}
	
	public Order(int number, String date, String item, double price) {
		this.number = number;
		this.date = date;
		this.item = item;
		this.price = price;
	}
	
	
	
	public void createOrder(Customer customer) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionFactory.getConnection();
		
		connection.setAutoCommit(false);
		
		this.setCustomer(customer);
		
		PreparedStatement stmt = connection.prepareStatement("INSERT INTO orders(number,"
				+ "date, item, price, order_customer_id) VALUES(?,?,?,?,?)");
		stmt.setInt(1, this.getNumber());
		stmt.setString(2, this.getDate());
		stmt.setString(3, this.getItem());
		stmt.setDouble(4, this.getPrice());
		stmt.setInt(5, customer.getId());
		
		stmt.executeUpdate();
	
		
		stmt = connection.prepareStatement("SELECT * FROM customer order by id desc");
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			customer.setId(rs.getInt("id"));
		}
		
		
		
		connection.commit();
	}
	
	
	
	
	// search feature
	public boolean searchOrder(int orderNum) {
		boolean check = false;
		Order order = new Order();
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Order.class).
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			order = session.get(Order.class, orderNum);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
		if (order == null) {
			System.out.println("Order not found");
			check = false;
		} else {
			System.out.println("Found: " + order.getNumber());
			check = true;
		}
		return check;
	}
	
	public Order getSearchOrder(int orderNum) {
		Order order = new Order();
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Order.class).
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			order = session.get(Order.class, orderNum);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
		if (order == null) {
			System.out.println("Order not found");
		} else {
			System.out.println("Found: " + order.getNumber());
		}
		return order;
	}
	
	
	// methods to update the order info
	
	// update order date
	public void updateOrderDate(int orderNum, String newDate) {
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Order.class).
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Order order = session.get(Order.class, orderNum);
			order.setDate(newDate);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	
	
	// update order item
	public void updateOrderItem(int orderNum, String newItem) {
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Order.class).
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Order order = session.get(Order.class, orderNum);
			order.setItem(newItem);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	
	
	// update order price
	public void updateOrderPrice(int orderNum, double newPrice) {
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Order.class).
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Order order = session.get(Order.class, orderNum);
			order.setPrice(newPrice);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	
	
	// update order customer
	public void updateOrderCustomer(int orderNum, Customer newCust) throws ClassNotFoundException, SQLException {
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Order.class).
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Order ord = session.get(Order.class, orderNum);
			
			Customer check = new Customer();
			if(check.searchCust(newCust.getId()) == false) {
				System.out.println("Customer does not exist");
				Customer add = new Customer(newCust.getId(), newCust.getName(), newCust.getPhone(),
						newCust.getEmail());
				Address hold = new Address(12345, "update later", "update later", "update later", 12345);
				if(hold.searchAddress(hold.getId()) == false) {
					hold.createAddress();
					add.createCustomer(hold);
					ord.setCustomer(add);
				} else {
					add.createCustomer(hold);
					ord.setCustomer(add);
				}

			} else {
				ord.setCustomer(newCust);
			}
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	
	
	
	
	// delete an order from the database
	public void deleteOrder(int orderNum) {
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Order.class).
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Order ord = session.get(Order.class, orderNum);
			session.delete(ord);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
