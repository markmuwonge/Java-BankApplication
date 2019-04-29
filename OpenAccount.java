
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mark
 */
public class OpenAccount{
    
    JFrame f;
    JLabel nameLbl, addressLbl;
    JTextField nameTxtF, addressTxtF;
    JPanel topPanel, middlePanel, insideMiddlePanel1, insideMiddlePanel2, bottomPanel;
    JRadioButton accountTypeCurrentRadioBtn, accountTypeSavingRadioBtn, genderMaleRadioBtn, genderFemaleRadioBtn; 
    ButtonGroup buttonGroupAccountType, buttonGroupGender;
    JButton createAccountBtn, homeBtn;
    
    AWindowListener awl = new AWindowListener();
    
    Connection con;
    Statement st;
    ResultSet rs;
    
    public OpenAccount()
    {
        f = new JFrame("OpenAccount");
        f.setSize(400, 400);
        f.setLocationRelativeTo(null);
        f.addWindowListener(awl);
        
        nameLbl = new JLabel("Name");
        addressLbl = new JLabel("Address");
        
        nameTxtF = new JTextField(20);
        addressTxtF = new JTextField(20);
        
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,2));
        topPanel.add(nameLbl);
        topPanel.add(nameTxtF);
        topPanel.add(addressLbl);
        topPanel.add(addressTxtF);
        
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(1,2));
        
        insideMiddlePanel1 = new JPanel();
        insideMiddlePanel1.setBorder(BorderFactory.createTitledBorder("Account Type"));
       
        accountTypeCurrentRadioBtn = new JRadioButton("Current");
        accountTypeSavingRadioBtn = new JRadioButton("Saving");
        
        buttonGroupAccountType = new ButtonGroup();
        buttonGroupAccountType.add(accountTypeCurrentRadioBtn);
        buttonGroupAccountType.add(accountTypeSavingRadioBtn);
        
        insideMiddlePanel1.add(accountTypeCurrentRadioBtn);
        insideMiddlePanel1.add(accountTypeSavingRadioBtn);
        
        insideMiddlePanel2 = new JPanel();
        insideMiddlePanel2.setBorder(BorderFactory.createTitledBorder("Gender"));
        
        genderMaleRadioBtn = new JRadioButton("Male");
        genderFemaleRadioBtn = new JRadioButton("Female");
        
        buttonGroupGender = new ButtonGroup();
        buttonGroupGender.add(genderMaleRadioBtn);
        buttonGroupGender.add(genderFemaleRadioBtn);
        
        insideMiddlePanel2.add(genderMaleRadioBtn);
        insideMiddlePanel2.add(genderFemaleRadioBtn);
        
        
        middlePanel.add(insideMiddlePanel1);
        middlePanel.add(insideMiddlePanel2);
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        
        homeBtn = new JButton("Home");
        homeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {

                Home h = new Home();
                f.dispose();
                
                
            }
        });
        createAccountBtn = new JButton("Create Account");
        
        createAccountBtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent ae)
            {
                if (!(nameTxtF.getText().trim().equals("")) && !(addressTxtF.getText().trim().equals("")) && buttonGroupAccountType.getSelection()!=null && buttonGroupGender.getSelection()!=null)
                {
                    boolean currentAccount = false;
                    boolean savingAccount = false;
                    boolean genderMale = false;
                    boolean  genderFemale = false;
                    
                    String accountType = "";
                    String gender = "";
                    
                    if (accountTypeCurrentRadioBtn.isSelected())
                    {
                        currentAccount = true;
                        accountType = "C";
                    }
                    else
                    {
                        savingAccount = true;
                        accountType = "S";
                    }
                    
                    if (genderMaleRadioBtn.isSelected())
                    {
                        genderMale = true;
                        gender = "M";
                    }
                    else
                    {
                        genderFemale = true;
                        gender = "F";
                    }
                    
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost/marks_test_db", "root", "");
                        st = con.createStatement();
                        
                        rs = st.executeQuery("select concat("+ "'"+ accountType + "'" + "," + "'" + gender + "'" + ", (select lpad((select ifnull((SELECT max(substr(AcNo, 3, 3)) from bank as t where substr(AcNo, 1, 1) = " + "'" +accountType + "'" + "), 0) + 1), 3, '0')))");
                        rs.next();         
                        String accNo = rs.getString(1);
                        
                        String s = "insert into bank values "+ "((select concat("+ "'"+ accountType + "'" + "," + "'" + gender + "'" + ", (select lpad((select ifnull((SELECT max(substr(AcNo, 3, 3)) from bank as t where substr(AcNo, 1, 1) = " + "'" +accountType + "'" + "), 0) + 1), 3, '0')))), " + "'" + nameTxtF.getText() + "'" + "," + "'" + addressTxtF.getText() + "'" + ")";
                        st.executeUpdate(s);
                     
                        JOptionPane.showMessageDialog(null, "Account " + accNo +" Created");
                        
                        Home h = new Home();
                        f.dispose();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.toString());
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Incomplete fields");
                }
            }
        });
        
        bottomPanel.add(homeBtn);
        bottomPanel.add(createAccountBtn);
        
        
        f.add(topPanel, BorderLayout.NORTH);
        f.add(middlePanel, BorderLayout.CENTER);
        f.add(bottomPanel, BorderLayout.SOUTH);
        
        
        
        f.setVisible(true);
    }
    
    
}
