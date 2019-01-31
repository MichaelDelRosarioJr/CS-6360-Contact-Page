/***************************************************************************************************************
 * CS 6360-501 Contact_Page
 * Author: Michael Del Rosario
 * Goal: 
 * 		This is the main page of the contact software.
 * 		This page will list all contacts and their full name in a list
 * 		and allows to search for names, addresses, and phone information
 * 		Clicking a contact will display all contact's information
 * 		This page allows adding new contacts and editing selected contacts
 ***************************************************************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Contact_Page implements Runnable
{
	final int BUTTONSIZE = 50;
	
	//Create Sql variables
	Connection myConnection;
	String SQL;
    Statement stmt;
    ResultSet rs;
     
    //Create list arrays
     String[] contactArray = new String[9999];
     int contactArraySize = 0;
     String[] phoneArray = new String[9999];
     int phoneArraySize = 0;
     String[] addressArray = new String[9999];
     int addressArraySize = 0;
     String[] dateArray = new String[9999];
     int dateArraySize = 0;
     
     //These variables store the next value of the id of each table
     int contactID_size;
     int phoneID_size;
     int addressID_size;
     int dateID_size;
     
     //Create Search bar and button
     final JTextField searchText = new JTextField("Enter Search Criterial...");
     JButton searchButton = new JButton("Search");
     
     JButton addContactButton = new JButton("Add Contact");
     JButton editContactButton = new JButton("Edit Contact");
     JButton deleteContactButton = new JButton("Delete Contact");
     JButton refreshContactButton = new JButton("Refresh List");
     
     //Create Contact List
     DefaultListModel contactListModel = new DefaultListModel();
     JList contactList = new JList(contactListModel);
     JScrollPane scrollPane = new JScrollPane(contactList);
     
     //Create Text Information
     JTextArea contactInfo = new JTextArea();
     JScrollPane scrollContactPane = new JScrollPane(contactInfo);
     
     
	public void run() {
        // Create the window
        JFrame f = new JFrame("Contact list");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //Create layout
        //f.setLayout(new FlowLayout());
        f.setLayout(new GridLayout(4,2));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        //Get contact information
        try{
        	//Connect to database
        	Class.forName("com.mysql.jdbc.Driver");
        	myConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/contactlist","root","G@ming1over");
        	
        	//TEST: See connection is successful
        	//System.out.println("SUCCESS");
        	
        	//Get all contacts
            SQL = "SELECT * FROM contact";
            stmt = myConnection.createStatement();
            rs = stmt.executeQuery(SQL);

            //Store each contact within the contactList
            while (rs.next()) {
            	contactListModel.addElement(rs.getString("contact_id") + " " + rs.getString("Fname") + " " + rs.getString("Mname") + " " + rs.getString("Lname"));
            	contactArraySize++;
            }
            
          //Get the next possible ID value of each table id
	      SQL = "SELECT max(contact_id) AS contact_id FROM contact";
	      rs = stmt.executeQuery(SQL);
	      
	      while (rs.next()) {
          	contactID_size = Integer.parseInt(rs.getString("contact_id")) + 1;
          }
	      
	      SQL = "SELECT max(phone_id) AS phone_id FROM phone";
	      rs = stmt.executeQuery(SQL);
	      
	      while (rs.next()) {
          	phoneID_size = Integer.parseInt(rs.getString("phone_id")) + 1;
          }
	      
	      SQL = "SELECT max(address_id) AS address_id FROM address";
	      rs = stmt.executeQuery(SQL);
	      
	      while (rs.next()) {
          	addressID_size = Integer.parseInt(rs.getString("address_id")) + 1;
          }
	      
	      SQL = "SELECT max(date_id) AS date_id FROM cdate";
	      rs = stmt.executeQuery(SQL);
	      
	      while (rs.next()) {
          	dateID_size = Integer.parseInt(rs.getString("date_id")) + 1;
          }
	      
	      //TEST: See the next value for each table's id
//	      System.out.println("next contact ID number = " + contactID_Size);
//	      System.out.println("next phone ID number = " + phoneID_Size);
//	      System.out.println("next address ID number = " + addressID_Size);
//	      System.out.println("next date ID number = " + dateID_Size);
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
        	System.out.println("FAILED");
            e.printStackTrace();
        }

        //Add components
        f.add(searchText);
        f.add(searchButton);    
        f.add(scrollPane);
        f.add(scrollContactPane);
        f.add(addContactButton);
        f.add(editContactButton);
        f.add(deleteContactButton);
        f.add(refreshContactButton);
        
        //contactInfo.set
        contactInfo.setLineWrap(true);
        searchText.setLayout(new GridLayout(2,2));
        addContactButton.setLayout(new GridLayout(3,1));
        editContactButton.setLayout(new GridLayout(3,2));
        deleteContactButton.setLayout(new GridLayout(3,3));
        
        // Arrange the components inside the window
        f.pack();
        f.setSize(600, 600);
        // By default, the window is not visible. Make it visible.
        f.setVisible(true);
        
        //Add Button functionality
        /*
         * Search button accepts a string and searches the contact, phone, and address tables
         * and fills the list with each result.
         * An empty string is treated as refreshing the list.
         * 
         */
        searchButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  	//Get search Text
        		  	String[] input = searchText.getText().split(" ");
        		  	
        		  	//Get contact information
        	        try{
        	        	//Clear List
        	        	contactListModel.clear();
        	            
        	        	//Name Search
        	        	if(!searchText.getText().isEmpty())
        	        	{
        	        		//This for loop will search the input by each array cell.
		        		  	for(int i = 0; i < input.length;i++)
		        		  	{
		        		  		//TEST: See input
		        		  		//System.out.println(input[i]);
		        		  		
		        		  		SQL = "SELECT * FROM contact WHERE Fname LIKE " + "'" + input[i] + "'" + "OR Mname LIKE " + "'" + input[i] + "'" + "OR Lname LIKE " + "'" + input[i] + "'";
		        	            rs = stmt.executeQuery(SQL);
		        		  		
		        		  		// Iterate through the data in the result set and display it.
		        	            while (rs.next()) {
		        	            	//System.out.println(rs.getString("contact_id") + " " + rs.getString("Fname") + " " + rs.getString("Mname") + " " + rs.getString("Lname"));
		        	            	if(notInList(rs.getString("contact_id")))
		        	            		contactListModel.addElement(rs.getString("contact_id") + " " + rs.getString("Fname") + " " + rs.getString("Mname") + " " + rs.getString("Lname"));
		        	            }
		        		  	
		        		  	}
		        		  	
		        		  	//Address search
		        		  	for(int i = 0; i < input.length;i++)
		        		  	{
		        		  		//TEST: See input
		        		  		//System.out.println(input[i]);
		        		  		
		        		  		SQL = "SELECT * FROM address WHERE address_type LIKE " + "'" + input[i] + "'" + "OR address_info LIKE " + "'" + 
		        		  		input[i] + "'" + "OR city LIKE " + "'" + input[i] + "'" + "OR state_location LIKE " + "'" + input[i] + "'" + "OR zip LIKE " + "'" + input[i] + "'";
		        	            rs = stmt.executeQuery(SQL);
		        	            ResultSet r2;
		        	            Statement stmt2 = myConnection.createStatement();
		        	            String SQL2;
		        		  		// Iterate through the data in the result set and display it.
		        	            while (rs.next()) 
		        	            {
		        	            	SQL2 = "SELECT * FROM contact WHERE contact_id = " + rs.getString("c_id");
		        	            	r2 = stmt2.executeQuery(SQL2);
		        	            	 while (r2.next())
		        	            	 {
		        	            		 if(notInList(r2.getString("contact_id")))
		        	            			 contactListModel.addElement(r2.getString("contact_id") + " " + r2.getString("Fname") + " " + r2.getString("Mname") + " " + r2.getString("Lname"));
		        	            	 }
		        	            }
		        		  	
		        		  	}
		        		  	
		        		  	//Phone search
		        		  	for(int i = 0; i < input.length;i++)
		        		  	{
		        		  		//TEST: See input
		        		  		//System.out.println(input[i]);
		        		  		
		        		  		SQL = "SELECT * FROM phone WHERE phone_type LIKE " + "'" + input[i] + "'" + "OR area_code LIKE " + "'" + input[i] + "'" +
		        		  				"OR phone_number LIKE " + "'" + input[i] + "'";
		        	            rs = stmt.executeQuery(SQL);
		        	            ResultSet r2;
		        	            Statement stmt2 = myConnection.createStatement();
		        	            String SQL2;
		        		  		// Iterate through the data in the result set and display it.
		        	            while (rs.next()) 
		        	            {
		        	            	SQL2 = "SELECT * FROM contact WHERE contact_id = " + rs.getString("c_id");
		        	            	r2 = stmt2.executeQuery(SQL2);
		        	            	 while (r2.next())
		        	            	 {
		        	            		 if(notInList(r2.getString("contact_id")))
		        	            			 contactListModel.addElement(r2.getString("contact_id") + " " + r2.getString("Fname") + " " + r2.getString("Mname") + " " + r2.getString("Lname"));
		        	            	 }
		        	            }
		        		  	}

        	        	}
        	        	else//If the search text is empty, than just refresh the list
        	        	{
        	        		setList();
        	        	}
            		  	
            		  	
        	        }
        	        // Handle any errors that may have occurred.
        	        catch (Exception ex) {
        	        	System.out.println("FAILED");
        	            ex.printStackTrace();
        	        }
        		  	
        		  	
        		  
        		    //System.out.println("Search Clicked");
    		  } 
		});
        
        /*
         * Add contact button creates a window for creating another contact and
         * passes the next possible id entries for all tables.
         * Once the add contact page clicks "add", this class will add all data
         * to the appropriate table and refresh list
         */
        addContactButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Add Contact Clicked");
        		  	final addContact_Page add = new addContact_Page(contactID_size, addressID_size, phoneID_size, dateID_size);
        		    add.run();
        		    //System.out.println("HEY"); 

        		    add.addButton.addActionListener(new ActionListener() 
        	        { 
        	        	  public void actionPerformed(ActionEvent e) 
        	        	  {
        	        		  if(add.firstNameFilled())
        	        		  {
        	        			  //Get data from the addContact_Page class and store it as insert commands
        	        			  String[] commands = add.getSQLCommands();
        	        			  
        	        			  //Execute sql commands
        	        			  for(int i = 0; i < commands.length; i++)
        	        			  {
        	        				  //If the next command is empty, skip to the next one
        	        				  if(commands[i].isEmpty())
        	        				  {
        	        				  }
        	        				  else//Execute inert SQL query
        	        				  {
        	        					  try
        	        					  {
    	        				            SQL = commands[i];
    	        				            stmt.executeUpdate(commands[i]);
        	        					  }
        	        					  catch(Exception eAdd)
        	        					  {
        	        						  eAdd.printStackTrace();
        	        					  }
        	        				  }
        	        			  }
        	        			  
        	        			  //Refresh the list
        	        			  setList();
        	        		  }
        	        		    
        	    		  } 
        			});
    		  } 
		});
        
        /*
         * Edit contact button opens another window and passes the selected contact id
         * and the next possible id entries for address, phone, edit tables.
         */
        editContactButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  if(contactList.getSelectedValue() != null)
        		  {
	        		  //System.out.println("Edit Contact Clicked");
	        		  String selectedItem = (String) contactList.getSelectedValue();
	                  //System.out.println(selectedItem);
	                  
	                  String[] idString = selectedItem.split(" ");
	                  int idNum = Integer.parseInt(idString[0]);
	                  
	        		  final editContact_Page edit = new editContact_Page(idNum, addressID_size, phoneID_size, dateID_size);
	        		  edit.run();
	        		  
	        		  edit.okButton.addActionListener(new ActionListener() 
	        	        { 
	        	        	  public void actionPerformed(ActionEvent e) 
	        	        	  {
	    	        			  //Refresh the list
	        	        		  try
	        	        		  {
	        	        			  SQL = "UPDATE contact SET "
	        	        			  		+ "Fname = " + "'" + edit.firstNameText.getText() + "', "
	        	        			  		+ "Mname = " + "'" + edit.middleNameText.getText() + "', " 
	        	        					+ "Lname = " + "'" + edit.lastNameText.getText() + "' "
	        	        			  		+ " WHERE contact_id = " + edit.contactID + ";";
	        	        			  
	        	        			  stmt.executeUpdate(SQL);
	        	        		  }
	        	        		  catch(Exception editEx)
	        	        		  {
	        	        			  editEx.printStackTrace();
	        	        		  }
	    	        		  
	    	        			  setList();	    
	        	    		  } 
	        			});
        		  }
    		  } 
		});
        
        //Delete contact buttons
        //Deletes the contact and all related data from other tables by the selected contact_id
        deleteContactButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  if(contactList.getSelectedValue() != null)
        		  {
	        		  //System.out.println("Delete Contact Clicked");
	        		  String selectedItem = (String) contactList.getSelectedValue();
	                  //System.out.println(selectedItem);
	                  
	                  String[] idString = selectedItem.split(" ");
	                  int idNum = Integer.parseInt(idString[0]);
	                  
	                  try
	                  {
                	  		//Delete all other tables and finally delete contact table
	                	  	SQL = "DELETE FROM address WHERE c_id = " + idNum;
	      	            	stmt.executeUpdate(SQL);
	      	            	SQL = "DELETE FROM phone WHERE c_id = " + idNum;
	      	            	stmt.executeUpdate(SQL);
	      	            	SQL = "DELETE FROM cdate WHERE c_id = " + idNum;
	      	            	stmt.executeUpdate(SQL);
	      	            	SQL = "DELETE FROM contact WHERE contact_id = " + idNum;
	      	            	stmt.executeUpdate(SQL);
	      	            	
	      	            	//Reset contact GUI list
	      	            	setList();
	                  }
	                  catch(Exception eDelete)
	                  {
	                	  eDelete.printStackTrace();
	                  }
        		  }
    		  } 
		});
        
        contactList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {


                   String selectedItem = (String) contactList.getSelectedValue();
                   String results = "";
                   String[] idString = selectedItem.split(" ");
                   int idNum = Integer.parseInt(idString[0]);
                   
                 //Get contact information
       	        try{
       	        	
       	        	
       	        	//Get phone info
   	        		results += "Phone numbers:\n";
    		  		SQL = "SELECT * FROM phone WHERE c_id = " + idNum;
    	            rs = stmt.executeQuery(SQL);
    	            
    	            while (rs.next()) {
	                	//System.out.println((rs.getString("phone_id") + " " + rs.getString("phone_type") + " " + rs.getString("area_code") + " " + rs.getString("phone_number")));
	                	results +=  rs.getString("phone_type") + " " + rs.getString("area_code") + " " + rs.getString("phone_number") + "\n";
	                }
    	            
    	            //Get address info
   	        		results += "Address:\n";
    		  		SQL = "SELECT * FROM address WHERE c_id = " + idNum;
    	            rs = stmt.executeQuery(SQL);
    	            
    	            while (rs.next()) {
	                	//System.out.println((rs.getString("phone_id") + " " + rs.getString("phone_type") + " " + rs.getString("area_code") + " " + rs.getString("phone_number")));
	                	results +=  rs.getString("address_type") + " " + rs.getString("address_info") + " " + rs.getString("city") + rs.getString("state_location") + rs.getString("zip") + "\n";
	                	
	                }
    	            
    	            //Get date info
   	        		results += "Dates:\n";
    		  		SQL = "SELECT * FROM cdate WHERE c_id = " + idNum;
    	            rs = stmt.executeQuery(SQL);
    	            
    	            while (rs.next()) {
	                	//System.out.println((rs.getString("phone_id") + " " + rs.getString("phone_type") + " " + rs.getString("area_code") + " " + rs.getString("phone_number")));
	                	results += rs.getString("date_type") + " " + rs.getString("con_date") + "\n";
	                }
    	            //Set result text into the contact info text area
    	            contactInfo.setText(results);
   	        	}
       	        // Handle any errors that may have occurred.
       	        catch (Exception ex) {
       	        	System.out.println("FAILED");
       	            ex.printStackTrace();
       	        }
            }
        }
        });
        
        //Refresh buttons resets the list to show all contacts
        refreshContactButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    setList();
    		  } 
		});
        
    }
	
	//This function resets all search criteria and shows all contacts on the list
	//This function also sets the next possible id entries for all tables
	private void setList()
    {
		try{
        	
			contactListModel.clear();
			
        	//Get all contacts
            SQL = "SELECT * FROM contact";
            rs = stmt.executeQuery(SQL);

            //Store each contact within the contactList
            while (rs.next()) {
            	contactListModel.addElement(rs.getString("contact_id") + " " + rs.getString("Fname") + " " + rs.getString("Mname") + " " + rs.getString("Lname"));
            	contactArraySize++;
            }
            
          //Get the last ID value of each table id
	      SQL = "SELECT max(contact_id) AS contact_id FROM contact";
	      rs = stmt.executeQuery(SQL);
	      
	      while (rs.next()) {
          	contactID_size = Integer.parseInt(rs.getString("contact_id")) + 1;
          }
	      
	      SQL = "SELECT max(phone_id) AS phone_id FROM phone";
	      rs = stmt.executeQuery(SQL);
	      
	      while (rs.next()) {
          	phoneID_size = Integer.parseInt(rs.getString("phone_id")) + 1;
          }
	      
	      SQL = "SELECT max(address_id) AS address_id FROM address";
	      rs = stmt.executeQuery(SQL);
	      
	      while (rs.next()) {
          	addressID_size = Integer.parseInt(rs.getString("address_id")) + 1;
          }
	      
	      SQL = "SELECT max(date_id) AS date_id FROM cdate";
	      rs = stmt.executeQuery(SQL);
	      
	      while (rs.next()) {
          	dateID_size = Integer.parseInt(rs.getString("date_id")) + 1;
          }
	      
	      
	    //TEST: See the next value for each table's id
//	      System.out.println("next contact ID number = " + contactID_Size);
//	      System.out.println("next phone ID number = " + phoneID_Size);
//	      System.out.println("next address ID number = " + addressID_Size);
//	      System.out.println("next date ID number = " + dateID_Size);
		}
      catch (Exception e) {
        	System.out.println("FAILED");
            e.printStackTrace();
        }
		
		
    }
	
	/*
	 * This function searches the contact list for the arguement string and returns
	 * true if the string is not in the list.
	 * Returns true otherwise
	 */
	private boolean notInList(String str)
	{
		for(int i = 0; i < contactListModel.getSize();i++)
		{
			if(contactListModel.elementAt(i).toString().contains(str))
				return false;
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		
        
		Contact_Page se = new Contact_Page();
        // Schedules the application to be run at the correct time in the event queue.
        SwingUtilities.invokeLater(se);

	}

}