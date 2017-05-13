package com.javedak09.databasebackup;

import android.provider.BaseColumns;

/**
 * Created by javed.khan on 5/11/2017.
 */

public class DBContract {

    private int id;
    private String nme;

    public int getId() {
        return id;
    }

    public String getnme() {
        return nme;
    }

    public void setId(int val1) {
        id = val1;
    }

    public void setnme(String val1) {
        nme = val1;
    }
}