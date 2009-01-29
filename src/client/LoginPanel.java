package client;

import game.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*; 
import java.rmi.*;

/**
 *
 *	@author	Admin
 */
public class LoginPanel	extends javax.swing.JPanel	implements ActionListener
{
	private double initCash;
	private RMIInterface server;
	public boolean ok = false;
	/** Creates new form LoginPanel	*/
	public LoginPanel()
	{
		initComponents();
		initRMI();
	}
	
	//---------------------------
	//Variables for sliding panel
	//---------------------------
	boolean minimizing;
	boolean expanding;
	Timer	expandTimer, minimizeTimer;
	int currentDividerLoc;
	int iterator =	0;
	
	private void initRMI()
	{
		server = null;
		try
		{
			server = (RMIInterface)Naming.lookup(RMIInterface.SERVER_NAME);
			System.out.println("Connected to Server");
		}
		catch(Exception e)
		{
			System.out.println("Server Not Found");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void loginBtnActionPerformed(java.awt.event.ActionEvent evt)
	{
		int userID = RMIInterface.LOGIN_FAILED;
		if(userNameTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Enter your user name");
		}
		else if(passwordField.getPassword().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Enter your password");
		}
		try
		{
			if((userID = server.login(userNameTextField.getText(), String.valueOf(passwordField.getPassword()))) != RMIInterface.LOGIN_FAILED)
			{
                initCash = server.getBank(userID);
				this.removeAll();
				Table table1 = new Table(initCash, userID, server);
				this.setLayout(new java.awt.BorderLayout());
				this.add(table1,	java.awt.BorderLayout.CENTER);
				JLabel statusLabel = new JLabel("...");
				this.add(statusLabel, java.awt.BorderLayout.SOUTH);
				this.revalidate();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Login failed!");
			}
		}
		catch(RemoteException re)
		{
			System.err.println("Client error: " + re.getMessage());
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
		if(regUserNameTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Enter your user name");
		}
		else if(emailTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Enter an email");
		}
		else if(regPasswordField.getPassword().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Enter a password");
		}
		else if(regPasswordTwoField.getPassword().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Repeat your password");
		}
		else if(!(String.valueOf(regPasswordField.getPassword()).equals(String.valueOf((regPasswordTwoField.getPassword())))))
		{
			JOptionPane.showMessageDialog(null, "Your passwords don't match.");
			regPasswordField.setText(""); regPasswordTwoField.setText("");
		}
		else if(firstNameTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Enter your first name");
		}
		else if(creditCardTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Enter a credit card");
		}
		else if(lastNameTextField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Enter your last name");
		}
		else
		{
			System.out.println(String.valueOf(regPasswordField.getPassword()) + " " + String.valueOf(regPasswordTwoField.getPassword()));
			while(true)
			{
				String initCashStr = JOptionPane.showInputDialog(null, "How much money would you like to put into your account?");
				try
				{
					initCash = Double.parseDouble(initCashStr);
					if(methodRegistration() == false)
					{
						JOptionPane.showMessageDialog(null, "Impossible to register, this login is already taken. Try another");
						clearFields();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Registration successful!");
						expanding = true;
						if(expandTimer != null)
						{
							expandTimer.stop();
						}
						expandTimer =	new Timer(10, this);
						iterator =	10;
						expandTimer.start();
					}
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null, "You have entered an invalid number.");
					continue;
				}
				catch(NullPointerException npe)
				{
					JOptionPane.showMessageDialog(null, "You need to enter a number.");
					continue;
				}
				catch(RemoteException re)
				{
					System.out.println("Client error: " + re.getMessage());
					System.out.println(re.getStackTrace());
				}
				break;
			}
		}
	}
	
	private boolean methodRegistration() throws RemoteException
	{
		ok = server.register(regUserNameTextField.getText(), String.valueOf(regPasswordField.getPassword()),
						firstNameTextField.getText(), lastNameTextField.getText(), 
						emailTextField.getText(), creditCardTextField.getText(), initCash);
		return ok;
	}

	private void userNameTextFieldActionPerformed(java.awt.event.ActionEvent evt)
	{
	
	}

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

			if	(currentDividerLoc >= 800)
			{
					this.jSplitPane.setDividerLocation(800);
					expandTimer.stop();
					expanding = false;
			}
		}
	}
	
