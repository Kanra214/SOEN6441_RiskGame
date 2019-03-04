//this is center controller
package Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import Models.*;
import View_Components.CountryButton;

import View_Components.Window;
import View_Components.StartManu;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import MapEditor.MapEditorGUI;


public class Controller {
    Window window;
    Phases p;
    StartManu startmanu;
    MapEditorGUI mapeditor; 

    String filename ;
    
    public Controller(Window window) throws IOException {
        this.window = window;

//        this.window.welcome();

    }
    class Listener implements ActionListener{
//        private Phases phase;
        private Country chosenFrom = null;
        private Country chosenTo = null;
        private Listener(){

        }




        public void actionPerformed(ActionEvent e) {


            System.out.println();


//            System.out.println("Phase "+p.currentPhase);
//            System.out.println("Turn "+ p.currentTurn);
            if (e.getSource() == window.phasePanel.completePhaseButton) {
                System.out.println("Complete is called");
                p.nextPhase();
//                if (phaseNow == 2) phase.nextPhase();
//                if (phaseNow == 3) phase.nextTurn();
            }
            if (e.getSource() instanceof CountryButton) {
                Country chosen = ((CountryButton) e.getSource()).getCountry();
                if (p.getCurrentPhase() == 0) {
                    p.startUpPhase(chosen);
                } else if (p.getCurrentPhase() == 1) { // deploy army
                    p.reinforcementPhase(chosen);
                } else if (p.getCurrentPhase() == 3) {
                    if (chosenFrom == null) {
                        chosenFrom = chosen;
                    } else {
                        chosenTo = chosen;
                        int num = Integer.parseInt(window.promptPlayer("How many armies to move? max: " + (chosenFrom.getArmy() - 1) + ", min: 1"));
                        try {
                            p.fortificatePhase(chosenFrom, chosenTo, num);
                            chosenFrom = null;
                            chosenTo = null;
                        } catch (RiskGameException ex) {
                            String errorMsg;


                            switch (ex.type) {
                                case 0:
                                    errorMsg = "Out of army.";
                                    break;
                                case 1:
                                    errorMsg = "No such path.";
                                    break;
                                case 2:
                                    errorMsg = "Not in realms.";
                                    break;
                                case 3:
                                    errorMsg = "The source cannot be the target.";
                                    break;
                                case 4:
                                    errorMsg = "At lease move one army.";
                                    break;

                                default:
                                    errorMsg = "Unknown.";

                            }

                            window.showMsg(errorMsg + " Try again please.");
                            chosenFrom = null;
                            chosenTo = null;
                        }

                    }
                }
                else {//phase 2

                    p.attackPhase();

                }


            }
        }








    }
    
    public void startManu() throws IOException {
    	startmanu = new StartManu("Risk Manu",20,30,300,400);    	
    	startmanu.setVisible(true);
    	
    	startManuAction lisStart = new startManuAction(1);
    	startManuAction lisEditMap = new startManuAction(2);
    	startManuAction lisInstruction = new startManuAction(3);
    	startManuAction lisExit = new startManuAction(4);
    	
    	startmanu.startGame.addActionListener(lisStart);
    	startmanu.editMap.addActionListener(lisEditMap);
    	startmanu.instructions.addActionListener(lisInstruction);
    	startmanu.exit.addActionListener(lisExit);
    	
    	
    }
    
    
	public boolean ChooseFile() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			filename=selectedFile.getName();		
		}
		return true;
	}
    
    public class startManuAction implements ActionListener{

    	private int buttonFlag;
    	public startManuAction(int buttonFlag) {
    		this.buttonFlag = buttonFlag;
    	}
    	

    	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		  	switch (buttonFlag){
	    	
	    	case 1:
	    		if(ChooseFile()) {
	    			startmanu.dispose();		    			
				try {
					start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				startmanu.dispose();	
				
		}
		 
	    		
	    	break;
	    	
	    	case 2:
	    		mapeditor = new MapEditorGUI();
	    		mapeditor.frame.setVisible(true);
	    		startmanu.dispose();	
	    	break;
	    	
	    	case 4:
	    		startmanu.dispose();
	    	
	    		
	    	break;
	    		
	    	
	    	}
			
		}  
    	
    }

    public void start() throws IOException {

    	System.out.println(filename);
        ArrayList<ArrayList> tempMap = new MapLoader().loadMap(filename);

        int numOfPlayers = Integer.parseInt(window.promptPlayer("how many players?"));
        p = new Phases(tempMap.get(0), tempMap.get(1));
        p.addObserver(window);
        Listener lis = new Listener();
        p.gameSetUp(numOfPlayers);

        //draw the countries on the mapPanel
        window.drawMapPanel(p);

        for(CountryButton cb : window.mapPanel.cbs) {
            cb.addActionListener(lis);
        }

        window.phasePanel.completePhaseButton.addActionListener(lis);
        p.connectView();//after this updating window is enabled


        window.setVisible(true);



    }








}
