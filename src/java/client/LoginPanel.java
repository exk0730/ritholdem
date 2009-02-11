package client;

import game.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*; 

/**
 *  Register and login panels
 *	@author	Admin
 */
public class LoginPanel	extends javax.swing.JPanel	implements ActionListener
{
	private double initCash;
    private int userID;
    private Client client;
    public JMenuBar jMenuBar;
    public JMenuItem addMoneyBtn;
    public JMenuItem logoutBtn;
    public Table table1;
	 
	//---------------------------
	//Variables for sliding panel
	//---------------------------
	boolean minimizing;
	boolean expanding;
	Timer	expandTimer, minimizeTimer;
	int currentDividerLoc;
	int iterator =	0;
	 
	/** Creates new form LoginPanel	*/
	public LoginPanel(String host, JMenuBar _jMenuBar, JMenuItem _addMoneyBtn, JMenuItem _logoutBtn)
	{
        client = Client.instance(host);
        jMenuBar = _jMenuBar;
        addMoneyBtn = _addMoneyBtn;
        logoutBtn = _logoutBtn;
		initComponents();
	}
	
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSplitPane = new javax.swing.JSplitPane();
        userPanel = new javax.swing.JPanel();
        userNameLabel = new javax.swing.JLabel();
        userNameTextField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        loginBtn = new javax.swing.JButton();
        registerBtn = new javax.swing.JButton();
        registerPanel = new javax.swing.JPanel();
        enterUserNameLabel = new javax.swing.JLabel();
        regUserNameTextField = new javax.swing.JTextField();
        emailTextField = new javax.swing.JTextField();
        finishBtn = new javax.swing.JButton();
        regPasswordField = new javax.swing.JPasswordField();
        regPasswordTwoField = new javax.swing.JPasswordField();
        enterPasswordLabel = new javax.swing.JLabel();
        reenterPasswordLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        firstNameLabel = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        lastNameLabel = new javax.swing.JLabel();
        lastNameTextField = new javax.swing.JTextField();
        moneyLabel = new javax.swing.JLabel();
        moneyTextField = new javax.swing.JTextField();
        creditCardLabel1 = new javax.swing.JLabel();
        creditCardTextField1 = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        jSplitPane.setDividerLocation(800);
        jSplitPane.setDividerSize(0);
        jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        userPanel.setBackground(new java.awt.Color(0, 102, 0));
        userPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        userPanel.setLayout(new java.awt.GridBagLayout());

        userNameLabel.setFont(new java.awt.Font("Tahoma", 0, 18));
        userNameLabel.setForeground(new java.awt.Color(153, 255, 0));
        userNameLabel.setText("Username: ");
        userPanel.add(userNameLabel, new java.awt.GridBagConstraints());

