package hibernate;

import java.awt.Insets;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class display extends Application{
	
	Scene homeMenu;
	Scene customerPage;
	Scene orderPage;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception{
		// home menu
		Label home = new Label("Home");
		home.setStyle("-fx-font-size: 40; -fx-font-weight: bold");
		Button custButton = new Button("Customer");
		Button ordButton = new Button("Order");
		VBox homeHold = new VBox(40, home, custButton, ordButton);
		homeHold.setAlignment(Pos.CENTER);
		homeMenu = new Scene(homeHold, 500, 500);
		
		// home button event handlers
		custButton.setOnAction(event ->
		{
			stage.setScene(customerPage);
			stage.show();
		});
				
		ordButton.setOnAction(event ->
		{
			stage.setScene(orderPage);
			stage.show();
		});
		
		stage.setScene(homeMenu);
		stage.show();
		
		
		
		
		// customer page
		// customer part
		Label customerLabel = new Label("Customer");
		customerLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold");
		Label idLabel = new Label("Customer ID");
		TextField idField = new TextField();
		Label nameLabel = new Label("Name");
		TextField nameField = new TextField();
		Label phoneLabel = new Label("Phone");
		TextField phoneField = new TextField();
		Label emailLabel = new Label("Email");
		TextField emailField = new TextField();
		Label cFeedback = new Label("");
		cFeedback.setStyle("-fx-text-fill: red");
		
		// address part
		Label addressLabel = new Label("Address");
		addressLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold");
		Label addressIDLabel = new Label("Address ID");
		TextField addressIDField = new TextField();
		Label streetLabel = new Label("Street");
		TextField streetField = new TextField();
		Label cityLabel = new Label("City");
		TextField cityField = new TextField();
		Label stateLabel = new Label("State");
		TextField stateField = new TextField();
		Label zipLabel = new Label("ZIP Code");
		TextField zipField = new TextField();
		Label spacing1 = new Label("");
		
		// customer buttons
		Button cSearch = new Button("Search");
		Button cAdd = new Button("Add");
		Button cUpdate = new Button("Update");
		Button cDel = new Button("Delete");
		Button cBack = new Button("Back");
		
		
		// customer button event handlers
		cSearch.setOnAction(event ->
		{
			int custID = Integer.parseInt(idField.getText());
			Customer cust = new Customer();
			if(cust.searchCust(custID) == true)
			{
				cust = cust.getSearchCust(custID);
				idField.setText(String.valueOf(cust.getId()));
				nameField.setText(cust.getName());
				phoneField.setText(cust.getPhone());
				emailField.setText(cust.getEmail());
				
				Address temp = new Address();
				temp = cust.getAddress();
				streetField.setText(temp.getStreet());
				cityField.setText(temp.getCity());
				stateField.setText(temp.getState());
				zipField.setText(String.valueOf(temp.getZipcode()));
			}
		});
		
		cAdd.setOnAction(event ->
		{
			Address newAdd = new Address(Integer.parseInt(addressIDField.getText()),
					streetField.getText(), cityField.getText(), stateField.getText(),
					Integer.parseInt(zipField.getText()));
			
			Address check = new Address();
			if(check.searchAddress(newAdd.getId()) == false) {
				try {
					newAdd.createAddress();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			Customer newCust = new Customer(Integer.parseInt(idField.getText()),
					nameField.getText(), phoneField.getText(), emailField.getText());
			try {
				newCust.createCustomer(newAdd);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Customer cCheck = new Customer();
			if (cCheck.searchCust(newCust.getId()) == true) {
				cFeedback.setText("Successfully added customer");
			}
			else {
				cFeedback.setText("Failed to add customer");
			}
		});
		
		
		cUpdate.setOnAction(event ->
		{
			int holdID = Integer.parseInt(idField.getText());
			Customer check = new Customer();
			if(check.searchCust(holdID) == true)
			{
				check = check.getSearchCust(holdID);
				
				if(nameField.getText() != null)
				{
					check.updateCustomerName(holdID, nameField.getText());
				}
				
				if(phoneField.getText() != null)
				{
					check.updateCustomerPhone(holdID, phoneField.getText());
				}
				
				if(emailField.getText() != null)
				{
					check.updateCustomerEmail(holdID, emailField.getText());
				}
				
			}
			
		});
		
		
		cDel.setOnAction(event ->
		{
			int holdID = Integer.parseInt(idField.getText());
			Customer check = new Customer();
			if(check.searchCust(holdID) == true)
			{
				check = check.getSearchCust(holdID);
				
				check.deleteCustomer(holdID);
			}
		});
		
		cBack.setOnAction(event ->
		{
			stage.setScene(homeMenu);
			stage.show();
		});
		
		
		HBox cButtons = new HBox(20, cSearch, cAdd, cUpdate, cDel, cBack);
		
		VBox cHold = new VBox(20, customerLabel, idLabel, idField, nameLabel, nameField, 
				phoneLabel, phoneField, emailLabel, emailField, spacing1, addressLabel,
				addressIDLabel, addressIDField, streetLabel, streetField, cityLabel, 
				cityField, stateLabel, stateField, zipLabel, zipField, cButtons, cFeedback);
		cHold.setAlignment(Pos.CENTER_LEFT);

		customerPage = new Scene(cHold, 1000, 950);
		
		
		
		
		
		
		
		
		
		// order page
		Label orderLabel = new Label("Order");
		orderLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold");
		Label orderNumLabel = new Label("Order Number");
		TextField orderNumField = new TextField();
		Label dateLabel = new Label("Date");
		TextField dateField = new TextField();
		Label orderCustLabel = new Label("Customer ID");
		TextField orderCustField = new TextField();
		Label itemLabel = new Label("Item");
		TextField itemField = new TextField();
		Label priceLabel = new Label("Price");
		TextField priceField = new TextField();
		Label oFeedback = new Label("");
		
		// order page buttons
		Button oSearch = new Button("Search");
		Button oAdd = new Button("Add");
		Button oUpdate = new Button("Update");
		Button oDel = new Button("Delete");
		Button oBack = new Button("Back");
		
		// order buttons event handlers
		oSearch.setOnAction(event ->
		{
			int orderNum = Integer.parseInt(orderNumField.getText());
			Order ord = new Order();
			if(ord.searchOrder(orderNum) == true)
			{
				ord.getSearchOrder(orderNum);
				orderNumField.setText(String.valueOf(ord.getNumber()));
				dateField.setText(ord.getDate());
				
				Customer temp = new Customer();
				temp = ord.getCustomer();
				temp = temp.getSearchCust(temp.getId());
				
				orderCustField.setText(String.valueOf(temp.getId()));
				itemField.setText(ord.getItem());
				priceField.setText(String.valueOf(ord.getPrice()));
			}
		});
		
		oAdd.setOnAction(event ->
		{
			int oNum = Integer.parseInt(orderNumField.getText());
			String oD = dateField.getText();
			int cID = Integer.parseInt(orderCustField.getText());
			String i = itemField.getText();
			double p = Double.parseDouble(priceField.getText());
			
			Order no = new Order(oNum, oD, i, p);
			Customer check = new Customer();
			check = check.getSearchCust(cID);
			try {
				no.createOrder(check);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		);
		
		
		oUpdate.setOnAction(event ->
		{
			int holdNum = Integer.parseInt(orderNumField.getText());
			Order check = new Order();
			if(check.searchOrder(holdNum) == true)
			{
				check = check.getSearchOrder(holdNum);
				
				if(dateField.getText() != null)
				{
					check.updateOrderDate(holdNum, dateField.getText());
				}
				
				if(itemField.getText() != null)
				{
					check.updateOrderItem(holdNum, itemField.getText());
				}
				
				if(priceField.getText() != null)
				{
					check.updateOrderPrice(holdNum, Double.parseDouble(priceField.getText()));
				}
				
				oFeedback.setText("Successfully updated");
			}
		});
		
		
		oDel.setOnAction(event ->
		{
			int holdNum = Integer.parseInt(orderNumField.getText());
			Order check = new Order();
			if(check.searchOrder(holdNum) == true)
			{
				check = check.getSearchOrder(holdNum);
				
				check.deleteOrder(holdNum);
			}
		});
		
		
		oBack.setOnAction(event ->
		{
			stage.setScene(homeMenu);
			stage.show();
		});
		
		HBox oButtons = new HBox(20, oSearch, oAdd, oUpdate, oDel, oBack);
		
		VBox oHold = new VBox(20, orderLabel, orderNumLabel, orderNumField, dateLabel,
				dateField, orderCustLabel, orderCustField, itemLabel, itemField,
				priceLabel, priceField, oButtons);
		
		orderPage = new Scene(oHold, 1000, 500);
		
	}
	
}
