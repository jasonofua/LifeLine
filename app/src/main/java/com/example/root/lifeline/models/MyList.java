package com.example.root.lifeline.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.example.root.lifeline.Helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyList extends BaseModel implements Parcelable {

    String id;
    String listName;
    String steps;

    public MyList() {
    }
    public static MyList fromDictionary(Object dictionary){
        return fromDictionary(dictionary,MyList.class);
    }

    public MyList(String listName, String steps) {
        this.listName = listName;
        this.steps = steps;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private MyList(Parcel in) {
        id= in.readString();
        listName= in.readString();
        steps = in.readString();
    }

    public static final Creator<MyList> CREATOR = new Creator<MyList>() {
        @Override
        public MyList createFromParcel(Parcel in) {
            return new MyList(in);
        }

        @Override
        public MyList[] newArray(int size) {
            return new MyList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(listName);
       parcel.writeString(steps);
    }

    public void saveToDatabase(final AppCompatActivity activity, final Database database){

        if (database == null)
        {
            Toast.makeText(activity, "Cannot to save to store. Database unavailable.", Toast.LENGTH_SHORT).show();
            return;
        }

        Document MyListDocument;
        Map<String, Object> properties;

        if (TextUtils.isEmpty(this.getId())){
            //new style
            MyListDocument  = database.createDocument();
            this.setId(MyListDocument.getId());
            properties = this.toDictionary();
        }
        else{
            MyListDocument = database.getDocument(this.getId());
            properties = new HashMap<>();
            properties.putAll(MyListDocument.getProperties());
            properties.putAll(this.toDictionary());
        }

        try {
            MyListDocument.putProperties(properties);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Failed to save to store. Fatal error occurred.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean delete(Context context) {
        Database ListItems = DatabaseHelper.getDatabase(context, DatabaseHelper.OFFLINE_LIST_DATABASE);

        if (ListItems != null){
            try{
                Query queryClothes = ListItems.createAllDocumentsQuery();
                queryClothes.setAllDocsMode(Query.AllDocsMode.ALL_DOCS); //ALL_DOCS by id, BY_SEQUENCE by last modified
                QueryEnumerator result = queryClothes.run();

                ArrayList<String> listItemsArray = new ArrayList<>();

                for (; result.hasNext(); ) {
                    QueryRow row = result.next();
                    MyList cloth = MyList.fromDictionary(row.getDocument().getProperties());

                    if (cloth.getId().equals(this.id))
                        listItemsArray.add(cloth.getId());
                }

                for (String listID : listItemsArray){
                    ListItems.getDocument(listID).delete();
                }
            }
            catch (CouchbaseLiteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
