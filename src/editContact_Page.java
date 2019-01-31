/***************************************************************************************************************
 * CS 6360-501 addContact_Page
 * Author: Michael Del Rosario
 * Goal: 
 * 		This page allows user to edit a contact.
 * 		User can fills first, middle, and last name and fill addresses, phone, and date information.
 * 		User can delete and modify data as well.
 ***************************************************************************************************************/
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class editContact_Page implements Runnable
{
	//Create Sql variables
	Connection myConnection;
	String SQL;
    Statement stmt;
    ResultSet rs;
	
	//These variables store the next value of the id of each table
    int contactID;
    int phoneID_size;
    int addressID_size;
    int dateID_size;
	
	//Create labels
    JLabel firstNameLabel = new JLabel("First Name:");
    JLabel middleNameLabel = new JLabel("Middle Name:");
    JLabel lastNameLabel = new JLabel("Last Name:");
    JLabel addressLabel = new JLabel("Address:");
    JLabel addressTypeLabel = new JLabel("Address Type:");
    JLabel addressCityLabel = new JLabel("City:");
    JLabel addressStateLabel = new JLabel("State:");
    JLabel addressZipLabel = new JLabel("Zip Code:");
    JLabel phoneTypeLabel = new JLabel("Phone Type:");
    JLabel phoneAreaLabel = new JLabel("Area Code:");
    JLabel phoneLabel = new JLabel("Phone:");
    JLabel dateTypeLabel = new JLabel("Date Type:");
    JLabel dateLabel = new JLabel("Date:");
    JLabel blankLabel1 = new JLabel("");
    JLabel blankLabel2 = new JLabel("");
    JLabel blankLabel3 = new JLabel("");
    JLabel blankLabel4 = new JLabel("");
    JLabel blankLabel5 = new JLabel("");
    JLabel blankLabel6 = new JLabel("");
    JLabel blankLabel7 = new JLabel("");
    
    //Create Text Field
    JTextField firstNameText = new JTextField();
    JTextField middleNameText = new JTextField();
    JTextField lastNameText = new JTextField();
    JTextField addressTypeText = new JTextField();
    JTextField addressText = new JTextField();
    JTextField cityText = new JTextField();
    JTextField stateText = new JTextField();
    JTextField zipText = new JTextField();
    JTextField phoneTypeText = new JTextField();
    JTextField phoneAreaText = new JTextField();
    JTextField phoneText = new JTextField();
    JTextField dateTypeText = new JTextField();
    JTextField dateText = new JTextField();
   
    //Create Lists
    DefaultListModel phoneListModel = new DefaultListModel();
    JList phoneList = new JList(phoneListModel);
    JScrollPane scrollPhonePane = new JScrollPane(phoneList);
    DefaultListModel dateListModel = new DefaultListModel();
    JList dateList = new JList(dateListModel);
    JScrollPane scrollDatePane = new JScrollPane(dateList);
    DefaultListModel addressListModel = new DefaultListModel();
    JList addressList = new JList(addressListModel);
    JScrollPane scrollAddressPane = new JScrollPane(addressList);
    
    //Create Buttons
    JButton okButton = new JButton("Save");
    JButton addPhoneButton = new JButton("Add Phone");
    JButton editPhoneButton = new JButton("Edit Phone");
    JButton deletePhoneButton = new JButton("Delete Phone");
    JButton addAddressButton = new JButton("Add Address");
    JButton editAddressButton = new JButton("Edit Address");
    JButton deleteAddressButton = new JButton("Delete Address");
    JButton addDateButton = new JButton("Add Date");
    JButton editDateButton = new JButton("Edit Date");
    JButton deleteDateButton = new JButton("Delete Date");
    
    editContact_Page(int c_id, int a_size, int p_size, int d_size)
    {
    	//These variables store the next value of the id of each table except for the contactID which stores the contact id
        contactID = c_id;
        phoneID_size = p_size;
        addressID_size = a_size;
        dateID_size = d_size;
        
        //Connect to database
        try
        {
        	Class.forName("com.mysql.jdbc.Driver");
        	myConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/contactlist","root","G@ming1over");
        	
        	stmt = myConnection.createStatement();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    	
    }

	public void run() 
	{   
        // Create the window
        final JFrame f = new JFrame("Add Contact");
        
        //Get contact information
        getInfo();
        
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        // Add a layout manager so that the button is not placed on top of the label
        //f.setLayout(new FlowLayout());
        f.setLayout(new GridLayout(10,3));  
        
        //Add components
        f.add(firstNameLabel);
        f.add(firstNameText);
        f.add(blankLabel1);
        f.add(middleNameLabel);
        f.add(middleNameText);
        f.add(blankLabel2);
        f.add(lastNameLabel);
        f.add(lastNameText);
        f.add(blankLabel3);
        f.add(addressLabel);
        f.add(scrollAddressPane);
        f.add(blankLabel4);
        f.add(addAddressButton);
        f.add(editAddressButton);
        f.add(deleteAddressButton);
        f.add(phoneLabel);
        f.add(scrollPhonePane);
        f.add(blankLabel5);
        f.add(addPhoneButton);
        f.add(editPhoneButton);
        f.add(deletePhoneButton);
        f.add(dateLabel);
        f.add(scrollDatePane);
        f.add(blankLabel6);
        f.add(addDateButton);
        f.add(editDateButton);
        f.add(deleteDateButton);
        f.add(okButton);
        f.add(blankLabel7);
        
        //Add Button functionality
        okButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    f.dispose();
    		  } 
		});
        
        //Add info buttons
        addAddressButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    addAddress();
    		  } 
		});
        
        addPhoneButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    addPhone();
    		  } 
		});
        
        addDateButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    addDate();
    		  } 
		});
        
        //Edit info buttons
        editAddressButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  if(addressList.getSelectedValue() != null)
        			  editAddress();
    		  } 
		});
        
        editPhoneButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  if(phoneList.getSelectedValue() != null)
        			  editPhone();
    		  } 
		});
        
        editDateButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  if(dateList.getSelectedValue() != null)
        			  editDate();
    		  } 
		});
        
        //Delete for address, phone, and date buttons
        deleteAddressButton.addActionListener(new ActionListener() 
        { 
        	
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  if(addressList.getSelectedValue() != null)
	        		  {
	        		  String selectedItem = (String) addressList.getSelectedValue();
	                  String[] idString = selectedItem.split(" ");
	                  int idNum = Integer.parseInt(idString[0]);
	                  
	                  try
	                  {
	                	//Delete all element from table
	              	  	SQL = "DELETE FROM address WHERE address_id = " + idNum;
		            	stmt.executeUpdate(SQL);
		            	
		            	//Reset GUI lists
		            	getInfo();
	                  }
	                  catch(Exception deleteAEx)
	                  {
	                	  deleteAEx.printStackTrace();
	                  }
        		  }
    		  } 
		});
        
        deletePhoneButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  if(phoneList.getSelectedValue() != null)
        		  {
	        		  String selectedItem = (String) phoneList.getSelectedValue();
	                  String[] idString = selectedItem.split(" ");
	                  int idNum = Integer.parseInt(idString[0]);
	                  
	                  try
	                  {
	                	//Delete all element from table
	              	  	SQL = "DELETE FROM phone WHERE phone_id = " + idNum;
		            	stmt.executeUpdate(SQL);
		            	
		            	//Reset GUI lists
		            	getInfo();
	                  }
	                  catch(Exception deleteAEx)
	                  {
	                	  deleteAEx.printStackTrace();
	                  }
        		  }
    		  } 
		});
        
        deleteDateButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  {
        		  if(dateList.getSelectedValue() != null)
        		  {
	        		  String selectedItem = (String) dateList.getSelectedValue();
	                  String[] idString = selectedItem.split(" ");
	                  int idNum = Integer.parseInt(idString[0]);
	                  
	                  try
	                  {
	                	//Delete all element from table
	              	  	SQL = "DELETE FROM cdate WHERE date_id = " + idNum;
		            	stmt.executeUpdate(SQL);
		            	
		            	//Reset GUI lists
		            	getInfo();
	                  }
	                  catch(Exception deleteAEx)
	                  {
	                	  deleteAEx.printStackTrace();
	                  }
        		  }
    		  } 
        });
        
        // Arrange the components inside the window
        f.pack();
        f.setSize(600, 600);
        
        // By default, the window is not visible. Make it visible.
        f.setVisible(true);
	}
	
	/*
	 * This function resets all lists after each modify, add, and deletion.
	 */
	private void getInfo()
	{
		addressListModel.clear();
		phoneListModel.clear();
		dateListModel.clear();
		
		try
		{
			//Get all addresses
            SQL = "SELECT * FROM contact WHERE contact_id = " + contactID; 
            rs = stmt.executeQuery(SQL);
            
            //Store each address within the List
            while (rs.next()) {
            	firstNameText.setText(rs.getString("Fname"));
            	middleNameText.setText(rs.getString("Mname"));
            	lastNameText.setText(rs.getString("Lname"));
            }
			
			//Get all addresses
            SQL = "SELECT * FROM address WHERE c_id = " + contactID; 
            rs = stmt.executeQuery(SQL);
            
            //Store each address within the List
            while (rs.next()) {
            	addressListModel.addElement(rs.getString("address_id") + " :" + rs.getString("address_type") + " " + rs.getString("address_info") 
            			+ " " + rs.getString("city") + " " + rs.getString("state_location")+ " " + rs.getString("zip"));
            }
            
            //Get all addresses
            SQL = "SELECT * FROM phone WHERE c_id = " + contactID;
            rs = stmt.executeQuery(SQL);
            
            //Store each address within the List
            while (rs.next()) {
            	phoneListModel.addElement(rs.getString("phone_id") + " :" + rs.getString("phone_type") + " " + rs.getString("area_code") 
            			+ " " + rs.getString("phone_number"));
            }
            
            //Get all addresses
            SQL = "SELECT * FROM cdate WHERE c_id = " + contactID;
            rs = stmt.executeQuery(SQL);
            
            //Store each address within the List
            while (rs.next()) {
            	dateListModel.addElement(rs.getString("date_id") + " :" + rs.getString("date_type") + " " + rs.getString("con_date"));
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Add functionality
	public void addAddress()
	{
		// Create the window
        final JFrame f = new JFrame("Add Address");
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setLayout(new GridLayout(6,2));
        
        f.add(addressTypeLabel);
        f.add(addressTypeText);
        f.add(addressLabel);
        f.add(addressText);
        f.add(addressCityLabel);
        f.add(cityText);
        f.add(addressStateLabel);
        f.add(stateText);
        f.add(addressZipLabel);
        f.add(zipText);
        f.add(addButton);
        f.add(cancelButton);
        
        f.pack();
        f.setVisible(true);
        f.setSize(400, 150);
        
        //Add button functionality
        addButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Add Address Clicked");
        		    try
        		    {
        		    	//Get all addresses
        	            SQL = "INSERT INTO address VALUES (" + "'" + addressID_size + "',"+ "'" + contactID + "',";
        	          //Add data to command string if text box is filled
        				if(!addressTypeText.getText().isEmpty())
        					SQL += "'" + addressTypeText.getText() + "',"; 
        				else
        					SQL += "'',"; 
        				if(!addressText.getText().isEmpty())
        					SQL += "'" + addressText.getText() + "',"; 
        				else
        					SQL += "'',"; 
        				if(!cityText.getText().isEmpty())
        					SQL += "'" + cityText.getText() + "',";
        				else
        					SQL += "'',"; 
        				if(!stateText.getText().isEmpty())
        					SQL += "'" + stateText.getText() + "',";
        				else
        					SQL += "'',"; 
        				if(!zipText.getText().isEmpty())
        					SQL += "'" + zipText.getText() + "'";
        				else
        					SQL += "''"; 
        				SQL += ");";
        	            
        				stmt.execute(SQL);
        				
        	            //Increment counters
            		    addressID_size++;

        		    }
        		    catch(Exception addEx)
        		    {
        		    	addEx.printStackTrace();
        		    }
        		    
        		    getInfo();
        		    f.dispose();
    		  } 
		});
        
        cancelButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Cancel Address Clicked");
        		    f.dispose();
    		  } 
		});
	}
	
	public void addPhone()
	{
		// Create the window
        final JFrame f = new JFrame("Add Phone");
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setLayout(new GridLayout(4,2));
        
        f.add(phoneTypeLabel);
        f.add(phoneTypeText);
        f.add(phoneAreaLabel);
        f.add(phoneAreaText);
        f.add(phoneLabel);
        f.add(phoneText);
        f.add(addButton);
        f.add(cancelButton);
        
        f.pack();
        f.setVisible(true);
        f.setSize(400, 125);
        
        //Add button functionality
        addButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  //System.out.println("Add Phone Clicked");
        		  
        		  try
        		  {
        			//Add to to address command list
      				SQL = "INSERT INTO phone VALUES (" + "'" + phoneID_size + "',"+ "'" + contactID + "',";
      				//Add data to command string if text box is filled
      				if(!phoneTypeText.getText().isEmpty())
      					SQL += "'" + phoneTypeText.getText() + "',"; 
      				else
      					SQL += "'',"; 
      				if(!phoneAreaText.getText().isEmpty())
      					SQL += "'" + phoneAreaText.getText() + "',"; 
      				else
      					SQL += "'',"; 
      				if(!phoneText.getText().isEmpty())
      					SQL += "'" + phoneText.getText() + "'";
      				else
      					SQL += "''"; 
      				SQL += ");";
          			
      				stmt.executeUpdate(SQL);
      				
      				//Increment counters
          		    phoneID_size++;
        		  }
        		  catch(Exception addEx)
        		  {
        			  addEx.printStackTrace();
        		  }
        		  
        		  	getInfo();
      		    	f.dispose();
    		  } 
		});
        
        cancelButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Cancel Phone Clicked");
        		    f.dispose();
    		  } 
		});
	}
	
	public void addDate()
	{
		// Create the window
        final JFrame f = new JFrame("Add Date");
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setLayout(new GridLayout(3,2));
        
        
        f.add(dateTypeLabel);
        f.add(dateTypeText);
        f.add(dateLabel);
        f.add(dateText);
        f.add(addButton);
        f.add(cancelButton);
        
        f.pack();
        f.setVisible(true);
        f.setSize(400, 125);
        
        //Add button functionality
        addButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  
					  try
					  {
						//Add to to date command list
	    				SQL = "INSERT INTO cdate VALUES (" + "'" + dateID_size + "',"+ "'" + contactID + "',";
	    				
	    				//Add data to command string if text box is filled
	    				if(!dateTypeText.getText().isEmpty())
	    					SQL += "'" + dateTypeText.getText() + "',"; 
	    				else
	    					SQL += "'',"; 
	    				if(!dateText.getText().isEmpty())
	    					SQL += "'" + dateText.getText() + "'"; 
	    				else
	    					SQL += "''"; 
	    				SQL += ");";
	  
	        		    stmt.executeUpdate(SQL);
	        		    
	        		    //Increment counters
	        		    dateID_size++;
					  }
					  catch(Exception addEx)
					  {
						  addEx.printStackTrace();
					  }
        		   
        		    
					getInfo();
        		    f.dispose();
    		  } 
		});
        
        cancelButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Cancel Date Clicked");
        		    f.dispose();
    		  } 
		});
	}
	
	//For edit functionality
	public void editAddress()
	{
		// Create the window
        final JFrame f = new JFrame("Edit Address");
        JButton addButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setLayout(new GridLayout(6,2));
        
        f.add(addressTypeLabel);
        f.add(addressTypeText);
        f.add(addressLabel);
        f.add(addressText);
        f.add(addressCityLabel);
        f.add(cityText);
        f.add(addressStateLabel);
        f.add(stateText);
        f.add(addressZipLabel);
        f.add(zipText);
        f.add(addButton);
        f.add(cancelButton);
        
        f.pack();
        f.setVisible(true);
        f.setSize(400, 150);
        
        //Get address_id
        String selectedItem = (String) addressList.getSelectedValue();
        String[] idString = selectedItem.split(" ");
        final int idNum = Integer.parseInt(idString[0]);
        
        //Populate the text fields
        try
        {
        	
        	
        	//Get address info based on the address_id Primary key
    	  	SQL = "SELECT * FROM address WHERE address_id = " + idNum;
    	  	rs = stmt.executeQuery(SQL);
    	  	
    	  	
    	  //Store each data within the List
            while (rs.next()) {
            	addressTypeText.setText(rs.getString("address_type"));
            	addressText.setText(rs.getString("address_info"));
            	cityText.setText(rs.getString("city"));
            	stateText.setText(rs.getString("state_location"));
            	zipText.setText(rs.getString("zip"));
            }
        }
        catch(Exception deleteAEx)
        {
      	  deleteAEx.printStackTrace();
        }
        
        //Add button functionality
        addButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Add Address Clicked");
        		    try
        		    {
        		    	//Get all addresses
        	            SQL = "UPDATE address SET "
    	            		+ "address_type = " + "'" + addressTypeText.getText() + "', "
        			  		+ "address_info = " + "'" + addressText.getText() + "', " 
        			  		+ "city = " + "'" + cityText.getText() + "', " 
        			  		+ "state_location = " + "'" + stateText.getText() + "', " 
        			  		+ "zip = " + "'" + zipText.getText() + "' " 
        			  		+ " WHERE address_id = " + idNum + ";";
        	            
        	            
        				stmt.executeUpdate(SQL);
        		    }
        		    catch(Exception addEx)
        		    {
        		    	addEx.printStackTrace();
        		    }
        		    
        		    getInfo();
        		    f.dispose();
    		  } 
		});
        
        cancelButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Cancel Address Clicked");
        		    f.dispose();
    		  } 
		});
	}
	
	public void editPhone()
	{
		// Create the window
        final JFrame f = new JFrame("Edit Phone");
        JButton addButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setLayout(new GridLayout(4,2));
        
        f.add(phoneTypeLabel);
        f.add(phoneTypeText);
        f.add(phoneAreaLabel);
        f.add(phoneAreaText);
        f.add(phoneLabel);
        f.add(phoneText);
        f.add(addButton);
        f.add(cancelButton);
        
        f.pack();
        f.setVisible(true);
        f.setSize(400, 125);
        
        //Get phone_id
        String selectedItem = (String) phoneList.getSelectedValue();
        String[] idString = selectedItem.split(" ");
        final int idNum = Integer.parseInt(idString[0]);
        
        //Populate the text fields
        try
        {
        	
        	
        	//Get phone info based on the selected primary phone_id key
    	  	SQL = "SELECT * FROM phone WHERE phone_id = " + idNum;
    	  	rs = stmt.executeQuery(SQL);
    	  	
    	  	
    	  //Store each data within the List
            while (rs.next()) {
            	phoneTypeText.setText(rs.getString("phone_type"));
            	phoneAreaText.setText(rs.getString("area_code"));
            	phoneText.setText(rs.getString("phone_number"));
            }
        }
        catch(Exception deleteAEx)
        {
      	  deleteAEx.printStackTrace();
        }
        
        //Add button functionality
        addButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Add Address Clicked");
        		    try
        		    {
        		    	//Get all addresses
        	            SQL = "UPDATE phone SET "
    	            		+ "phone_type = " + "'" + phoneTypeText.getText() + "', "
        			  		+ "area_code = " + "'" + phoneAreaText.getText() + "', " 
        			  		+ "phone_number = " + "'" + phoneText.getText() + "' " 
        			  		+ " WHERE phone_id = " + idNum + ";";
        	            
        	            
        				stmt.executeUpdate(SQL);
        		    }
        		    catch(Exception addEx)
        		    {
        		    	addEx.printStackTrace();
        		    }
        		    
        		    getInfo();
        		    f.dispose();
    		  } 
		});
        
        cancelButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Cancel Phone Clicked");
        		    f.dispose();
    		  } 
		});
	}
	
	public void editDate()
	{
		// Create the window
        final JFrame f = new JFrame("Edit Date");
        JButton addButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setLayout(new GridLayout(3,2));
        
        
        f.add(dateTypeLabel);
        f.add(dateTypeText);
        f.add(dateLabel);
        f.add(dateText);
        f.add(addButton);
        f.add(cancelButton);
        
        f.pack();
        f.setVisible(true);
        f.setSize(400, 125);
        
        //Get date_id
        String selectedItem = (String) dateList.getSelectedValue();
        String[] idString = selectedItem.split(" ");
        final int idNum = Integer.parseInt(idString[0]);
        
        //Populate the text fields
        try
        {
        	
        	
        	//Get phone info based on the selected primary phone_id key
    	  	SQL = "SELECT * FROM cdate WHERE date_id = " + idNum;
    	  	rs = stmt.executeQuery(SQL);
    	  	
    	  	
    	  	//Store each data within the List
            while (rs.next()) {
            	dateTypeText.setText(rs.getString("date_type"));
            	dateText.setText(rs.getString("con_date"));
            }
        }
        catch(Exception deleteAEx)
        {
      	  deleteAEx.printStackTrace();
        }
        
        //Add button functionality
        addButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Add Address Clicked");
        		    try
        		    {
        		    	//Get all addresses
        	            SQL = "UPDATE cdate SET "
    	            		+ "date_type = " + "'" + dateTypeText.getText() + "', "
        			  		+ "con_date = " + "'" + dateText.getText() + "' " 
        			  		+ " WHERE date_id = " + idNum + ";";
        	            
        	            
        				stmt.executeUpdate(SQL);
        		    }
        		    catch(Exception addEx)
        		    {
        		    	addEx.printStackTrace();
        		    }
        		    
        		    getInfo();
        		    f.dispose();
    		  } 
		});
        
        cancelButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Cancel Date Clicked");
        		    f.dispose();
    		  } 
		});
	}

}
