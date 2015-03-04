package com.apppreview.shuvojit.tourismbd.allpackges.infos;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DocumentaryVideoInfo implements Serializable {

	private String docName;
	private String docKey;
	private int id;

	public DocumentaryVideoInfo(int id, String docName, String docKey) {

		this.id = id;
		this.docName = docName;
		this.docKey = docKey;

	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocKey() {
		return docKey;
	}

	public void setDocKey(String docKey) {
		this.docKey = docKey;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
