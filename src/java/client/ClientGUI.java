/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClientGUI.java
 *
 * Created on 09.02.2009, 18:51:53
 */

package client;

import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;


/**
 *
 * @author Admin
 */
public class ClientGUI extends javax.swing.JFrame implements WindowListener{

    String url;


    private client.LoginPanel loginPanel1;

    /** Creates new form ClientGUI */
    public ClientGUI() {

		loginPanel1 = null;
        initComponents();
		this.addWindowListener(this);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        connectionPanel = new javax.swing.JPanel();
        guiPanel = new javax.swing.JPanel();
        ipAddressLabel = new javax.swing.JLabel();
        ipAddressTextField = new javax.swing.JTextField();
        connectBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        teamName1 = new javax.swing.JLabel();
        teamName2 = new javax.swing.JLabel();
        teamName3 = new javax.swing.JLabel();
        teamName4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RITBlackjack");
        setResizable(false);

        connectionPanel.setPreferredSize(new java.awt.Dimension(1050, 700));
        connectionPanel.setLayout(new java.awt.BorderLayout());

        guiPanel.setBackground(new java.awt.Color(0, 102, 0));

        ipAddressLabel.setFont(new java.awt.Font("Tahoma", 0, 18));
        ipAddressLabel.setForeground(new java.awt.Color(153, 255, 0));
        ipAddressLabel.setText("Enter IP address: ");

        ipAddressTextField.setBackground(new java.awt.Color(51, 204, 0));
        ipAddressTextField.setFont(new java.awt.Font("Tahoma", 1, 18));
        ipAddressTextField.setText("localhost");
        ipAddressTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        ipAddressTextField.setPreferredSize(new java.awt.Dimension(150, 25));
        ipAddressTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ipAddressTextFieldKeyPressed(evt);
            }
        });

        connectBtn.setBackground(new java.awt.Color(0, 153, 51));
        connectBtn.setForeground(new java.awt.Color(153, 255, 0));
        connectBtn.setText("Connect");
        connectBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        connectBtn.setPreferredSize(new java.awt.Dimension(80, 25));
        connectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectBtnActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("(Ex: 129.234.13.13, localhost)");

        titleLabel.setFont(new java.awt.Font("Tahoma", 2, 48));
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("Blackjack Game ");

        teamName1.setBackground(new java.awt.Color(255, 255, 255));
        teamName1.setFont(new java.awt.Font("Tahoma", 0, 18));
        teamName1.setForeground(new java.awt.Color(255, 255, 255));
        teamName1.setText("Emilien Girault");

        teamName2.setBackground(new java.awt.Color(255, 255, 255));
        teamName2.setFont(new java.awt.Font("Tahoma", 0, 18));
        teamName2.setForeground(new java.awt.Color(255, 255, 255));
        teamName2.setText("Eric Kisner");

        teamName3.setBackground(new java.awt.Color(255, 255, 255));
        teamName3.setFont(new java.awt.Font("Tahoma", 0, 18));
        teamName3.setForeground(new java.awt.Color(255, 255, 255));
        teamName3.setText("Daniyar Amanchin");

        teamName4.setBackground(new java.awt.Color(255, 255, 255));
        teamName4.setFont(new java.awt.Font("Tahoma", 0, 18));
        teamName4.setForeground(new java.awt.Color(255, 255, 255));
        teamName4.setText("Tyler Schindel");

        javax.swing.GroupLayout guiPanelLayout = new javax.swing.GroupLayout(guiPanel);
        guiPanel.setLayout(guiPanelLayout);
        guiPanelLayout.setHorizontalGroup(
            guiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guiPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(guiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guiPanelLayout.createSequentialGroup()
                        .addGroup(guiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titleLabel)
                            .addGroup(guiPanelLayout.createSequentialGroup()
                                .addComponent(ipAddressLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(guiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(guiPanelLayout.createSequentialGroup()
                                        .addComponent(ipAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(connectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(369, 369, 369))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guiPanelLayout.createSequentialGroup()
                        .addGroup(guiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guiPanelLayout.createSequentialGroup()
                                .addGroup(guiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(teamName2)
                                    .addComponent(teamName1))
                                .addGap(29, 29, 29))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(teamName4)
                                .addComponent(teamName3)))
                        .addContainerGap())))
        );
        guiPanelLayout.setVerticalGroup(
            guiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guiPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(teamName1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teamName2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teamName3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teamName4)
                .addGap(89, 89, 89)
                .addComponent(titleLabel)
                .addGap(88, 88, 88)
                .addGroup(guiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ipAddressLabel)
                    .addComponent(ipAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(connectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        connectionPanel.add(guiPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(connectionPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Actions taken when connect button is pressed
     * @param evt connect button clicked
     */
    private void connectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectBtnActionPerformed
        boolean ok = false;
        url = ipAddressTextField.getText();
        
        if(!url.equals(""))
        { 
            try
            {
                loginPanel1 = new client.LoginPanel(url);
                ok = true;
            } catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this, "Unable to connect to the server.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if(ok){
                connectionPanel.removeAll();
                connectionPanel.add(loginPanel1, java.awt.BorderLayout.CENTER);
                connectionPanel.revalidate();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please enter an IP address.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
     
}//GEN-LAST:event_connectBtnActionPerformed

    /**
     * Actions taken when enter key is pressed on ip address textfield
     * @param evt key pressed event.
     */
    private void ipAddressTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ipAddressTextFieldKeyPressed
        // TODO add your handling code here:
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER)
        {
            boolean ok = false;
            url = ipAddressTextField.getText();

            if(!url.equals(""))
            {
                try
                {
                    loginPanel1 = new client.LoginPanel(url);
                    ok = true;
                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(this, "Unable to connect to the server.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                if(ok){
                    connectionPanel.removeAll();
                    connectionPanel.add(loginPanel1, java.awt.BorderLayout.CENTER);
                    connectionPanel.revalidate();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please enter an IP address.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_ipAddressTextFieldKeyPressed

    /**
    * main method that runs the gui
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectBtn;
    private javax.swing.JPanel connectionPanel;
    private javax.swing.JPanel guiPanel;
    private javax.swing.JLabel ipAddressLabel;
    private javax.swing.JTextField ipAddressTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel teamName1;
    private javax.swing.JLabel teamName2;
    private javax.swing.JLabel teamName3;
    private javax.swing.JLabel teamName4;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    //Not used
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

	/**
	 * Called when we close the window
	 * @param e window event
	 */
    public void windowClosing(WindowEvent e) {
        if(loginPanel1 != null){
			loginPanel1.logout();
		}
    }

    //Not used
    public void windowClosed(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    //Not used
    public void windowIconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    //Not used
    public void windowDeiconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    //Not used
    public void windowActivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //Not used
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
