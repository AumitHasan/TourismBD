package com.apppreview.shuvojit.tourismbd.allpackges.infos;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LatLongInfo implements Serializable{
	private int _id;
	private String spotName,spotSnippet,spotType;
	private double latitudeVal,longtitudeVal;
	
	
	public LatLongInfo(int _id, String spotName, String spotSnippet,
			String spotType, double latitudeVal, double longtitudeVal) {
		
		this._id = _id;
		this.spotName = spotName;
		this.spotSnippet = spotSnippet;
		this.spotType = spotType;
		this.latitudeVal = latitudeVal;
		this.longtitudeVal = longtitudeVal;
	}


	public LatLongInfo(String spotName, String spotSnippet, String spotType,
			double latitudeVal, double longtitudeVal) {
		this.spotName = spotName;
		this.spotSnippet = spotSnippet;
		this.spotType = spotType;
		this.latitudeVal = latitudeVal;
		this.longtitudeVal = longtitudeVal;
	}


	public LatLongInfo(double latitudeVal, double longtitudeVal) {
		
		this.latitudeVal = latitudeVal;
		this.longtitudeVal = longtitudeVal;
	}


	public int get_id() {
		return _id;
	}


	public void set_id(int _id) {
		this._id = _id;
	}


	public String getSpotName() {
		return spotName;
	}


	public void setSpotName(String spotName) {
		this.spotName = spotName;
	}


	public String getSpotSnippet() {
		return spotSnippet;
	}


	public void setSpotSnippet(String spotSnippet) {
		this.spotSnippet = spotSnippet;
	}


	public String getSpotType() {
		return spotType;
	}


	public void setSpotType(String spotType) {
		this.spotType = spotType;
	}


	public double getLatitudeVal() {
		return latitudeVal;
	}


	public void setLatitudeVal(double latitudeVal) {
		this.latitudeVal = latitudeVal;
	}


	public double getLongtitudeVal() {
		return longtitudeVal; 
	}


	public void setLongtitudeVal(double longtitudeVal) {
		this.longtitudeVal = longtitudeVal;
	}
	
	
}
