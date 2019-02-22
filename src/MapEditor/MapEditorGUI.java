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

import java.awt.BorderLayout;
import java.awt.Button;


import Models.MapEdit;
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
	
    
    Panel toolPanel;
    Button addCountry,addNeighbor,saveMap,clearMap,addContinent,showInfo;
    int toolFlag=0;
    int x1,y1,x2,y2;
    String name1,name2;
    ArrayList<MapCountry> graph = new ArrayList<>();
    MapEdit mapedit =new MapEdit("Mapedit");
    
    
    private JFrame frame;

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
        	

        String Countryname="";
        String Continentname="";
        
        frame = new JFrame();
        frame.setBounds(100, 100, 1260, 785);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        
        toolPanel = new Panel();
        addCountry = new Button("AddCountry");
        addNeighbor = new Button("AddNeighbor");
        saveMap = new Button("SaveMap");
        clearMap = new Button("ClearMap");
        addContinent = new Button("AddContinent");
        showInfo = new Button("ShowInfo");
        
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
        
        toolPanel.add(addCountry);
        toolPanel.add(addNeighbor);
        toolPanel.add(addContinent);
        toolPanel.add(saveMap);
        toolPanel.add(clearMap);
        toolPanel.add(showInfo);
        frame.add(toolPanel,BorderLayout.NORTH);
        frame.add(js,BorderLayout.EAST);
       // setBounds(60,60,900,600); setVisible(true);
        
        validate();
        int num=0;
        final JPanel panel = new JPanel();
      
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {           	
            	x1=e.getX();
            	y1=e.getY();
            	//System.out.println(x1+"press"+y1);
            	switch(toolFlag){ 
	              
            	case 2:
                for(MapCountry o : graph) {
                   //System.out.println(o.getX()+"-"+o.getY()+"-"+o.getName()+"-"+o.getCont());
                	
                	if(o.getX()<x1&x1<=o.getX()+30&y1>o.getY()&y1<o.getY()+30) {
                		//System.out.println("catch");
                		name1=o.getName();
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
            	Graphics g2 =panel.getGraphics();
            	g2.drawLine(x1, y1, x2, y2);
                for(MapCountry o : graph) {
                    //System.out.println(o.getX()+"-"+o.getY()+"-"+o.getName()+"-"+o.getCont());
                 	
                 	if(o.getX()<x2&x2<o.getX()+30&y2>o.getY()&y2<o.getY()+30) {
                 		//System.out.println("catch2");
                 		name2=o.getName();
                 		o.addNeighbour(name1);
                 		
                 		mapedit.addConnection(name1, name2);
                 		
                 		for(MapCountry o2 : graph) {
                 			if(o2.getName().equals(name1)) {
                 				o2.addNeighbour(name2);
                 			}
                 		}                		
                 		System.out.println("catch"+name2);
                 	}                 	
                 }           	
            	break;
                                
            }
            }
            
            
            
            });
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	
            	switch(toolFlag){ 
            	
            	case 1:
            	Graphics g =panel.getGraphics();  
                g.drawOval(e.getX(), e.getY(), 30, 30);   //draw circle
                
                //Create new country
                
                
                MapCountry tempCountry = new MapCountry(e.getX(),e.getY(),Countryname,Continentname,10);
                String Countryname=setCountryName("Enter countryname");
                tempCountry.setName(Countryname);
                String Contname=setCountryName("Enter continentname");
                tempCountry.setContinent(Contname);
                
                mapedit.addCountry(Countryname, Contname, e.getX(), e.getY());
                
                g.drawString(""+tempCountry.getName(),e.getX(),e.getY());
                graph.add(tempCountry);

                //print info
                for(MapCountry o : graph) {
                	System.out.println(o.getX()+"-"+o.getY()+"-"+o.getName()+"-"+o.getCont());
                }
                break;
                
            	case 3:
                String continentName=setCountryName("Enter CountinentName");
                int controlNum=setContNum("Enter ControlNumber");
            	mapedit.addContinent(continentName, controlNum);
       
            	break;
                
            	case 4:
            	for(MapCountry o : graph) {
                    System.out.println(o.getX()+"-"+o.getY()+"-"+o.getName()+"-"+o.getCont());
                    o.printNeighbors();
                    System.out.println();
                    
            	} 		
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
                		"\r\n" + "[Countries]\r\n" + mapedit.showCountries());
            	
            	break;
                
                
            }
            
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

