package www.jeppesen.extractor;

import java.net.URISyntaxException;
import java.util.LinkedList;

public class Extractor {

	public static void main(String[] args) throws URISyntaxException {
		System.out.println();
		ExtractorRunner runner = new ExtractorRunner();
		LinkedList<Waypoint> extractedWaypointList = runner.compareCSV(runner.loadJAD(), runner.loadOPS());
		runner.printWPT(extractedWaypointList);
	}
}
