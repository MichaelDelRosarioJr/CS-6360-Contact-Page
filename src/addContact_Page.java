/***************************************************************************************************************
 * CS 6360-501 addContact_Page
 * Author: Michael Del Rosario
 * Goal: 
 * 		This page allows user to add another contact.
 * 		User can fills first, middle, and last name and fill addresses, phone, and date information.
 * 		User can delete added data as well.
 * 		Software will not add contact until the first name text field is filled
 ***************************************************************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class addContact_Page implements Runnable
{
	//These variables store the next value of the id of each table
    int contactID_size;
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
    JButton addButton = new JButton("Add");
    JButton cancelButton = new JButton("Cancel");
    JButton addPhoneButton = new JButton("Add Phone");
    JButton deletePhoneButton = new JButton("Delete Phone");
    JButton addAddressButton = new JButton("Add Address");
    JButton deleteAddressButton = new JButton("Delete Address");
    JButton addDateButton = new JButton("Add Date");
    JButton deleteDateButton = new JButton("Delete Date");
    
    //Create command lists
    //These store sql commands as text for address, phone, and date lists
    //the corresponding array contains the ListModel text for deletion.
    String[] addressCommands = new String[9999];
    String[] address = new String[9999];
    String[] phoneCommands = new String[9999];
    String[] phone = new String[9999];
    String[] dateCommands = new String[9999];
    String[] date = new String[9999];
    int addressCounter = 0;
    int phoneCounter = 0;
    int dateCounter = 0;
    
    addContact_Page(int c_size, int a_size, int p_size, int d_size)
    {
    	//These variables store the next value of the id of each table
        contactID_size = c_size;
        phoneID_size = p_size;
        addressID_size = a_size;
        dateID_size = d_size;
        
        //Clear command lists
        for(int i = 0; i < addressCommands.length;i++)
        {
        	addressCommands[i] = "";
        	phoneCommands[i] = "";
        	dateCommands[i] = "";
        }
        
        System.out.println(contactID_size + " " + addressID_size + " " + phoneID_size + " " + dateID_size);
    }

	public void run() 
	{
		// Create the window
        final JFrame f = new JFrame("Add Contact");
        
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        // Add a layout manager so that the button is not placed on top of the label
        //f.setLayout(new FlowLayout());
        f.setLayout(new GridLayout(10,2));
        
       
        
        //Add components
        f.add(firstNameLabel);
        f.add(firstNameText);
        f.add(middleNameLabel);
        f.add(middleNameText);
        f.add(lastNameLabel);
        f.add(lastNameText);
        f.add(addressLabel);
        f.add(scrollAddressPane);
        f.add(addAddressButton);
        f.add(deleteAddressButton);
        f.add(phoneLabel);
        f.add(scrollPhonePane);
        f.add(addPhoneButton);
        f.add(deletePhoneButton);
        f.add(dateLabel);
        f.add(scrollDatePane);
        f.add(addDateButton);
        f.add(deleteDateButton);
        f.add(addButton);
        f.add(cancelButton);
        
        
        // Arrange the components inside the window
        f.pack();
        f.setSize(600, 600);
        
        // By default, the window is not visible. Make it visible.
        f.setVisible(true);
        
        //Add button functionality
        //Add for address, phone, and date fields
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
        
        //Delete for address, phone, and date buttons
        //This just removes the selected element from their respective list
        deleteAddressButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  if(addressList.getSelectedValue() != null)
        		  {
        		  
	        		  //Render the command of the selected value to contain nothing
	        		  //This will allow the commands to skip over the deleted item
	        		  String selectedItem = (String) addressList.getSelectedValue();
	        		  for(int i = 0; i < addressCommands.length;i++)
	        		  {
	        			  if(selectedItem.equals(address[i]))
	        			  {
	        				  addressCommands[i] = "";
	        				  addressListModel.removeElement(addressList.getSelectedValue());
	        				  
	        				  //TEST: See completion
	        				  //System.out.println("DELETE SUCCESSFUL");
	        			  }
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
        		  
	        		  //Render the command of the selected value to contain nothing
	        		  //This will allow the commands to skip over the deleted item
	        		  String selectedItem = (String) phoneList.getSelectedValue();
	        		  for(int i = 0; i < phoneCommands.length;i++)
	        		  {
	        			  if(selectedItem.equals(phone[i]))
	        			  {
	        				  phoneCommands[i] = "";
	        				  phoneListModel.removeElement(phoneList.getSelectedValue());
	        				  
	        				  //TEST: See completion
	        				  //System.out.println("DELETE SUCCESSFUL");
	        			  }
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
        		  
	        		  //Render the command of the selected value to contain nothing
	        		  //This will allow the commands to skip over the deleted item
	        		  String selectedItem = (String) dateList.getSelectedValue();
	        		  for(int i = 0; i < dateCommands.length;i++)
	        		  {
	        			  if(selectedItem.equals(date[i]))
	        			  {
	        				  dateCommands[i] = "";
	        				  dateListModel.removeElement(dateList.getSelectedValue());
	        				  
	        				  //TEST: See completion
	        				  //System.out.println("DELETE SUCCESSFUL");
	        			  }
	        		  }
        		  }
    		  } 
		});

        //Finally, the add and cancel functions for the contact
        //Add a new contact and close the window
        addButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		  //If first name is not filled in yet, do not add contact
        		  if(!firstNameText.getText().isEmpty())
        		  {
        			  f.dispose();
        		  }  
    		  } 
		});
        
        //Disregard adding a new contact and close the window
        cancelButton.addActionListener(new ActionListener() 
        { 
        	  public void actionPerformed(ActionEvent e) 
        	  { 
        		    //System.out.println("Cancel Clicked");
        		    f.dispose();
    		  } 
		});
        
        
	}
	
	//This function returns true if first name text box is filled.
	//Return false otherwise
	public boolean firstNameFilled()
    {
    	return !firstNameText.getText().isEmpty();
    }
	
	/*
	 * This function converts all command arrays into SQL String array
	 * and returns the string as commands.
	 */
	public String[] getSQLCommands()
	{
		String [] commands = new String[9999];
		
		//Empty commands array
		for(int i = 0; i < commands.length;i++)
		{
			commands[i] = "";
		}
		
		int counter = 0;
		
		commands[counter] = "INSERT INTO contact VALUES (" + "'" + contactID_size + "'," + "'" + firstNameText.getText() + "'," + 
		"'" + middleNameText.getText() + "'," + "'" + lastNameText.getText() + "'" + ");";
		
		counter++;
		
		//Add address inserts
		for(int i = 0;i < addressCommands.length;i++)
		{
			
			if(!addressCommands[i].isEmpty())
			{
				commands[counter] = addressCommands[i];
				counter++;
			}
			
		}
		//Add phone inserts
		for(int i = 0;i < phoneCommands.length;i++)
		{
			
			if(!phoneCommands[i].isEmpty())
			{
				commands[counter] = phoneCommands[i];
				counter++;
			}
			
		}
		//Add date inserts
		for(int i = 0;i < dateCommands.length;i++)
		{
			
			if(!dateCommands[i].isEmpty())
			{
				commands[counter] = dateCommands[i];
				counter++;
			}
			
		}
		
		//TEST: See commands
//		for(int i = 0; i < commands.length; i++)
//		{
//			if(commands[i].isEmpty())
//			{
//				//break;
//			}
//			else
//			{
//				System.out.println(commands[i]);
//			}
//		}
		
		return commands;
	}
	
	/*
	 * This function creates another window for adding an address.
	 */
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
        		    
        		    addressListModel.addElement(addressTypeText.getText() + " " + addressText.getText() + " " + cityText.getText() + 
        		    		" " + stateText.getText() + " " + zipText.getText());
        		    address[addressCounter] = addressTypeText.getText() + " " + addressText.getText() + " " + cityText.getText() + 
        		    		" " + stateText.getText() + " " + zipText.getText();
        		    //Add to to address command list
    				addressCommands[addressCounter] = "INSERT INTO address VALUES (" + "'" + addressID_size + "',"+ "'" + contactID_size + "',";
    				//Add data to command string if text box is filled
    				if(!addressTypeText.getText().isEmpty())
    					addressCommands[addressCounter] += "'" + addressTypeText.getText() + "',"; 
    				else
    					addressCommands[addressCounter] += "'',"; 
    				if(!addressText.getText().isEmpty())
    					addressCommands[addressCounter] += "'" + addressText.getText() + "',"; 
    				else
    					addressCommands[addressCounter] += "'',"; 
    				if(!cityText.getText().isEmpty())
    					addressCommands[addressCounter] += "'" + cityText.getText() + "',";
    				else
    					addressCommands[addressCounter] += "'',"; 
    				if(!stateText.getText().isEmpty())
    					addressCommands[addressCounter] += "'" + stateText.getText() + "',";
    				else
    					addressCommands[addressCounter] += "'',"; 
    				if(!zipText.getText().isEmpty())
    					addressCommands[addressCounter] += "'" + zipText.getText() + "'";
    				else
    					addressCommands[addressCounter] += "''"; 
    				addressCommands[addressCounter] += ");";
        			
    				//Increment counters
        		    addressCounter++;
        		    addressID_size++;
    				
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
	
	/*
	 * This function creates another window for adding an phone.
	 */
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
        		  
        		  	phoneListModel.addElement(phoneTypeText.getText() + " " + phoneAreaText.getText() + " " + phoneText.getText());
        		  	phone[phoneCounter] = phoneTypeText.getText() + " " + phoneAreaText.getText() + " " + phoneText.getText();
        		  	
        		  	//Add to to address command list
    				phoneCommands[phoneCounter] = "INSERT INTO phone VALUES (" + "'" + phoneID_size + "',"+ "'" + contactID_size + "',";
    				//Add data to command string if text box is filled
    				if(!phoneTypeText.getText().isEmpty())
    					phoneCommands[phoneCounter] += "'" + phoneTypeText.getText() + "',"; 
    				else
    					phoneCommands[phoneCounter] += "'',"; 
    				if(!phoneAreaText.getText().isEmpty())
    					phoneCommands[phoneCounter] += "'" + phoneAreaText.getText() + "',"; 
    				else
    					phoneCommands[phoneCounter] += "'',"; 
    				if(!phoneText.getText().isEmpty())
    					phoneCommands[phoneCounter] += "'" + phoneText.getText() + "'";
    				else
    					phoneCommands[phoneCounter] += "''"; 
    				phoneCommands[phoneCounter] += ");";
        			
    				//Increment counters
    				phoneCounter++;
        		    phoneID_size++;
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
	
	/*
	 * This function creates another window for adding an date.
	 */
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
        		    //System.out.println("Add Date Clicked");
        		    dateListModel.addElement(dateTypeText.getText() + " " + dateText.getText());
        		    date[dateCounter] = dateTypeText.getText() + " " + dateText.getText();
        		    
        		    //Add to to date command list
    				dateCommands[dateCounter] = "INSERT INTO cdate VALUES (" + "'" + dateID_size + "',"+ "'" + contactID_size + "',";
    				
    				//Add data to command string if text box is filled
    				if(!dateTypeText.getText().isEmpty())
    					dateCommands[dateCounter] += "'" + dateTypeText.getText() + "',"; 
    				else
    					dateCommands[dateCounter] += "'',"; 
    				if(!dateText.getText().isEmpty())
    					dateCommands[dateCounter] += "'" + dateText.getText() + "'"; 
    				else
    					dateCommands[dateCounter] += "''"; 
    				dateCommands[dateCounter] += ");";
        			
    				//Increment counters
    				dateCounter++;
        		    dateID_size++;
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
