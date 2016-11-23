package www.jeppesen.extractor;

public class Waypoint {
	private String country;
	private String country1;
	private String country2;
	private String ICAO;
	private String WPT_id;
	private String latitude;
	private String longitude;
	private String longxlati;

public Waypoint(String country, String country2, String ICAO, String WPT_id, String latitude, String longitude){
	this.country1=country;
	this.country2=country2;
	this.country=(country1.concat(",").concat(country2));
	this.ICAO=ICAO;
	this.WPT_id=WPT_id;
	this.latitude=latitude;
	this.longitude=longitude;
	this.longxlati=latitude.concat(longitude);
}

public Waypoint(String country, String ICAO, String WPT_id, String latitude, String longitude){
	this.country1=country;
	this.country2="";
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
}