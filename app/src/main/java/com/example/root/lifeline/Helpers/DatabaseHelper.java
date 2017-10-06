package com.example.root.lifeline.Helpers;

import android.content.Context;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

public class DatabaseHelper {
    // database name must consist only of lowercase ASCII letters, digits, and the special characters _$()+-/.
    // It must also be less than 240 bytes and start with a lower case letter.
    public static final String OFFLINE_LIST_DATABASE = "offline_list_database";


    public static Database getDatabase(Context context, String databaseName){
        try {
            AndroidContext androidContext = new AndroidContext(context);
            Manager dbManager = new Manager(androidContext, Manager.DEFAULT_OPTIONS);
            return dbManager.getDatabase(databaseName);
        } catch (CouchbaseLiteException | IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Cannot get access to required database!", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


}
