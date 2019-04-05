
package View_Components;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * <h1>StartManu</h1>
 * This class is start menu window that will be shown at the beginning of this game
 */
public class StartManu extends JFrame{

	    public JPanel startPanel;
	    public JButton startGame,editMap,loadMap,instructions,exit, startTournament;
	    private int buttonH=30;
	    private int buttonW=150;

	/**
	 * This is constructor
	 */
	public StartManu() {
			
		}

	/**
	 * This is constructor used in the project
	 * @param s title of this StartManu
	 * @param x x coordinate of this StartManu
	 * @param y y coordinate of this StartManu
	 * @param w width of this StartManu
	 * @param h height of this StartManu
	 */
		public StartManu(String s,int x,int y,int w,int h) { 
		    super(s);
			//this.init(s); 
			this.setLocation(x,y); 
			this.setSize(w,h); 
			//this.setVisible(true); 
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);     
	        setLocationRelativeTo(null);
	        //setResizable(false);
	        
	        startPanel = new JPanel();
	        startGame = new JButton("Start Game");
	        startTournament = new JButton("Start Tournament");
	        editMap = new JButton("Edit Map");
	        loadMap = new JButton("Load Map");
	        instructions = new JButton("Instructions");
	        exit = new JButton("Exit Game");	
	        
	        startPanel.setLayout(null);
	        startGame.setBounds(w/2-buttonW/2, y, buttonW, buttonH);
	        startTournament.setBounds(w/2-buttonW/2, y+40, buttonW, buttonH);
	        editMap.setBounds(w/2-buttonW/2, y+80, buttonW, buttonH);
			loadMap.setBounds(w/2-buttonW/2, y+120, buttonW, buttonH);
	        instructions.setBounds(w/2-buttonW/2, y+160, buttonW, buttonH);
	        exit.setBounds(w/2-buttonW/2, y+200, buttonW, buttonH);
	        
	        startPanel.add(startGame);
	        startPanel.add(startTournament);
	        startPanel.add(editMap);
	        startPanel.add(loadMap);
	        startPanel.add(instructions);
	        startPanel.add(exit);
	        add(startPanel);
	        
	        
		}

	/**
	 * Main method
	 * @param args args in main method
	 */
	public static void main(String[] args) {
		    	StartManu mwnd=new StartManu("Risk Manu",20,30,300,400);
		    	mwnd.setVisible(true);
		    }
	}


	


