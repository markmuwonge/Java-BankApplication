import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckBalance {
	
	JFrame f;
	JLabel balanceLbl, balanceAmountLbl;
	JTextField accNoTxtF;
	JButton searchAcNo, homeBtn;
	JPanel panel, panel2, panel3;
	
	AWindowListener awl = new AWindowListener();
	
	Connection con;
	Statement st;
	ResultSet rs;
	
	boolean accountFound = false;

	
	public CheckBalance()
	{
				
        f = new JFrame("Check Balance");
        f.setSize(400, 400);
        f.setLocationRelativeTo(null);
        f.addWindowListener(awl);
        
        balanceLbl = new JLabel("Balance:");
        balanceAmountLbl = new JLabel("");  
        
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
        panel.add(balanceLbl);
        panel.add(balanceAmountLbl);
        
        accNoTxtF = new JTextField(20);
        searchAcNo = new JButton("Search Account Number");
        searchAcNo.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent ae)
        	{
        		try
        		{
        			Class.forName("com.mysql.cj.jdbc.Driver");
      	          	con = DriverManager.getConnection("jdbc:mysql://localhost/marks_test_db", "root", "");
      	          	st = con.createStatement();         
      	          	rs = st.executeQuery("select * from bank where AcNo="+ "'" + accNoTxtF.getText() + "'" + "");
      	          	
      	          	if (rs.next())
      	          	{
      	          		accountFound = true; 
      	          	}
      	          	else
      	          	{
      	          		accountFound = false; 
      	          	}
      	          	         	    		
      	        }
        		catch (Exception e)
        		{
        			System.out.print(e.getMessage());
        		}
        		
        		if (accountFound == true)
        		{
        			try
        			{
        		      	  Class.forName("com.mysql.cj.jdbc.Driver");
        		          con = DriverManager.getConnection("jdbc:mysql://localhost/marks_test_db", "root", "");
        		          st = con.createStatement();         
        		          rs = st.executeQuery("select ifnull((select sum(amount) from deposit where AcNo=" + "'" + accNoTxtF.getText() + "'" + "), 0) - (select ifnull((select sum(amount) from withdraw where AcNo=" + "'" + accNoTxtF.getText() + "'" + "), 0))");
        		          
        		          if (rs.next() && accountFound == true)
        		          {
        		        	  balanceAmountLbl.setText(rs.getString(1));
        		        	  
        		        	  if (balanceAmountLbl.getText().trim().equals("0"))
        		        	  {
        		        		  balanceAmountLbl.setText("0.00");
        		        	  }
        		          }  
        			}
        			catch(Exception e)
        			{
        				
        			}
        		}
        		else
        		{
        			JOptionPane.showMessageDialog(null, "No Account Found");
        			balanceAmountLbl.setText("");
        			accNoTxtF.setText("");
        		}
        		
        		
        	}
        });
        
        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        
        panel2.add(accNoTxtF);
        panel2.add(searchAcNo);
        
        homeBtn = new JButton("Home");
	    homeBtn.addActionListener(new ActionListener() {
		    
		    public void actionPerformed(ActionEvent ae)
		    {
		        Home h = new Home();
		        f.dispose();
		    }
		});
        
        f.add(panel, BorderLayout.NORTH);
        f.add(panel2, BorderLayout.CENTER);
        f.add(homeBtn, BorderLayout.SOUTH);
        
        f.setVisible(true);
	}

}
