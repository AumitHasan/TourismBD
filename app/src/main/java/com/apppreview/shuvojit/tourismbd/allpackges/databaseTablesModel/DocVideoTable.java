package com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.DatabaseClient;

import java.util.List;

/**
 * Created by shuvojit on 3/7/15.
 */

@Table(name = "doc_video")
public class DocVideoTable extends Model implements DatabaseClient {

    @Column(name = "doc_name")
    private String docName;

    @Column(name = "key")
    private String key;

    public DocVideoTable() {
        super();
    }

    public static List<DocVideoTable> getAllDocVideoTableModelDataList() {
        List<DocVideoTable> docVideoTableDataList = new Select()
                .from(DocVideoTable.class)
                .orderBy(DOC_NAME_FIELD + " ASC")
                .execute();
        return docVideoTableDataList;
    }


    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getKeyName() {
        return key;
    }

    public void setKeyName(String keyName) {
        this.key = keyName;
    }
}
