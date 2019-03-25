package MapEditor;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Game.FileChooser;
import Game.MapLoader;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;


import Models.Continent;
import Models.Country;

/**
 * In MapEditor package and this class is used for edit the map via GUI approach
 * 
 * @author Team36
 * @version 1.1
 */


public class MapEditorGUI extends JFrame implements ActionListener,MouseMotionListener,MouseListener{

    /**
     * Paramaters Setting.
     * 
     * graph the list to store countries when creating map
     * countries the list to store countries when loading map
     * continents the list to store continents  when loading map
     * 
     */

    int toolFlag=0;
    int x1,y1,x2,y2;
    int radius=15;
    String name1,name2,filename;
    ArrayList<Country> graph = new ArrayList<>();
    ArrayList<ArrayList> tempMap;
  	ArrayList<Country> countries = new ArrayList<>();                   	
  	ArrayList<Continent> continents = new ArrayList<>(); ;
    Country FromCountry,ToCountry;
    Continent NewContinent;
    
    Panel toolPanel,paintPanel;
    Button addCountry,addNeighbor,saveMap,clearMap,addContinent,showInfo,loadMap,editInfo;
    JTextArea TerrTextField;
    FileChooser fc;

    MapEdit mapedit =new MapEdit("Mapedit");


    public JFrame frame;

    /**
     * Launch the application and test MapGUI individually.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	MapEditorGUI window = new MapEditorGUI();
                    window.frame.setVisible(true);
                                        
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MapEditorGUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        //Map Array
        	       
        frame = new JFrame();
        frame.setBounds(100, 100, 1260, 785);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
    
  
        
        toolPanel = new Panel();
        paintPanel=new Panel();
        paintPanel.setLayout(null);
        paintPanel.setBackground(Color.white);
        paintPanel.setBounds(0,0,(int)1260*3/4, (int)785*3/4);
        
        addCountry = new Button("AddCountry");
        addNeighbor = new Button("AddNeighbor");
        saveMap = new Button("SaveMap");
        clearMap = new Button("ClearMap");
        addContinent = new Button("AddContinent");
        showInfo = new Button("ShowInfo");
        loadMap=new Button("loadMap");
        editInfo=new Button("editInfo");
        
        TerrTextField= new JTextArea(30,15);
        TerrTextField.setFont(new Font(null, Font.PLAIN, 16));
        JScrollPane js=new JScrollPane(TerrTextField);
        js.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        		js.setVerticalScrollBarPolicy(
        		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        TerrTextField.setLineWrap(false);
        
        addCountry.addActionListener(this);
        addNeighbor.addActionListener(this);
        saveMap.addActionListener(this);
        //clearMap.addActionListener(this);
        addContinent.addActionListener(this);
        showInfo.addActionListener(this);
        loadMap.addActionListener(this);
        editInfo.addActionListener(this);
        
        toolPanel.add(addCountry);
        toolPanel.add(addNeighbor);
        toolPanel.add(addContinent);
        toolPanel.add(saveMap);
        toolPanel.add(clearMap);
        toolPanel.add(showInfo);
        toolPanel.add(loadMap);
        toolPanel.add(editInfo);
        
       
        frame.add(toolPanel,BorderLayout.NORTH);
        frame.add(paintPanel);
        frame.add(js,BorderLayout.EAST);
     
        
        validate();

        final JPanel panel = new JPanel();
      
        /**
         * Add listeners to paintPanel.
         */
        paintPanel.addMouseListener(new MouseAdapter() {
        	
        	
            /**
             * mousePressed method is the listener for select the country on panel
             * 
             */
  
            @Override
            public void mousePressed(MouseEvent e) {           	
            	x1=e.getX();
            	y1=e.getY();
            	switch(toolFlag){ 
	              
            	case 2:
                for(Country o : graph) {                	
                	if(o.getX()<x1&x1<=o.getX()+30&y1>o.getY()&y1<o.getY()+30) {
                		name1=o.getName();
                		FromCountry=o;
                	}
                	
                }              
            	break;
                                
            }
            }
            /**
             * mouseReleased method is the listener for select the other country on panel
             * 
             */
            public void mouseReleased(MouseEvent e) {            	
            	x2=e.getX();
            	y2=e.getY();           	
            	switch(toolFlag){ 
            	              
            	case 2:
            	System.out.println(x1);	
            	Graphics g2 =paintPanel.getGraphics();
            	
                for(Country o : graph) {
                   
                 	
                 	if(o.getX()<x2&x2<o.getX()+30&y2>o.getY()&y2<o.getY()+30) {
                 		name2=o.getName();
                 		ToCountry=o;
                 		
                 		ToCountry.addNeighbour(FromCountry);
                 		FromCountry.addNeighbour(ToCountry);
                 		mapedit.addConnection(name1, name2);
                 		mapedit.addConnection(name2, name1);
                 	
                 	}                 	
                 }  
                showinfo();  
                g2.drawLine(FromCountry.getX()+radius,FromCountry.getY()+radius , ToCountry.getX()+radius, ToCountry.getY()+radius);
            	break;
                                
            }
            }
                        
            /**
             * mouseClicked method is the listener for addCountry,
             * saveMap,clearMap,addContinent,showInfo, buttons 
             * 
             */
            @Override
            public void mouseClicked(MouseEvent e) {
            	
            	switch(toolFlag){ 
            	
            	case 1:
            	Graphics g =paintPanel.getGraphics();  
                //g.drawOval(e.getX(), e.getY(), radius*2, radius*2);   //draw circle
                
                //Create new country
                String Countryname=setCountryName("Enter countryname");
                String Contname=setCountryName("Enter continentname");
                System.out.println(Contname);
                if(Countryname!=null&&Contname!=null) {
                	   g.drawOval(e.getX(), e.getY(), radius*2, radius*2);   //draw circle
                       
                       Continent tempcont=mapedit.findContinent(Contname);
                       Country tempCountry = new Country(e.getX(),e.getY(),Countryname,tempcont);
                      
                       mapedit.addCountry(Countryname, Contname, e.getX(), e.getY());
                	   g.drawString(""+tempCountry.getName(),e.getX(),e.getY());
                       graph.add(tempCountry);
                       countries.add(tempCountry);
                }
;
                
                
          
                showinfo();  

             
                break;
                
            	case 3:
                String continentName=setCountryName("Enter CountinentName");
                int controlNum=setContNum("Enter ControlNumber");
                Continent tempcont2 =new Continent(continentName, controlNum);
            	mapedit.addContinent(continentName, controlNum);
            	continents.add(tempcont2);
            	showinfo();  
       
            	break;
                
            	case 4:
            	for(Country o : graph) {
                    System.out.println(o.getX()+"-"+o.getY()+"-"+o.getName()+"-"+o.getCont().getName());
                    o.printNeighbors();
                    System.out.println();
                    
            	} 	
            	
            	String nm = JOptionPane.showInputDialog("Input the file name");
            	mapedit.saveToFile(nm+".txt");
            	break;
            	
            	case 5:
            	mapclear();
            	showinfo();  
            	break;
            	
            	case 6:           		
            	showinfo();  
            	
            	break;                               
            }
            
            }
                                         
            });
        
