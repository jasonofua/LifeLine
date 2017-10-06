package com.example.root.lifeline.Activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.root.lifeline.Constant.IntentExtras;
import com.example.root.lifeline.R;
import com.example.root.lifeline.models.MyList;

public class ListDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MyList list = getIntent().getParcelableExtra(IntentExtras.ITEM_ID);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_listDetail);
        collapsingToolbarLayout.setTitle(list.getListName());
        collapsingToolbarLayout.setBackgroundResource(R.drawable.firstaid);


        TextView details = (TextView) findViewById(R.id.details);
        details.setText(list.getSteps());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
