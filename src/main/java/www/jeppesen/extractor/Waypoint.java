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
	
	/*
	 * Constructor for waypoints which country name is made of two parts (for example: Poland, republic of)
	 * @param country - String with first part of country name
	 * @param country2 - String with second part of coutry name
	 * @param ICAO - Operators code String
	 * @param WPT_id Particular Waypoint id, that is unique (can not be repeated in whole continent)
	 * @param latitude - String with latitude data
	 * @param longitude - String with longitude data
	 * @return a valid Waypoint object with two segments countryname
	 */

public Waypoint(String country, String country2, String ICAO, String WPT_id, String latitude, String longitude){
	this.country=(country.concat(",").concat(country2));
	this.ICAO=ICAO;
	this.WPT_id=WPT_id;
	this.latitude=latitude;
	this.longitude=longitude;
	this.longxlati=latitude.concat(longitude);
}

/*
 * Constructor for waypoints which country name is made of one part (for example: Russia)
 * @param country - String with first (and only) part of country name
 * @param ICAO - Operators code String
 * @param WPT_id Particular Waypoint id, that is unique (can not be repeated in whole continent)
 * @param latitude - String with latitude data
 * @param longitude - String with longitude data
 * @return a valid Waypoint object with two segments countryname
 */

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