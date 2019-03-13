package View_Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import java.util.Observable;
import java.util.Observer;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import Models.Phases;
import Models.Player;

public class CardExchangeView extends JFrame implements Observer{
    private final static int X = 0;
    private final static int Y = 0;


    private final static int WIDTH = 600;
    private final static int HEIGHT = 400;
	
	private CardPanel cp;
	private JPanel mainPanel;
	

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
        
        cp.setBounds(X,Y,WIDTH,HEIGHT);
        cp.setBackground(Color.WHITE);
        cp.setLayout(new GridLayout(6,1));
        mainPanel.add(cp);
        add(mainPanel);
	}
	
	@Override
    public void update(Observable o, Object arg) {

        Phases p = (Phases)o;
        //mapPanel is not a part of observer pattern
        //update phasePanel
      
        cp.setContext(p.getCurrent_player());


    }
	//extends JFrame implements Observer 
	
	
	
	
	
	
	private class CardPanel extends JPanel {
	    private JLabel[] cardLabels = new JLabel[6];

	    /**
	     * This is constructor
	     */
	    protected CardPanel(){
	        cardLabels[0] = new JLabel();
	        cardLabels[1] = new JLabel();
	        cardLabels[2] = new JLabel();

	        add(cardLabels[0]);
	        add(cardLabels[1]);
	        add(cardLabels[2]);

	    }

	    /**
	     * Set context of this SidePanel
	     * @param players ArrayList of Players
	     */
	    protected void setContext(Player currentPlayer){
	    	for(int i=0;i<3;i++) {
	 	       //currentPlayer.showPlayerCards(i);
	    		
	 	       cardLabels[i].setText(getPlayerCardInfo(currentPlayer,i));
	    	}

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
