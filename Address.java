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
@Table(name="address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name = "street")
	private String street;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "zip_code")
	private int zipcode;

	public Address() {
		super();
	}
	
	public Address(int id, String street, String city, String state, int zipcode) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	
	// method to create an address object in the database
		public void createAddress() throws ClassNotFoundException, SQLException {
			Connection connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			
			// create author record
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO address(id,"
					+"street, city, state, zip_code) VALUES(?,?,?,?,?)");
			stmt.setInt(1,  this.getId());
			stmt.setString(2,  this.getStreet());
			stmt.setString(3, this.getCity());
			stmt.setString(4,  this.getState());
			stmt.setInt(5,  this.getZipcode());
			
			stmt.executeUpdate();
			
			connection.commit();		
		}
		
		// Search feature
		public boolean searchAddress(int addressID) {
			boolean check = false;
			Address addr = new Address();
			SessionFactory factory = new Configuration().
					configure("hibernate.cfg.xml").
					addAnnotatedClass(Address.class).
					buildSessionFactory();

			Session session = factory.getCurrentSession();

			try {
				session.beginTransaction();
				addr = session.get(Address.class, addressID);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close();
			}
			if (addr == null) {
				System.out.println("Customer not found");
				check = false;
			} else {
				System.out.println("Found: " + addr.getId());
				check = true;
			}
			return check;
		}
		
		public Address getSearchAddress(int addressID) {
			boolean check = false;
			Address addr = new Address();
			SessionFactory factory = new Configuration().
					configure("hibernate.cfg.xml").
					addAnnotatedClass(Address.class).
					buildSessionFactory();

			Session session = factory.getCurrentSession();

			try {
				session.beginTransaction();
				addr = session.get(Address.class, addressID);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close();
			}
			if (addr == null) {
				System.out.println("Customer not found");
				check = false;
			} else {
				System.out.println("Found: " + addr.getId());
				check = true;
			}
			return addr;
		}

	

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return "Address: " + street + "," + city + "," + state + "," + zipcode;
	}
}
