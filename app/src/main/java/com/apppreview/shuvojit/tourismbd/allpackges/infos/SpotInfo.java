package com.apppreview.shuvojit.tourismbd.allpackges.infos;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SpotInfo implements Serializable {

	private String spotName, locationInfo, discripInfo, famousInfo, hotelsInfo,
			urlInfo, spotTypeInfo, spotSnippet;
	private int id;

	public String getSpotTypeInfo() {
		return spotTypeInfo;
	}

	public void setSpotTypeInfo(String spotTypeInfo) {
		this.spotTypeInfo = spotTypeInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SpotInfo(int id, String spotName, String spotTypeInfo, String spotSnippet) {
		this.id = id;
		this.spotName = spotName;
		this.spotTypeInfo = spotTypeInfo;
        this.spotSnippet = spotSnippet;
	}

	public SpotInfo(String spotName, String locationInfo, String discripInfo,
			String famousInfo, String hotelsInfo, String urlInfo) {
		this.spotName = spotName;
		this.locationInfo = locationInfo;
		this.discripInfo = discripInfo;
		this.famousInfo = famousInfo;
		this.hotelsInfo = hotelsInfo;
		this.urlInfo = urlInfo;
	}

	public String getSpotName() {
		return spotName;
	}

	public void setSpotName(String spotName) {
		this.spotName = spotName;
	}

	public String getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(String locationInfo) {
		this.locationInfo = locationInfo;
	}

	public String getDiscripInfo() {
		return discripInfo;
	}

	public void setDiscripInfo(String discripInfo) {
		this.discripInfo = discripInfo;
	}

	public String getFamousInfo() {
		return famousInfo;
	}

	public void setFamousInfo(String famousInfo) {
		this.famousInfo = famousInfo;
	}

	public String getHotelsInfo() {
		return hotelsInfo;
	}

	public void setHotelsInfo(String hotelsInfo) {
		this.hotelsInfo = hotelsInfo;
	}

	public String getUrlInfo() {
		return urlInfo;
	}

	public void setUrlInfo(String urlInfo) {
		this.urlInfo = urlInfo;
	}

    public String getSpotSnippet() {
        return spotSnippet;
    }

    public void setSpotSnippet(String spotSnippet) {
        this.spotSnippet = spotSnippet;
    }
}
