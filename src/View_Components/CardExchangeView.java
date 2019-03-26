package View_Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import Models.Phases;
import Models.Player;


/**
 * <h1>CardExchangeView</h1>
 * This class is used as the Card Exchange View window to show the cards to Players
 */

public class CardExchangeView extends JFrame implements Observer{
	
	public static void main(String[] args) {
		   
			
		CardExchangeView cardexchange = new CardExchangeView();
		cardexchange.setVisible(true);
		}
	
	
    private final static int X = 0;
    private final static int Y = 0;


    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
	
	private CardPanel cp;
	private JPanel mainPanel;
	public JButton Exchange3Infantry,Exchange3Cavalry,Exchange3Artillery,Exchange3Diff,Cancel;


    /**
     * This is the constructor
     */

	public CardExchangeView(){
	    super("CardExchangeView");
		this.cp = new CardPanel();
		this.mainPanel = new JPanel();
		    
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setSize(WIDTH,HEIGHT);

        setResizable(false);
        
        //main panel settings
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(X,Y,WIDTH,HEIGHT);
        //side panel settings
        //"Infantry", "Cavalry", "Artillery"
        Exchange3Infantry=new JButton("Exchange 3 Infantry");
        Exchange3Cavalry=new JButton("Exchange 3 Cavalry");
        Exchange3Artillery=new JButton("Exchange 3 Artillery");
        Exchange3Diff=new JButton("Exchange 3 Different Cards");
        Cancel=new JButton("Cancel");
        
        GridBagLayout layout = new GridBagLayout();
        cp.setLayout(layout);
        GridBagConstraints s= new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(Exchange3Infantry, s);
        layout.setConstraints(Exchange3Cavalry, s);
        layout.setConstraints(Exchange3Artillery, s);
        layout.setConstraints(Exchange3Diff, s);
        layout.setConstraints(Cancel, s);
        layout.setConstraints(cp.cardLabels[0], s);
        layout.setConstraints(cp.cardLabels[1], s);
        layout.setConstraints(cp.cardLabels[2], s);
        layout.setConstraints(cp.cardLabels[3], s);
        cp.setBounds(X,Y,WIDTH,HEIGHT);      
        cp.setBackground(Color.WHITE);

        //Exchange3Same.setBounds(WIDTH-300  ,HEIGHT-500 , 200, 80);
        //Exchange3Diff.setBounds(WIDTH-300, HEIGHT-400, 200, 80);
        //Cancel.setBounds(WIDTH-300, HEIGHT-300, 200, 80);
        //cp.setLayout(new FlowLayout(FlowLayout.RIGHT,10,15));
        cp.add(Exchange3Infantry);
        cp.add(Exchange3Cavalry);
        cp.add(Exchange3Artillery);
        cp.add(Exchange3Diff);
        cp.add(Cancel);
       
        s.fill = GridBagConstraints.BOTH;
        
        
        
        mainPanel.add(cp);
        add(mainPanel);
  
	}
	
    /**
     * Update this window
     */

	@Override
    public void update(Observable o, Object arg) {

        Phases p = (Phases)o;
        //cp.cardLabels[0].setText("Your current cards: ");
        //cp.cardLabels[1].setText(cardToString(p.getCurrent_player()));
        cp.setContext(p.getCurrent_player());


    }
	//extends JFrame implements Observer 

	

    /**
     * The method is used for collecting the card info and change into String type/
     * @param player the Player object,pass the player to this method
     */

	private String cardToString(Player player){
		String output = "";
		for(int i = 0; i < 3; i++){
			output += player.getCards().showCardsName(i) + ": " + player.getCards().showCardsNumber(i) + "\r\n"+"\n";
		}
		return output;
	}
	private class CardPanel extends JPanel {
	    private JLabel[] cardLabels = new JLabel[6];

	    /**
	     * This is constructor
	     */
	    protected CardPanel(){
	        cardLabels[0] = new JLabel();
	        cardLabels[1] = new JLabel();
	        cardLabels[2] = new JLabel();
	        cardLabels[3] = new JLabel();//placeholder
	        
	        add(cardLabels[0]);
	        add(cardLabels[1]);
	        add(cardLabels[2]);
	        add(cardLabels[3]);


	    }

	    /**
	     * Set context of this SidePanel
	     * @param currentPlayer	 ArrayList of Players
	     */
	    public void setContext(Player currentPlayer){
	    	for(int i=0;i<3;i++) {
	 	       currentPlayer.showPlayerCards(i);
	    		
	 	       cardLabels[i].setText(getPlayerCardInfo(currentPlayer,i));
	    	}
	    	cardLabels[3].setText(" ");

	    }

	    /**
	     * Get player's information
	     * @param player info from whom you want to show
	     * @return String representation of player
	     */
	    private String getPlayerCardInfo(Player player,int cardID){
	          return    "<html><body>" +
	                    "<h3>CardName " + player.showPlayerCardsName(cardID) + "</h3>" +
	                    "<p>CardNumber: " + player.showPlayerCards(cardID) + "<br>" +

	                    "</body></html>";

	    }
	}
}
