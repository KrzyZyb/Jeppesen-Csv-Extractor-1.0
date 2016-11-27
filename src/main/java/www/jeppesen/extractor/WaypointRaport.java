package www.jeppesen.extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.time.Instant;
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
	public void printRaport(){
		Raporttxt = new File(jarParentPath.concat("\\WPTDataRaport.txt"));
		try{
			PrintWriter writer = new PrintWriter(Raporttxt);
			writer.println("****************************************");
			writer.println("Raport date: "+Instant.now());
			writer.println();
			writer.println("JAD Data Base size: " +JADLength);
			writer.println("OPS Data Base size: " +OPSLength);
			writer.println("Tests provided: " +Tests);
			writer.println("Differences found: " +changesFound);
			writer.println();
			writer.println("Possible changes in Waypoints: ");
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