        userNameTextField.setBackground(new java.awt.Color(51, 204, 0));
        userNameTextField.setFont(new java.awt.Font("Tahoma", 1, 18));
        userNameTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        userNameTextField.setPreferredSize(new java.awt.Dimension(150, 25));
        userNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameTextFieldActionPerformed(evt);
            }
        });
        userPanel.add(userNameTextField, new java.awt.GridBagConstraints());

        passwordLabel.setBackground(new java.awt.Color(153, 255, 0));
        passwordLabel.setFont(new java.awt.Font("Tahoma", 0, 18));
        passwordLabel.setForeground(new java.awt.Color(153, 255, 0));
        passwordLabel.setText("Password: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        userPanel.add(passwordLabel, gridBagConstraints);

        passwordField.setBackground(new java.awt.Color(51, 204, 0));
        passwordField.setFont(new java.awt.Font("Tahoma", 0, 18));
        passwordField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        passwordField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        userPanel.add(passwordField, gridBagConstraints);

        loginBtn.setBackground(new java.awt.Color(0, 153, 51));
        loginBtn.setForeground(new java.awt.Color(153, 255, 0));
        loginBtn.setText("Log-in");
        loginBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        loginBtn.setPreferredSize(new java.awt.Dimension(80, 25));
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 15, 0);
        userPanel.add(loginBtn, gridBagConstraints);

        registerBtn.setBackground(new java.awt.Color(0, 153, 51));
        registerBtn.setForeground(new java.awt.Color(153, 255, 0));
        registerBtn.setText("Register");
        registerBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        registerBtn.setPreferredSize(new java.awt.Dimension(80, 25));
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 15, 0);
        userPanel.add(registerBtn, gridBagConstraints);

        jSplitPane.setTopComponent(userPanel);

        registerPanel.setBackground(new java.awt.Color(102, 255, 102));
        registerPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        registerPanel.setLayout(new java.awt.GridBagLayout());

        enterUserNameLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        enterUserNameLabel.setText("Enter username: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(enterUserNameLabel, gridBagConstraints);

        regUserNameTextField.setFont(new java.awt.Font("Tahoma", 1, 14));
        regUserNameTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        regUserNameTextField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(regUserNameTextField, gridBagConstraints);

        emailTextField.setFont(new java.awt.Font("Tahoma", 1, 14));
        emailTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        emailTextField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(emailTextField, gridBagConstraints);

        finishBtn.setBackground(new java.awt.Color(0, 153, 51));
        finishBtn.setForeground(new java.awt.Color(153, 255, 0));
        finishBtn.setText("Finish");
        finishBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        finishBtn.setPreferredSize(new java.awt.Dimension(80, 25));
        finishBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        registerPanel.add(finishBtn, gridBagConstraints);

        regPasswordField.setFont(new java.awt.Font("Tahoma", 1, 14));
        regPasswordField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        regPasswordField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(regPasswordField, gridBagConstraints);

        regPasswordTwoField.setFont(new java.awt.Font("Tahoma", 1, 14));
        regPasswordTwoField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        regPasswordTwoField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(regPasswordTwoField, gridBagConstraints);

        enterPasswordLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        enterPasswordLabel.setText("Enter password: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        registerPanel.add(enterPasswordLabel, gridBagConstraints);

        reenterPasswordLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        reenterPasswordLabel.setText("Re-enter password: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(reenterPasswordLabel, gridBagConstraints);

        emailLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        emailLabel.setText("Enter email address: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(emailLabel, gridBagConstraints);

        firstNameLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        firstNameLabel.setText("First name: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(firstNameLabel, gridBagConstraints);

        firstNameTextField.setFont(new java.awt.Font("Tahoma", 1, 14));
        firstNameTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        firstNameTextField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(firstNameTextField, gridBagConstraints);

        lastNameLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        lastNameLabel.setText("Last name: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(lastNameLabel, gridBagConstraints);

        lastNameTextField.setFont(new java.awt.Font("Tahoma", 1, 14));
        lastNameTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        lastNameTextField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(lastNameTextField, gridBagConstraints);

        moneyLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        moneyLabel.setText("Money Amount: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(moneyLabel, gridBagConstraints);

        moneyTextField.setFont(new java.awt.Font("Tahoma", 1, 14));
        moneyTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        moneyTextField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(moneyTextField, gridBagConstraints);

        creditCardLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        creditCardLabel1.setText("Credit Card No.:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(creditCardLabel1, gridBagConstraints);

        creditCardTextField1.setFont(new java.awt.Font("Tahoma", 1, 14));
        creditCardTextField1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        creditCardTextField1.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        registerPanel.add(creditCardTextField1, gridBagConstraints);

        jSplitPane.setRightComponent(registerPanel);

        add(jSplitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * Logout the client
	 */
	public void logout(){
		client.logout();
	}


	@SuppressWarnings("deprecation")
	private void loginBtnActionPerformed(java.awt.event.ActionEvent evt)
	{
		userID = 0;
		if(userNameTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Enter your user name");
		}
		else if(String.valueOf(passwordField.getPassword()).equals(""))
		{
			JOptionPane.showMessageDialog(this, "Enter your password");
		}
        else {
            userID = client.login(userNameTextField.getText(), String.valueOf(passwordField.getPassword()));
            if(userID == RMIInterface.LOGIN_FAILED) {
                JOptionPane.showMessageDialog(this, "Unable to login!");
                passwordField.setText("");
                userNameTextField.setText("");
            }
            else {
                initCash = client.getBank(userID);
                if(initCash <= 0){
                    try{
                        initCash = client.retrieveEmergencyFunds(userID);
                    }
                    catch(Exception e) { initCash = 0; }
                }
                this.removeAll();
                table1 = new Table(initCash, userID, client);

                jMenuBar.setVisible(true);
                logoutBtn.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        logoutBtnActionPerformed(evt);
                    }
                });

                addMoneyBtn.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addMoneyBtnActionPerformed(evt);
                    }
                });

                this.setLayout(new java.awt.BorderLayout());
                this.add(table1,	java.awt.BorderLayout.CENTER);
                //JLabel statusLabel = new JLabel("...");
                //this.add(statusLabel, java.awt.BorderLayout.SOUTH);
                this.revalidate();

            }
        }
	}                                        

	private void registerBtnActionPerformed(java.awt.event.ActionEvent evt)
	{
		minimizing	= true;
		if(minimizeTimer !=	null)
		{
			minimizeTimer.stop();
		}
		minimizeTimer = new	Timer(10, this);
		iterator =	10;
		minimizeTimer.start();
		currentDividerLoc =	this.jSplitPane.getDividerLocation();
	}                                           

	private void finishBtnActionPerformed(java.awt.event.ActionEvent evt)
	{
        boolean registerOk = true;
        
		if(regUserNameTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Enter your user name");
            registerOk = false;
        }
		else if(emailTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Enter an email");
            registerOk = false;
        }
		else if(String.valueOf((regPasswordField.getPassword())).equals(""))
		{
			JOptionPane.showMessageDialog(this, "Enter a password");
            registerOk = false;
        }
		else if(String.valueOf((regPasswordTwoField.getPassword())).equals(""))
		{
			JOptionPane.showMessageDialog(this, "Repeat your password");
            registerOk = false;
        }
		else if(!(String.valueOf(regPasswordField.getPassword()).equals(String.valueOf((regPasswordTwoField.getPassword())))))
		{
			JOptionPane.showMessageDialog(this, "Your passwords don't match.");
			regPasswordField.setText(""); regPasswordTwoField.setText("");
            registerOk = false;
        }
		else if(firstNameTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Enter your first name");
            registerOk = false;
        }
		else if(moneyTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Enter a credit card");
            registerOk = false;
        }
		else if(lastNameTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Enter your last name");
            registerOk = false;
        }
		else
		{
                String initCashStr = moneyTextField.getText();
                try
				{
					initCash = Double.parseDouble(initCashStr);
                    if(initCash < 0)
                    {
                          JOptionPane.showMessageDialog(this, "You've entered negative money amount, please re-enter.");
                          registerOk = false;
                    }
                    if(initCash > 1000000)
                    {
                        JOptionPane.showMessageDialog(this, "You've maxed out your credit card! Maximum amount is 1000000. Please, re-enter.");
                        registerOk = false;
                    }
                }
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(this, "You have entered an invalid number.");
                    registerOk = false;
                }
				catch(NullPointerException npe)
				{
					JOptionPane.showMessageDialog(this, "You need to enter a number.");
                    registerOk = false;
				}
			}



            if(registerOk)
            {
                if(methodRegistration()== RMIInterface.LOGIN_FAILED)
                {
                    JOptionPane.showMessageDialog(this, "Impossible to register, this login is already taken. Try another");
                    registerOk = false;
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                    expanding = true;
                    if(expandTimer != null)
                    {
                        expandTimer.stop();
                    }
                    expandTimer =	new Timer(10, this);
                    iterator =	10;
                    expandTimer.start();
                    clearFields();
                }
            }
            
	}                                         

    /**
     * Attempt to register a user
     * @return
     */
	private int methodRegistration()
	{
		userID = client.register(regUserNameTextField.getText(), String.valueOf(regPasswordField.getPassword()),
						firstNameTextField.getText(), lastNameTextField.getText(), 
						emailTextField.getText(), moneyTextField.getText(), initCash);
		return userID;
	}

	private void userNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameTextFieldActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_userNameTextFieldActionPerformed

	public void actionPerformed(ActionEvent e)
	{		
		iterator++;
		if(minimizing)
		{
			currentDividerLoc-=iterator;
			this.jSplitPane.setDividerLocation(currentDividerLoc);

			this.registerPanel.setVisible(true);
			if(currentDividerLoc	<=	0)
			{
					this.jSplitPane.setDividerLocation(0);
					minimizeTimer.stop();
					minimizing	= false;
			}
		}
		
		if(expanding)
		{
			currentDividerLoc+=iterator;
			this.jSplitPane.setDividerLocation(currentDividerLoc);

			if	(currentDividerLoc >= 850)
			{
					this.jSplitPane.setDividerLocation(850);
					expandTimer.stop();
					expanding = false;
			}
		}
	}
	
	private void clearFields()
	{
		moneyTextField.setText("");
		emailTextField.setText("");
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		regPasswordField.setText("");
		regPasswordTwoField.setText("");
		regUserNameTextField.setText("");
	}

    public void addMoneyBtnActionPerformed(ActionEvent evt)
    {
        Boolean validAmount = true;
        double moneyToAddDbl = 0;
        String moneyToAddString = JOptionPane.showInputDialog(this, "How much money would you like to put into your account?");
        if(!moneyToAddString.equals(""))
        {
            try
            {
                moneyToAddDbl = Double.parseDouble(moneyToAddString);

                if(moneyToAddDbl < 0)
                {
                      JOptionPane.showMessageDialog(this, "You've entered negative money amount to add, please re-enter a positive amount.");
                      validAmount = false;
                }
                if(1000000 - initCash < moneyToAddDbl)
                {
                    JOptionPane.showMessageDialog(this, "You've maxed out the addable amount. Maximum amount you can have is 1000000. Please, re-enter.");
                    validAmount = false;
                }
            }
            catch(NumberFormatException nfe)
            {
                JOptionPane.showMessageDialog(this, "You have entered an invalid number.");
                validAmount = false;
            }
            catch(NullPointerException npe)
            {
                JOptionPane.showMessageDialog(this, "You need to enter a number.");
                validAmount = false;
            }

            if(validAmount)
            {
                client.addMoney(moneyToAddDbl);
                initCash += moneyToAddDbl;
                table1.updateCashAmount(initCash);

                JOptionPane.showMessageDialog(this, "You have successfully added $" + moneyToAddDbl + " to your account.");
            }
        }
    }

    public void logoutBtnActionPerformed(ActionEvent evt)
    {
		client.logout();
        this.removeAll();
        initComponents();
        jMenuBar.setVisible(false);
        this.revalidate();
    }
		

	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel creditCardLabel1;
    private javax.swing.JTextField creditCardTextField1;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JLabel enterPasswordLabel;
    private javax.swing.JLabel enterUserNameLabel;
    private javax.swing.JButton finishBtn;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JSplitPane jSplitPane;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JButton loginBtn;
    private javax.swing.JLabel moneyLabel;
    private javax.swing.JTextField moneyTextField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel reenterPasswordLabel;
    private javax.swing.JPasswordField regPasswordField;
    private javax.swing.JPasswordField regPasswordTwoField;
    private javax.swing.JTextField regUserNameTextField;
    private javax.swing.JButton registerBtn;
    private javax.swing.JPanel registerPanel;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JTextField userNameTextField;
    private javax.swing.JPanel userPanel;
    // End of variables declaration//GEN-END:variables
}
