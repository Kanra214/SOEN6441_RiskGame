package Game;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * <h1>FileChooser</h1>
 * This class is for choose lap for the game
 */
public class FileChooser {

	public String filename,filepath;
	public boolean ChooseFile() {
		JFileChooser jfc = new JFileChooser(".");

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			filename=selectedFile.getName();
			filepath=selectedFile.getAbsolutePath();
		}
		return true;
	}
	
	
	
}
