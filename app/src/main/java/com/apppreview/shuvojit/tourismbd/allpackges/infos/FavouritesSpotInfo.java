package com.apppreview.shuvojit.tourismbd.allpackges.infos;

public class FavouritesSpotInfo {

	private int id;
	private String spotName;
	private String spotTypeInfo;

	public FavouritesSpotInfo(int id, String spotName, String spotTypeInfo) {

		this.id = id;
		this.spotName = spotName;
		this.spotTypeInfo = spotTypeInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSpotName() {
		return spotName;
	}

	public void setSpotName(String spotName) {
		this.spotName = spotName;
	}

	public String getSpotTypeInfo() {
		return spotTypeInfo;
	}

	public void setSpotTypeInfo(String spotTypeInfo) {
		this.spotTypeInfo = spotTypeInfo;
	}
	
	

}
