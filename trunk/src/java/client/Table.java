package client;

import game.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

/**
 *
 * @author  Admin
 */
public class Table extends javax.swing.JPanel
{

	/**
	*Card width
	*/
	private final int CARD_WIDTH = 73;
	/**
	*Card height
	*/
	private final int CARD_HEIGHT = 100;
    private ArrayList<JLabel> labels = new ArrayList<JLabel>();
	private int cardCount, userID;
	private double initCash, bet;
	private Client client;
    private PlayerCards hand;
    private DealerCards dealer;
	private boolean dealt;
	private Card dealerCard;
    private JLabel jl;
	
    /** Creates new form Table */
   public Table(double startCash, int userID, Client client)
	{
		initCash = startCash;
		initComponents();
        this.userID = userID;
		this.client = client;
		bet = 0;
		cardCount = 0;
		dealt = false;
   }
	
	private void betBtn1ActionPerformed(java.awt.event.ActionEvent evt)
	{
		bet += 1;
        betAmountLabel.setText("" + bet);
	}
	
	private void betBtn10ActionPerformed(java.awt.event.ActionEvent evt)
	{
		bet += 10;
        betAmountLabel.setText("" + bet);
	}
	
	private void betBtn25ActionPerformed(java.awt.event.ActionEvent evt)
	{
		bet += 25;
        betAmountLabel.setText("" + bet);
	}

	private void betBtn50ActionPerformed(java.awt.event.ActionEvent evt)
	{
		bet += 50;
        betAmountLabel.setText("" + bet);
	}

	private void betBtnx100ActionPerformed(java.awt.event.ActionEvent evt)
	{
		bet *= 100;
        betAmountLabel.setText("" + bet);
	}