	private void clearFields()
	{
		creditCardTextField.setText("");
		emailTextField.setText("");
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		regPasswordField.setText("");
		regPasswordTwoField.setText("");
		regUserNameTextField.setText("");
	}
		
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		
		Graphics2D	graphics	= (Graphics2D)g;
			
		graphics.setPaint(Color.GREEN);
			//graphics.setPaint(new GradientPaint(0,	300, Color.WHITE,	800, 400, Color.GREEN));
			//graphics.fillRect(0,	0,	getWidth(),	getHeight());
	}
	
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"	desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents()
	{
		java.awt.GridBagConstraints gridBagConstraints;
		jSplitPane =	new javax.swing.JSplitPane();
		userPanel	= new	javax.swing.JPanel();
		userNameLabel =	new javax.swing.JLabel();
		userNameTextField = new javax.swing.JTextField();
		passwordLabel =	new javax.swing.JLabel();
		passwordField =	new javax.swing.JPasswordField();
		loginBtn = new javax.swing.JButton();
		registerBtn = new javax.swing.JButton();
		registerPanel =	new javax.swing.JPanel();
		enterUserNameLabel	= new	javax.swing.JLabel();
		regUserNameTextField =	new javax.swing.JTextField();
		emailTextField = new javax.swing.JTextField();
		finishBtn	= new	javax.swing.JButton();
		regPasswordField =	new javax.swing.JPasswordField();
		regPasswordTwoField =	new javax.swing.JPasswordField();
		enterPasswordLabel	= new	javax.swing.JLabel();
		reenterPasswordLabel = new javax.swing.JLabel();
		emailLabel =	new javax.swing.JLabel();
		firstNameLabel = new javax.swing.JLabel();
		firstNameTextField	= new	javax.swing.JTextField();
		lastNameLabel =	new javax.swing.JLabel();
		lastNameTextField = new javax.swing.JTextField();
		creditCardLabel	= new	javax.swing.JLabel();
		creditCardTextField =	new javax.swing.JTextField();

		setPreferredSize(new java.awt.Dimension(800, 600));
		setLayout(new java.awt.BorderLayout());

		jSplitPane.setDividerLocation(600);
		jSplitPane.setDividerSize(0);
		jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
		
		userPanel.setBackground(new	java.awt.Color(0,	102, 0));
		userPanel.setPreferredSize(new	java.awt.Dimension(500,	300));
		userPanel.setLayout(new java.awt.GridBagLayout());
		
		userNameLabel.setFont(new java.awt.Font("Tahoma", 0, 18));
		userNameLabel.setForeground(new java.awt.Color(153,	255, 0));
		userNameLabel.setText("Username: ");
		userPanel.add(userNameLabel, new java.awt.GridBagConstraints());
		
		userNameTextField.setBackground(new java.awt.Color(51,	204, 0));
		userNameTextField.setFont(new java.awt.Font("Tahoma", 1, 18));
		userNameTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		userNameTextField.setPreferredSize(new java.awt.Dimension(150, 25));
		userNameTextField.addActionListener(new	java.awt.event.ActionListener()
		{
			public void	actionPerformed(java.awt.event.ActionEvent evt)
			{
				userNameTextFieldActionPerformed(evt);
			}
		});
		userPanel.add(userNameTextField, new	java.awt.GridBagConstraints());
		passwordLabel.setBackground(new java.awt.Color(153,	255, 0));
		passwordLabel.setFont(new java.awt.Font("Tahoma", 0, 18));
		passwordLabel.setForeground(new java.awt.Color(153,	255, 0));
		passwordLabel.setText("Password: ");
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 0;
		gridBagConstraints.gridy	= 1;
		userPanel.add(passwordLabel, gridBagConstraints);
		passwordField.setBackground(new java.awt.Color(51, 204, 0));
		passwordField.setFont(new java.awt.Font("Tahoma", 0, 18));
		passwordField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		passwordField.setPreferredSize(new java.awt.Dimension(150, 25));
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 1;
		gridBagConstraints.gridy	= 1;
		userPanel.add(passwordField, gridBagConstraints);

		loginBtn.setBackground(new java.awt.Color(0, 153, 51));
		loginBtn.setForeground(new java.awt.Color(153, 255,	0));
		loginBtn.setText("Log-in");
		loginBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		loginBtn.setPreferredSize(new java.awt.Dimension(80, 25));
		loginBtn.addActionListener(new	java.awt.event.ActionListener()
		{
			public void	actionPerformed(java.awt.event.ActionEvent evt)
			{
						loginBtnActionPerformed(evt);
			}
		});
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 1;
		gridBagConstraints.gridy	= 3;
		gridBagConstraints.insets =	new java.awt.Insets(15,	0,	15, 0);
		userPanel.add(loginBtn, gridBagConstraints);

		registerBtn.setBackground(new java.awt.Color(0, 153, 51));
		registerBtn.setForeground(new java.awt.Color(153, 255,	0));
		registerBtn.setText("Register");
		registerBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		registerBtn.setPreferredSize(new java.awt.Dimension(80, 25));
		registerBtn.addActionListener(new	java.awt.event.ActionListener()
		{
			public void	actionPerformed(java.awt.event.ActionEvent evt)
			{
						registerBtnActionPerformed(evt);
			}
		});
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 0;
		gridBagConstraints.gridy	= 3;
		gridBagConstraints.insets =	new java.awt.Insets(15,	0,	15, 0);
		userPanel.add(registerBtn, gridBagConstraints);

		jSplitPane.setTopComponent(userPanel);

		registerPanel.setBackground(new java.awt.Color(102,	255, 102));
		registerPanel.setPreferredSize(new java.awt.Dimension(400, 300));
		registerPanel.setLayout(new	java.awt.GridBagLayout());

		enterUserNameLabel.setFont(new	java.awt.Font("Tahoma",	1,	14));
		enterUserNameLabel.setText("Enter	username: ");
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(enterUserNameLabel, gridBagConstraints);

		regUserNameTextField.setFont(new java.awt.Font("Tahoma", 1, 14));
		regUserNameTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		regUserNameTextField.setPreferredSize(new java.awt.Dimension(150, 25));
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(regUserNameTextField, gridBagConstraints);

		emailTextField.setFont(new java.awt.Font("Tahoma", 1, 14));
		emailTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		emailTextField.setPreferredSize(new java.awt.Dimension(150, 25));
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 1;
		gridBagConstraints.gridy	= 6;
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(emailTextField,	gridBagConstraints);

		finishBtn.setBackground(new	java.awt.Color(0,	153, 51));
		finishBtn.setForeground(new	java.awt.Color(153, 255, 0));
		finishBtn.setText("Finish");
		finishBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		finishBtn.setPreferredSize(new	java.awt.Dimension(80, 25));
		finishBtn.addActionListener(new java.awt.event.ActionListener()
		{
			public void	actionPerformed(java.awt.event.ActionEvent evt)
			{
					finishBtnActionPerformed(evt);
			}
		});
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 0;
		gridBagConstraints.gridy	= 7;
		gridBagConstraints.anchor =	java.awt.GridBagConstraints.EAST;
		registerPanel.add(finishBtn, gridBagConstraints);

		regPasswordField.setFont(new java.awt.Font("Tahoma", 1, 14));
		regPasswordField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		regPasswordField.setPreferredSize(new java.awt.Dimension(150, 25));
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 1;
		gridBagConstraints.gridy	= 1;
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(regPasswordField, gridBagConstraints);

		regPasswordTwoField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		regPasswordTwoField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		regPasswordTwoField.setPreferredSize(new java.awt.Dimension(150, 25));
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 1;
		gridBagConstraints.gridy	= 2;
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(regPasswordTwoField, gridBagConstraints);

		enterPasswordLabel.setFont(new	java.awt.Font("Tahoma",	1,	14));	//	NOI18N
		enterPasswordLabel.setText("Enter	password: ");
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 0;
		gridBagConstraints.gridy	= 1;
		registerPanel.add(enterPasswordLabel, gridBagConstraints);

		reenterPasswordLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		reenterPasswordLabel.setText("Re-enter password:	");
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 0;
		gridBagConstraints.gridy	= 2;
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(reenterPasswordLabel,	gridBagConstraints);

		emailLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
		emailLabel.setText("Enter email address: ");
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 0;
		gridBagConstraints.gridy	= 6;
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(emailLabel, gridBagConstraints);

		firstNameLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		firstNameLabel.setText("First name: ");
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 0;
		gridBagConstraints.gridy	= 3;
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(firstNameLabel,	gridBagConstraints);

		firstNameTextField.setFont(new	java.awt.Font("Tahoma",	1,	14));	//	NOI18N
		firstNameTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		firstNameTextField.setPreferredSize(new	java.awt.Dimension(150,	25));
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 1;
		gridBagConstraints.gridy	= 3;
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(firstNameTextField, gridBagConstraints);

		lastNameLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lastNameLabel.setText("Last	name:	");
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 0;
		gridBagConstraints.gridy	= 4;
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(lastNameLabel, gridBagConstraints);

		lastNameTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lastNameTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		lastNameTextField.setPreferredSize(new java.awt.Dimension(150, 25));
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 1;
		gridBagConstraints.gridy	= 4;
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(lastNameTextField,	gridBagConstraints);

		creditCardLabel.setFont(new	java.awt.Font("Tahoma",	1,	14));	//	NOI18N
		creditCardLabel.setText("Credit Card	No.:");
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 0;
		gridBagConstraints.gridy	= 5;
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(creditCardLabel, gridBagConstraints);

		creditCardTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		creditCardTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		creditCardTextField.setPreferredSize(new java.awt.Dimension(150, 25));
		gridBagConstraints	= new	java.awt.GridBagConstraints();
		gridBagConstraints.gridx	= 1;
		gridBagConstraints.gridy	= 5;
		gridBagConstraints.insets =	new java.awt.Insets(10,	0,	10, 0);
		registerPanel.add(creditCardTextField, gridBagConstraints);

		jSplitPane.setRightComponent(registerPanel);

		add(jSplitPane,	java.awt.BorderLayout.CENTER);
	}// </editor-fold>//GEN-END:initComponents
	
	// Variables declaration - do not modify                     
	private	javax.swing.JLabel creditCardLabel;
	private	javax.swing.JTextField creditCardTextField;
	private	javax.swing.JLabel emailLabel;
	private	javax.swing.JTextField emailTextField;
	private	javax.swing.JLabel enterPasswordLabel;
	private	javax.swing.JLabel enterUserNameLabel;
	private	javax.swing.JButton finishBtn;
	private	javax.swing.JLabel firstNameLabel;
	private	javax.swing.JTextField firstNameTextField;
	private	javax.swing.JSplitPane jSplitPane;
	private	javax.swing.JLabel lastNameLabel;
	private	javax.swing.JTextField lastNameTextField;
	private	javax.swing.JButton loginBtn;
	private	javax.swing.JPasswordField	passwordField;
	private	javax.swing.JLabel passwordLabel;
	private	javax.swing.JLabel reenterPasswordLabel;
	private	javax.swing.JPasswordField	regPasswordField;
	private	javax.swing.JPasswordField	regPasswordTwoField;
	private	javax.swing.JTextField regUserNameTextField;
	private	javax.swing.JButton registerBtn;
	private	javax.swing.JPanel registerPanel;
	private	javax.swing.JLabel userNameLabel;
	private	javax.swing.JTextField userNameTextField;
	private	javax.swing.JPanel userPanel;
}
