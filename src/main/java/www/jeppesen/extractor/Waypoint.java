package www.jeppesen.extractor;

/*
 * Regular Waypoint is a object representation of every line in cvs data bases:
 * JAD.csv, OpsData.csv and WPTData.csv
 */
public class Waypoint {
	private String country;
	private String ICAO;
	private String WPT_id;
	private String latitude;
	private String longitude;
	private String longxlati;

public Waypoint(String country, String country2, String ICAO, String WPT_id, String latitude, String longitude){
	this.country=(country.concat(",").concat(country2));
	this.ICAO=ICAO;
	this.WPT_id=WPT_id;
	this.latitude=latitude;
	this.longitude=longitude;
	this.longxlati=latitude.concat(longitude);
}

public Waypoint(String country, String ICAO, String WPT_id, String latitude, String longitude){
	this.country=country;
	this.ICAO=ICAO;
	this.WPT_id=WPT_id;
	this.latitude=latitude;
	this.longitude=longitude;
	this.longxlati=latitude.concat(longitude);
}

@Override
public String toString() {
	String waypointString = (country+","+ICAO+","+WPT_id+","+latitude+","+longitude);
	return waypointString;
}

public String getWPT_id() {
	return WPT_id;
}

public String getLongxlati() {
	return longxlati;
}
public String getCountry() {
	return country;
}
}