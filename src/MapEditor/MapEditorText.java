package MapEditor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MapEditor
 * Logics for map editor
 */
public class MapEditorText extends JFrame{

	/**
	 * Logics for map editor
	 */
    public MapEditorText() {
        //JFrame jf = new JFrame("MapEditor");
        super("MapEditor");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        
        //add read file here read into "text".
        
        String text="[Map]\r\n" + 
        		"author=Sean O'Connor\r\n" + 
        		"image=world.bmp\r\n" + 
        		"wrap=no\r\n" + 
        		"scroll=horizontal\r\n" + 
        		"warn=yes\r\n" + 
        		"\r\n" + 
        		"[Continents]\r\n" + 
        		"North America=5\r\n" + 
        		"South America=2\r\n" + 
        		"Africa=3\r\n" + 
        		"Europe=5\r\n" + 
        		"Asia=7\r\n" + 
        		"Australia=2\r\n" + 
        		"\r\n" + 
        		"[Territories]\r\n" + 
        		"Alaska,70,126,North America,Northwest Territory,Alberta,Kamchatka\r\n" + 
        		"Northwest Territory,148,127,North America,Alaska,Alberta,Ontario,Greenland\r\n" + 
        		"Alberta,147,163,North America,Alaska,Northwest Territory,Ontario,Western United States\r\n" + 
        		"Greenland,324,76,North America,Northwest Territory,Ontario,Quebec,Iceland\r\n" + 
        		"Ontario,199,167,North America,Northwest Territory,Alberta,Greenland,Quebec,Western United States,Eastern United States\r\n" + 
        		"Quebec,253,166,North America,Greenland,Ontario,Eastern United States\r\n" + 
        		"Western United States,159,204,North America,Alberta,Ontario,Eastern United States,Central America\r\n" + 
        		"Eastern United States,219,216,North America,Ontario,Quebec,Western United States,Central America\r\n" + 
        		"Central America,183,262,North America,Western United States,Eastern United States,Venezuala\r\n" + 
        		"\r\n" + 
        		"Venezuala,259,303,South America,Central America,Peru,Brazil\r\n" + 
        		"Peru,262,349,South America,Venezuala,Brazil,Argentina\r\n" + 
        		"Argentina,263,407,South America,Peru,Brazil\r\n" + 
        		"Brazil,308,337,South America,Venezuala,Peru,Argentina,North Africa\r\n" + 
        		"\r\n" + 
        		"North Africa,420,264,Africa,Brazil,Western Europe,Southern Europe,Egypt,East Africa,Congo\r\n" + 
        		"Congo,475,318,Africa,North Africa,East Africa,South Africa\r\n" + 
        		"South Africa,483,371,Africa,Congo,East Africa,Madagascar\r\n" + 
        		"Madagascar,536,361,Africa,East Africa,South Africa\r\n" + 
        		"East Africa,517,298,Africa,Egypt,Middle East,Congo,North Africa,South Africa,Madagascar\r\n" + 
        		"Egypt,480,249,Africa,Southern Europe,North Africa,Middle East,East Africa\r\n" + 
        		"\r\n" + 
        		"Iceland,380,126,Europe,Greenland,Great Britain,Scandinavia\r\n" + 
        		"Great Britain,419,169,Europe,Iceland,Scandinavia,Western Europe,Northern Europe\r\n" + 
        		"Western Europe,423,202,Europe,Great Britain,Northern Europe,Southern Europe,North Africa\r\n" + 
        		"Scandinavia,463,127,Europe,Iceland,Great Britain,Northern Europe,Ukraine\r\n" + 
        		"Northern Europe,460,173,Europe,Great Britain,Scandinavia,Ukraine,Southern Europe,Western Europe\r\n" + 
        		"Southern Europe,473,197,Europe,Northern Europe,Ukraine,Middle East,Egypt,North Africa,Western Europe\r\n" + 
        		"Ukraine,524,157,Europe,Ural,Afghanistan,Middle East,Southern Europe,Northern Europe,Scandinavia\r\n" + 
        		"\r\n" + 
        		"Middle East,530,234,Asia,Ukraine,Afghanistan,India,Southern Europe,Egypt,East Africa\r\n" + 
        		"Ural,613,136,Asia,Siberia,China,Afghanistan,Ukraine\r\n" + 
        		"Afghanistan,585,192,Asia,Ural,China,India,Middle East,Ukraine\r\n" + 
        		"India,612,249,Asia,China,Siam,Afghanistan,Middle East\r\n" + 
        		"Siberia,666,110,Asia,Yatusk,Irkutsk,Mongolia,China,Ural\r\n" + 
        		"China,662,217,Asia,Ural,Siberia,Mongolia,Siam,India,Afghanistan\r\n" + 
        		"Siam,671,270,Asia,China,India,Indonesia\r\n" + 
        		"Mongolia,707,188,Asia,Siberia,Irkutsk,Japan,China,Kamchatka\r\n" + 
        		"Irkutsk,698,152,Asia,Siberia,Yatusk,Kamchatka,Mongolia\r\n" + 
        		"Yatusk,738,118,Asia,Siberia,Irkutsk,Kamchatka\r\n" + 
        		"Kamchatka,806,125,Asia,Yatusk,Irkutsk,Japan,Alaska,Mongolia\r\n" + 
        		"Japan,759,220,Asia,Kamchatka,Mongolia\r\n" + 
        		"\r\n" + 
        		"Indonesia,698,314,Australia,Siam,New Guinea,Western Australia\r\n" + 
        		"New Guinea,768,325,Australia,Indonesia,Western Australia,Eastern Australia\r\n" + 
        		"Western Australia,729,373,Australia,Indonesia,New Guinea,Eastern Australia\r\n" + 
        		"Eastern Australia,779,381,Australia,Western Australia,New Guinea\r\n" + 
        		"\r\n" + 
        		"";
       
        
        //Set up TextArea
        final JTextField textField = new JTextField(8);
        textField.setFont(new Font(null, Font.PLAIN, 20));
        final JTextArea TerrTextField= new JTextArea(text,30,50);
        TerrTextField.setFont(new Font(null, Font.PLAIN, 16));
              
        JScrollPane js=new JScrollPane(TerrTextField);
        js.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        		js.setVerticalScrollBarPolicy(
        		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        TerrTextField.setLineWrap(false);
        //panel.add(TerrTextField);
        panel.add(js);
        //anel.add(textField);

        //Set up a button
        JButton btn = new JButton("Submit");
        btn.setFont(new Font(null, Font.PLAIN, 20));
        btn.addActionListener(new ActionListener() {
			/**
			 * action performed
			 * @param e paramter e
			 */
			@Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Submit: " + TerrTextField.getText());
                //Add write file here...save as text
            }
        });
        panel.add(btn);

        setContentPane(panel);
        setVisible(false);
    }
    
   

}
