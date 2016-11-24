package www.jeppesen.extractor;

import java.net.URISyntaxException;
import java.util.LinkedList;

/*
 * Used for running the ExtractorRunner and printing both: csv file
 * that contains Waipoint lists within which changes were done
 * i either WPT_id, Longitude or Latitude. After printing the results file
 * it prints also a raport of the whole process.
 */

public class Extractor {

	public static void main(String[] args) throws URISyntaxException {
		System.out.println();
		ExtractorRunner runner = new ExtractorRunner();
		LinkedList<Waypoint> extractedWaypointList = runner.compareCSV(runner.loadJAD(), runner.loadOPS());
		runner.createWPTcsv(extractedWaypointList);
		runner.printRaport();
	}
}