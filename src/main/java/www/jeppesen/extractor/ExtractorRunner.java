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

public class ExtractorRunner {
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
	private LinkedList<Waypoint> Pre_WPTData = new LinkedList<Waypoint>();
	private LinkedList<Waypoint> WPTData = new LinkedList<Waypoint>();

	
	LinkedList<Waypoint> loadJAD(){
		try{
			JADfile = new FileReader(JADcsv);
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
		return JADData;
	}
	
	LinkedList<Waypoint> loadOPS(){
		try{
			OPSfile = new FileReader(OPScsv);
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
	return OPSData;
	}

	LinkedList<Waypoint> compareCSV(LinkedList<Waypoint> JAD, LinkedList<Waypoint> OPS){
		int counter = 0;
		ListIterator<Waypoint> itOPS = OPS.listIterator();
		ListIterator<Waypoint> itJAD;

        A: while(itOPS.hasNext()){
            Waypoint OPSwaypoint = itOPS.next();
            itJAD = JAD.listIterator();
            while(itJAD.hasNext()){
                Waypoint JADwaypoint = itJAD.next();   
                if((OPSwaypoint.toString()).equals(JADwaypoint.toString())){
                    continue A;
                }else{
                    if(!itJAD.hasNext()){ // If there were no identical lines then...
                    	Pre_WPTData.add(OPSwaypoint);
                    }
                }
                counter++;
            }
        }
        return WPTVerify();
	}
	
	public LinkedList<Waypoint> WPTVerify(){
		ListIterator<Waypoint> itPreWPTData = Pre_WPTData.listIterator();
		ListIterator<Waypoint> itJAD;
		
		A: while(itPreWPTData.hasNext()){
            Waypoint OPSWaypoint = itPreWPTData.next();
            itJAD = JADData.listIterator();
            
            while(itJAD.hasNext()){
            	Waypoint JADWaypoint = itJAD.next();
            	if(OPSWaypoint.getLongxlati().equals(JADWaypoint.getLongxlati())&&OPSWaypoint.getWPT_id().equals(JADWaypoint.getWPT_id())){
            		continue A;
            	}
            }
    		WPTData.add(OPSWaypoint);
		}
		return WPTData;
	}
	public void printWPT(LinkedList<Waypoint> extractedWaypointList){
		String WPTString = "\\WPTData.csv";
		String WPTpath = jarParentPath.concat(WPTString);
		File WPTcsvFile = new File(WPTpath);
		try {
			PrintWriter WPTcsvWriter = new PrintWriter(WPTcsvFile);
			for(Waypoint waypoint:WPTData){
			WPTcsvWriter.println(waypoint.toString());
			}
			WPTcsvWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}