        /**
         * Add listener to buttons.
         */
        
        addContinent.addMouseListener(new MouseAdapter() {
            /**
             * mouseClicked listener for adding continent.
             */
        	
	       	 public void mouseClicked(MouseEvent e) {  
	       		 	String continentName=setCountryName("Enter CountinentName");
	                int controlNum=setContNum("Enter ControlNumber");
	                System.out.println(controlNum);
	                if(continentName!=null) {
	                	Continent tempcont2 =new Continent(continentName, controlNum);
		            	mapedit.addContinent(continentName, controlNum);
		            	continents.add(tempcont2);
	                	
	                }
	                
	                
	            	showinfo(); 
	         	 
	       	 	}
        });
        
        saveMap.addMouseListener(new MouseAdapter() {
        	
            /**
             * mouseClicked listener for saving map.
             */
        	 public void mouseClicked(MouseEvent e) {  
        		 
        		String nm = JOptionPane.showInputDialog("Input the file name");
                	
        		 if(mapedit.saveToFile(nm+".map")) {
        		 JOptionPane.showMessageDialog(null,"Successfully Saved");
        		 }
        		}
        });
        
        clearMap.addMouseListener(new MouseAdapter() {
        	
            /**
             * mouseClicked listener for clearing map.
             */
       	 public void mouseClicked(MouseEvent e) {  
       		mapclear();
        	showinfo();  
       	 	}
        });
        
        editInfo.addMouseListener(new MouseAdapter() {
        	
            /**
             * mouseClicked listener for editing map.
             */
          	 public void mouseClicked(MouseEvent e) {  
          		String newInfo=TerrTextField.getText();
          	  try {
          		 
  	            FileWriter writer = new FileWriter(fc.filepath,false);  	            
  	            writer.write(newInfo);  	            
  	            writer.flush();
  	            writer.close();
  	        } catch (IOException e2) {
  	            e2.printStackTrace();
  	        }
          	Graphics g2 =paintPanel.getGraphics();
        	g2.clearRect(0,0,paintPanel.getSize().width,paintPanel.getSize().height);
        	
        	graph.clear();
        	mapedit.clear();
          	loadmap(fc.filename);
    		
            	 	}
             });
        
