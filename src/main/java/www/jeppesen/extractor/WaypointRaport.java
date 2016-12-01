package www.jeppesen.extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;

/* 
 * WaypointRaports holds and prints the raport txt file from comparsion process of
 * JAD.csv and OpsData.csv. Raport text file appears in the same path the JAR was
 * runned
 */
public class WaypointRaport {
	public WaypointRaport() throws URISyntaxException{
		File jarFilePath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		jarParentPath = jarFilePath.getParentFile().getPath();
	}
	String jarParentPath;
	File Raporttxt;
	int OPSLength;
	int JADLength;
	int Tests;
	int changesFound;
	ArrayList<String> StringDiff = new ArrayList<String>();

	/*
	 * Takes the same waypoint from OPS and JAD and adds differences between them
	 * to the raport file
	 * @param JAD - valid string representation od JAD waypoint
	 * @param OPS - valid string representation od OPS waypoint
	 * @param Status - Info about differences between both waypoints
	 */

	void Differences(String JAD, String OPS, String Status){
		StringDiff.add("Status: "+Status);
		StringDiff.add("JAD: "+JAD);
		StringDiff.add("OPS: "+OPS);
		StringDiff.add("***");
	}
	public void setOPSLength(int OPSLength) {
		this.OPSLength = OPSLength;
	}
	public void setJADLength(int jADLength) {
		this.JADLength = jADLength;
	}
	public void setTests(int tests) {
		this.Tests = tests;
	}
	public void setChangesFound(int changes) {
		this.changesFound = changes;
	}
	
	/*
	 * Creates raport file that contains details about comparing process and differences
	 * between JAD and OPS versions of waypoints
	 */
	public void printRaport(){
		Raporttxt = new File(jarParentPath.concat("\\WPTDataRaport.txt"));
		try{
			PrintWriter writer = new PrintWriter(Raporttxt);
			writer.println("WPT DATA RAPORT:");
			writer.println("****************************************");
			writer.println("Comparsion process details:");
			writer.println("JAD Data Base size: " +JADLength);
			writer.println("OPS Data Base size: " +OPSLength);
			writer.println("Tests provided: " +Tests);
			writer.println("Differences found: " +changesFound);
			writer.println("****************************************");
			writer.println("Possible differences in JAD/OPS waypoint versions: ");
			writer.println();
			for(String line:StringDiff){
				writer.println(line);
			}
			writer.println("Status: Completed.");
			writer.println("****************************************");
			writer.close();
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
	}

}