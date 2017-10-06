package com.example.root.lifeline.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.example.root.lifeline.Helpers.DatabaseHelper;
import com.example.root.lifeline.R;
import com.example.root.lifeline.adapters.ListAdapter;
import com.example.root.lifeline.models.MyList;

import java.util.ArrayList;

public class CustomListActivity extends AppCompatActivity {

   private ArrayList<MyList> myListArrayList;
   private RecyclerView listItem ;
   private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        database = DatabaseHelper.getDatabase(getApplicationContext(), DatabaseHelper.OFFLINE_LIST_DATABASE);
        fetchData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddItemCustom);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(CustomListActivity.this, AddItemActivity.class));

                final View customDialog = CustomListActivity.this.getLayoutInflater().inflate(R.layout.custom_add_item, null);

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CustomListActivity.this, R.style.MyDialogTheme);
                dialogBuilder.setView(customDialog);

                final EditText stepName = (EditText) customDialog.findViewById(R.id.list_name);
                final EditText steps = (EditText) customDialog.findViewById(R.id.listStep);


                dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String myListName = stepName.getText().toString().trim();
                        String myListStep = steps.getText().toString().trim();

                        if (TextUtils.isEmpty(myListName)||TextUtils.isEmpty(myListStep)){
                            Toast.makeText(CustomListActivity.this,"Please enter a Text",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // saving to student database
                        Database database = DatabaseHelper.getDatabase(getApplicationContext(), DatabaseHelper.OFFLINE_LIST_DATABASE);

                        MyList myList =new MyList();
                        myList.setListName(myListName);
                        myList.setSteps(myListStep);
                        myList.saveToDatabase(CustomListActivity.this,database);
                        Toast.makeText(CustomListActivity.this,"data Saved",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CustomListActivity.this,CustomListActivity.class));

                       dialog.dismiss();

                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialogBuilder.setCancelable(false);
                AlertDialog addCategoryDialog = dialogBuilder.create();
                addCategoryDialog.setTitle("Add new Item");
                addCategoryDialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData() {

        listItem = (RecyclerView) findViewById(R.id.rvCustom);
        listItem.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CustomListActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listItem.setLayoutManager(layoutManager);

        if (database == null)
            return;

        Query query = database.createAllDocumentsQuery();
        query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS); //ALL_DOCS by id, BY_SEQUENCE by last modified

        try {
            QueryEnumerator result = query.run();
            myListArrayList = new ArrayList<>();

            for (; result.hasNext(); ) {
                QueryRow row = result.next();
                MyList list = MyList.fromDictionary(row.getDocument().getProperties());
                myListArrayList.add(list);
            }
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            Toast.makeText(CustomListActivity.this, "Get customers info failed", Toast.LENGTH_SHORT).show();
        }

         ListAdapter adapter = new ListAdapter( myListArrayList,CustomListActivity.this);
        listItem.setAdapter(adapter);


    }



}
