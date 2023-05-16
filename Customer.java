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
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // same here
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="customer_address_id")
	private Address address;

	
	
	public Customer() {
		super();
	}
	
	public Customer(int id, String name, String phone, String email) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public void createCustomer(Address address) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionFactory.getConnection();
		
		connection.setAutoCommit(false);
		
		this.setAddress(address);
		
		PreparedStatement stmt = connection.prepareStatement("INSERT INTO customer("
				+"id, name, phone, email, customer_address_id) VALUES(?,?,?,?,?)");
		stmt.setInt(1,  this.getId());
		stmt.setString(2,  this.getName());
		stmt.setString(3,  this.getPhone());
		stmt.setString(4, this.getEmail());
		stmt.setInt(5,  this.getAddress().getId());
		
		stmt.executeUpdate();
		
		connection.commit();
	}

	// Search feature
	public boolean searchCust(int customerID) {
		boolean check = false;
		Customer cust = new Customer();
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			cust = session.get(Customer.class, customerID);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
		if (cust == null) {
			System.out.println("Customer not found");
			check = false;
		} else {
			System.out.println("Found: " + cust.getId());
			check = true;
		}
		return check;
	}
	
	public Customer getSearchCust(int customerID) {
		boolean check = false;
		Customer cust = new Customer();
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			cust = session.get(Customer.class, customerID);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
		if (cust == null) {
			System.out.println("Customer not found");
			check = false;
		} else {
			System.out.println("Found: " + cust.getId());
			check = true;
		}
		return cust;
	}
	
	// methods to update customer info
	
	// update customer name
	public void updateCustomerName(int customerID, String newName) {
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Customer cust = session.get(Customer.class, customerID);
			cust.setName(newName);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	
	
	// update customer phone
	public void updateCustomerPhone(int customerID, String newPhone) {
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Customer cust = session.get(Customer.class, customerID);
			cust.setPhone(newPhone);
			
			session.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	
	
	// update customer email
	public void updateCustomerEmail(int customerID, String newEmail) {
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Customer cust = session.get(Customer.class, customerID);
			cust.setEmail(newEmail);
			
			session.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	
	
	// update customer address
	public void updateCustomerAddress(int customerID, Address newAddy) throws ClassNotFoundException, SQLException {
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Customer cust = session.get(Customer.class, customerID);
			
			Address check = new Address();
			if(check.searchAddress(newAddy.getId()) == false) {
				System.out.println("ADDRESS DOES NOT EXIST");
				Address add = new Address(newAddy.getId(), newAddy.getStreet(), newAddy.getCity(), 
						newAddy.getState(), newAddy.getZipcode());
				add.createAddress();
				cust.setAddress(add);
				
			} else {
				cust.setAddress(newAddy);
			}
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	
	
	
	// delete a customer from the database
	public void deleteCustomer(int custID) {
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(Customer.class).
				addAnnotatedClass(Address.class).
				buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Customer cust = session.get(Customer.class, custID);
			session.delete(cust);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
	
	

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	/*
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	*/

	@Override
	public String toString() {
		return "Customer: " + name + "," + email + "," + phone;
	}
}