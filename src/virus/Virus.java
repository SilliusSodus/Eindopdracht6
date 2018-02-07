package virus;

import gui.VirusGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class Virus implements Comparable<Virus>{
	/**
	 * id, the virus ID
	 * host, the host ID
	 * soort, the name of the virus
	 * hostList, the list of potential hosts
	 * virusList, the list of all viruses in the file
	 * classList, the list of all classifications found in the file
	 * classificatie, the classification of the virus
	 * hostAmount, list of occurances of viruses
	 */
	private int id;
	private int host;
	private String soort;
	private static TreeMap<String, Integer> hostList = new TreeMap<String, Integer>();
	private static ArrayList<Virus>  virusList = new ArrayList<Virus>();
	private static HashSet<String> classList = new HashSet<String>();
	private static String classificatie;
	private static HashMap<Integer, Integer> hostAmount = new HashMap<Integer,Integer>();
	
	/**
	 * Translates a String found in a file to a Virus object.
	 * 
	 * @param virus, A row in the file that translates to a virus
	 */
	public Virus(String virus){
		String[] virusAtrbs = virus.split("\\t");
		try{
			this.id = Integer.parseInt(virusAtrbs[0]);
			if(hostAmount.containsKey(id)){
				hostAmount.put(id, hostAmount.get(id)+1);
			}
			else{
				hostAmount.put(id, 1);
			}
			this.classificatie = "";
			this.soort = virusAtrbs[1];
			this.classificatie = virusAtrbs[2].split(";")[1];
			classList.add(classificatie);
			this.host = Integer.parseInt(virusAtrbs[7]);
			if(!hostList.containsKey(virusAtrbs[7] + " (" + virusAtrbs[8] + ")")){
				hostList.put(virusAtrbs[7] + " (" + virusAtrbs[8] + ")",Integer.parseInt(virusAtrbs[7]));
			}
			virusList.add(this);
		} catch(ArrayIndexOutOfBoundsException err){
			virusList.add(this);
			//System.out.println("" + virusAtrbs[0] + " Row does not contain all necessary info. Info has been added as present.");
		} catch(NumberFormatException err){
			System.out.println("Row is corrupt. Has not been added to list.");
		}
		
	}
	
	/**
	 * Resets all static lists to load another file.
	 */
	public static void removeAll(){
		hostList = new TreeMap<String, Integer>();
		virusList = new ArrayList<Virus>();
		classList = new HashSet<String>();
		hostAmount = new HashMap<Integer,Integer>();
		
		classList.add("");
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static HashSet<String> getClassList() {
		return classList;
	}

	public int getHost() {
		return host;
	}
	public void setHost(int host) {
		this.host = host;
	}
	public String getSoort() {
		return soort;
	}
	public void setSoort(String soort) {
		this.soort = soort;
	}
	public static TreeMap<String, Integer> getHostList() {
		return hostList;
	}

	public static ArrayList<Virus> getVirusList() {
		return virusList;
	}
	public String getClassificatie() {
		return classificatie;
	}
	public void setClassificatie(String classificatie) {
		this.classificatie = classificatie;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Virus v) {
		switch(VirusGUI.getSortMethod()){
			case "Id":
				return Integer.compare(this.id,v.getId());
			case "Classification":
				return this.getClassificatie().compareTo(v.getClassificatie());
			case "Host amount":
				return Integer.compare(Virus.hostAmount.get(v.getId()),Virus.hostAmount.get(this.getId()));
			default:
				return -1;
		}
	}
}
