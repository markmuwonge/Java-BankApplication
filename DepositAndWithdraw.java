import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DepositAndWithdraw {
	
	JFrame f;
	JLabel accNoLbl, nameLbl, addressLbl, accountTypeLbl, genderLbl, currentBalanceLbl, operationLbl;
	JTextField accNoTxtF, nameTxtF, addressTxtF, accountTypeTxtF, genderTxtF, currentBalanceTxtF, operationTxtF;
	JPanel panel, panel2;
	JButton searchAccNoBtn, homeBtn, saveBtn;
	
	boolean deposit = false;
	boolean withdraw = false;
	String operation = "";
	
	AWindowListener awl = new AWindowListener();
	String accNo = "";
	String accName = "";
	String address = "";
	String accountType = "";
	String gender = "";
	String currentBalance = "";
	
	boolean accountFound = false;
	
	Connection con;
	Statement st;
	ResultSet rs;
	
	public DepositAndWithdraw(String o)
	{
		operation = o;
		
		if (operation.equals("Deposit"))
		{
			deposit = true;
		}
		if (operation.equals("Withdraw"))
		{
			withdraw = true;
		}
		
        f = new JFrame(operation + " Money");
        f.setSize(400, 400);
        f.setLocationRelativeTo(null);
        f.addWindowListener(awl);
        
        panel = new JPanel();
	    panel.setLayout(new GridLayout(8,3));
	    
	    accNoLbl = new JLabel("AccNo:");
	    nameLbl = new JLabel("Name:");
	    addressLbl = new JLabel("Address:");
	    accountTypeLbl = new JLabel("Account Type:");
	    genderLbl = new JLabel("Gender:");
	    currentBalanceLbl = new JLabel("Current Balance:");
	    operationLbl = new JLabel(operation + ":");
	    
	    accNoTxtF = new JTextField(20);
	    nameTxtF = new JTextField(20);
	    nameTxtF.setEditable(false);
	    addressTxtF = new JTextField(20);
	    addressTxtF.setEditable(false);
	
	    accountTypeTxtF = new JTextField(20);
	    accountTypeTxtF.setEditable(false);
	    accountTypeTxtF.setText(accountType);
	    genderTxtF = new JTextField(20);
	    genderTxtF.setEditable(false);
	    genderTxtF.setText(gender);
	    currentBalanceTxtF = new JTextField(20);
	    currentBalanceTxtF.setEditable(false);
	    currentBalanceTxtF.setText(currentBalance);
	    operationTxtF = new JTextField(20);
	    
	    searchAccNoBtn = new JButton("Search");
	    searchAccNoBtn.addActionListener(new ActionListener() {
	    
	    public void actionPerformed(ActionEvent ae)
	    {
	      try
	      {
	      	  Class.forName("com.mysql.cj.jdbc.Driver");
	          con = DriverManager.getConnection("jdbc:mysql://localhost/marks_test_db", "root", "");
	          st = con.createStatement();
	          
	          rs = st.executeQuery("select * from bank where AcNo="+ "'" + accNoTxtF.getText() + "'" + "");
	          rs.next(); 
	          accNo = accNoTxtF.getText();
	          nameTxtF.setText(rs.getString(2));
	          addressTxtF.setText(rs.getString(3));
	          if(rs.getString(1).substring(0, 1).equals("S"))
	          {
	        	  accountTypeTxtF.setText("Savings");
	          }
	          else
	          {
	        	  accountTypeTxtF.setText("Current");
	          }
	          if(rs.getString(1).substring(1, 2).equals("M"))
	          {
	        	  genderTxtF.setText("Male");
	          }
	          else
	          {
	        	  genderTxtF.setText("Female");
	          }
	          
	          accountFound = true;
	      }
	      catch (Exception e)
	      {
	    	  JOptionPane.showMessageDialog(null, "No Account Found");
	       		//System.out.println(e.toString());
	      }
	      
	      try
	      {
	      	  Class.forName("com.mysql.cj.jdbc.Driver");
	          con = DriverManager.getConnection("jdbc:mysql://localhost/marks_test_db", "root", "");
	          st = con.createStatement();         
	          rs = st.executeQuery("select ifnull((select sum(amount) from deposit where AcNo=" + "'" + accNo + "'" + "), 0) - (select ifnull((select sum(amount) from withdraw where AcNo=" + "'" + accNo + "'" + "), 0))");
	          
	          if (rs.next() && accountFound == true)
	          {
	        	  currentBalanceTxtF.setText(rs.getString(1));
	        	  
	        	  if (currentBalanceTxtF.getText().trim().equals("0"))
	        	  {
	        		  currentBalanceTxtF.setText("0.00");
	        	  }
	          }        
	      }
	      catch (Exception e)
	      {
	      		System.out.println(e.toString());
	      }
	      
	           
	    }
	});
	    
	    
	    panel.add(accNoLbl);
	    panel.add(accNoTxtF);
	    panel.add(searchAccNoBtn);
	    panel.add(nameLbl);
	    panel.add(nameTxtF);
	    panel.add(new JLabel()); //space
	    panel.add(addressLbl);
	    panel.add(addressTxtF);
	    panel.add(new JLabel()); //space
	    panel.add(accountTypeLbl);
	    panel.add(accountTypeTxtF);
	    panel.add(new JLabel()); //space
	    panel.add(genderLbl);
	    panel.add(genderTxtF);
	    panel.add(new JLabel()); //space
	    panel.add(currentBalanceLbl);
	    panel.add(currentBalanceTxtF);
	    panel.add(new JLabel()); //space
	    
	    panel.add(new JLabel()); //space
	    panel.add(new JLabel()); //space
	    panel.add(new JLabel()); //space
	    
	    panel.add(operationLbl);
	    panel.add(operationTxtF);
	    
	    panel2 = new JPanel();
	    panel2.setLayout(new GridLayout(1,2));
	    
	    homeBtn = new JButton("Home");
	    homeBtn.addActionListener(new ActionListener() {
	    
		    public void actionPerformed(ActionEvent ae)
		    {
		        Home h = new Home();
		        f.dispose();
		    }
		});
	    
	    saveBtn = new JButton("Save");
	    saveBtn.addActionListener(new ActionListener() {
	    
	    public void actionPerformed(ActionEvent ae)
	    {
	    	if (accountFound == true)
	    	{
	    		if (operationTxtF.getText().trim().equals(""))
	    		{
	    			JOptionPane.showMessageDialog(null, "No Deposit Amount Specified");
	    		}
	    		else
	    		{         
	                if (deposit == true)
	                {
	                    insertInto(operation, operationTxtF.getText());
	                }
	                if (withdraw == true)
	                {
	                	if (Integer.parseInt(currentBalanceTxtF.getText()) - Integer.parseInt(operationTxtF.getText()) > 0)
	                	{
	                		insertInto(operation, operationTxtF.getText());
	                	}
	                	else
	                	{
	                		JOptionPane.showMessageDialog(null, "Cannot withdraw more than current balance");
	                	}
	                	
	                }       
	    		}
	    	}
	    	else
	    	{
	    		JOptionPane.showMessageDialog(null, "No Account Found");
	    	}
	    }
	});
	    
	    panel2.add(homeBtn, BorderLayout.CENTER);
	    panel2.add(saveBtn, BorderLayout.CENTER);
        
	    f.add(panel, BorderLayout.NORTH);
	    f.add(panel2, BorderLayout.SOUTH);
        f.setVisible(true);
        
        
	}
	
	public void insertInto(String op, String amount)
	{
		try
		{
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost/marks_test_db", "root", "");
	        st = con.createStatement();
	        
			String s = "insert into "+op+ " values (" + "'" + accNo + "'" + "," + "'" + amount + "'" + ", now());";
	        st.executeUpdate(s);
	        JOptionPane.showMessageDialog(null, op + " of " + amount + " has been made for account number " + accNo);
	        
	        Home h = new Home();
	        f.dispose();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Invalid Deposit Amount Entered");
			System.out.println(e.toString());
		}
		


        
        
	}

}
