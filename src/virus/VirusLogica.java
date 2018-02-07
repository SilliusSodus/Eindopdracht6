package virus;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.widgets.Text;

import readFile.ReadFile;

public class VirusLogica {
	/**
	 * virusLists, two lists of viruses for a certain host (and classification)
	 */
	private static ArrayList<Virus>[] virusLists = new ArrayList[]{new ArrayList<Virus>(),new ArrayList<Virus>()}; 

	/**
	 * opens a file
	 * @param filePath, file path to the virus file
	 */
	public static void openViruses(String filePath){
		try {
			if(filePath.length() > 0 ){
				ReadFile.readVirusFile(filePath,true);
			}
			else{
				ReadFile.readVirusFile(true);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			System.out.println("Could not find file.");
		}
	}
	
	/**
	 * Sorts The virus lists and displays them in the output.
	 * @param output, The three text areas in which the two virus lists are displayed and another in which the similarities between the former are shown.
	 */
	public static void SortedBy(Text[] output){
		for(int i =0; i< virusLists.length; i++){
			Collections.sort(virusLists[i]);
			output[i].setText(String.join("\n", getViruses(virusLists[i])));
		}
		output[2].setText(simLists());
	}
	
	/**
	 * Finds similarities between the two lists 
	 * @return a string with the found similarities
	 */
	public static String simLists(){
		ArrayList<String> retainedList = new ArrayList<String>();
		retainedList.addAll(getViruses(virusLists[0]));
		retainedList.retainAll(getViruses(virusLists[1]));
		return String.join("\n", retainedList);	
	}
	
	/**
	 * Gets the id's from the virus objects in a list
	 * @param viruses, list of viruses
	 * @return A list of the found id's as strings
	 */
	private static List<String> getViruses(List<Virus> viruses){
		ArrayList<String> ids = new ArrayList<String>();
		for(int i = 0; i < viruses.size(); i++){
			ids.add("" + viruses.get(i).getId());
		}
		return ids;
	}
	
	/**
	 * Generates a list of viruses for a certain host and potentially classification
	 * @param host, The id of the host for who relevant viruses should be found
	 * @param classificatie, The classification the viruses must be a part of
	 * @param listIndex, The list it should save its viruses to
	 */
	public static void hostToList(int host, String classificatie, int listIndex){
		virusLists[listIndex] = new ArrayList<Virus>();
		for(Virus v : Virus.getVirusList()){
			if(v.getHost() == host && (classificatie == "" || classificatie.equals(v.getClassificatie()))){
				virusLists[listIndex].add(v);
			}
		}
	}
}
