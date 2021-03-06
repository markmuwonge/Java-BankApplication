
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mark
 */
public class Home{
    
    JFrame f;
    JButton openAccountBtn, depositMoneyBtn, withdrawMoneyBtn, checkBalanceBtn;
    boolean accNumberSet = false;

    AWindowListener awl = new AWindowListener();
    
    String accNo = "";
    
    public Home()
    {
        loadFrame();
    }
    
    
    public void loadFrame()
    {
        f = new JFrame("Home");
        f.setSize(400, 400);
        f.setLayout(new GridLayout(4,1));
        f.setLocationRelativeTo(null);
        f.addWindowListener(awl);
        
        openAccountBtn = new JButton("Open Account");
        openAccountBtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent ae)
            {
                OpenAccount oa = new OpenAccount();
                f.dispose();
            }
        });
        
        depositMoneyBtn = new JButton("Deposit Money");
        depositMoneyBtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent ae)
            {
                DepositAndWithdraw daw = new DepositAndWithdraw("Deposit");
                f.dispose(); 	
            }
        });
        withdrawMoneyBtn = new JButton("Withdraw Money");
        withdrawMoneyBtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent ae)
            {
                DepositAndWithdraw daw = new DepositAndWithdraw("Withdraw");
                f.dispose(); 	
            }
        });
        checkBalanceBtn = new JButton("Check Balance");
        checkBalanceBtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent ae)
            {
                CheckBalance cb = new CheckBalance();
                f.dispose();
            }
        });
        
        f.add(openAccountBtn);
        f.add(depositMoneyBtn);
        f.add(withdrawMoneyBtn);
        f.add(checkBalanceBtn);
        
        
        
        f.setVisible(true);
    }
    
    public static void main(String args[])
    {
        Home h = new Home();
    }
    
}
