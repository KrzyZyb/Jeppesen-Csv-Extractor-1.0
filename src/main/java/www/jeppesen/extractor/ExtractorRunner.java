package www.jeppesen.extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

/*
 * Extractor Runner Reads searches and reads two CSV files (JAD.csv & OpsData.csv)
 * Every line in those files represents a waypoint. Extractor Runner scans this data
 * looking if there are some same waypoints in both files. If there are ExtractRunners 
 * searches for changes in those waypoints. Finally it creates a csv File which
 * contains a list of waypoints that are concurrent in both files, but have been
 * changed. A changed waypoint is a waypoint in which:
 * OPS WPT_id is not equal to JAD Wpt_id, but OPS longxlati is OPS equal to JAD longxlati
 * or
 * OPS WPT_id isequal to JAD Wpt_id, but OPS longxlati is not OPS equal to JAD longxlati
 */

public class ExtractorRunner {
	/*
	 * Constructor gets the path of the folder in wich the JAR file of the program
	 * is held. When the program is runned it searches for JAD.csv and OPSData.csv
	 * in the same path in which the JAR is held.
	 */
	public ExtractorRunner() throws URISyntaxException{
		File jarFilePath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		jarParentPath = jarFilePath.getParentFile().getPath();
		JADcsv = new File(jarParentPath.concat("\\JAD.csv"));
		OPScsv = new File(jarParentPath.concat("\\OpsData.csv"));
	}
	String jarParentPath;
	File JADcsv;
	File OPScsv;
	FileReader JADfile; 
	FileReader OPSfile; 
	
	private LinkedList<Waypoint> JADData = new LinkedList<Waypoint>();
	private LinkedList<Waypoint> OPSData = new LinkedList<Waypoint>();
	private LinkedList<Waypoint> WPTData = new LinkedList<Waypoint>();
	WaypointRaport raport = new WaypointRaport();
	
