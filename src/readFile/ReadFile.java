package readFile;
import java.io.*;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import virus.Virus;

public class ReadFile {
/**
 * inFile, the file to be read.
 */
    private static BufferedReader inFile;

    /**
     * Choose a file in a file browser
     * 
     * @param header, true if the first line in the file is supposed to be ignored.
     * @throws FileNotFoundException, In case a wrong file path is chosen.
     */
    public static void readVirusFile(boolean header) throws FileNotFoundException{
    	JFileChooser chooser = new JFileChooser();
    	int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	readVirusFile(chooser.getSelectedFile().getAbsolutePath(),header);
        	return;
        }
        throw new FileNotFoundException();
    }
    
    /**
     * Creates the list of viruses from a file
     * 
     * @param path, The path to the file
     * @param header, true if the first line of the file is supposed to be ignored
     * @throws FileNotFoundException, In case the path does not go to a file.
     */
    public static void readVirusFile(String path, boolean header) throws FileNotFoundException{
    	Virus.removeAll();
        try {
            String line;
            inFile = new BufferedReader(new FileReader(path));  
            if(header){
            	inFile.readLine();
            }
            while ((line = inFile.readLine()) != null) {
            	new Virus(line);
            }
            
            inFile.close();   
            return;
        } catch (FileNotFoundException fnfe) {
        	JOptionPane.showMessageDialog(null, "Bestand niet gevonden");
        } catch (IOException ioe) {
        	JOptionPane.showMessageDialog(null, "Kan niet lezen in bestand");
        } /*catch (Exception e) {
            System.out.println("Onbekende fout: geef op");
        }*/
        throw new FileNotFoundException();

    }
}