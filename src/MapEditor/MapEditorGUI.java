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
import java.util.ArrayList;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Game.Controller;
import Game.FileChooser;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;

import Models.MapEdit;
import Models.Continent;
import Models.Country;




public class MapEditorGUI extends JFrame implements ActionListener,MouseMotionListener,MouseListener{

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
	
    
    Panel toolPanel,paintPanel;
    Country FromCountry,ToCountry;
    Button addCountry,addNeighbor,saveMap,clearMap,addContinent,showInfo,loadMap;
    int toolFlag=0;
    int x1,y1,x2,y2;
    String name1,name2,filename;
    Continent NewContinent;
    ArrayList<Country> graph = new ArrayList<>();
    MapEdit mapedit =new MapEdit("Mapedit");
    
    
    public JFrame frame;

    /**
     * Launch the application.
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
        
        final JTextArea TerrTextField= new JTextArea(20,15);
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
        clearMap.addActionListener(this);
        addContinent.addActionListener(this);
        showInfo.addActionListener(this);
        loadMap.addActionListener(this);
        
        toolPanel.add(addCountry);
        toolPanel.add(addNeighbor);
        toolPanel.add(addContinent);
        toolPanel.add(saveMap);
        toolPanel.add(clearMap);
        toolPanel.add(showInfo);
        toolPanel.add(loadMap);
        
        
       
        frame.add(toolPanel,BorderLayout.NORTH);
        frame.add(paintPanel);
        frame.add(js,BorderLayout.EAST);
     
        
        validate();

        final JPanel panel = new JPanel();
      
        paintPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {           	
            	x1=e.getX();
            	y1=e.getY();
            	//System.out.println(x1+"press"+y1);
            	switch(toolFlag){ 
	              
            	case 2:
                for(Country o : graph) {
                   //System.out.println(o.getX()+"-"+o.getY()+"-"+o.getName()+"-"+o.getCont());
                	
                	if(o.getX()<x1&x1<=o.getX()+30&y1>o.getY()&y1<o.getY()+30) {
                		//System.out.println("catch");
                		name1=o.getName();
                		FromCountry=o;
                		System.out.println("catch"+name1);
                	}
                	
                }              
            	break;
                                
            }
            }
            
            public void mouseReleased(MouseEvent e) {            	
            	x2=e.getX();
            	y2=e.getY();           	
            	switch(toolFlag){ 
            	              
            	case 2:
            	System.out.println(x1);	
            	Graphics g2 =paintPanel.getGraphics();
            	
                for(Country o : graph) {
                   
                 	
                 	if(o.getX()<x2&x2<o.getX()+30&y2>o.getY()&y2<o.getY()+30) {
                 		//System.out.println("catch2");
                 		name2=o.getName();
                 		ToCountry=o;
                 		ToCountry.addNeighbour(FromCountry);
                 		
                 		mapedit.addConnection(name1, name2);
                 		
                 		for(Country o2 : graph) {
                 			if(o2.getName().equals(name1)) {
                 				o2.addNeighbour(FromCountry);
                 			}
                 		}                		
                 		System.out.println("catch"+name2);
                 	}                 	
                 }  
                
                g2.drawLine(FromCountry.getX()+15,FromCountry.getY()+15 , ToCountry.getX()+15, ToCountry.getY()+15);
            	break;
                                
            }
            }
            
            
            @Override
            public void mouseClicked(MouseEvent e) {
            	
            	switch(toolFlag){ 
            	
            	case 1:
            	Graphics g =paintPanel.getGraphics();  
                g.drawOval(e.getX(), e.getY(), 30, 30);   //draw circle
                
                //Create new country
                String Countryname=setCountryName("Enter countryname");
                String Contname=setCountryName("Enter continentname");
                
                Continent tempcont=mapedit.findContinent(Contname);
                Country tempCountry = new Country(e.getX(),e.getY(),Countryname,tempcont);
                toolPanel.add(tempCountry.countryButton);
                
                mapedit.addCountry(Countryname, Contname, e.getX(), e.getY());
                
                g.drawString(""+tempCountry.getName(),e.getX(),e.getY());
                graph.add(tempCountry);

                //print info
                for(Country o : graph) {
                	System.out.println(o.getX()+"-"+o.getY()+"-"+o.getName()+"-"+o.getCont());
                }
                break;
                
            	case 3:
                String continentName=setCountryName("Enter CountinentName");
                int controlNum=setContNum("Enter ControlNumber");
            	mapedit.addContinent(continentName, controlNum);
       
            	break;
                
            	case 4:
            	for(Country o : graph) {
                    System.out.println(o.getX()+"-"+o.getY()+"-"+o.getName()+"-"+o.getCont().getName());
                    o.printNeighbors();
                    System.out.println();
                    
            	} 	
            	mapedit.saveToFile("nm");
            	break;
            	
            	case 5:
            	System.out.print(toolFlag);	
            	Graphics g2 =panel.getGraphics();
            	g2.clearRect(0,0,panel.getSize().width,panel.getSize().height);
            	graph.clear();
            	break;
            	
            	case 6:           		
            	TerrTextField.setText("[Map]\r\n" + 
                		"author=Sean O'Connor\r\n" + 
                		"image=world.bmp\r\n" + 
                		"wrap=no\r\n" + 
                		"scroll=horizontal\r\n" + 
                		"warn=yes\r\n" + 
                		"\r\n" + "[Continents]\r\n" +mapedit.showContinents()+
                		"\r\n" + "[Territories]\r\n" + mapedit.showCountries());
            	
            	break;                               
            }
            
            }
            

                              
            });
        addContinent.addMouseListener(new MouseAdapter() {
       	 public void mouseClicked(MouseEvent e) {  
             String continentName=setCountryName("Enter CountinentName");
             int controlNum=setContNum("Enter ControlNumber");
         	 mapedit.addContinent(continentName, controlNum);
       		 
       	 	}
        });
        
        saveMap.addMouseListener(new MouseAdapter() {
        	 public void mouseClicked(MouseEvent e) {  
        		 mapedit.saveToFile("nm");
        	 }
        });
        
        clearMap.addMouseListener(new MouseAdapter() {
       	 public void mouseClicked(MouseEvent e) {  
       		Graphics g2 =panel.getGraphics();
        	g2.clearRect(0,0,panel.getSize().width,panel.getSize().height);
        	graph.clear();
       	 	}
        });
        
        loadMap.addMouseListener(new MouseAdapter() {
          	 public void mouseClicked(MouseEvent e) {  
          		
          		 FileChooser ctrl = new FileChooser();
          		 ctrl.ChooseFile();
          		 String filename = ctrl.filename;
          	
          	 	}
           });
        
        showInfo.addMouseListener(new MouseAdapter() {
        	
        	 public void mouseClicked(MouseEvent e) {            		
             	TerrTextField.setText("[Map]\r\n" + 
                 		"author=Sean O'Connor\r\n" + 
                 		"image=world.bmp\r\n" + 
                 		"wrap=no\r\n" + 
                 		"scroll=horizontal\r\n" + 
                 		"warn=yes\r\n" + 
                 		"\r\n" + "[Continents]\r\n" +mapedit.showContinents()+
                 		"\r\n" + "[Territories]\r\n" + mapedit.showCountries());            
        	 }
        	
        });
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    
    
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
	
	
	        
	}
	
	private void mapLoader() {
		
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

