package com.example.root.lifeline.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.root.lifeline.Constant.IntentExtras;
import com.example.root.lifeline.R;
import com.example.root.lifeline.models.FirstAid;

public class DefaultListDetail extends AppCompatActivity {

    Button goToHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FirstAid firstAid = getIntent().getParcelableExtra(IntentExtras.ITEM_ID);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_default);
        collapsingToolbarLayout.setBackgroundResource(firstAid.getFirstAidImage());
        collapsingToolbarLayout.setTitle(firstAid.getFirstAidName());

        goToHospital = (Button) findViewById(R.id.hospitalNearYou);

        goToHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openMap = new Intent(DefaultListDetail.this, HospitalsAroundYou.class);
                openMap.putExtra(IntentExtras.HOSPITAL,"hospital");
                openMap.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(openMap);
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