	/*
	 * Loads the JAD.csv file from the same directory in which JAR file is held
	 * Splits every line by "," and creates a LinkedList of Waypoint objects.
	 * @return LinkedList of Waypoint objects created from every JAD.csv line
	 */
	LinkedList<Waypoint> loadJAD(){
		try{
			JADfile = new FileReader(JADcsv);
			@SuppressWarnings("resource")
			Scanner JADscan = new Scanner(JADfile);
			
		while(JADscan.hasNextLine()){
			String JADline = JADscan.nextLine();
			String[] JADlineSplitter = JADline.split(",");
			if(JADlineSplitter.length==6){
			Waypoint JAD_Waypoint = new Waypoint(JADlineSplitter[0],JADlineSplitter[1],JADlineSplitter[2],JADlineSplitter[3],JADlineSplitter[4],JADlineSplitter[5]);
			JADData.add(JAD_Waypoint);
			}
			if(JADlineSplitter.length==5){
			Waypoint JAD_Waypoint = new Waypoint(JADlineSplitter[0],JADlineSplitter[1],JADlineSplitter[2],JADlineSplitter[3],JADlineSplitter[4]);
			JADData.add(JAD_Waypoint);
			}

		}
		}catch(NullPointerException e){
			System.out.println("Error: JAD.csv file is not available ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		raport.setJADLength(JADData.size()); //Adds the size of JAD.csv to raport
		return JADData;
	}
	
	/*
	 * Loads the OpsData.csv file from the same directory in which JAR file is held
	 * Splits every line by "," and creates a LinkedList of Waypoint objects.
	 * @return LinkedList of Waypoint objects created from every OpsData.csv line
	 */
	LinkedList<Waypoint> loadOPS(){
		try{
			OPSfile = new FileReader(OPScsv);
			@SuppressWarnings("resource")
			Scanner OPSscan = new Scanner(OPSfile);

		while(OPSscan.hasNextLine()){
			String OPSline = OPSscan.nextLine();
			String[] OPSlineSplitter = OPSline.split(",");
			if(OPSlineSplitter.length==6){
			Waypoint OPS_Waypoint = new Waypoint(OPSlineSplitter[0], OPSlineSplitter[1], OPSlineSplitter[2], OPSlineSplitter[3],OPSlineSplitter[4],OPSlineSplitter[5]);
			OPSData.add(OPS_Waypoint);
			}
			if(OPSlineSplitter.length==5){
				Waypoint OPS_Waypoint = new Waypoint(OPSlineSplitter[0], OPSlineSplitter[1], OPSlineSplitter[2], OPSlineSplitter[3],OPSlineSplitter[4]);
				OPSData.add(OPS_Waypoint);
			}
		}
		}catch(NullPointerException e){
			System.out.println("Error: OPS.csv file is not available ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	raport.setOPSLength(OPSData.size()); //Adds the size of OpsData.csv to raport
	return OPSData;
	}
	
	/*
	 * Compares two LinkedLists of Waypoint objects and creates a third Linked
	 * which is a result of comparsion. This method searches JAD collection for
	 * the same elements in JAD and OPS. An element is the same when it has the same
	 * WPT_id or longxlati.
	 * @param LinkedList<Waypoint>JAD, LinkedList<Waypoint>OPS, - bot are valid 
	 * collections of Waypoint objects.
	 * @return LinkedList that contains the same Waypoints found both in JAD and OPS
	 */
	LinkedList<Waypoint> compareCSV(LinkedList<Waypoint> JAD, LinkedList<Waypoint> OPS){
		int counter = 0; //Number of comparsions for the raport textfile
		int diffs = 0; //Number of differences found in comparsion
		ListIterator<Waypoint> itOPS = OPS.listIterator();
		ListIterator<Waypoint> itJAD;

        A: while(itOPS.hasNext()){
            Waypoint OPSwaypoint = itOPS.next();
            itJAD = JAD.listIterator();
            while(itJAD.hasNext()){
                Waypoint JADwaypoint = itJAD.next();   
                if((OPSwaypoint.toString()).equals(JADwaypoint.toString())){
                    continue A;
                }else if((OPSwaypoint.getWPT_id().equals(JADwaypoint.getWPT_id())&&!OPSwaypoint.getLongxlati().equals(JADwaypoint.getLongxlati()))){
                		if(OPSwaypoint.getCountry().equals(JADwaypoint.getCountry())){ //Do not mention if waypoints have same ID but are in different countries
                    	WPTData.add(OPSwaypoint);
                    	raport.Differences(JADwaypoint.toString(), OPSwaypoint.toString(), "Possible missmatch in coordinates.");
                    	diffs++;
                		}
                }else if((!OPSwaypoint.getWPT_id().equals(JADwaypoint.getWPT_id())&&OPSwaypoint.getLongxlati().equals(JADwaypoint.getLongxlati()))){
                		if(OPSwaypoint.getCountry().equals(JADwaypoint.getCountry())){ //Do not mention if waypoints have same ID but are in different countries
                    	WPTData.add(OPSwaypoint);
                    	raport.Differences(JADwaypoint.toString(), OPSwaypoint.toString(), "Possible missmatch in Waypoint ID's");
                    	diffs++;
                		}
                }
                counter++;
            }
        }
		// Comparing OPS and WPT for lacking Waypoints
		itOPS = OPS.listIterator();
		while(itOPS.hasNext()){
            Waypoint OPSwaypoint = itOPS.next();
            if(!JADDatacontains(OPSwaypoint)){
            	WPTData.add(OPSwaypoint);
            	raport.Differences("---", OPSwaypoint.toString(), "Waypoint not found");
            	diffs++;
            }
          counter++;
          }
		
		raport.setTests(counter); //Adds number of tests to raport
		raport.setChangesFound(diffs); //Adds number of tests to raport
        return WPTData;
	}
	
	/*
	 * Creates the csv file in the location in which  JAR file was runned
	 * the file positions are formatted just as the  required data csv files:
	 * JAD.csv and OpsData.csv
	 * @param 
	 */
	public void createWPTcsv(LinkedList<Waypoint> extractedWaypointList){
		String WPTString = "\\WPTData.csv";
		String WPTpath = jarParentPath.concat(WPTString);
		File WPTcsvFile = new File(WPTpath);
		try {
			PrintWriter WPTcsvWriter = new PrintWriter(WPTcsvFile);
			for(Waypoint waypoint:extractedWaypointList){
			WPTcsvWriter.println(waypoint.toString());
			}
			WPTcsvWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void printRaport(){
		raport.printRaport();
	}
	
	/*
	 * Checks the JADData for existence of particular waypoint. The purpouse is to find
	 * a Waypoint which exists in OPSData, but is not included in JADData
	 * @param waypoint w for which it is checked to exist in JADData
	 */
	public boolean JADDatacontains(Waypoint w){
		for(Waypoint wpt: JADData){
			if(wpt.toString().equals(w.toString())){
				return true;
			}else if((wpt.getWPT_id().equals(w.getWPT_id())&&!wpt.getLongxlati().equals(w.getLongxlati()))){
				return true;
			}else if((!wpt.getWPT_id().equals(w.getWPT_id())&&wpt.getLongxlati().equals(w.getLongxlati()))){
				return true;
			}else if(wpt.getLongxlati().equals(w.getLongxlati())){
				return true;
			}
		}
		return false;
		
	}
}