	private void dealButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		/*
		*TODO check if they can do anything right now (if it's their turn)
		*/
		if(bet == 0)
		{
			JOptionPane.showMessageDialog(null, "You have to enter a bet first");
		}
        else if(initCash - bet < 0){
            JOptionPane.showMessageDialog(null, "You don't have enough money to make this bet!");
            bet = 0;
            betAmountLabel.setText("" + bet);
        }
		else if(!dealt)
		{
			this.setLayout(null);
            cardCount = 0;
            hand = client.deal(userID, bet);
            renderPlayerHand(hand);
            dealer = client.deal();
            renderDealerHand(dealer);
            dealt = true;
		}
	}

	private void hitButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		/*
         * TODO
         * check if it is their turn (this includes if they have stood or doubled - meaning they can't receive another card)
		 * check if they have already busted
		*/
		if(!dealt)
		{
			JOptionPane.showMessageDialog(null, "You have not received any cards yet");
		}
		else
        {
            Card temp = client.hit(userID);
            renderHitCard(temp, true);
            if(client.bust(userID,true)) {
                renderDealerCard();
                playerBust();
            }
		}
	}

	private void standButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
        cardCount = 2;
        renderDealerCard();
        dealerActions();
		dealt = false;
	}

	private void doubleButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
        bet *= 2;
        betAmountLabel.setText("" + bet);
        Card temp = client.hit(userID);
        renderHitCard(temp, true);
        if(client.bust(userID, true)) {
            renderDealerCard();
            playerBust();
        }
        else{
            cardCount = 2;
            renderDealerCard();
            dealerActions();
        }
        dealt = false;
	
	}

	private void startGameActionPerformed(java.awt.event.ActionEvent evt)
	{
	
	}

    private void playerBust() {
        JOptionPane.showMessageDialog(null, "You busted");
        bet *= -1;
        double temp = client.updateBank(userID, bet);
        cashAmountLabel.setText("" + temp);
        bet = 0;
        betAmountLabel.setText("" + bet);
        wipe();
        dealt = false;
    }

	private void dealerActions() {
        while(!client.dealerStand()){
            Card temp = client.hit();
            renderHitCard(temp, false);
            if(client.bust(userID, false)) {
                break;
            }
        }
        String s = client.checkWin(userID, bet);
        String temp = s.substring(0, s.indexOf('_'));
        JOptionPane.showMessageDialog(null, temp);
        try{
            bet = Double.parseDouble(s.substring(s.indexOf('_')+1, s.length()));
        }
        catch(NumberFormatException nfe){
            System.err.println("Error receiving bet during checkWin: " + nfe.getMessage());
            System.exit(1);
        }
        cashAmountLabel.setText("" + (client.updateBank(userID, bet)) );
        bet = 0;
        betAmountLabel.setText("" + bet);
        wipe();
    }

	private void renderPlayerHand(PlayerCards hand)
	{
		for(int i = 0; i < hand.getSize(); i++)
		{
			Card cardP = hand.getCardAt(i);
			jl = cardP.getCardImage();
            labels.add(jl);
			jl.setBounds(400 + (cardCount * CARD_WIDTH),400,CARD_WIDTH,CARD_HEIGHT);
			cardCount++;
			jl.setVisible(true);
			this.add(jl);
		}
		update();
	}
	
	private void renderDealerHand(DealerCards dealer)
	{
		dealerCard = dealer.getCardAt(0);
		Card cardD = dealer.getCardAt(1);
		Card back = new Card(0,0);
		JLabel jlD = cardD.getCardImage();
        labels.add(jlD);
        labels.add(jl);
		jl = back.getCardImage();
		jl.setBounds(400+CARD_WIDTH,100,CARD_WIDTH,CARD_HEIGHT);
		jl.setVisible(true);
		jlD.setBounds(400,100,CARD_WIDTH,CARD_HEIGHT);
		jlD.setVisible(true);
		this.add(jlD);
        this.add(jl);
		update();
	}
	
	private void renderHitCard(Card c, boolean playerOrDealer)
	{
		Card card = c;
		JLabel jlHit = card.getCardImage();
        labels.add(jlHit);
        if(playerOrDealer) {
            jlHit.setBounds(400+(CARD_WIDTH*cardCount), 400, CARD_WIDTH, CARD_HEIGHT);
        }
        else{
            jlHit.setBounds(400+(CARD_WIDTH*cardCount), 100, CARD_WIDTH, CARD_HEIGHT);
        }
		cardCount++;
		jlHit.setVisible(true);
		this.add(jlHit);
		update();
	}

    private void renderDealerCard() {
        this.remove(jl);
        jl = dealerCard.getCardImage();
        labels.add(jl);
        jl.setBounds(400+CARD_WIDTH,100,CARD_WIDTH,CARD_HEIGHT);
        jl.setVisible(true);
        this.add(jl);
        update();
    }

    private void wipe(){
        for(JLabel jlabels : labels){
            this.remove(jlabels);
        }
        update();
    }

	private void update()
	{
		this.validate();
		this.update(this.getGraphics());
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		Graphics2D graphics = (Graphics2D)g;
		
		RadialGradientPaint p = new RadialGradientPaint(new Point2D.Double((getWidth()+getWidth()/3) / 2.0,
		(getHeight()/2)-getHeight()/8), getWidth() / 2.5f, new float[]{0.0f, 1.0f},
		new Color[]{Color.GREEN, Color.BLACK});
	      
		graphics.setPaint(p);
       
		graphics.fillRect(0, 0, getWidth(), getHeight());
    
	 	//graphics.setPaint(new GradientPaint(0, getHeight()/2, Color.WHITE, getWidth(), getHeight()/2, Color.GREEN));
		//RadialGradientPaint p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
		//								getHeight() / 2.0), getWidth() / 2.0f, new float[]{0.0f, 1.0f},
		//								new Color[]{Color.GREEN, Color.BLACK}); 
	}
	
	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents()
	{
		java.awt.GridBagConstraints gridBagConstraints;
	
		betBar =	new javax.swing.JPanel();
		betBtn50	= new	javax.swing.JButton();
		betBtnx100 = new javax.swing.JButton();
		betBtn10	= new	javax.swing.JButton();
		betBtn1 = new javax.swing.JButton();
		betBtn25	= new	javax.swing.JButton();
		dealButton = new javax.swing.JButton();
		hitButton =	new javax.swing.JButton();
		standButton	= new	javax.swing.JButton();
		doubleButton =	new javax.swing.JButton();
		scoreInfo =	new javax.swing.JPanel();
		cashLabel =	new javax.swing.JLabel();
		cashAmountLabel =	new javax.swing.JLabel(initCash + "");
		betLabel	= new	javax.swing.JLabel();
		betAmountLabel	= new	javax.swing.JLabel();
		startGame =	new javax.swing.JButton();
	
		setLayout(new java.awt.BorderLayout());
	
		betBar.setBackground(new java.awt.Color(0, 0, 0));
		betBar.setLayout(new	java.awt.GridBagLayout());
	
		betBtn50.setIcon(new	javax.swing.ImageIcon(getClass().getResource("/images/bet50.jpg")));	//	NOI18N
		betBtn50.setPreferredSize(new	java.awt.Dimension(40, 40));
		betBtn50.addActionListener(new java.awt.event.ActionListener()	{
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
			betBtn50ActionPerformed(evt);
			 }
		});
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	3;
		gridBagConstraints.gridy =	0;
		betBar.add(betBtn50,	gridBagConstraints);
	
		betBtnx100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/betx100.jpg"))); // NOI18N
		betBtnx100.setPreferredSize(new java.awt.Dimension(40, 40));
		betBtnx100.addActionListener(new	java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
			betBtnx100ActionPerformed(evt);
			 }
		});
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	4;
		gridBagConstraints.gridy =	0;
		gridBagConstraints.insets = new java.awt.Insets(0,	0,	0,	150);
		betBar.add(betBtnx100, gridBagConstraints);
	
		betBtn10.setIcon(new	javax.swing.ImageIcon(getClass().getResource("/images/bet10.jpg")));	//	NOI18N
		betBtn10.setPreferredSize(new	java.awt.Dimension(40, 40));
		betBtn10.addActionListener(new java.awt.event.ActionListener()	{
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
			betBtn10ActionPerformed(evt);
			 }
		});
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	1;
		gridBagConstraints.gridy =	0;
		betBar.add(betBtn10,	gridBagConstraints);
	
		betBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bet1.jpg"))); // NOI18N
		betBtn1.setPreferredSize(new java.awt.Dimension(40, 40));
		betBtn1.addActionListener(new	java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
			betBtn1ActionPerformed(evt);
			 }
		});
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	0;
		gridBagConstraints.gridy =	0;
		betBar.add(betBtn1, gridBagConstraints);
	
		betBtn25.setIcon(new	javax.swing.ImageIcon(getClass().getResource("/images/bet25.jpg")));	//	NOI18N
		betBtn25.setPreferredSize(new	java.awt.Dimension(40, 40));
		betBtn25.addActionListener(new java.awt.event.ActionListener()	{
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
			betBtn25ActionPerformed(evt);
			 }
		});
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	2;
		gridBagConstraints.gridy =	0;
		betBar.add(betBtn25,	gridBagConstraints);
	
		dealButton.setBackground(new java.awt.Color(0, 153, 51));
		dealButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
		dealButton.setForeground(new java.awt.Color(153, 255,	0));
		dealButton.setText("DEAL");
		dealButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		dealButton.setPreferredSize(new java.awt.Dimension(50, 25));
		dealButton.addActionListener(new	java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
			dealButtonActionPerformed(evt);
			 }
		});
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	6;
		gridBagConstraints.gridy =	0;
		betBar.add(dealButton, gridBagConstraints);
	
		hitButton.setBackground(new java.awt.Color(0, 153,	51));
		hitButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
		hitButton.setForeground(new java.awt.Color(153,	255, 0));
		hitButton.setText("HIT");
		hitButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		hitButton.setPreferredSize(new java.awt.Dimension(50,	25));
		hitButton.addActionListener(new java.awt.event.ActionListener() {
			public void	actionPerformed(java.awt.event.ActionEvent evt)	{
			hitButtonActionPerformed(evt);
			 }
		});
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	7;
		gridBagConstraints.gridy =	0;
		betBar.add(hitButton, gridBagConstraints);
	
		standButton.setBackground(new	java.awt.Color(0,	153, 51));
		standButton.setFont(new	java.awt.Font("Tahoma",	1,	12));	//	NOI18N
		standButton.setForeground(new	java.awt.Color(153, 255, 0));
		standButton.setText("STAND");
		standButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		standButton.setPreferredSize(new	java.awt.Dimension(65, 25));
		standButton.addActionListener(new java.awt.event.ActionListener()	{
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
			standButtonActionPerformed(evt);
			 }
		});
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	8;
		gridBagConstraints.gridy =	0;
		betBar.add(standButton,	gridBagConstraints);
	
		doubleButton.setBackground(new java.awt.Color(0, 153,	51));
		doubleButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
		doubleButton.setForeground(new java.awt.Color(153,	255, 0));
		doubleButton.setText("DOUBLE");
		doubleButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		doubleButton.setPreferredSize(new java.awt.Dimension(71,	25));
		doubleButton.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
			doubleButtonActionPerformed(evt);
			 }
		});
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	9;
		gridBagConstraints.gridy =	0;
		betBar.add(doubleButton, gridBagConstraints);
	
		add(betBar,	java.awt.BorderLayout.PAGE_END);
	
		scoreInfo.setBackground(new java.awt.Color(0, 0, 0));
		scoreInfo.setLayout(new	java.awt.GridBagLayout());
	
		cashLabel.setBackground(new java.awt.Color(0, 0, 0));
		cashLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
		cashLabel.setForeground(new java.awt.Color(204,	204, 0));
		cashLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		cashLabel.setText("CASH");
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	0;
		gridBagConstraints.gridy =	0;
		scoreInfo.add(cashLabel, gridBagConstraints);
	
		cashAmountLabel.setBackground(new java.awt.Color(153,	153, 153));
		cashAmountLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
		cashAmountLabel.setForeground(new java.awt.Color(255,	255, 255));
		cashAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		//cashAmountLabel.setText();
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	0;
		gridBagConstraints.gridy =	1;
		scoreInfo.add(cashAmountLabel, gridBagConstraints);
	
		betLabel.setBackground(new	java.awt.Color(0,	0,	0));
		betLabel.setFont(new	java.awt.Font("Tahoma",	1,	24));	//	NOI18N
		betLabel.setForeground(new	java.awt.Color(204, 204, 0));
		betLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		betLabel.setText("Bet");
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	0;
		gridBagConstraints.gridy =	2;
		scoreInfo.add(betLabel,	gridBagConstraints);
	
		betAmountLabel.setBackground(new	java.awt.Color(153, 153, 153));
		betAmountLabel.setFont(new	java.awt.Font("Tahoma",	1,	24));	//	NOI18N
		betAmountLabel.setForeground(new	java.awt.Color(255, 255, 255));
		betAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		betAmountLabel.setText("0");
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	0;
		gridBagConstraints.gridy =	3;
		gridBagConstraints.insets = new java.awt.Insets(0,	0,	100, 0);
		scoreInfo.add(betAmountLabel,	gridBagConstraints);
	
		startGame.setBackground(new java.awt.Color(0, 0, 0));
		startGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deckofcards.jpg"))); // NOI18N
		startGame.setBorder(null);
		startGame.setPreferredSize(new java.awt.Dimension(198, 194));
		startGame.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
			startGameActionPerformed(evt);
			 }
		});
		gridBagConstraints =	new java.awt.GridBagConstraints();
		gridBagConstraints.gridx =	0;
		gridBagConstraints.gridy =	4;
		gridBagConstraints.insets = new java.awt.Insets(0,	0,	100, 0);
		scoreInfo.add(startGame, gridBagConstraints);
	
		add(scoreInfo,	java.awt.BorderLayout.LINE_START);
   }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel betAmountLabel;
    private javax.swing.JPanel betBar;
    private javax.swing.JButton betBtn1;
    private javax.swing.JButton betBtn10;
    private javax.swing.JButton betBtn25;
    private javax.swing.JButton betBtn50;
    private javax.swing.JButton betBtnx100;
    private javax.swing.JLabel betLabel;
    private javax.swing.JLabel cashAmountLabel;
    private javax.swing.JLabel cashLabel;
    private javax.swing.JButton dealButton;
    private javax.swing.JButton doubleButton;
    private javax.swing.JButton hitButton;
    private javax.swing.JPanel scoreInfo;
    private javax.swing.JButton standButton;
    private javax.swing.JButton startGame;
    // End of variables declaration//GEN-END:variables
}
