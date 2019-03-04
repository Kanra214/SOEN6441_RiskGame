
package View_Components;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

 
public class StartManu extends JFrame{

	    public JPanel startPanel;
	    public JButton startGame,editMap,instructions,exit;
	    private int buttonH=30;
	    private int buttonW=120;

		public StartManu() { 
			
		} //?????????? 
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
	        editMap = new JButton("Edit Map");	 
	        instructions = new JButton("Instructions");
	        exit = new JButton("Exit Game");	
	        
	        startPanel.setLayout(null);
	        startGame.setBounds(w/2-buttonW/2, y, buttonW, buttonH);
	        editMap.setBounds(w/2-buttonW/2, y+60, buttonW, buttonH);
	        instructions.setBounds(w/2-buttonW/2, y+120, buttonW, buttonH);
	        exit.setBounds(w/2-buttonW/2, y+180, buttonW, buttonH);
	        
	        startPanel.add(startGame);
	        startPanel.add(editMap);
	        startPanel.add(instructions);
	        startPanel.add(exit);
	        add(startPanel);
	        
	        
		} 
	
		
		 public static void main(String[] args) {
		    	StartManu mwnd=new StartManu("Risk Manu",20,30,300,400);
		    	mwnd.setVisible(true);
		    }
	}


	