        loadMap.addMouseListener(new MouseAdapter() {
            /**
             * mouseClicked listener for loading map.
             */
          	 public void mouseClicked(MouseEvent e) {  
          		
          		 fc = new FileChooser();
          	
          		 if(fc.ChooseFile()) mapclear();
          		 String filename = fc.filename;
          		 loadmap(filename);
          		 mapedit.checkValid();
          		 showinfo();  
          	 	}
           });
        
        showInfo.addMouseListener(new MouseAdapter() {
            /**
             * mouseClicked listener for updating information in text area.
             */
        	 public void mouseClicked(MouseEvent e) {            		
        		 showinfo();           
        	 }
        	
        });
        
        
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    
    private void showinfo() {
     	TerrTextField.setText("[Map]\r\n" + 
        		"author=Invincible Team Four\r\n" + 
        		"warn=yes\r\n" + 
        		"image=none\r\n" + 
        		"wrap=no\r\n" + 
        		"scroll=none\r\n" + 
        		"\r\n" + "[Continents]\r\n" +mapedit.showContinents()+
        		"\r\n" + "[Territories]\r\n" + MapshowCountries());
    	
    }
    
    private void mapclear() {
	Graphics g2 =paintPanel.getGraphics();
	g2.clearRect(0,0,paintPanel.getSize().width,paintPanel.getSize().height);
	countries.clear();
	continents.clear();
	graph.clear();
	mapedit.clear();
	}
    
    //private method used for load map
    private void loadmap(String filename) {
			
		tempMap = new MapLoader().loadMap(filename);
	  	countries = tempMap.get(0);                   	
	  	continents = tempMap.get(1);
  	
	  	for(Continent ct : continents) {
	  		mapedit.addContinent(ct.getName(), ct.getControl_value());
	  	}
	  	
	  	for(Country comp : countries) {
	  		System.out.println("neighbor"+comp.getNeighbours().size());
	  		
	  		Graphics g =paintPanel.getGraphics();  
	        g.drawOval(comp.getX(), comp.getY(), 30, 30);
	        g.drawString(""+comp.getName(),comp.getX(),comp.getY());
	        graph.add(comp);
	        mapedit.addCountry(comp.getName(), comp.getContName(), comp.getX(), comp.getY());
	        for(Country cont : comp.getNeighbours()){
	            g.drawLine(cont.getX()+radius,cont.getY()+radius,comp.getX()+radius,comp.getY()+radius);
	        }
	  	}
	  	
	  	for(Country comp : countries) {
	        for(Country cont : comp.getNeighbours()){
	            mapedit.addConnection(comp.getName(), cont.getName());
	        }
	  	} 	
    }
    
    private String MapshowCountries() {
    	
    	String info="";
    	for (Country loopCountry:countries){  
    		info=info+loopCountry.getName()+","+loopCountry.getX()+","+loopCountry.getY()+","+loopCountry.getContName()+","+loopCountry.printNeighbors()+"\r\n";
         }                 	
    	return info;
    }
    
    /**
     * Set different flag for buttons
     */
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(e.getSource()==addCountry)//
		{toolFlag = 1;}

		if(e.getSource()==addNeighbor)//
		{toolFlag = 2;}

		if(e.getSource()==addContinent)//
		{toolFlag = 3;}

		if(e.getSource()==saveMap)//
		{toolFlag = 4;}
		
		if(e.getSource()==clearMap)//
		{toolFlag = 5;}

		if(e.getSource()==showInfo)//
		{toolFlag = 6;}
		
		if(e.getSource()==loadMap)//
		{toolFlag = 7;}
		
		if(e.getSource()==editInfo)//
		{toolFlag = 8;}
		
	
	        
	}
	//Used for entering information
    public String setCountryName(String dialog) {
        String input = JOptionPane.showInputDialog(dialog);
        return input;
    }	
    
    public String setContName(String dialog) {
        String input = JOptionPane.showInputDialog(dialog);
        return input;
    }	
    
    public int setContNum(String dialog) {
        int input = Integer.parseInt(JOptionPane.showInputDialog(dialog));
        return input;
    }
	

	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		x1=e.getX();
		y1=e.getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		x2=e.getX();
		y2=e.getY();
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(Graphics g)
	{
	paint(g);
	}
	
	public void paint(Graphics g)
	{
		
	}

}


